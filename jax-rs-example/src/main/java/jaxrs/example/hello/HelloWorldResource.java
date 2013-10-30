
package jaxrs.example.hello;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.apache.felix.scr.annotations.Service;
import org.apache.felix.scr.annotations.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.awt.*;

@Path("/hello")
@Api("/hello")
@Produces(MediaType.APPLICATION_JSON)
@Component(immediate = true, metatype = false)
@Service(value = {HelloWorldResource.class})
public class HelloWorldResource {

    @GET
    @ApiOperation("Says hello world")
    public Message sayHello() {
        return sayHello("world");
    }

    @GET
    @Path("/{to}")
    @ApiOperation("Says hello (given_name)")
    public Message sayHello(@PathParam("to") String to) {
        return new Message(String.format("Hello, %s!", to));
    }
}
