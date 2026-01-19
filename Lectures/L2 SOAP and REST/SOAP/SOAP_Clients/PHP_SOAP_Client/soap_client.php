<?php

/**
 * A simple PHP SOAP client script for consuming a Java-based SOAP service.
 * 
 * This script demonstrates how to:
 * - Initialize a {@link SoapClient} instance using a WSDL URL.
 * - Call a SOAP service method with parameters.
 * - Handle the SOAP response and potential errors.
 * 
 * Prerequisites:
 * - A running Java-based SOAP service available at the specified WSDL URL.
 * - The `SoapClient` class must be enabled in your PHP configuration.
 * 
 * Installation Instructions (for XAMPP users):
 * 1. Ensure XAMPP is installed and running on your system.
 *    - Start the Apache server from the XAMPP control panel.
 * 2. Verify that the `SoapClient` extension is enabled in PHP:
 *    - Open the `php.ini` file located in the XAMPP installation directory (e.g., `C:\xampp\php\php.ini`).
 *    - Find the line `;extension=soap` and remove the `;` to uncomment it, making it `extension=soap`.
 *    - Save the changes and restart the Apache server via the XAMPP control panel.
 * 3. Deploy the Java-based SOAP service and ensure it is accessible at the WSDL URL (`http://localhost:8080/hello?wsdl`).
 * 4. Save this script as a `.php` file (e.g., `soap_client.php`) in the `htdocs` directory of your XAMPP installation (e.g., `C:\xampp\htdocs\soap_client.php`).
 * 5. Open a browser and navigate to `http://localhost/soap_client.php` to run the script.
 * 
 * Key Components:
 * - {@code $wsdlUrl}: The WSDL URL of the SOAP service.
 * - {@code $options}: Optional configurations for the SOAP client, such as WSDL caching.
 * - {@code sayHello()}: A SOAP service method that returns a greeting message.
 * 
 * Notes:
 * - This script assumes that the SOAP response contains a `return` property with the expected output.
 * - Proper exception handling is implemented to manage {@link SoapFault} exceptions.
 * 
 * @author Your Name
 * @version 1.0
 */

// The WSDL URL of the Java-based SOAP service
$wsdlUrl = 'http://localhost:8080/hello?wsdl';

// Options for the SOAP client (optional, for non-WSDL mode, proxy, etc.)
$options = [
    'cache_wsdl' => WSDL_CACHE_NONE,
];

// Initialize the SOAP client
// SoapClient is a built-in class that enables PHP scripts to communicate with SOAP servers
$client = new SoapClient($wsdlUrl, $options);

// Prepare the parameters for the sayHello method
$params = ['arg0' => 'Alan'];

// Call the sayHello method
try {
    $response = $client->sayHello($params);
    echo $response->return; // Assuming the service returns a property named 'return'
} catch (SoapFault $e) {
    // Handle exceptions
    echo "SOAP Fault: " . $e->getMessage();
}
?>



//The WSDL URL of the Java-based SOAP service
$wsdlUrl = 'http://localhost:8080/hello?wsdl';

//Options for the SOAP client (optional, for non-WSDL mode, proxy, etc.)
$options = [
    'cache_wsdl' => WSDL_CACHE_NONE,
];

//Initialize the SOAP client
//SoapClient is a built-in class that enables PHP scripts to communicate with SOAP servers
$client = new SoapClient($wsdlUrl, $options);

//Prepare the parameters for the sayHello method
$params = ['arg0' => 'Alan'];

//Call the sayHello method
try {
    $response = $client->sayHello($params);
    echo $response->return; //Assuming the service returns a property named 'return'
} catch (SoapFault $e) {
    //Handle exceptions
    echo "SOAP Fault: " . $e->getMessage();
}
?>

