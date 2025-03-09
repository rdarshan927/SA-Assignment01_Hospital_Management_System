package doctor_producer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class DoctorProducerActivator implements BundleActivator {
    private ServiceRegistration<?> serviceRegistration;

    @Override
    public void start(BundleContext context) throws Exception {
        DoctorService doctorService = new DoctorServiceImpl();
        serviceRegistration = context.registerService(DoctorService.class.getName(), doctorService, null);
        System.out.println("🔹 Doctor Service Registered!");
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        serviceRegistration.unregister();
        System.out.println("🔹 Doctor Service Unregistered!");
    }
}
