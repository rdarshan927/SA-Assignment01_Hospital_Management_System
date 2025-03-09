package security_producer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class SecurityActivator implements BundleActivator {

	@SuppressWarnings("rawtypes")
	ServiceRegistration publishServiceRegistration;
	
    public void start(BundleContext context) throws Exception {
		
		System.out.println("Security Producer Start");
	
		SecurityService SecurityService = new SecurityServiceImpl();
		
		publishServiceRegistration = context.registerService(SecurityService.class.getName(), SecurityService, null);
	}

	public void stop(BundleContext context) throws Exception {
		System.out.println("Security Publisher Stop");
		publishServiceRegistration.unregister();
	}
}
