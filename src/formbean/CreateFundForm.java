package formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class  CreateFundForm extends FormBean{

	@JsonProperty
	private String name;
	@JsonProperty
	private String symbol;
	@JsonProperty
	private String initial_value;
	
	public void setName(String name) {
		this.name = trimAndConvert(name,"<>\"");
	}
	public void setSymbol(String symbol)    { 
		this.symbol = trimAndConvert(symbol,"<>\"");
	}
	public void setInitial_value (String initial_value) {
		this.initial_value = initial_value;
	}
	public String getName() { 
		return name;
	}
	public String getSymbol() {
		 return symbol;
	}
	public String getInitial_value() {
		 return initial_value;
	}
	
    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<String>();

        if (name == null || name.trim().length() == 0) {
            errors.add("fundName is required!");           
        }
        if (symbol == null || symbol.trim().length() == 0) {
            errors.add("fundSymbol is required!");
        }
        double value = 0.0;
        try{
        	value=Double.parseDouble(initial_value);
        }catch(NumberFormatException e) {
        	errors.add("Value should be a number!");
        }
        if (value == 0) {
            errors.add("Value is required!");
        }
        if (errors.size() > 0) {
			//System.out.println(errors.size());
			return errors;
		}
        
        if (name.matches(".*[<>\"].*"))
			errors.add("FundName may not contain angle brackets or quotes");
		if (symbol.matches(".*[<>\"].*"))
			errors.add("FundSymbol may not contain angle brackets or quotes");


        return errors;
    }
 
}