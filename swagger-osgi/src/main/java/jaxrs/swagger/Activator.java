package jaxrs.swagger;

import jaxrs.swagger.resource.SwaggerApiListingResource;
import org.apache.felix.scr.annotations.Reference;
import org.osgi.framework.*;
import org.osgi.service.cm.ManagedService;

/**
 *
 */
public class Activator implements BundleActivator {

    SwaggerResourceTracker tracker;
    ServiceRegistration<SwaggerResourceTracker> serviceRegistration;

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        openAllServiceTracker(bundleContext);
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        tracker.close();
        serviceRegistration.unregister();
    }


    private void openAllServiceTracker(BundleContext context) throws InvalidSyntaxException {
        Filter filter = context.createFilter(SwaggerResourceTracker.ANY_SERVICE_FILTER);

        tracker = new SwaggerResourceTracker(context, filter);
        serviceRegistration = context.registerService(SwaggerResourceTracker.class, tracker, null);
        tracker.open();
    }
}
