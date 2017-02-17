package formbean;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.mybeans.form.FormBean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerRegisterForm extends FormBean{
	@JsonProperty
	private String fname;
	@JsonProperty
	private String lname;
	@JsonProperty
	private String address;
	@JsonProperty
	private String city;
	@JsonProperty
	private String state;
	@JsonProperty
	private String zip;
	@JsonProperty
	private String email;
	@JsonProperty
	private String cash;
	@JsonProperty
	private String username;
	@JsonProperty
	private String password;
	
	public void setUserName(String userName) {
		this.username =  trimAndConvert(userName,"<>\"");
	}
	public void setFirstName(String firstName) {
		this.fname = trimAndConvert(firstName,"<>\"");
	}
	public void setLastName(String lastName) {
		this.lname = trimAndConvert(lastName,"<>\"");
	}
	public void setAddress(String s) {
		this.address = trimAndConvert(s,"<>\"");
	}

	public void setCity(String city) {
		this.city = trimAndConvert(city,"<>\"");
	}
	public void setState(String state) {
		this.state = trimAndConvert(state,"<>\"");
	}
	public void setZip(String zip) {
		this.zip = trimAndConvert(zip,"<>\"");
	}
	public void setPassword(String password) {
		this.password = password.trim();
	}
	public void setEmail(String s) {
		this.email = s.trim();
	}
	public void setCash(String s) {
		this.cash = s;
	}
	
	public String getUsername() {
		return username;
	}
	public String getFname() {
		return fname;
	}
	public String getLname() {
		return lname;
	}
	public String getAddress() {
		return address;
	}
	public String getCity() {
		return city;
	}
	public String getState() {
		return state;
	}
	public String getZip() {
		return zip;
	}
	public String getPassword() {
		return password;
	}
	public String getEmail() {
		return email;
	}
	public String getCash() {
		return cash;
	}
	public List<String> getValidationErrors() {
		//System.out.println(lastName.length());
		List<String> errors = new ArrayList<String>();
		if (username == null || username.length() == 0) {
			errors.add("Email is required!");
		}
		if (username.equals("jadmin")) {
			errors.add("can't register as admin!");
		}
		if (lname == null || lname.length() == 0) {
			errors.add("LastName is required!");
		}
		if (fname == null || fname.length() == 0) {
			errors.add("FirstName is required!");
		}
		if (password == null || password.length() == 0) {
			errors.add("password is required!");
		}
		if (email == null || email.length() == 0) {
			errors.add("email is required");
		}
		if (zip == null || zip.length() == 0) {
			errors.add("zip is required");
		}
		if (address == null || address.length() == 0) {
			errors.add("address is required");
		}
		if (state == null || state.length() == 0) {
			errors.add("state is required");
		}
		if (city == null || city.length() == 0) {
			errors.add("city is required");
		}
		
		if (cash == null || cash.length() == 0) {
			cash = "0";
		}
		try {
			double temp = Double.parseDouble(cash);
			if (temp < 0) errors.add("cash can't be negative");
		} catch (NumberFormatException e) {
			errors.add("cash format is wrong!");
		}
		
		if (errors.size() > 0) {
			//System.out.println(errors.size());
			return errors;
		}
		
		if (username.matches(".*[<>\"].*"))
			errors.add("UserName may not contain angle brackets or quotes");
		if (lname.matches(".*[<>\"].*"))
			errors.add("LastName may not contain angle brackets or quotes");
		if (fname.matches(".*[<>\"].*"))
			errors.add("FirstName may not contain angle brackets or quotes");
		if (address.matches(".*[<>\"].*"))
			errors.add("Address Line1 may not contain angle brackets or quotes");
		if (city.matches(".*[<>\"].*"))
			errors.add("City may not contain angle brackets or quotes");
		if (state.matches(".*[<>\"].*"))
			errors.add("State may not contain angle brackets or quotes");
		if (zip.matches(".*[<>\"].*"))
			errors.add("Zip may not contain angle brackets or quotes");
		
		return errors;
	}
}
