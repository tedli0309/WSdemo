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
<<<<<<< HEAD


	
	
	
	
=======
	public FundBean getFund(String name) throws RollbackException {
		try	{
			Transaction.begin();
			FundBean[] res =  match(MatchArg.equals("name",name));
			if (res.length == 0)  return null;
			Transaction.commit();
			return res[0];
		} finally {
			if (Transaction.isActive()) Transaction.rollback();
		}
	}
	
	public FundBean getFundBySymbol(String symbol) throws RollbackException {
		//try	{
			//.begin();
			FundBean[] res =  match(MatchArg.equals("symbol",symbol));
			if (res.length == 0)  return null;
			//Transaction.commit();
			return res[0];
		//} finally {
			//if (Transaction.isActive()) Transaction.rollback();
		//}
	}
	public void create(FundBean fund) throws RollbackException {		
		try {
			Transaction.begin();
			super.create(fund);
			Transaction.commit();
		} finally {
			if (Transaction.isActive()) Transaction.rollback();
		}
	}
	
	public FundBean[] getFunds() throws RollbackException {
		try{
			FundBean[] fundList = null;
			Transaction.begin();
			fundList = match();
			Transaction .commit();
			return fundList;
		} finally {
			
			if(Transaction.isActive()) {
				System.out.println("match");
				Transaction.rollback();
			}
		}
	}
	public FundBean getFundById(int fundId) {
		// TODO Auto-generated method stub
		return null;
	}
>>>>>>> f068bbe80e771a4b5316061be42dea4bfb7521fd
}
