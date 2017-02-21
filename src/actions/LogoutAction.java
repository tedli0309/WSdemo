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

import org.genericdao.DAOException;
import org.genericdao.RollbackException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;


@Path("/logout")
public class LogoutAction {

	@Context 
	HttpServletRequest request;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.APPLICATION_JSON)
	public ObjectNode logout()  {
		HttpSession session = request.getSession();
		ObjectMapper mapper = new ObjectMapper();
	    ObjectNode root = mapper.createObjectNode();
	    try{
			if (session.getAttribute("customer") != null || session.getAttribute("employee") != null) {
				 root.put("message", "You have been successfully logged out");
			} else {
				root.put("message", "You are not currently logged in");
			}
			session.invalidate();
			return root; 
	    } catch(Exception e) {
	    	root.put("message", "You are not currently logged in");
	    	return root;
	    }
	}
}

