package actions;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import init.Model;
import model.FundDAO;
import model.FundPositionViewDAO;
import model.PositionDAO;
import model.UserDAO;

@Path("/viewPortfolio")
public class ViewPortfolio {
	@Context 
	HttpServletRequest request;
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.APPLICATION_JSON)
	public ObjectNode viewPortfolio() {
		System.out.println("entered the viewPortfolio");
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
		
		//ConnectionPool pool = new ConnectionPool("com.mysql.jdbc.Driver", "jdbc:mysql:///test?useSSL=false");
		//PositionDAO positionDAO = Model.getPositionDAO();
		
		OwnerFundsBean[] fundsOfOwener2;
		
		try {
			Transaction.begin();
			FundPositionViewDAO fundPositionViewDAO = Model.getFundPostionViewDAO();
			UserDAO userDAO = Model.getUserDAO();
			user = userDAO.read(user.getUserId());
			fundsOfOwener2 = fundPositionViewDAO.getFundPosition();			
			Transaction.commit();
			List<OwnerFundsBean> ans2= new ArrayList<>();
	        for(OwnerFundsBean fo: fundsOfOwener2) {
	        	System.out.println(fo.toString());
	        	 if(fo.getUserId() == user.getUserId())
	        		 ans2.add(fo);
	        }			
	        if (ans2.size() == 0) {
	        	root.put("message", "You don't have any funds in your Portfolio");
	        	return root;
	        }
	        DecimalFormat df = new DecimalFormat("##0.00");
	        root.put("message", "The action was successful");
			root.put("cash", "" + df.format(user.getCash()));
			
			ArrayNode fundsNode = mapper.createArrayNode();
			
			
	        for (OwnerFundsBean oneFund : ans2) {
				ObjectNode curFund = mapper.createObjectNode();
				curFund.put("name", oneFund.getName());
				curFund.put("shares", Integer.toString(oneFund.getShares()));
				String curPrice = oneFund.getPrice();
				 
				double priceFormat = Double.parseDouble(curPrice);
				String output = df.format(priceFormat);
				curFund.put("price", output);
				fundsNode.add(curFund);			 
	        }
	        root.set("funds", fundsNode);
			
			return root;
		}  catch (RollbackException e2){
			System.out.println("transaction error message");			
		} finally {
			if (Transaction.isActive()) Transaction.rollback();
		}
		return null;	
	}
}
