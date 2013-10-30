package jaxrs.example.provider;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

/**
 * @author ddragosd@gmail.com
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
@Component(immediate = true, metatype = false)
@Service(value = {CustomJacksonJaxbJsonProvider.class})
public class CustomJacksonJaxbJsonProvider extends JacksonJaxbJsonProvider {
    private static final ObjectMapper OBJECT_MAPPER = buildObjectMapper();

    public CustomJacksonJaxbJsonProvider() {
        super.setMapper(OBJECT_MAPPER);
    }

    private static ObjectMapper buildObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return mapper;
    }
}
