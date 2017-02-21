package actions;

import java.util.Arrays;
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
import org.genericdao.DuplicateKeyException;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import databean.UserBean;
import formbean.CustomerRegisterForm;
import model.UserDAO;
import init.*;
@Path("/createCustomerAccount")
public class CreateCustomerAccountAction{
	@Context 
	HttpServletRequest request;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.APPLICATION_JSON)
	public ObjectNode createAccount(CustomerRegisterForm form) {
		System.out.println("entered the createCustomerAccount");
		HttpSession session = request.getSession();
		ObjectMapper mapper = new ObjectMapper();
	    ObjectNode root = mapper.createObjectNode();  
		if (session.getAttribute("customer") == null && session.getAttribute("employee") == null) {
			 root.put("message", "You are not currently logged in");
			 return root;
		} else if (session.getAttribute("employee") == null) {
			 root.put("message", "You must be an employee to perform this action");
			 return root;
		}
		
		List<String> errors =  form.getValidationErrors();
		System.out.println(errors);
		if (errors.size() != 0) {
			root.put("message", "The input you provided is not valid");
			return root;
		}

		UserBean newUser = new UserBean();

        newUser.setUserName(form.getUsername());
        newUser.setFirstName(form.getFname());
        newUser.setLastName(form.getLname());
        newUser.setAddress(form.getAddress());
        newUser.setCity(form.getCity());
        newUser.setState(form.getState());
        newUser.setZip(form.getZip());
        newUser.setPassword(form.getPassword());
        newUser.setEmail(form.getEmail());
        
        newUser.setCash(Double.parseDouble(form.getCash()));
        try {
    		//ConnectionPool pool = new ConnectionPool("com.mysql.jdbc.Driver", "jdbc:mysql:///test?useSSL=false");
    		UserDAO userDAO  = Model.getUserDAO();
    		Transaction.begin();
    		
    		UserBean[] u =	userDAO.match(MatchArg.equals("userName",newUser.getUserName()));
			if (u.length > 0) throw new DuplicateKeyException("this UserName has been used!");
            userDAO.create(newUser);            
                      
            Transaction.commit();
            root.put("message", newUser.getFirstName() + " was registered successfully");

            return root;
        } catch (DuplicateKeyException e) {
        	root.put("message", "The input you provided is not valid");
        } catch (RollbackException e) {
        	System.out.println("roll back exception for create customer!");
        	root.put("message", "The input you provided is not valid");
		}
        return root;

	}
}
