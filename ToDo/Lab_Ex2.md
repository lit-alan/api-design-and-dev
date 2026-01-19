# Lab Exercise :two:

:point_right: Students are encouraged to limit or eliminate the use of Generative AI for this task as it is not being assessed and relying on AI will offer little value in terms of learning or skill development in this instance. I will publish a complete solution next week.



Create a Spring Boot project in IntelliJ and a dependency for `Spring Web` to it.   

The focus of this lab exercise is to guide you through the different ways that an API can be versioned. Two versions of a `product` will be maintained The key difference between v1 and v2 is that v2 includes a product `rating` in the API response, providing a small detail not present in v1.

To begin create a package called `model` and add the following classes to it.

```java
/**
 * Represents the core data model for a product in the application.
 * This class serves as the central entity for product-related operations,
 * containing all fields necessary for internal processing and storage.
 *
 * <p>This class is the definitive data model for a product within the application
 * API responses should use version-specific DTOs (e.g., {@link ProductV1},
 * {@link ProductV2}) to ensure backward compatibility and to control exposed fields.</p>
 *
 * <p>Fields:</p>
 * <ul>
 *   <li><b>id</b>: The unique identifier of the product.</li>
 *   <li><b>name</b>: The name of the product.</li>
 *   <li><b>description</b>: A brief description of the product's features.</li>
 *   <li><b>price</b>: The price of the product.</li>
 *   <li><b>rating</b>: The average user rating of the product, ranging from 1 to 5.</li>
 * </ul>
 */
public class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    private int rating;

    public Product(int id) {
        this.id = id;
    }

    public Product(int id, String name, String description, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.rating = rating;
    }
    public Product(int id, String name, String description, double price, int rating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", rating=" + rating +
                '}';
    }
}
```

_Note this exercise requires the use of DTOs. They are necessary here to separate the internal data model (Product) from the API responses, ensuring backward compatibility and allowing each API version to expose only the fields relevant to its clients._  

```java

/**
 * Data Transfer Object (DTO) for version 1 of the Product API.
 * Represents a simplified product view without a rating field.
 *
 * <p>This DTO is used for the v1 API response to ensure backward compatibility
 * and maintain a minimal data structure for older clients.</p>
 *
 * <p>Fields:</p>
 * <ul>
 *   <li><b>id</b>: The unique identifier of the product.</li>
 *   <li><b>name</b>: The name of the product.</li>
 *   <li><b>description</b>: A brief description of the product.</li>
 *   <li><b>price</b>: The price of the product.</li>
 * </ul>
 */

public class ProductV1 {

    private int id;
    private String name;
    private String description;
    private double price;

    public ProductV1(int id, String name, String description, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

```

```java
/**
 * Data Transfer Object (DTO) for version 2 of the Product API.
 * Represents an enhanced product view that includes a rating field.
 *
 * <p>This DTO is used for the v2 API response, providing additional product
 * details to meet the requirements of newer clients.</p>
 *
 * <p>Fields:</p>
 * <ul>
 *   <li><b>id</b>: The unique identifier of the product.</li>
 *   <li><b>name</b>: The name of the product.</li>
 *   <li><b>description</b>: A brief description of the product.</li>
 *   <li><b>price</b>: The price of the product.</li>
 *   <li><b>rating</b>: The average user rating of the product, ranging from 1 to 5.</li>
 * </ul>
 */

public class ProductV2 {
    private int id;
    private String name;
    private String description;
    private double price;
    private int rating;

    public ProductV2(int id, String name, String description, double price, int rating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
```


Create a package called `service` and add the following class to it.


```java
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final Map<Integer, Product> productMap = Map.of(
            1, new Product(1, "Wireless Headphones", "Noise-cancelling over-ear headphones", 199.99),
            2, new Product(2, "Smartphone", "Latest model with 128GB storage and 5G connectivity", 899.00),
            3, new Product(3, "Laptop", "15-inch laptop with 16GB RAM and 512GB SSD", 1299.50),
            4, new Product(4, "Gaming Console", "Next-gen gaming console with 1TB storage", 499.99),
            5, new Product(5, "Electric Kettle", "1.7L electric kettle with temperature control", 59.99),
            6, new Product(6, "Smartwatch", "Fitness tracking smartwatch with GPS and heart rate monitor", 149.95),
            7, new Product(7, "Bluetooth Speaker", "Portable speaker with 12-hour battery life", 79.99),
            8, new Product(8, "4K TV", "55-inch 4K UHD Smart TV with HDR support", 699.00),
            9, new Product(9, "Air Purifier", "HEPA filter air purifier for large rooms", 249.50),
            10, new Product(10, "Robot Vacuum Cleaner", "Wi-Fi connected robot vacuum with app control", 399.00)
    );

    //this is a basic version without ratings
    public List<ProductV1> getAllProductsSimple() {
        return productMap.values().stream()
                .map(product -> new ProductV1(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice()
                ))
                .collect(Collectors.toList());
    }

    //a more detailed version with product ratings
    public List<ProductV2> getAllProductsDetailed() {
        return productMap.values().stream()
                .map(product -> new ProductV2(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        generateRandomRating() //Set the rating dynamically for v2
                ))
                .collect(Collectors.toList());
    }

    //Generate a random rating between 1 and 5
    private int generateRandomRating() {
        return (int) (1 + Math.random() * 5); 
    }
}
```
## Task 1

### Basic API Versioning with URL's.  

Create a package called `controllers` and add the following two controllers to it.


```java
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductControllerV1 {

    private final ProductService productService;

    public ProductControllerV1(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductV1> getProducts() {
        return productService.getAllProductsSimple();
    }
}
```

