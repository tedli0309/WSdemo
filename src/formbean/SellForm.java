package formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SellForm extends FormBean {
	@JsonProperty
	private String symbol;
	@JsonProperty
	private String numShares;
	
	public void setSymbol (String s) {
		this.symbol = trimAndConvert(s,"<>\"");
	}
	public void setNumShares(String s) {
		this.numShares = s.trim();
	}
	
	public String getSymbol() {
		return this.symbol;
	}

	public String getNumShare() {
		return this.numShares;
	}

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();
		if (symbol == null || symbol.length()== 0) {
			errors.add("Fund symbol is required.");
		}
		if (numShares == null || numShares.length() == 0) {
			errors.add("Cash value is required.");
		}
		
		if(errors.size() > 0) return errors; // no need to further detect the errors anymore.

		try{
			int share = Integer.parseInt(numShares);
			if(share <= 0){
				throw new Exception();
			}
		}catch(Exception e) {
			errors.add("Invalid cash value");
		}
		
		return errors;
    }
}
