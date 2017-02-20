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

import databean.FundBean;
import databean.UserBean;
import formbean.CreateFundForm;
import formbean.LoginForm;
import init.Model;
import model.FundDAO;

@Path("/createFund")
public class CreateFundAction {

	@Context 
	HttpServletRequest request;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.APPLICATION_JSON)
	public ObjectNode createFund(CreateFundForm fundForm)  {
		try{
			//ConnectionPool pool = new ConnectionPool("com.mysql.jdbc.Driver", "jdbc:mysql:///test?useSSL=false");
			//FundDAO fundDAO  = new FundDAO(pool, "task8_fund");
	        FundDAO fundDAO = Model.getFundDAO();
		
		Transaction.begin();
		HttpSession session = request.getSession();
		ObjectMapper mapper = new ObjectMapper();
        ObjectNode root = mapper.createObjectNode();
		if (session.getAttribute("employee") == null) {
			if(session.getAttribute("customer") != null) {
				root.put("message", "You must be an employee to perform this action");
			} else {
				root.put("message", "You are not currently logged in");
			}
			return root;
		}
		
		System.out.println(fundForm.getName());
		
		List<String> errors = fundForm.getValidationErrors();
		FundBean[] fund = fundDAO.match(MatchArg.equals("symbol", fundForm.getSymbol())); 
		if(fund.length != 0) {
			errors.add("Already exists");
		}
		
		for(String s :errors){
			System.out.println(s);
		}
		
		if(errors.size() !=0) {
			root.put("message", "The input you provided is not valid");
			return root;
		}
		FundBean bean = new FundBean(fundForm.getName(), fundForm.getSymbol(), fundForm.getInitial_value());
		fundDAO.create(bean);
		
		root.put("message", "Fund successfully created");
		
		Transaction.commit();
		return root;
	}catch(RollbackException e){
		e.printStackTrace();
		return null;
	}finally{
		if(Transaction.isActive())Transaction.rollback();
	}
		
		
	}
}
