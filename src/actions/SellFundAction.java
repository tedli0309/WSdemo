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
import formbean.SellForm;
import init.*;

@Path("/sellFund")
public class SellFundAction {
	@Context 
	HttpServletRequest request;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.APPLICATION_JSON)
	public ObjectNode buyFund(SellForm sellForm) {
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
		
		if(sellForm.getValidationErrors().size() > 0) {
			root.put("message", "The input you provided is not valid");
			return root;
		}
		
		try {
			Transaction.begin();
			//ConnectionPool pool = new ConnectionPool("com.mysql.jdbc.Driver", "jdbc:mysql:///test?useSSL=false");
			TransactionDAO transactionDAO = Model.getTransactionDAO();
			PositionDAO positionDAO = Model.getPositionDAO();
			UserDAO userDAO = Model.getUserDAO();
			FundDAO fundDAO = Model.getFundDAO();
			
			
			
			UserBean customer = (UserBean)session.getAttribute("customer");
			UserBean user = userDAO.read(customer.getUserId());
			
			FundBean[] res =  fundDAO.match(MatchArg.equals("symbol",sellForm.getSymbol()));
			if (res.length == 0)  {
				root.put("message", "The input you provided is not valid");
				return root;
			}
			
			if(res[0] == null) {
				root.put("message", "The input you provided is not valid");
				return root;
			}
			FundBean fund = res[0];
	
			int share = Integer.parseInt(sellForm.getNumShare());			
			double price = Double.parseDouble(fund.getPrice());
			PositionBean position = positionDAO.getPosition(user.getUserId(), fund.getFundId());
			if(position == null) {
				root.put("message", "You don't have that many shares in your portfolio");
				return root;
			}
			
			if(position.getShares() < share) {
				root.put("message", "You don't have that many shares in your portfolio");
				return root;
			}
	
			position.setShares(position.getShares() - share);
			user.setCash(user.getCash() + share * price);
			
			positionDAO.update(position);
			if(position.getShares() == 0) {
				if(positionDAO.read(position.getUserId(), position.getFundId()) != null)
					positionDAO.delete( position.getFundId(), position.getUserId());
			}
			userDAO.update(user);
			//transactionDAO.create(new TransactionBean(user.getUserId(), fund.getFundId(), System.currentTimeMillis(), amount/price ,"buy",amount));
			Transaction.commit();
			root.put("message", "The shares have been successfully sold");
			return root;
		}  catch (RollbackException e) {
			e.printStackTrace();
		} finally {
			if (Transaction.isActive()) Transaction.rollback();
		}
		
		root.put("message", "exception");
		return root;
	}
}
