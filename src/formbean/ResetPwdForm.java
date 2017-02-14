/**
 *  Author : 	Zebangli 
 *  AndrewId :  zebangl
 *  Date   :	2016.12.5
 *  Course :	08672
 */
package formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class ResetPwdForm extends FormBean {
	private String confirmPassword;
	private String password;
	
	public String getConfirmPassword() { return confirmPassword; }
	public String getPassword()     { return password;	 }
	
	public void setConfirmPassword(String s) { confirmPassword = s.trim(); }
	public void setPassword(String s)     { password     = s.trim(); }
	
	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (password == null || password.length() == 0) {
			errors.add("Password is required");
		}
		
		if (confirmPassword == null || confirmPassword.length() == 0) {
			errors.add("Confirm Pwd is required");
		}
		
		if (errors.size() > 0) {
			return errors;
		}
		
		if (!password.equals(confirmPassword)) {
			errors.add("Passwords do not match");
		}

		return errors;
	}
}
