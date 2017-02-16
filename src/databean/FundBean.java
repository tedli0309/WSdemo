package databean;

import org.genericdao.PrimaryKey;

@PrimaryKey("fundId")
public class FundBean {
	private int fundId;
	private String name;
	private String symbol;
	private double price;
	
	public FundBean() {
		
	}
	
	public FundBean(String name, String symbol, double price){
		this.name = name;
		this.symbol = symbol;
		this.price = price;
	}
	
	public int getFundId() 			{return fundId;}
	public String getName() 		{return name;}
	public String getSymbol() 		{return symbol;}
	public double getPrice()		{return price;}
	
	public void setFundId(int i)		{fundId = i;}
	public void setName(String s) 		{name = s;}
	public void setSymbol(String s)  	{symbol = s;}
	public void setPrice(double d)		{price = d;}
}
