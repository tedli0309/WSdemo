
package databean;

import org.genericdao.PrimaryKey;

@PrimaryKey("userId")
public class UserBean {
	private int userId;
	//private String email;
	private String lastName;
	private String firstName;
	private String userName;
	private String addr1;
	private String addr2;
	private String city;
	private String state;
	private String zip;
	private double cash =0.0;
	private String  hashedPassword;
	
	public UserBean() {}
	public UserBean(String email, String lastName, String firstName, String password, String userName, String addr1, String addr2, String city, String state, String zip) {
		//this.email = email;
		this.lastName = lastName;
		this.firstName = firstName;
		this.hashedPassword = password;
		this.userName = userName;
		this.addr1 = addr1;
		this.addr2 = addr2;
		this.city = city;
		this.state = state;
		this.zip = zip;
		
	}
 
	public String getHashedPassword() 		{return hashedPassword;}
	public int getUserId() 			    {return userId;}
	public String getLastName() 		{return lastName;}
	public String getFirstName() 		{return firstName;}
	public String getUserName() 		{return userName;}
	public String getAddr1() 		{return addr1;}
	public String getAddr2() 		{return addr2;}
	public String getCity() 		{return city;}
	public String getState() 		{return state;}
	public String getZip() 		    {return zip;}
	public double getCash() 		{return cash;}
	
	public void setHashedPassword(String hashedPassword) 	{this.hashedPassword = hashedPassword;}
	public void setUserId(int i)		{userId = i;}
	public void setLastName(String s) 	{lastName = s;}
	public void setFirstName(String s)  {firstName = s;}
	public void setUserName(String s) 	{userName = s;}
	public void setAddr1(String s) 	{addr1 = s;}
	public void setAddr2(String s) 	{addr2 = s;}
	public void setCity(String s) 	{city = s;}
	public void setState(String s) 	{state = s;}
	public void setZip(String s) 	{zip = s;}
	public void setCash(double d) 	{cash = d;}
		
	public String toString() {
		return getLastName()+ ", "+ getFirstName();
	}
	public String toAddressString() {
		return  getAddr1() +  getAddr2() ;
	}
 
}

