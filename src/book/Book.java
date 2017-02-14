package book;
//
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
////annotation
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

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
