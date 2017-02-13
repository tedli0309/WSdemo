package book;
import javax.sound.midi.Track;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
//annotation
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.fasterxml.jackson.databind.util.JSONWrappedObject;
import com.fasterxml.jackson.annotation.JsonProperty;

@Path("/book")
public class Book {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)	 //consumes for put and post 
	public String sayHello(Test test) {		
//		String inpuStr = (String)input.getValue();
//        String output = "The input you sent is :" + input;
        System.out.println(test.toString());
//        JSONPObject outputJsonObj = new JSONPObject(output, outputJsonObj);
        return test.toString();
	}
}
