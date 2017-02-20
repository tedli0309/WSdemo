package formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class RequestCheckForm extends FormBean{
	private String cashValue;
	
	public String getCashValue() {
		return cashValue;
	}
	public void setCashValue(String s) {
		this.cashValue = trimAndConvert(s,"<>\"");
	}
	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();
		if (cashValue == null || cashValue.length() == 0) {
			errors.add("Amount is required.");
		}
		if(errors.size() > 0) return errors; 
		
		try{
			Double check = Double.parseDouble(cashValue);
			if (check <= 0) {
				errors.add("Amount should be positive.");
			}
			
		} catch(Exception e) {
			errors.add("Please enter valid amount value.");
		}
        int pointPos = cashValue.indexOf('.');        
        if ((pointPos != -1) && (cashValue.length() - pointPos) > 3) {
        	errors.add("the two decimal problem!");
        } 
		
		if (cashValue.matches(".*[<>\"].*"))
			errors.add("CheckAmount may not contain angle brackets or quotes");
		
		return errors;
	}
}

