package actions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
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
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import databean.FundBean;
import databean.OwnerFundsBean;
import databean.UserBean;
import model.FundDAO;
import model.FundPositionViewDAO;

@Path("/viewPortfolio")
public class ViewPortfolio {
	@Context 
	HttpServletRequest request;
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.APPLICATION_JSON)
	public ObjectNode viewPortfolio() throws DAOException, RollbackException {
		System.out.println("entered the createCustomerAccount");
		HttpSession session = request.getSession();
		ObjectMapper mapper = new ObjectMapper();
	    ObjectNode root = mapper.createObjectNode();  
		if (session.getAttribute("customer") == null && session.getAttribute("employee") == null) {
			 root.put("message", "You are not currently logged in");
			 return root;
		} else if (session.getAttribute("customer") == null) {
			 root.put("message", "You must be a customer to perform this action");
			 return root;
		}
		
		UserBean user = (UserBean) session.getAttribute("customer");
		
		ConnectionPool pool = new ConnectionPool("com.mysql.jdbc.Driver", "jdbc:mysql:///test?useSSL=false");
		FundPositionViewDAO fundPositionViewDAO = new FundPositionViewDAO(pool);
		
		OwnerFundsBean[] fundsOfOwener2 = fundPositionViewDAO.getFundPosition();
		List<OwnerFundsBean> ans2= new ArrayList<>();
        for(OwnerFundsBean fo: fundsOfOwener2) {
        	 if(fo.getUserId() == user.getUserId())
        		 ans2.add(fo);
        }
		
        if (ans2.size() == 0) {
        	root.put("message", "You don't have any funds in your Portfolio");
        	return root;
        }
        root.put("message", "The action was successful");
		root.put("cash", "" + user.getCash());
		
		ArrayNode fundsNode = mapper.createArrayNode();
        for (OwnerFundsBean oneFund : ans2) {
			ObjectNode curFund = mapper.createObjectNode();
			curFund.put("name", oneFund.getName());
			curFund.put("shares", oneFund.getShares());
			curFund.put("price", oneFund.getPrice());
			fundsNode.add(curFund);			 
			
        }
        root.set("funds", fundsNode);
		
		return root;
	}
}
