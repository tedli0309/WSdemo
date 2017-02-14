package formbean;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.mybeans.form.FormBean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginForm extends FormBean{
	@JsonProperty
	private String username;
	@JsonProperty
	private String password;

	
	public void setUserName(String userName) {
		this.username = trimAndConvert(userName,"<>\"");
	}
	public void setPassword(String password) {
		this.password = password.trim();
	}
	
	public String getUserName() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();
		if (username == null || username.length()== 0) {
			errors.add("UserName is required.");
		}
		if (password == null || password.length() == 0) {
			errors.add("Password is required.");
		}
		
		if(errors.size() > 0) return errors; // no need to further detect the errors anymore.

		if (username.matches(".*[<>\"].*"))
			errors.add("UserName may not contain angle brackets or quotes");
		
		return errors;
    }
	public String toString() {
		return "User: " + username + "Password:" + password;
	}
}
