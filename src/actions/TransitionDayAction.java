package actions;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import databean.FundBean;
import formbean.CreateFundForm;
import model.FundDAO;
//import model.*;
import init.*;

@Path("/transitionDay")
public class TransitionDayAction {
	@Context 
	HttpServletRequest request;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.APPLICATION_JSON)
	public ObjectNode createFund(CreateFundForm fundForm){
		ObjectMapper mapper = new ObjectMapper();
        ObjectNode root = mapper.createObjectNode();
		try{
			
	        
	        //ConnectionPool pool = new ConnectionPool("com.mysql.jdbc.Driver", "jdbc:mysql:///test?useSSL=false");
			//FundDAO fundDAO  = new FundDAO(pool, "task8_fund");
	        
			double price,seed,value=0;
			Random rand = new Random();
			
			HttpSession session = request.getSession();
			if (session.getAttribute("employee") == null) {
				if(session.getAttribute("customer") != null) {
					root.put("message", "You must be an employee to perform this action");
				} else {
					root.put("message", "You are not currently logged in");
				}
				return root;
			}
			Transaction.begin();
	        FundDAO fundDAO = Model.getFundDAO();
				FundBean[] funds = fundDAO.match();
			for(FundBean fund:funds) {
				value = 0;
				price = Double.parseDouble(fund.getPrice());
				while(value <= 0) {
					seed = rand.nextDouble();
					seed = (seed*20) -10;
					value = price*seed/100 + price;
				}
				price = value;
				fund.setPrice(Double.toString(price));
				fundDAO.update(fund);
			}
			root.put("message", "The fund prices have been successfully recalculated");
			Transaction.commit();
			return root;
		}catch(Exception e) {
			e.printStackTrace();
			root.put("message", "The input you provided is not valid");
			return root;
		}finally{
			if(Transaction.isActive())Transaction.rollback();
		}
        
	}
}
