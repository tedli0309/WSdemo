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
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import databean.UserBean;
import formbean.DepositCheckForm;
import model.UserDAO;

@Path("/depositCheck")
public class DepositCheckAction {
	@Context 
	HttpServletRequest request;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.APPLICATION_JSON)
	public ObjectNode depositeCheck(DepositCheckForm checkForm) throws DAOException, RollbackException {
		
		ConnectionPool pool = new ConnectionPool("com.mysql.jdbc.Driver", "jdbc:mysql:///test?useSSL=false");
		UserDAO userDAO  = new UserDAO(pool, "task8_user");

		HttpSession session = request.getSession();
		ObjectMapper mapper = new ObjectMapper();
        ObjectNode root = mapper.createObjectNode();  
	    if (session.getAttribute("employee") == null) {
			if(session.getAttribute("customer") != null) {
				root.put("Message", "You must be a employee to perform this action");
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
		
		
		UserBean[] userList =  userDAO.match((MatchArg.equals("userName",checkForm.getUserName())));
		if(userList.length == 0) {
			root.put("Message", "The input you provided is not valid");
			return root;
		}
		try {
			Transaction.begin();
			UserBean customer = userList[0];
			double currentCash = customer.getCash();
			double updateCash = currentCash + Double.parseDouble(checkForm.getCheckAmount());
			customer.setCash(updateCash);
			userDAO.update(customer);
			
			root.put("Message", "The check was successfully deposited");
			Transaction.commit();
			return root;
		}catch (RollbackException e) {
			System.out.print(e.getMessage());
			root.put("Message", "The input you provided is not valid");
			return root;
		}finally {
			if (Transaction.isActive()) Transaction.rollback();
		}
		
	}
	
}



