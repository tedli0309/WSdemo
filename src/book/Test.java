package book;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Test {
	  @JsonProperty
	  private String name;
	  	
	  public String getName() {
	        return name;
	    }
	  public void setName(String name) {
	        this.name = name;
	  }
	  public String toString() {
		  return "hong xiao yue 's name is "+ name;
	  }
}
