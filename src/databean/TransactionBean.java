package databean;


import java.util.Date;

import org.genericdao.PrimaryKey;

import databean.FundBean;
import model.FundDAO;
//import model.Model;;
@PrimaryKey("transactionId")
public class TransactionBean {
	private int transactionId;
	private int userId;
	private int fundId;
	private String executeDate;
	private double shares ;
	private String transactionType;
	private double amount ;
	
	public TransactionBean(){
		
	}
//	public TransactionBean(int userId, int fundId, String executeDate,  double shares, String transactionType, double amount) {
//		this.userId = userId;
//		this.fundId = fundId;
//		this.executeDate = executeDate;
//		this.shares = shares;
//		this.transactionType = transactionType;
//		this.amount = amount;
//	}
	public TransactionBean(int userId2, int fundId2, long currentTimeMillis,
			double share, String type, double amount2) {
		this.userId = userId2;
		this.fundId = fundId2;
		this.executeDate = new Date(currentTimeMillis).toString();
		this.shares = share;
		this.transactionType = type;
		this.amount = amount2;
	}
	public int getTransactionId()       	{return transactionId;}
	public int getUserId()       			{return userId;}
	public int getFundId()       			{return fundId;}
	public String getExecuteDate()       	{return executeDate;}
	public double getShares()       		{return shares;}
	public String getTransactionType()      {return transactionType;}
	public double getAmount()       		{return amount;}
	
	public void setTransactionId(int i)       	{transactionId = i;}
	public void setUserId(int i)       			{userId = i;}
	public void setFundId(int i)       			{fundId = i;}
	public void setExecuteDate(String da)       {executeDate = da;}
	public void setShares(double d)       		{shares = d;}
	public void setTransactionType(String s)    {transactionType =s;}
	public void setAmount(double d)       		{amount = d;}
	
	
}
