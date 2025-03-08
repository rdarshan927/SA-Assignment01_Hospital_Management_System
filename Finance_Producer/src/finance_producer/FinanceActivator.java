package finance_producer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class FinanceActivator implements BundleActivator {
    private ServiceRegistration<?> serviceRegistration;

    public void start(BundleContext context) throws Exception {
        FinanceService service = new FinanceServiceImpl();
        serviceRegistration = context.registerService(FinanceService.class.getName(), service, null);
        System.out.println("💰 Finance Service Registered!");
    }

    public void stop(BundleContext context) throws Exception {
        serviceRegistration.unregister();
        System.out.println("💰 Finance Service Unregistered!");
    }
}
