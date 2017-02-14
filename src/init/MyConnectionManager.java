package init;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;

import model.UserDAO;

public class MyConnectionManager implements ServletContextListener {
	public UserDAO userDAO;
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("byebye");
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("hello, xiaoyue");
		// TODO Auto-generated method stub
		ConnectionPool pool = new ConnectionPool("com.mysql.jdbc.Driver", "jdbc:mysql:///test?useSSL=false");
		try {
			userDAO  = new UserDAO(pool, "task8_user");
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public UserDAO getUserDAO()  { return userDAO; }
}
