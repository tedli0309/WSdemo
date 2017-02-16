package databean;

public class OwnerFundsBean {
	private int fundId;
	private String symbol;
	private String name;
	private double shares = 0.0;
	private double price;
	private int userId = 0;
	public OwnerFundsBean(){
		 
	}
	public int getFundId() {return fundId;}
	public String getSymbol()		{return symbol;}
	public String getName()			{return name;}
	public double getShares()		{return shares;}
	public double getPrice()		{return price;}
	public int getUserId()       	{return userId;}
	
	public void setFundId(int i)         {fundId = i;}
	public void setSymbol(String s)		{symbol = s;}
	public void setName(String s)		{name = s;}
	public void setShares(double d)		{shares = d;}
	public void setPrice(double d)		{price = d;}
	public void setUserId(int i)       	{userId = i;}
	
	public double toAmount(){
		return shares*price;
	}
}
