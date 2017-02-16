package databean;

import org.genericdao.PrimaryKey;

@PrimaryKey("fundId,userId")
public class PositionBean {
	private int userId;
	private int fundId;
	private double shares;
	

	public PositionBean(){
		
	}
	public PositionBean(int userId,int fundId,double shares){
		this.userId = userId;
		this.fundId = fundId;
		this.shares = shares;
	}
	
	public int getUserId() 			{return userId;}
	public int getFundId() 			{return fundId;}
	public double getShares() 		{return shares;}
	
	public void setUserId(int i) 		{userId = i;}
	public void setFundId(int i)		{fundId = i;}
	public void setShares(double d)  	{shares = d;}
}
