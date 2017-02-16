package formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DepositCheckForm extends FormBean{
	@JsonProperty
	private String username;
	@JsonProperty
	private String cash;
	
	public void setUserName(String s) {
		username = trimAndConvert(s,"<>\"");
	}
	public void setCheckAmount(String s) {
		this.cash = trimAndConvert(s,"<>\"");
	}
	
	public String getUserName() {
		return username;
	}
	public String getCheckAmount() {
		return cash;
	}
	
	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();
		if (username == null || username.trim().length() == 0) {
			errors.add("userName is required.");
		}
		if (cash == null || cash.length() == 0) {
			errors.add("Amount is required.");
		}
		if(errors.size() > 0) return errors; 
		
		try{
			Double check = Double.parseDouble(cash);
			if (check <= 0) {
				errors.add("Amount should be positive.");
			}
			
		} catch(Exception e) {
			errors.add("Please enter valid amount value.");
		}
		
		if (username.matches(".*[<>\"].*"))
			errors.add("UserName may not contain angle brackets or quotes");
		if (cash.matches(".*[<>\"].*"))
			errors.add("CheckAmount may not contain angle brackets or quotes");
		
		return errors;
	}
}
