package formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class TransitionDayForm extends FormBean {
	private String date;
	public String getDate(){
		return date;
	}
	public void setDate(String s) {
		this.date  = s;
		//this.date = trimAndConvert(s,"<>\"");
	}
	
	public List<String> getValidationErrors() {
	List<String> errors = new ArrayList<String>();
	
	if (date.matches(".*[<>\"].*"))
		errors.add("Date may not contain angle brackets or quotes");
	
	return errors;
	
	}
}
