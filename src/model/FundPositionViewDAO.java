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
			String sql = "select task7_fund.fundId as fundId, task7_fund.symbol as symbol, task7_fund.name as name, "
			         + "task7_position.shares as shares,  task7_fund.price as price , task7_position.userId as userId "
			         + "from task7_fund , task7_position "
			         + "where task7_fund.fundId = task7_position.fundId";
			OwnerFundsBean[] res = executeQuery(sql);

			Transaction.commit();
			return res;
		} finally {
			if (Transaction.isActive()) Transaction.rollback();
		}
	}
}
