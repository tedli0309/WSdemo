package model;
import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import databean.FundBean;
public class FundDAO extends GenericDAO<FundBean>{
	
	public FundDAO(ConnectionPool connectionPool, String tableName) throws DAOException {
		super(FundBean.class, tableName, connectionPool);
	}
	
}
