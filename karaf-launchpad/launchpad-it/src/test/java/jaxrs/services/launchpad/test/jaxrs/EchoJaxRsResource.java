package jaxrs.services.launchpad.test.jaxrs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * Simple JaxRS resource used during tests, in order to check Jersey integration
 */
@Path("/echo-test")
@Produces("text/plain")
public class EchoJaxRsResource {
    @GET
    public String getMessage() {
        return "Hello World";
    }

    @GET
    @Path("/{message}")
    public String getCustomMessage(@PathParam("message") String message ){
        return "Hello " + message;
    }
}
