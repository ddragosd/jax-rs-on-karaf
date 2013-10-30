package jaxrs.services.launchpad.test;

import jaxrs.services.launchpad.test.jaxrs.EchoJaxRsResource;
import karaf.services.launchpad.test.support.KarafBaseTestSupport;
import org.glassfish.jersey.CommonProperties;
import org.glassfish.jersey.client.ClientConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.junit.ExamReactorStrategy;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.ops4j.pax.exam.spi.reactors.AllConfinedStagedReactorFactory;
import org.osgi.framework.ServiceRegistration;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Tests if JAX-RS resources are being published accordingly
 */
@RunWith(JUnit4TestRunner.class)
@ExamReactorStrategy(AllConfinedStagedReactorFactory.class)
public class JerseyEndpointTest extends KarafBaseTestSupport {

    @Test
    public void testEndpointIsPublished() throws Exception {
        //changing port not working right now
//        String portChangeResult = executeCommand("config:propset -p org.ops4j.pax.web org.osgi.service.http.port 9999");
        //1.make sure no service is published
        ClientConfig clientConfig = new ClientConfig();
        clientConfig = clientConfig.property(CommonProperties.FEATURE_AUTO_DISCOVERY_DISABLE, Boolean.TRUE); // no h2k
        Client client = ClientBuilder.newBuilder().withConfig(clientConfig).build();

        WebTarget target = client.target("http://localhost:50000/api");
        Response response = target.path("/echo-test").request().get();
        assertThat(response.getStatus(), is(equalTo(404)));

        //2.publish a new service ( deploy a test bundle )
        Thread.sleep(1000); // take a deep breath
        ServiceRegistration jaxrsResourceService = bundleContext.registerService(EchoJaxRsResource.class, new EchoJaxRsResource(), null);

        //3. wait a little for Jersey to be reinitialized then check that the new service is available
        Thread.sleep(5000);
        response = target.path("/echo-test").request().get();// .get(String.class);
        assertThat(response.getStatus(), is(equalTo(200)));
        String responseText = response.readEntity(String.class);
        assertThat(responseText, is(equalTo("Hello World")));

        //4. un-register service
        Thread.sleep(2000);
        jaxrsResourceService.unregister();
        response = target.path("/echo-test").request().get();
        assertThat(response.getStatus(), is(equalTo(404)));
    }
}
