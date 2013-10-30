package karaf.services.launchpad.test.support;

import org.apache.karaf.tooling.exam.options.KarafDistributionConfigurationFilePutOption;
import org.apache.karaf.tooling.exam.options.KarafDistributionOption;
import org.apache.karaf.tooling.exam.options.LogLevelOption;
import org.ops4j.pax.exam.CoreOptions;
import org.ops4j.pax.exam.MavenUtils;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.TestProbeBuilder;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.ProbeBuilder;
import org.ops4j.pax.exam.options.MavenArtifactProvisionOption;
import org.osgi.framework.Bundle;
import org.osgi.framework.Constants;

import static org.apache.karaf.tooling.exam.options.KarafDistributionOption.*;
import static org.ops4j.pax.exam.CoreOptions.maven;
import static org.ops4j.pax.exam.CoreOptions.provision;

/**
 * Inspired from
 *  https://github.com/jclouds/jclouds-karaf/blob/master/itests/src/test/java/org/jclouds/karaf/itests/JcloudsKarafTestSupport.java
 */
public class KarafBaseTestSupport extends KarafTestSupport {

    public static final Long DEFAULT_TIMEOUT = 10000L;
    public static final Long DEFAULT_WAIT = 10000L;

    public static final String KARAF_GROUP_ID = "jaxrs.launchpad";
    public static final String KARAF_ARTIFACT_ID = "launchpad";


    @Override
    @ProbeBuilder
    public TestProbeBuilder probeConfiguration(TestProbeBuilder probe) {
        probe.setHeader(Constants.DYNAMICIMPORT_PACKAGE, "*");
        return probe;
    }

    /**
     * Create an {@link org.ops4j.pax.exam.Option} for using Apache Karaf distribution.
     *
     * @return
     */
    @Configuration
    @Override
    public Option[] config() {
        return new Option[]{
                karafDistributionConfiguration().frameworkUrl(
                        maven().groupId(KARAF_GROUP_ID).artifactId(KARAF_ARTIFACT_ID).type("tar.gz").versionAsInProject()) // or .version("0.1.0-SNAPSHOT"))
                        .karafVersion("2.3.2")
                        .name("Jaxrs Karaf Launchpad"),
                provision(
                        mavenBundle("jaxrs.launchpad","launchpad-test-support").versionAsInProject().startLevel(100)
                ),
                new KarafDistributionConfigurationFilePutOption(
                        "etc/config.properties", // config file to modify based on karaf.base
                        "karaf.framework", // key to add or change
                        "felix"), // value to add or change
                new KarafDistributionConfigurationFilePutOption(
                        "etc/org.ops4j.pax.web.cfg", // config file to modify based on karaf.base
                        "org.osgi.service.http.port", // key to add or change
                        "50000"), // value to add or change
                keepRuntimeFolder(),
                logLevel(LogLevelOption.LogLevel.INFO),
                debugConfiguration( getDebugPort(), getDebugHold() )
        };
    }

    protected String getDebugPort() {
        return "5000";
    }

    protected boolean getDebugHold() {
        return false;
    }

    /**
     * Sets a System property.
     *
     * @param propertyName
     * @return
     */
    public static Option systemProperty(String propertyName, String propertyValue) {
        return KarafDistributionOption.editConfigurationFileExtend("etc/system.properties", propertyName, propertyValue != null ? propertyValue : "");
    }

    /**
     * Copies the actual System property to the container properties.
     *
     * @param propertyName
     * @return
     */
    public static Option systemProperty(String propertyName) {
        return systemProperty(propertyName, System.getProperty(propertyName));
    }


    protected Bundle installBundle(String groupId, String artifactId) throws Exception {
        MavenArtifactProvisionOption mvnUrl = mavenBundle(groupId, artifactId);
        return bundleContext.installBundle(mvnUrl.getURL());
    }


    protected Bundle getInstalledBundle(String symbolicName) {
        for (Bundle b : bundleContext.getBundles()) {
            if (b.getSymbolicName().equals(symbolicName)) {
                return b;
            }
        }
        for (Bundle b : bundleContext.getBundles()) {
            System.err.println("Bundle: " + b.getSymbolicName());
        }
        throw new RuntimeException("Bundle " + symbolicName + " does not exist");
    }

    /**
     * Create an provisioning option for the specified maven artifact
     * (groupId and artifactId), using the version found in the list
     * of dependencies of this maven project.
     *
     * @param groupId    the groupId of the maven bundle
     * @param artifactId the artifactId of the maven bundle
     * @return the provisioning option for the given bundle
     */
    protected static MavenArtifactProvisionOption mavenBundle(String groupId, String artifactId) {
        return CoreOptions.mavenBundle(groupId, artifactId).versionAsInProject();
    }

    /**
     * Create an provisioning option for the specified maven artifact
     * (groupId and artifactId), using the version found in the list
     * of dependencies of this maven project.
     *
     * @param groupId    the groupId of the maven bundle
     * @param artifactId the artifactId of the maven bundle
     * @param version    the version of the maven bundle
     * @return the provisioning option for the given bundle
     */
    protected static MavenArtifactProvisionOption mavenBundle(String groupId, String artifactId, String version) {
        return CoreOptions.mavenBundle(groupId, artifactId).version(version);
    }

    /**
     * Returns the Version of Karaf to be used.
     *
     * @return
     */
    protected String getKarafVersion() {
        return MavenUtils.getArtifactVersion(KARAF_GROUP_ID, KARAF_ARTIFACT_ID);
    }
}
