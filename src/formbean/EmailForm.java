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

public class EmailForm extends FormBean {
	private String email = "";
	
	public String getEmail()  { return email; }
	
	public void setEmail(String s)  { email = trimAndConvert(s,"<>>\"]"); }

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (email == null || email.length() == 0) {
			errors.add("User Name is required");
		}
		
		if (email.matches(".*[<>\"].*"))
			errors.add("Email may not contain angle brackets or quotes");
		
		return errors;
	}
}
