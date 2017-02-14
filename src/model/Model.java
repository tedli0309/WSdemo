package model;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.RollbackException;

import databean.UserBean;

public class Model {
	 
	private UserDAO userDAO;
//	private FundDAO fundDAO;
//	private EmployeeDAO employeeDAO;
//	private TransactionDAO  transactionDAO; 
//	private PositionDAO positionDAO;
//
//	private FundPriceHistoryDAO fundPriceHistoryDAO;
//
//	private FundPositionViewDAO fundPositionViewDAO;
//	private TransactionHistoryViewDAO transactionHistoryViewDAO;
	
	public Model(ServletConfig config) throws ServletException {
		try {
			/*
			String jdbcDriver = config.getInitParameter("jdbcDriverName");
			String jdbcURL    = config.getInitParameter("jdbcURL");
			ConnectionPool pool = new ConnectionPool(jdbcDriver,jdbcURL);
			*/
			ConnectionPool pool = new ConnectionPool("com.mysql.jdbc.Driver", "jdbc:mysql:///test?useSSL=false");
			userDAO  = new UserDAO(pool, "task8_user");
//			fundDAO  = new FundDAO(pool, "task7_fund");
//			positionDAO = new PositionDAO(pool, "task7_position");
//			fundPositionViewDAO = new FundPositionViewDAO(pool);
//			transactionDAO   = new TransactionDAO (pool, "task7_transaction");
//			employeeDAO  = new EmployeeDAO(pool, "task7_employee");
//			fundPriceHistoryDAO = new FundPriceHistoryDAO(pool, "task7_fundpricehistory"); 
//			transactionHistoryViewDAO = new TransactionHistoryViewDAO(pool);
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
	public UserDAO getUserDAO()  { return userDAO; }
//	public FundDAO getFundDAO()  { return fundDAO; }
//	public TransactionDAO getTransactionDAO()  { return transactionDAO; }
//	public EmployeeDAO getEmployeeDAO()  { return employeeDAO; }
//	public PositionDAO getPositionDAO()  { return positionDAO; }
//
//	public FundPriceHistoryDAO getFundPriceHistoryDAO()  { return fundPriceHistoryDAO;}
//	public FundPositionViewDAO getFundPostionViewDAO() { return fundPositionViewDAO;}
//	public TransactionHistoryViewDAO getTransactionHistoryViewDAO() {	 
//		return transactionHistoryViewDAO;
//	}

}