```java
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v2/products")
class ProductControllerV2 {

    private final ProductService productService;

    public ProductControllerV2(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductV2> getProducts() {
        return productService.getAllProductsDetailed();
    }
}
```

**Test both endpoints for v1 and v2:**

| ![image](https://github.com/user-attachments/assets/bc580917-4965-4f90-adc3-e2518dfcbaca) |
|:--------------------------------------------------------------------------------------:|
| **Testing v1 of the api, note that the rating is not included in this version** |


|![image](https://github.com/user-attachments/assets/d7bea700-24d6-42c0-9498-ce84f59d83f3) |
|:--------------------------------------------------------------------------------------:|
| **Testing v2 of the api, note that the rating is included in this version** |



## Task 2

### Modify the API to support versioning via query parameters (e.g., ?version=1 or ?version=2).

Example:

`GET /api/products?version=1` returns data in v1 format.  
`GET /api/products?version=2` returns data in v2 format.  
`GET /api/products` If no query parameters are specified the response should default to v1    

In this implementation, API versions (v1 and v2) are not expressed in the URL path but are instead determined using a query parameter (?version=1 or ?version=2) 

```java
@RestController
@RequestMapping("/api/products")
class ProductControllerQueryParam {

    private final ProductService productService;

    public ProductControllerQueryParam(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public Object getProducts(@RequestParam(required = false, defaultValue = "1") String version) {
        if ("1".equals(version)) {
           //ToDo....
        }
  }

```

*_Test the code_*

|![image](https://github.com/user-attachments/assets/eb510cc9-4bd4-4090-993c-b5f4e804cda3) |
|:--------------------------------------------------------------------------------------:|
| **Testing v1 of the api using query params**





## Task 3

### Modify the API to support versioning using custom HTTP headers (e.g., X-API-Version).

Example:  
Client includes `X-API-Version: 1` in the request header to receive v1 format.  
Client includes `X-API-Version: 2` in the request header to receive v2 format.  
If no header is sent in the request, the response should default to v1      


```java

@RestController
@RequestMapping("/api/products")
class ProductControllerHeader {

    private final ProductService productService;

    public ProductControllerHeader(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public Object getProducts(@RequestHeader(value = "X-API-Version", defaultValue = "1") String version) {
       //ToDo
}

```

:warning: In the above code, the mapping for the controller is the same as the mapping for the controller in [Task 2](#task-2). Two controllers/endpoints in the same project cannot have the same mapping. So either change the mapping for this controller or comment out the code for task 2.  


*_Test the code_*

| ![image](https://github.com/user-attachments/assets/9fe7b99b-8540-4a30-9e56-3bd6c151733e) |
|:--------------------------------------------------------------------------------------:|
| **Requesting v1 via the custom X-API-Version header** |



| ![image](https://github.com/user-attachments/assets/97cc3ff0-a4e5-4c0f-a267-f10735e2a884)  |
|:--------------------------------------------------------------------------------------:|
| **Requesting v2 via the custom X-API-Version header** |


| ![image](https://github.com/user-attachments/assets/f952ba6c-94a5-4011-bcf6-6d095d3c06a7) |
|:--------------------------------------------------------------------------------------:|
| **Not sending the X-API-Version header in the request will see response default to v1** |



| ![image](https://github.com/user-attachments/assets/6ded1db4-23ec-42d0-8d52-b73ee8888f79)  |
|:--------------------------------------------------------------------------------------:|
| **Request for a version that is not v1 or v2 will see response default to v1** |







## Task 4

### Modify the API to versioning using the Accept header (e.g., Accept: application/vnd.example.v1+json).

Example:  

If the `Accept` header contains v1, return a specific JSON format for v1.  
If the `Accept` header contains v2, return an updated JSON format for v2.
If no header is sent in the request, the response should default to v1.    


```java

@RestController
@RequestMapping("/api/products")
class ProductControllerContentNegotiation {

    private final ProductService productService;

    public ProductControllerContentNegotiation(ProductService productService) {
        this.productService = productService;
    }

   //A suggestion woold be to replace 'myapp' with something unique to your application or domain.
    @GetMapping(produces = "application/vnd.myapp.v1+json")
    public ResponseEntity<List<ProductV1>> getProductsV1() {
         //ToDo
    }

    //A suggestion woold be to replace 'myapp' with something unique to your application or domain.
    @GetMapping(produces = "application/vnd.myapp.v2+json")
    public ResponseEntity<List<ProductV2>> getProductsV2() {
        //ToDo
    }

}

```

:warning: In the above code, the mapping for the controller is the same as the mappings for the controllers in [Task 2](#task-2) and [Task 3](#task-3). Two controllers/endpoints in the same project cannot have the same mapping. So either change the mapping for this controller or comment out the code for tasks 2 and 3.  


*_Test the code_*

| ![image](https://github.com/user-attachments/assets/4edc3f6a-f8c2-4b3a-a8ec-bd7929eb56d0) |
|:--------------------------------------------------------------------------------------:|
| **Requesting v1 via the accept header** |


| ![image](https://github.com/user-attachments/assets/23e7b46f-d03a-4b69-8bd2-b81e598b0aeb)  |
|:--------------------------------------------------------------------------------------:|
| **Requesting v2 via the accept header** |



## Task 5

### Modify task 4 to simulate deprecating v1 of the API by returning a Warning header in the response.

| ![image](https://github.com/user-attachments/assets/06fe9e7a-b1d9-4396-841f-a0cffa0c305f) |
|:--------------------------------------------------------------------------------------:|
| **Requesting v1 will see a warning returned in a custom warning header in the response** |


