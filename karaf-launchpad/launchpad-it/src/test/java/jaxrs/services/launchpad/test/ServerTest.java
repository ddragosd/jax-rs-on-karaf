package jaxrs.services.launchpad.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.junit.ExamReactorStrategy;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.ops4j.pax.exam.spi.reactors.AllConfinedStagedReactorFactory;

import static org.junit.Assert.assertTrue;

/**
 * Basic integration test for squid
 */
@RunWith(JUnit4TestRunner.class)
@ExamReactorStrategy(AllConfinedStagedReactorFactory.class)
public class ServerTest {

    @Test
    public void testServerStart() throws Exception {
        // if we reach this point it means that the server has started
        assertTrue(true);
    }
}
