package formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BuyForm extends FormBean {
	@JsonProperty
	private String symbol;
	@JsonProperty
	private String cashValue;
	
	public void setSymbol (String s) {
		this.symbol = trimAndConvert(s,"<>\"");
	}
	public void setCashValue(String s) {
		this.cashValue = s.trim();
	}
	
	public String getSymbol() {
		return this.symbol;
	}

	public String getCashValue() {
		return this.cashValue;
	}

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();
		if (symbol == null || symbol.length()== 0) {
			errors.add("Fund symbol is required.");
		}
		if (cashValue == null || cashValue.length() == 0) {
			errors.add("Cash value is required.");
		}
		
		if(errors.size() > 0) return errors; // no need to further detect the errors anymore.

		try{
			Integer.parseInt(cashValue);
		}catch(Exception e) {
			errors.add("Invalid cash value");
		}
		
		return errors;
    }
}
