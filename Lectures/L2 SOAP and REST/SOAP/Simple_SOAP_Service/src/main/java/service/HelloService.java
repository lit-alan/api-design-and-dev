package service;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

/**
 * A SOAP service implementation class providing a simple greeting service.
 * <p>
 * This class is annotated with {@link jakarta.jws.WebService} to define it as a SOAP service
 * and exposes a single method, {@code sayHello}, via the {@link jakarta.jws.WebMethod} annotation.
 * Clients can call this method to receive a personalized greeting.
 * </p>
 *
 * <p>
 * Example usage:
 * <pre>
 * {@code
 * //Example SOAP client invocation:
 * String response = helloService.sayHello("Alan");
 * System.out.println(response); // Outputs: "Hello, Alan!"
 * }
 * </pre>
 * </p>
 *
 * Key Features:
 * <ul>
 *     <li>Annotated with {@code @WebService} to mark this class as a SOAP web service.</li>
 *     <li>Exposes a single public method {@code sayHello}, annotated with {@code @WebMethod},
 *         for client interaction.</li>
 * </ul>
 *
 * Prerequisites:
 * <ul>
 *     <li>The service must be hosted on a SOAP server, such as via {@link jakarta.xml.ws.Endpoint}.</li>
 *     <li>Clients must connect to the published endpoint to consume this service.</li>
 * </ul>
 *
 * @author Alan Ryan
 * @version 1.0
 */

@WebService
public class HelloService {

    @WebMethod
    public String sayHello(String name) {
        return "Hello, " + name + "!";
    }
}
