package actions;

import java.util.List;

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

import databean.UserBean;
import formbean.RequestCheckForm;
import init.Model;
import model.UserDAO;

@Path("/requestCheck")
public class RequestCheckAction {
	@Context 
	HttpServletRequest request;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.APPLICATION_JSON)
	public ObjectNode requestCheck(RequestCheckForm checkForm) throws DAOException, RollbackException {
		//ConnectionPool pool = new ConnectionPool("com.mysql.jdbc.Driver", "jdbc:mysql:///test?useSSL=false");
		UserDAO userDAO  = Model.getUserDAO();

		HttpSession session = request.getSession();
		ObjectMapper mapper = new ObjectMapper();
        ObjectNode root = mapper.createObjectNode();  
	    if (session.getAttribute("customer") == null) {
			if(session.getAttribute("employee") != null) {
				root.put("message", "You must be a customer to perform this action");
			} else {
				root.put("message", "You are not currently logged in");
			}
			return root;
		}
	    
	    List<String> errors = checkForm.getValidationErrors();
	 
		for(String s :errors){
			System.out.println(s);
		}
		
		if(errors.size() !=0) {
			root.put("message", "The input you provided is not valid");
			return root;
		}
		
		
		UserBean customer = (UserBean) session.getAttribute("customer");
		Double requestAmount = Double.parseDouble(checkForm.getCashValue());
		
		try {
			Transaction.begin();
			UserBean user = userDAO.read(customer.getUserId());
			double currentCash = user.getCash();
			if (currentCash < requestAmount) {
				root.put("message", "You don't have sufficient funds in your account to cover the requested check");
				return root;
			}
			double updateCash = currentCash - requestAmount;
			
			user.setCash(updateCash);
			userDAO.update(user);
			
			root.put("message", "The check has been successfully requested");
			Transaction.commit();
			return root;
		}catch (RollbackException e) {
			//root.put("message", "You don't have sufficient funds in your account to cover the requested check");
			return null;
		}finally {
			if (Transaction.isActive()) Transaction.rollback();
		}
	}
	
}



