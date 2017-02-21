package actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import model.FundDAO;
import model.PositionDAO;
import model.TransactionDAO;
import model.UserDAO;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import databean.FundBean;
import databean.PositionBean;
import databean.TransactionBean;
import databean.UserBean;
import formbean.BuyForm;
import init.*;
@Path("/buyFund")
public class BuyFundAction {
	@Context 
	HttpServletRequest request;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.APPLICATION_JSON)
	public ObjectNode buyFund(BuyForm buyForm) {
		ObjectNode root =  new ObjectMapper().createObjectNode();
		
		HttpSession session = request.getSession();
		if( session.getAttribute("employee") != null) {
			root.put("message", "You must be a customer to perform this action");
			return root;
		}
				
		if(session.getAttribute("customer") == null){
			root.put("message", "You are not currently logged in");
			return root;
		}
		
		if(buyForm.getValidationErrors().size() > 0) {
			root.put("message", "The input you provided is not valid");
			return root;
		}
		
		try {
			//ConnectionPool pool = new ConnectionPool("com.mysql.jdbc.Driver", "jdbc:mysql:///test?useSSL=false");
			//TransactionDAO transactionDAO = new TransactionDAO(pool, "task8_transaction");
			Transaction.begin();
			PositionDAO positionDAO = Model.getPositionDAO();
			UserDAO userDAO = Model.getUserDAO();
			FundDAO fundDAO = Model.getFundDAO();
			
			
			
			UserBean customer = (UserBean)session.getAttribute("customer");
			UserBean user = userDAO.read(customer.getUserId());
			FundBean[] res =  fundDAO.match(MatchArg.equals("symbol",buyForm.getSymbol()));
			if (res.length == 0)  {
				root.put("message", "The input you provided is not valid");
				return root;
			}
			
			if(res[0] == null) {
				root.put("message", "The input you provided is not valid");
				return root;
			}
			FundBean fund = res[0];
			double amount = Double.parseDouble(buyForm.getCashValue());			
			double balance = user.getCash();
			double price = Double.parseDouble(fund.getPrice());
			
			if(balance < amount){
				root.put("message", "You don't have enough cash in your account to make this purchase");
				return root;
			}	
			if(price > amount){
				root.put("message", "You didn't provide enough cash to make this purchase");
				return root;
			}
			
			PositionBean position = positionDAO.getPosition(user.getUserId(), fund.getFundId());
			if(position == null) {
				position = new PositionBean(user.getUserId(), fund.getFundId(), 0);
				positionDAO.create(position);
			}
			
			int share = (int)(amount/price);
			user.setCash(balance - share * price);			
			position.setShares(position.getShares() +  share);
			
			positionDAO.update(position);
			userDAO.update(user);
			//transactionDAO.create(new TransactionBean(user.getUserId(), fund.getFundId(), System.currentTimeMillis(), amount/price ,"buy",amount));
			Transaction.commit();
			root.put("message", "The fund has been successfully purchased");
			return root;
		}  catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (Transaction.isActive()) Transaction.rollback();
		}
		
		//root.put("message", "exception");
		return root;
	}
}
