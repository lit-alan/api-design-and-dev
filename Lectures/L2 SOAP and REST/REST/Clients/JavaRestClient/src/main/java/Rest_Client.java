import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;


/**
 * A simple REST client for making HTTP requests and processing JSON responses.
 * <p>
 * This class demonstrates how to:
 * <ul>
 *     <li>Send a GET request to a REST API endpoint using {@link HttpClient}.</li>
 *     <li>Parse the JSON response using the {@link ObjectMapper} from the Jackson library.</li>
 *     <li>Handle HTTP responses and errors appropriately.</li>
 * </ul>
 * The client fetches a list of authors from the specified API endpoint and prints the parsed data.
 * </p>
 *
 * <p>
 * Example usage:
 * <pre>
 * {@code
 * public static void main(String[] args) {
 *     Rest_Client.main(args);
 * }
 * }
 * </pre>
 * </p>
 *
 * Dependencies:
 * <ul>
 *     <li>{@code com.fasterxml.jackson.core:jackson-databind}</li>
 *     <li>{@code java.net.http.HttpClient} (introduced in Java 11)</li>
 * </ul>
 *
 * @author Alan Ryan
 * @version 1.0
 */


public class Rest_Client {
    public static void main(String[] args) {
        String url = "http://localhost:8080/api/v2/authors";

        try {
            //Create an HttpClient instance
            HttpClient client = HttpClient.newHttpClient();

            //Create the request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Accept", "application/json")
                    .build();

            //Send the request and get the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            //Check if the response is successful
            if (response.statusCode() == 200) {
                //Parse the JSON response
                ObjectMapper objectMapper = new ObjectMapper();
                List<Author> authors = objectMapper.readValue(response.body(), new TypeReference<List<Author>>() {});

                //Use the parsed JSON data as needed
                System.out.println(authors);
            } else {
                System.out.println("Request failed with status code: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
