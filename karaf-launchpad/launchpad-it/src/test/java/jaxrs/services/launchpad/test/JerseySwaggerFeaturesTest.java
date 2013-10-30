package jaxrs.services.launchpad.test;

import karaf.services.launchpad.test.support.KarafBaseTestSupport;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.junit.ExamReactorStrategy;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.ops4j.pax.exam.spi.reactors.EagerSingleStagedReactorFactory;

/**
 * Tests if Jersey features are installed properly
 */
@RunWith(JUnit4TestRunner.class)
//@ExamReactorStrategy(AllConfinedStagedReactorFactory.class)
@ExamReactorStrategy(EagerSingleStagedReactorFactory.class) // info at: https://ops4j1.jira.com/wiki/display/paxexam/Reactor+Strategies
public class JerseySwaggerFeaturesTest extends KarafBaseTestSupport {

    @Test
    public void testJerseyFeatureIsInstalled() throws Exception {
        // Make sure the command services are available
//        assertNotNull(getOsgiService(BlueprintContainer.class, "osgi.blueprint.container.symbolicname=org.apache.karaf.shell.obr", 20000));
//        assertNotNull(getOsgiService(BlueprintContainer.class, "osgi.blueprint.container.symbolicname=org.apache.karaf.shell.wrapper", 20000));

        /*String result = executeCommand("features:list | grep jersey-core");
        assertThat(result, containsString("[installed  ]"));
        assertThat(result, containsString("[2."));*/

        assertFeatureInstalled("jersey-core");
        assertFeatureInstalled("jersey-containers");
        assertFeatureInstalled("jersey");
        assertFeatureInstalled("com.fasterxml.jackson");
    }

    @Test
    public void testSwaggerFeatureIsInstalled() throws Exception {
        assertFeatureInstalled("swagger-jersey2");
    }
}
