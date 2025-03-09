package all_consumer;

import java.util.InputMismatchException;
import java.util.Scanner;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;

public class Activator implements BundleActivator {

    private Scanner scanner;

    public void start(BundleContext bundleContext) {
        scanner = new Scanner(System.in);
        boolean running = true;

        String logo = """
        		                     | ------------------------------------ |                             
                                             | 𝓗𝓸𝓼𝓹𝓲𝓽𝓪𝓵 𝓜𝓪𝓷𝓪𝓰𝓮𝓶𝓮𝓷𝓽 𝓢𝔂𝓼𝓽𝓮𝓶 |
        				     |--------------------------------------|
        """;

        System.out.println(logo);

        while (running) {
            System.out.println("\t\t\t------------------------ SELECT AN ACTION ----------------------");
            System.out.println("");
            System.out.println("\t\t\t\t   1. Doctor Management");
            System.out.println("\t\t\t\t   2. Nurse Management");
            System.out.println("\t\t\t\t   3. Security Management");
            System.out.println("\t\t\t\t   4. Financial Management");
            System.out.println("\t\t\t\t   5. Exit");
            System.out.println("\t\t\t----------------------------------------------------------------");
            System.out.print("Enter your choice: ");

            try {
                if (!scanner.hasNextInt()) {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.next(); // Consume invalid input
                    continue;
                }

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1 -> startBundle(bundleContext, "Doctor_Consumer"); // Start Doctor bundle when selected
                    case 2 -> startBundle(bundleContext, "Nurse_Consumer");
                    case 3 -> startBundle(bundleContext, "Security_Consumer");
                    case 4 -> startBundle(bundleContext, "Finance_Consumer");
                    case 5 -> running = false;
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Consume invalid input
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void stop(BundleContext bundleContext) {
        System.out.println("Hospital Management System is shutting down...");
        if (scanner != null) {
            scanner.close(); 
        }
    }
    
    private void startBundle(BundleContext context, String symbolicName) {
        // Find the bundle by symbolic name
        Bundle bundle = findBundleBySymbolicName(context, symbolicName);
        if (bundle != null) {
            System.out.println("Bundle found: " + symbolicName);

            // Check if the bundle is already active
            if (bundle.getState() != Bundle.ACTIVE) {
                try {
                    // Start the bundle
                    bundle.start();
                    System.out.println(symbolicName + " started successfully.");
                } catch (BundleException e) {
                    System.out.println("Error starting " + symbolicName + ": " + e.getMessage());
                }
            } else {
                System.out.println(symbolicName + " is already active.");
            }
        } else {
            System.out.println("Bundle " + symbolicName + " not found.");
        }
    }


    private Bundle findBundleBySymbolicName(BundleContext context, String symbolicName) {
        Bundle[] bundles = context.getBundles();
        if (bundles == null || bundles.length == 0) {
            System.out.println("No bundles found.");
            return null;
        }

        // Log all the bundles
        for (Bundle bundle : bundles) {
            System.out.println("Found bundle: " + bundle.getSymbolicName());
            if (symbolicName.equals(bundle.getSymbolicName())) {
                return bundle;
            }
        }

        System.out.println("Bundle with symbolic name " + symbolicName + " not found.");
        return null;
    }


}
