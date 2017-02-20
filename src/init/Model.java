/**
 *  Author : 	Zebangli 
 *  AndrewId :  zebangl
 *  Date   :	2016.12.5
 *  Course :	08672
 */
package init;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.RollbackException;

//import databean.FavoriteBean;
import databean.UserBean;
import model.FundDAO;
import model.FundPositionViewDAO;
import model.PositionDAO;
import model.TransactionDAO;
import model.UserDAO;

public class Model {
	 
	private static  UserDAO userDAO;
	private FundDAO fundDAO;
	//private EmployeeDAO employeeDAO;
	private TransactionDAO  transactionDAO; 
	private PositionDAO positionDAO;

	//private FundPriceHistoryDAO fundPriceHistoryDAO;

	private FundPositionViewDAO fundPositionViewDAO;
	//private TransactionHistoryViewDAO transactionHistoryViewDAO;
	public Model() throws ServletException {
	//public Model(ServletConfig config) throws ServletException {
		try {
			/*
			String jdbcDriver = config.getInitParameter("jdbcDriverName");
			String jdbcURL    = config.getInitParameter("jdbcURL");
			ConnectionPool pool = new ConnectionPool(jdbcDriver,jdbcURL);
			*/
			ConnectionPool pool = new ConnectionPool("com.mysql.jdbc.Driver", "jdbc:mysql:///test?useSSL=false");
			userDAO  = new UserDAO(pool, "task8_user");
			fundDAO  = new FundDAO(pool, "task8_fund");
			positionDAO = new PositionDAO(pool, "task8_position");
			fundPositionViewDAO = new FundPositionViewDAO(pool);
			transactionDAO   = new TransactionDAO (pool, "task8_transaction");
			//employeeDAO  = new EmployeeDAO(pool, "task7_employee");
			//fundPriceHistoryDAO = new FundPriceHistoryDAO(pool, "task7_fundpricehistory"); 
			//transactionHistoryViewDAO = new TransactionHistoryViewDAO(pool);
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
	public static UserDAO getUserDAO()  { return userDAO; }
//	public static FundDAO getFundDAO()  { return fundDAO; }
//	public static TransactionDAO getTransactionDAO()  { return transactionDAO; }
	//public EmployeeDAO getEmployeeDAO()  { return employeeDAO; }
//	public static PositionDAO getPositionDAO()  { return positionDAO; }

	//public FundPriceHistoryDAO getFundPriceHistoryDAO()  { return fundPriceHistoryDAO;}
//	public static FundPositionViewDAO getFundPostionViewDAO() { return fundPositionViewDAO;}
	//public TransactionHistoryViewDAO getTransactionHistoryViewDAO() {	 
	//	return transactionHistoryViewDAO;
	//}

}
