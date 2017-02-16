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
import model.UserDAO;

@Path("/requestCheck")
public class RequestCheckAction {
	@Context 
	HttpServletRequest request;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.APPLICATION_JSON)
	public ObjectNode requestCheck(RequestCheckForm checkForm) throws DAOException, RollbackException {
		ConnectionPool pool = new ConnectionPool("com.mysql.jdbc.Driver", "jdbc:mysql:///test?useSSL=false");
		UserDAO userDAO  = new UserDAO(pool, "task8_user");

		HttpSession session = request.getSession();
		ObjectMapper mapper = new ObjectMapper();
        ObjectNode root = mapper.createObjectNode();  
	    if (session.getAttribute("customer") == null) {
			if(session.getAttribute("employee") != null) {
				root.put("Message", "You must be a customer to perform this action");
			} else {
				root.put("Message", "You are not currently logged in");
			}
			return root;
		}
	    
	    List<String> errors = checkForm.getValidationErrors();
	 
		for(String s :errors){
			System.out.println(s);
		}
		
		if(errors.size() !=0) {
			root.put("Message", "The input you provided is not valid");
			return root;
		}
		
		
		UserBean customer = (UserBean) session.getAttribute("customer");
		Double requestAmount = Double.parseDouble(checkForm.getCashValue());
		try {
			Transaction.begin();
			double currentCash = customer.getCash();
			if (currentCash < requestAmount) {
				root.put("Message", "You don't have sufficient funds in your account to cover the requested check");
				return root;
			}
			
			double updateCash = currentCash - requestAmount;
			customer.setCash(updateCash);
			userDAO.update(customer);
			
			root.put("Message", "The check whas been successfully requested");
			Transaction.commit();
			return root;
		}catch (RollbackException e) {
			root.put("Message", "You don't have sufficient funds in your account to cover the requested check");
			return root;
		}finally {
			if (Transaction.isActive()) Transaction.rollback();
		}
	}
	
}



