package actions;

import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
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
import formbean.LoginForm;
import init.Model;
import init.MyConnectionManager;
import model.UserDAO;

@Path("/login")
public class LoginAction {

	@Context 
	HttpServletRequest request;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.APPLICATION_JSON)
	public ObjectNode login(LoginForm loginForm) throws DAOException{
		HttpSession session = request.getSession();
		session.setAttribute("customer", null); 
		session.setAttribute("employee", null);  
		System.out.println(loginForm.toString());
		//ConnectionPool pool = new ConnectionPool("com.mysql.jdbc.Driver", "jdbc:mysql:///test?useSSL=false");
		//UserDAO userDAO  = new UserDAO(pool, "task8_user");
		try {
			
		
			UserDAO userDAO = Model.getUserDAO();
			ObjectMapper mapper = new ObjectMapper();
		    ObjectNode root = mapper.createObjectNode();  
			List<String> errors = loginForm.getValidationErrors();
			if (errors.size() != 0)	{
				root.put("message", "There seems to be an issue with the username/password combination that you entered");
				return root;
			}
			// this is for the login of the admin
			if (loginForm.getUserName().equals("jadmin")) {
					if (loginForm.getPassword().equals("admin")) {
						session.setAttribute("employee", "admin");
			        	session.setMaxInactiveInterval(15 * 60);
			        	root.put("message", "Welcome Jane");
					} else {
						root.put("message", "There seems to be an issue with the username/password combination that you entered");
					}
					return root;
			}
			
			Transaction.begin();
			UserBean[] res =  userDAO.match(MatchArg.equals("userName",loginForm.getUserName()));
			//if (res.length == 0)  return null;
	        if (res.length == 0) {
	            errors.add("User not found");
	            root.put("message", "There seems to be an issue with the username/password combination that you entered");
	            Transaction.commit();
	            return root;
	        } else	if (!res[0].getPassword().equals(loginForm.getPassword())){
	            errors.add("Incorrect password");
	            root.put("message", "There seems to be an issue with the username/password combination that you entered");
	            Transaction.commit();
	            return root;
	        }	
	        UserBean user = res[0];
	       
	        if (errors.size() == 0) {
	        	root.put("message", "Welcome " + user.getFirstName());
	        	session.setAttribute("customer", user);
	        	session.setMaxInactiveInterval(15 * 60); //Specifies the time, in seconds, between client requests before the servlet
	        											 //container will invalidate this session.
	        } else {
	        	for (String error : errors)  System.out.println(error); 
	        	root.put("message", "There seems to be an issue with the username/password combination that you entered");
	        }
	        Transaction.commit();
	        return root;
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(Transaction.isActive())Transaction.rollback();
		}
		return null;
	}
}
