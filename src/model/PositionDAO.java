package model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import databean.PositionBean;

public class PositionDAO extends GenericDAO<PositionBean> {
	public PositionDAO(ConnectionPool connectionPool, String tableName) throws DAOException {
		super(PositionBean.class, tableName, connectionPool);
	}
	public void create(PositionBean position) throws RollbackException {		
//		try {
//			Transaction.begin();
			super.create(position);
//			Transaction.commit();
//		} finally {
//			if (Transaction.isActive()) Transaction.rollback();
//		}
	}
	public PositionBean getPosition(int userId, int fundId) throws RollbackException {
		//try	{
			//Transaction.begin();
			PositionBean[] res =  match(MatchArg.and(MatchArg.equals("userId", userId), MatchArg.equals("fundId", fundId)));

			if (res.length == 0)  return null;

			//Transaction.commit();
			return res[0];
		//} finally {
			//if (Transaction.isActive()) Transaction.rollback();
		//}
	}
	public PositionBean[] getAllPositions(int userId) throws RollbackException {
		try	{
			Transaction.begin();
			PositionBean[] res =  match(MatchArg.equals("userId", userId));
			if (res.length == 0)  return null;
			Transaction.commit();
			return res;
		} finally {
			if (Transaction.isActive()) Transaction.rollback();
		}
	}
}
