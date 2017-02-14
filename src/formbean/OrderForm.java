package formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class OrderForm extends FormBean {
	private String orderAmount;
	private String orderSymbol;
	private String selectb;
	
	public String getOrderAmount() {
		return orderAmount;
	}
	public String getOrderSymbol() {
		return orderSymbol;
	}
	
	public String getSelectb() {
		return selectb;
	}
	public void setSelectb(String s) {
		this.selectb = trimAndConvert(s,"<>\"");
	}
	public void setOrderSymbol(String s) {
		this.orderSymbol = trimAndConvert(s,"<>\"");
	}
	
	public void setOrderAmount(String s) {
		this.orderAmount = trimAndConvert(s,"<>\"");
	}
	
	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();
		if (orderAmount == null || orderAmount.length() == 0) {
			errors.add("Amount/Share is required.");
		}
		if (orderSymbol == null || orderSymbol.length() == 0) {
			errors.add("Fund symbol is required.");
		}
		if (selectb == null || selectb.length() == 0) {
			errors.add("Please select buy or sell");
		}
		if(errors.size() > 0) return errors; // no need to further detect the errors anymore.
		if (!selectb.equals("buy") && !selectb.equals("sell")) {
			errors.add("Options should be buy/sell");
		}
		try{
			if(Double.parseDouble(orderAmount) <= 0) {
				errors.add("Amount/Share should be greater than 0");
			}
		} catch(Exception e) {
			errors.add("Please enter valid amount/share value");
		}		
		if (orderAmount.matches(".*[<>\"].*"))
			errors.add("Amount/Share may not contain angle brackets or quotes");
		if (orderSymbol.matches(".*[<>\"].*"))
			errors.add("Fund symbol may not contain angle brackets or quotes");
		if (selectb.matches(".*[<>\"].*"))
			errors.add("Select buy or sell may not contain angle brackets or quotes");
		
		return errors;
	}
}
