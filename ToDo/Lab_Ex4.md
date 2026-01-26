
# Lab Exercise :four:


In this lab exercise you will build on your solution to Lab Exercise 2. I would reccoment that you create a new controller with two endpoints - (`GET /products` and `GET /products/{id}`) that will return HATEOAS-compliant representations of a Product(s).

## 1. Add the following dependency for HATEOAS to the POM

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-hateoas</artifactId>
</dependency>
```

## 2. Refactor ProductV2.java
You are going to return HATEOAS representations of the ProductV2 DTO from Lab Ex 2, so you will need to refactor the class definition to provide HATEOAS support:

```java
public class ProductV2 extends RepresentationModel<ProductV2>  {
```

### 3. Retrieve All Products `GET /products`

This endpoint fetches all products and should wrap them in a HATEOAS CollectionModel. Each product it to include a self-link to its own resource as well as a link to all products.    

You should already have an endpoint which retrieves and returns all product in your controller that you can refactor.


| ![image](https://github.com/user-attachments/assets/8a723456-fb8d-42a0-9120-617d56213f98) |
|:--------------------------------------------------------------------------------------:|
| **Each product has a self link while the collection itself has a link to all products (a self-link in itself)** |

 

### 4. Retrieve a Single Product `GET /products/{id}`

This endpoint fetches a single product by its ID and provides:
- A self-link to the product resource.
- A link to the collection of all products.

_You will need to add an endpoint in your controller to retrieve a product by ID, as it's not currently included in your solution to Lab Ex 2. Don't forget to implement the corresponding logic in the service layer_


| ![image](https://github.com/user-attachments/assets/b11a6690-0a54-499d-b409-341e2077d327)|
|:--------------------------------------------------------------------------------------:|
| **The product has a self link and a link to all products** |


| ![image](https://github.com/user-attachments/assets/356f2a88-84d4-4b30-b54f-6a744b62cbdf)|
|:--------------------------------------------------------------------------------------:|
| **Return a 404 Not Found response if the user attempts to retrieve a product that does not exist** |




### 5. Add caching to `GET /products` 

Simulate a delay in the controller to test this.  

Add an end-point `GET /clear-cache` to clear the cache.


