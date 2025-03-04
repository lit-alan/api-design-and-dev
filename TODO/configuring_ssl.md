## Adding SSL support to a Spring Boot project involves configuring the application to use HTTPS by setting up an SSL certificate. :lock:

## 1. Obtain or Generate an SSL Certificate
   
  You can either:
- Use a self-signed certificate for testing (what we will be doing).
- Obtain a certificate from a Certificate Authority from the likes of [Let's Encrypt](https://letsencrypt.org/) or [DigiCert](https://www.digicert.com/) (which is outside the scope of this module).  

We will use the [Java Keytool](https://docs.oracle.com/javase/8/docs/technotes/tools/unix/keytool.html) to generate a self-signed certificate. Keytool is a key and certificate management utility. It enables users to administer their own public/private key pairs and associated certificates for use in self-authentication


Before proceeding to generate the cert using the Keytool, I would recommend that you create a folder called `keystore`in an easy to locate location like the `C:\` on your computer. _The folder can be called anything you like and can be located anywhere_ 

Open the command prompt and enter the followig command:  
`keytool -genkeypair -alias alans_keystore -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore "C:\keystore\keystore.p12" -validity 365`

|Switch	|Meaning|
|---    |---    |
|keytool	|Java's built-in tool for managing keys and certificates.|
|genkeypair |Generates a key pair (public + private key) and stores it in a keystore. |
|-alias alans_keystore	| A unique name (alias) for the key in the keystore. This is required to reference the key later. |
|-keyalg RSA	| Specifies the key algorithm. RSA is widely used for encryption and signing. |
|-keysize 2048	| The size of the key in bits. 2048 bits is secure and commonly used. |
|-storetype PKCS12	| Specifies the keystore format. PKCS12 is a cross-platform format, recommended over the older JKS format. |
| keystore "C:\keystore\keystore.p12"	| The path and filename where the keystore will be saved. |
|-validity 365	| The number of days the key is valid before it expires (in this case, 1 year). |

**What Does This Command Does**

Creates a new keystore file:
 - Saves it as keystore.p12 inside `C:\keystore\`
 - If the file already exists, it will prompt for a password to update it.

Generates an RSA key pair:
  - 2048-bit strength (secure for most applications).
  - The private key is stored securely in the keystore.

Creates a self-signed certificate:
  - This certificate is valid for 365 days.
  - It will prompt you for details like your name, organisation, and location. For Example:

| ![image](https://github.com/user-attachments/assets/7ad3b6a7-93a9-4b71-9ba9-8f703440d697) |
|:--------------------------------------------------------------------------------------:|
| **Creating the self signed cert using keytool** |

_When prompted to enter a password, I used `changeit`_

_You should be able to run the keytool command from anywhere in the command prompt. However, if the command is not recognised, you need to navigate to the `bin` directory in your Java installation directory before running it. On my laptop, Java 17 is installed at:_


```
C:\Program Files\Java\jdk-17\bin
```

To use keytool, first change to that directory in the command prompt:

```
cd "C:\Program Files\Java\jdk-17\bin"
```

Then run the keytool command as above.

| ![image](https://github.com/user-attachments/assets/94c50eac-0b84-441e-afa1-3836750ab5d0) |
|:--------------------------------------------------------------------------------------:|
| **They keystore file will now be created in the target directory** |  
<br>

## 2. Copy the .keystore file into the resources folder of your project in IntelliJ

| ![image](https://github.com/user-attachments/assets/ef179e7b-3e05-4428-ad41-e2bd6be38f89) |
|:--------------------------------------------------------------------------------------:|
| **Copying the .keystore file into your project** |  
   


<br>

## 3. Configure Spring Boot for SSL

Add the following to the `.properties` file of your project

```.properties
server.port=8443
server.ssl.key-store=classpath:keystore.p12
server.ssl.key-store-password=changeit
server.ssl.key-store-type=PKCS12
server.ssl.key-alias=alans_keystore
```

| Property	| What It Does|
|---    |---    |
|server.port=8443	|Sets the server port to 8443, which is the default HTTPS port. This means your Spring Boot app will be accessible at https://localhost:8443.
|server.ssl.key-store=classpath:keystore.p12	|Specifies the location of the keystore that contains the SSL certificate. Since classpath: is used, Spring Boot will look for keystore.p12 inside the src/main/resources/ directory.
|server.ssl.key-store-password=changeit	|The password required to access the keystore. This should match the password you used when generating the keystore file with keytool. (changeit is the default password for Java keystores, but you should change it to a secure value.)
|server.ssl.key-store-type=PKCS12	|Defines the keystore format. PKCS12 (.p12 or .pfx) is recommended because it is widely supported and preferred over the older JKS (Java KeyStore) format.
|server.ssl.key-alias=alans_keystore	|Specifies the alias of the key inside the keystore. This must match the alias used when creating the keystore with keytool (e.g., -alias alans_keystore).



## 4. Test the cert

I have a simple endpoint (`\books`) to return all books in a REST Controller. For Example: 

```java
@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("")
    public List<Book> getAllBooks() {
        return bookService.findAll();

    }
}
```

  
Navigate to `https://localhost:8443/books`in your browser - I am using Edge here but regarldess of what browser you are using the steps will be similiar. The browser will alert you to the fact the the connection isn't secure (as you are using a self-signed cert). Click on 'Advanced'.  

| ![image](https://github.com/user-attachments/assets/1d77391c-26e4-4ac2-8ae3-627630b37a08) |
|:--------------------------------------------------------------------------------------:|


<br>

Click on the option to continue to `localhost`.

| ![image](https://github.com/user-attachments/assets/1ba76eda-3a32-4cf3-81c3-193086ecb912)|
|:--------------------------------------------------------------------------------------:|


<br>

The data will now be presented to you over a secure/https connection.

| ![image](https://github.com/user-attachments/assets/d1ba0464-7bcd-443a-a001-0c19b8b6c49c)|
|:--------------------------------------------------------------------------------------:|


<br>
When using a self-signed certificate, a browser places a line through HTTPS or shows a "Not Secure" warning because the certificate is self-signed (not issued by a trusted source).




