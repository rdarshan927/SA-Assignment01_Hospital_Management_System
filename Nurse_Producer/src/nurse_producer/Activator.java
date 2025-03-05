package nurse_producer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {
    private ServiceRegistration<?> serviceRegistration;

    public void start(BundleContext context) throws Exception {
        NurseService service = new NurseServiceImpl();
        serviceRegistration = context.registerService(NurseService.class.getName(), service, null);
        System.out.println("🔹 Nurse Service Registered!");
    }

    public void stop(BundleContext context) throws Exception {
        serviceRegistration.unregister();
        System.out.println("🔹 Nurse Service Unregistered!");
    }
}
