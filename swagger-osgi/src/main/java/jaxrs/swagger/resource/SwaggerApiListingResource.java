package jaxrs.swagger.resource;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.core.SwaggerContext;
import com.wordnik.swagger.jaxrs.JaxrsApiReader;
import com.wordnik.swagger.jersey.JerseyApiReader;
import com.wordnik.swagger.jersey.listing.ApiListing;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;


@Path("/api-docs")
@Api("/api-docs")
@Produces({"application/json"})
@Component(immediate = true, metatype = false)
@Service(value = {ApiListing.class})
public class SwaggerApiListingResource extends ApiListing {
    // as per https://github.com/wordnik/swagger-core/wiki/java-jax-rs
    // this removes the ".json" suffix when listing the API
    static {
        JaxrsApiReader.setFormatString("");
        JerseyApiReader.setFormatString("");
    }

    private boolean initialized = false;


    public SwaggerApiListingResource() {
        if (!initialized) {
            //SwaggerContext.registerClassLoader(this.getClass().getClassLoader());
            initialized = true;
        }
    }

    public void registerClassLoader(ClassLoader classLoader) {
        SwaggerContext.registerClassLoader(classLoader);
    }


}
