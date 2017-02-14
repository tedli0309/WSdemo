package formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class RequestCheckForm extends FormBean{
	private String checkAmount;
	
	public String getCheckAmount() {
		return checkAmount;
	}
	public void setCheckAmount(String s) {
		this.checkAmount = trimAndConvert(s,"<>\"");
	}
	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();
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
		
		if (checkAmount.matches(".*[<>\"].*"))
			errors.add("CheckAmount may not contain angle brackets or quotes");
		
		return errors;
	}
}

