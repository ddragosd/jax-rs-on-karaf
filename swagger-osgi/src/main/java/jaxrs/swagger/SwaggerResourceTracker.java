package jaxrs.swagger;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.core.SwaggerContext;
import jaxrs.swagger.resource.SwaggerApiListingResource;
import com.wordnik.swagger.jersey.listing.ApiListing;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import javax.ws.rs.Path;

/**
 * Keeps track of JAX-RS resource annotated for Swagger
 */
public class SwaggerResourceTracker extends ServiceTracker<Object, Object> {
    static final String ANY_SERVICE_FILTER = "(objectClass=*)";

    private final BundleContext context;

    @Reference
    private ApiListing apiListingResource;


    public SwaggerResourceTracker(BundleContext context, Filter filter) {
        super(context, filter, null);
        this.context = context;
    }

    @Override
    public Object addingService(ServiceReference<Object> reference) {
        Object service = context.getService(reference);
        return delegateAddService(reference, service);
    }

    @Override
    public void removedService(ServiceReference<Object> reference, Object service) {
        delegateRemoveService(reference, service);
    }

    private Object delegateAddService(ServiceReference<Object> reference, Object service) {
        Object result = super.addingService(reference);
        if (isJaxRsSwaggerResource(service)) {
            if ( apiListingResource != null ) {
                apiListingResource.invalidateCache();
            }
            SwaggerContext.registerClassLoader(service.getClass().getClassLoader());
        }
        return result;
    }
    private void delegateRemoveService(ServiceReference<Object> reference, Object service) {
        super.removedService(reference, service);

        if (isJaxRsSwaggerResource(service)) {
            SwaggerContext.unregisterClassLoader(service.getClass().getClassLoader());
        }
    }

    private boolean isJaxRsSwaggerResource(Object service) {
        return service != null && hasRegisterableAnnotation(service);
    }

    private boolean hasRegisterableAnnotation(Object service) {
        boolean result = isRegisterableAnnotationPresent(service.getClass());
        if (!result) {
            Class<?>[] interfaces = service.getClass().getInterfaces();
            for (Class<?> type : interfaces) {
                result = result || isRegisterableAnnotationPresent(type);
            }
        }
        return result;
    }

    private boolean isRegisterableAnnotationPresent(Class<?> type) {
        if (type != null) {
            return type.isAnnotationPresent(Path.class) && type.isAnnotationPresent(Api.class);
        }
        return false;
    }

    protected void bindApiListingResource( ApiListing apiListing) {
        this.apiListingResource = apiListing;
    }
}
