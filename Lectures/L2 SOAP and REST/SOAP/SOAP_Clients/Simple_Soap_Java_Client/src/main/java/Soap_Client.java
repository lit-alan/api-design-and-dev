import com.example.client.HelloService;
import com.example.client.HelloServiceService;

/**
 * A simple SOAP client for consuming a web service.
 * <p>
 * This class demonstrates how to:
 * <ul>
 *     <li>Instantiate a generated service class to access a SOAP-based web service.</li>
 *     <li>Retrieve the service port (proxy object) for interacting with the service.</li>
 *     <li>Call a remote method and handle its response.</li>
 * </ul>
 * The client connects to a SOAP service and invokes the {@code sayHello} method
 * with a sample input, printing the response to the console.
 * </p>
 *
 * <p>
 * Example usage:
 * <pre>
 * {@code
 * public static void main(String[] args) {
 *     Soap_Client.main(args);
 * }
 * }
 * </pre>
 * </p>
 *
 * Prerequisites:
 * <ul>
 *     <li>WSDL-based classes for the service (e.g., {@link HelloServiceService} and {@link HelloService})
 *         must be generated using tools like JAX-WS {@code wsimport}.</li>
 *     <li>A valid SOAP endpoint must be available and configured.</li>
 * </ul>
 *
 * @author Alan Ryan
 * @version 1.0
 */

public class Soap_Client {

        public static void main(String[] args) {
            //Instantiate the service
            HelloServiceService service = new HelloServiceService();

            //Get the service port (proxy object)
            HelloService helloService = service.getHelloServicePort();

            //Call the remote method
            String response = helloService.sayHello("Alan");

            System.out.println("Response from SOAP service: " + response);
        }
}
