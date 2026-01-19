package server;

import jakarta.xml.ws.Endpoint;
import service.HelloService;
/**
 * A simple SOAP server for hosting a web service.
 * <p>
 * This class demonstrates how to:
 * <ul>
 *     <li>Create an instance of a SOAP service implementation class.</li>
 *     <li>Publish the service at a specified endpoint address using {@link jakarta.xml.ws.Endpoint}.</li>
 *     <li>Output the service's running address for client access.</li>
 * </ul>
 * The server hosts the {@link service.HelloService} SOAP service at the specified
 * address, making it available for client consumption.
 * </p>
 *
 * <p>
 * Example usage:
 * <pre>
 * {@code
 * public static void main(String[] args) {
 *     SoapServer.main(args);
 * }
 * }
 * </pre>
 * </p>
 *
 * Prerequisites:
 * <ul>
 *     <li>The {@code HelloService} class must implement the SOAP service interface
 *         and be annotated appropriately with JAX-WS annotations (e.g., {@code @WebService}).</li>
 *     <li>A valid endpoint address (e.g., {@code http://localhost:8080/hello}) must be specified.</li>
 *     <li>The application must include the necessary dependencies for JAX-WS (e.g., Jakarta EE).</li>
 * </ul>
 *
 * @author Alan Ryan
 * @version 1.0
 */

public class SoapServer {
    public static void main(String[] args) {
        HelloService service = new HelloService();
        String address = "http://localhost:8080/hello";

        Endpoint.publish(address, service);

        System.out.println("SOAP service is running at: " + address);
    }
}
