package actions;

import javax.servlet.http.HttpServletRequest;
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
			double price,seed;
			
			FundBean[] funds = fundDAO.match();
			for(FundBean fund:funds) {
				price = Double.parseDouble(fund.getPrice());
				seed = Math.random();
			}
			return root;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(Transaction.isActive())Transaction.rollback();
		}
        
	}
}
