package formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class  CreateFundForm extends FormBean{

	private String fundName;
	private String fundSymbol;
	
	public void setFundName(String fundName) {
		this.fundName = trimAndConvert(fundName,"<>\"");
	}
	public void setFundSymbol(String fundSymbol)    { 
		this.fundSymbol = trimAndConvert(fundSymbol,"<>\"");
	}
	public String getFundName() { 
		return fundName;
	}
	public String getFundSymbol() {
		 return fundSymbol;
	}
	
    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<String>();

        if (fundName == null || fundName.trim().length() == 0) {
            errors.add("fundName is required!");           
        }
        if (fundSymbol == null || fundSymbol.trim().length() == 0) {
            errors.add("fundSymbol is required!");
        }
        
        if (errors.size() > 0) {
			//System.out.println(errors.size());
			return errors;
		}
        
        if (fundName.matches(".*[<>\"].*"))
			errors.add("FundName may not contain angle brackets or quotes");
		if (fundSymbol.matches(".*[<>\"].*"))
			errors.add("FundSymbol may not contain angle brackets or quotes");


        return errors;
    }
 
}