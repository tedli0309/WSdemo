package model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericViewDAO;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import databean.OwnerFundsBean;;
public class FundPositionViewDAO extends GenericViewDAO<OwnerFundsBean>{
	public FundPositionViewDAO(ConnectionPool connectionPool) throws DAOException {
		super(OwnerFundsBean.class, connectionPool);
	}
	public OwnerFundsBean[] getFundPosition() throws RollbackException{
		try {
			Transaction.begin();
			String sql = "select task8_fund.fundId as fundId, task8_fund.symbol as symbol, task8_fund.name as name, "
			         + "task8_position.shares as shares,  task8_fund.price as price , task8_position.userId as userId "
			         + "from task8_fund , task8_position "
			         + "where task8_fund.fundId = task8_position.fundId";
			OwnerFundsBean[] res = executeQuery(sql);

			Transaction.commit();
			return res;
		} finally {
			if (Transaction.isActive()) Transaction.rollback();
		}
	}
}
