package databean;

import org.genericdao.PrimaryKey;

@PrimaryKey("fundId")
public class FundBean {

	private int fundId;
	private String name;
	private String symbol;

	private String price;
	
	public FundBean() {}
	public FundBean(String name, String symbol, String price) {
		this.name = name;
		this.symbol = symbol;
		this.price = price;
	}
<<<<<<< HEAD
	public String getName() 			{return name;}
	public String getSymbol() 			{return symbol;}
	public String getInitial_value() 	{return initial_value;}
	public int getFundId() 				{return fundId;}
=======
	
	public int getFundId() 			{return fundId;}
	public String getName() 		{return name;}
	public String getSymbol() 		{return symbol;}
	public String getPrice() 	{return price;}
>>>>>>> f068bbe80e771a4b5316061be42dea4bfb7521fd
	
	public void setName(String name) 					{this.name = name;}
	public void setSymbol(String symbol) 				{this.symbol = symbol;}
	public void setPrice(String price) 					{this.price =  price;}
	public void setFundId(int fundId) 					{this.fundId = fundId;}

	
	
	
	
	
	

}
