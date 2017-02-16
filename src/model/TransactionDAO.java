package model;

import java.util.ArrayList;
import java.util.List;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import databean.TransactionBean;

public class TransactionDAO extends GenericDAO<TransactionBean> {

	public TransactionDAO(ConnectionPool connectionPool, String tableName) throws DAOException {
		super(TransactionBean.class, tableName, connectionPool);
	}
	
	public TransactionBean[] getTransactions(int userId) throws RollbackException {
		try	{
			Transaction.begin();
			TransactionBean[] res =  match(MatchArg.equals("userId",userId));
			if (res.length == 0)  return null;
			Transaction.commit();
			return res;
		} finally {
			if (Transaction.isActive()) Transaction.rollback();
		}
	}
	
	public void create(TransactionBean transaction) throws RollbackException {		
		//try {
			//Transaction.begin();
			super.create(transaction);
			//Transaction.commit();
		

	}
    
    public double currentBalace(int userId, double ans) throws RollbackException {
    	TransactionBean[] transactions;
    	try {
			Transaction.begin();
			transactions = match(MatchArg.equals("userId",userId));
			Transaction.commit();
		} finally {
			if (Transaction.isActive()) Transaction.rollback();
		}
        for(TransactionBean transaction:transactions) {
        	if(transaction.getExecuteDate() != null) {
        		continue;
        	}
        	if(transaction.getTransactionType().equals("buy")) {
        		ans -= transaction.getAmount();
        	}
        	else if(transaction.getTransactionType().equals("deposit")) {
        		ans += transaction.getAmount();
        	}
        	else if(transaction.getTransactionType().equals("request")){
        		ans -= transaction.getAmount();
        	}
        }
        return ans;
    }
    public TransactionBean[] getTransactions() throws RollbackException{
    	TransactionBean[] transactions;
    	try {
			Transaction.begin();
			transactions = match(MatchArg.and(MatchArg.equals("executeDate",null),MatchArg.equals("transactionType","sell")));
			Transaction.commit();
			return transactions;
		} finally {
			if (Transaction.isActive()) Transaction.rollback();
		}
    }
    public int[] getCustomerIds() throws RollbackException {
    	int[] ans;
    	TransactionBean[] transactions;
    	try {
    		Transaction.begin();
    		transactions = match(MatchArg.equals("executeDate",null));
    		Transaction.commit();
    	} finally {
			if (Transaction.isActive()) Transaction.rollback();
		}
    	ans = new int[transactions.length];
    	int i=0;
    	for(TransactionBean transaction: transactions) {
    		ans[i] = transaction.getUserId();
    		i++;
    	}
    	return ans;
    }
    public int[] getCustomerUniqueIds() throws RollbackException {
    	//int[] ans;
    	TransactionBean[] transactions;
    	try {
    		Transaction.begin();
    		transactions = match(MatchArg.equals("executeDate",null));
    		Transaction.commit();
    	} finally {
			if (Transaction.isActive()) Transaction.rollback();
		}
    	List<Integer> ans = new ArrayList<>();
    	
    	for(TransactionBean transaction: transactions) {
    		if(!ans.contains(transaction.getUserId()))
    			ans.add(transaction.getUserId());
    		
    	}
    	int[] array = new int[ans.size()];
    	for(int i = 0; i < ans.size(); i++) array[i] = ans.get(i);
    	return array;
    }
    public TransactionBean[] getPendingTransactions() throws RollbackException {
    	
    	TransactionBean[] transactions;
    	try {
    		Transaction.begin();
    		transactions = match(MatchArg.equals("executeDate",null));
    		Transaction.commit();
    		return transactions;
    	} finally {
			if (Transaction.isActive()) Transaction.rollback();
    	}
    }
}