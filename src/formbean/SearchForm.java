package formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class SearchForm extends FormBean {
	private String symbol;
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String s) {
		this.symbol = trimAndConvert(s,"<>\"");
	}
	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();
		if (symbol == null || symbol.length() == 0) {
			errors.add("Please enter id");
		}
		
		if (errors.size() > 0) {
			System.out.println(errors.size());
			return errors;
		}
		
		if (symbol.matches(".*[<>\"].*"))
			errors.add("Symbol may not contain angle brackets or quotes");
		
		return errors;
	}
}
