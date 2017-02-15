package databean;

import org.genericdao.PrimaryKey;

@PrimaryKey("fundId")
public class FundBean {
	
	private int fundId;
	private String name;
	private String symbol;
	private String initial_value;
	
	public FundBean() {}
	public FundBean(String name, String symbol, String initial_value) {
		this.name = name;
		this.symbol = symbol;
		this.initial_value = initial_value;
	}
	public String getName() 		{return name;}
	public String getSymbol() 		{return symbol;}
	public String getInitial_value() 	{return initial_value;}
	public int getFundId() 			{return fundId;}
	
	public void getName(String name) 					{this.name = name;}
	public void getSymbol(String symbol) 				{this.symbol = symbol;}
	public void getInitial_value(String initial_value) 	{this.initial_value =  initial_value;}
	public void getFundId(int fundId) 					{this.fundId = fundId;}
	
	
}
