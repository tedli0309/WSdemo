
package databean;

import org.genericdao.PrimaryKey;

@PrimaryKey("userId")
public class UserBean {
	private int userId;
	//private String email;
	private String lastName;
	private String firstName;
	private String userName;
	private String address;
	private String city;
	private String state;
	private String zip;
	private double cash =0.0;
	private String password;
	private String email;
	
	public UserBean() {}
	public UserBean(String email, String lastName, String firstName,
			String password, String userName, String address,String city, String state, String zip) {
		this.email = email;
		this.lastName = lastName;
		this.firstName = firstName;
		this.password = password;
		this.userName = userName;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}
 
	public String getPassword() 		{return password;}
	public int getUserId() 			    {return userId;}
	public String getLastName() 		{return lastName;}
	public String getFirstName() 		{return firstName;}
	public String getUserName() 		{return userName;}
	public String getAddress() 		{return address;}
	public String getCity() 		{return city;}
	public String getState() 		{return state;}
	public String getZip() 		    {return zip;}
	public double getCash() 		{return cash;}
	public String getEmail() 		{return email;}
	
	public void setPassword(String password) 	{this.password = password;}
	public void setUserId(int i)		{userId = i;}
	public void setLastName(String s) 	{lastName = s;}
	public void setFirstName(String s)  {firstName = s;}
	public void setUserName(String s) 	{userName = s;}
	public void setAddress(String s) 	{address = s;}
	public void setCity(String s) 	{city = s;}
	public void setState(String s) 	{state = s;}
	public void setZip(String s) 	{zip = s;}
	public void setCash(double d) 	{cash = d;}
	public void setEmail(String s) 	{email = s;}
	public String toString() {
		return getLastName()+ ", "+ getFirstName();
	}
 
}

