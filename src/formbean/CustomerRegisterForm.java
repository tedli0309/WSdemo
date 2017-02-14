package formbean;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.mybeans.form.FormBean;

public class CustomerRegisterForm extends FormBean{
	private String userName;
	private String firstName;
	private String lastName;
	private String addr1;
	private String addr2;
	private String city;
	private String state;
	private String zip;
	private String password;
	private String confirmPassword;
	
	public void setUserName(String userName) {
		this.userName =  trimAndConvert(userName,"<>\"");
	}
	public void setFirstName(String firstName) {
		this.firstName = trimAndConvert(firstName,"<>\"");
	}
	public void setLastName(String lastName) {
		this.lastName = trimAndConvert(lastName,"<>\"");
	}
	public void setAddr1(String addr1) {
		this.addr1 = trimAndConvert(addr1,"<>\"");
	}
	public void setAddr2(String addr2) {
		this.addr2 = trimAndConvert(addr2,"<>\"");
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
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword.trim();
	}
	
	public String getUserName() {
		return userName;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getAddr1() {
		return addr1;
	}
	public String getAddr2() {
		return addr2;
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
	
	public List<String> getValidationErrors() {
		//System.out.println(lastName.length());
		List<String> errors = new ArrayList<String>();
		if (userName == null || userName.length() == 0) {
			errors.add("Email is required!");
		}
		if (lastName == null || lastName.length() == 0) {
			errors.add("LastName is required!");
		}
		if (firstName == null || firstName.length() == 0) {
			errors.add("FirstName is required!");
		}
		if (password == null || password.length() == 0) {
			errors.add("password is required!");
		}
		if(!password.equals(confirmPassword)) {
			errors.add("Passwords do not match");
		}
		
		if (errors.size() > 0) {
			//System.out.println(errors.size());
			return errors;
		}
		
		if (userName.matches(".*[<>\"].*"))
			errors.add("UserName may not contain angle brackets or quotes");
		if (lastName.matches(".*[<>\"].*"))
			errors.add("LastName may not contain angle brackets or quotes");
		if (firstName.matches(".*[<>\"].*"))
			errors.add("FirstName may not contain angle brackets or quotes");
		if (addr1.matches(".*[<>\"].*"))
			errors.add("Address Line1 may not contain angle brackets or quotes");
		if (addr2.matches(".*[<>\"].*"))
			errors.add("Address Line2 may not contain angle brackets or quotes");
		if (city.matches(".*[<>\"].*"))
			errors.add("City may not contain angle brackets or quotes");
		if (state.matches(".*[<>\"].*"))
			errors.add("State may not contain angle brackets or quotes");
		if (zip.matches(".*[<>\"].*"))
			errors.add("Zip may not contain angle brackets or quotes");
		
		return errors;
	}
}
