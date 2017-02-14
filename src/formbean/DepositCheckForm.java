package formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class DepositCheckForm extends FormBean{
	private String userName;
	private String checkAmount;
	
	public void setUserName(String s) {
		userName = trimAndConvert(s,"<>\"");
	}
	public void setCheckAmount(String s) {
		this.checkAmount = trimAndConvert(s,"<>\"");
	}
	
	public String getUserName() {
		return userName;
	}
	public String getCheckAmount() {
		return checkAmount;
	}
	
	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();
		if (userName == null || userName.trim().length() == 0) {
			errors.add("userName is required.");
		}
		if (checkAmount == null || checkAmount.length() == 0) {
			errors.add("Amount is required.");
		}
		if(errors.size() > 0) return errors; 
		
		try{
			Double check = Double.parseDouble(checkAmount);
			if (check <= 0) {
				errors.add("Amount should be positive.");
			}
			
		} catch(Exception e) {
			errors.add("Please enter valid amount value.");
		}
		
		if (userName.matches(".*[<>\"].*"))
			errors.add("UserName may not contain angle brackets or quotes");
		if (checkAmount.matches(".*[<>\"].*"))
			errors.add("CheckAmount may not contain angle brackets or quotes");
		
		return errors;
	}
}
