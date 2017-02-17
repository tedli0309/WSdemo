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
import model.*;

@Path("/transitionDay")
public class TransitionDayAction {
	@Context 
	HttpServletRequest request;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.APPLICATION_JSON)
	public ObjectNode createFund(CreateFundForm fundForm){
		try{
			ObjectMapper mapper = new ObjectMapper();
	        ObjectNode root = mapper.createObjectNode();
	        
	        ConnectionPool pool = new ConnectionPool("com.mysql.jdbc.Driver", "jdbc:mysql:///test?useSSL=false");
			FundDAO fundDAO  = new FundDAO(pool, "task8_fund");
			TransactionDAO transactionDAO = new TransactionDAO(pool, "task8_transaction");
			double price,seed,value=0;
			Random rand = new Random();
			Transaction.begin();
			HttpSession session = request.getSession();
			if (session.getAttribute("employee") == null) {
				if(session.getAttribute("customer") != null) {
					root.put("Message", "You must be an employee to perform this action");
				} else {
					root.put("Message", "You are not currently logged in");
				}
				return root;
			}
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
			root.put("Message", "The fund prices have been successfully recalculated");
			Transaction.commit();
			return root;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(Transaction.isActive())Transaction.rollback();
		}
        
	}
}
