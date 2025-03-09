package finance_producer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class FinanceProducerActivator implements BundleActivator {

    private static BundleContext context;
    private ServiceRegistration<FinanceService> serviceRegistration;

    static BundleContext getContext() {
        return context;
    }

    public void start(BundleContext bundleContext) throws Exception {
        FinanceProducerActivator.context = bundleContext;
        
        // Create an instance of the FinanceService (assuming FinanceServiceImpl is your implementation)
        FinanceService financeService = new FinanceServiceImpl();

        // Register the service with the OSGi service registry
        serviceRegistration = bundleContext.registerService(FinanceService.class, financeService, null);

        System.out.println("FinanceService has been registered.");
    }

    public void stop(BundleContext bundleContext) throws Exception {
        FinanceProducerActivator.context = null;

        // Unregister the service when the bundle is stopped
        if (serviceRegistration != null) {
            serviceRegistration.unregister();
        }

        System.out.println("FinanceService has been unregistered.");
    }
}
