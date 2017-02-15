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
import org.genericdao.RollbackException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import databean.UserBean;
import formbean.LoginForm;
import init.MyConnectionManager;
import model.UserDAO;

@Path("/login")
public class LoginAction {

	@Context 
	HttpServletRequest request;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.APPLICATION_JSON)
	public ObjectNode login(LoginForm loginForm) throws DAOException, RollbackException {
		HttpSession session = request.getSession();
		if (session.getAttribute("customer") != null || session.getAttribute("employee") != null) {
			 session.invalidate();
		} 
		System.out.println(loginForm.toString());
		ConnectionPool pool = new ConnectionPool("com.mysql.jdbc.Driver", "jdbc:mysql:///test?useSSL=false");
		UserDAO userDAO  = new UserDAO(pool, "task8_user");
		
		List<String> errors = loginForm.getValidationErrors();
		
		UserBean user = userDAO.read(loginForm.getUserName());      
        if (user == null) {
            errors.add("User not found");
        } else	if (!user.getPassword().equals(loginForm.getPassword())){
            errors.add("Incorrect password");
        }	
        
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode root = mapper.createObjectNode();  
        if (errors.size() == 0) {
        	root.put("Message", user.getLastName());
        	session.setAttribute("customer", user);
        	session.setMaxInactiveInterval(15 * 60); //Specifies the time, in seconds, between client requests before the servlet
        											 //container will invalidate this session.
        } else {
        	
        	for (String error : errors)  System.out.println(error); 
        	root.put("Message", "There seems to be an issue with the username/password combination that you entered");
        }
        return root;
		 
	}
}
