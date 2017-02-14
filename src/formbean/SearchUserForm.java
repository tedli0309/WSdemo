package formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class SearchUserForm extends FormBean {
	private String customerName;
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String s) {
		this.customerName = trimAndConvert(s,"<>\"");
	}
	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();
		if (customerName == null || customerName.length() == 0) {
			errors.add("Please enter customerName");
		}
		
		if (errors.size() > 0) {
			System.out.println(errors.size());
			return errors;
		}
		
		if (customerName.matches(".*[<>\"].*"))
			errors.add("CustomerName may not contain angle brackets or quotes");
		
		return errors;
	}
}
