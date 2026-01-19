<?php

/**
 * A simple PHP script for consuming a REST API using cURL.
 * 
 * This script demonstrates how to:
 * - Initialize a cURL session for making an HTTP GET request.
 * - Set options for the cURL request, including headers and response handling.
 * - Handle potential errors during the request.
 * - Decode a JSON response and use the parsed data.
 * 
 * Prerequisites:
 * - The REST API must be accessible at the specified URL (`http://localhost:8080/api/v2/authors`).
 * - PHP must have the cURL extension enabled. 
 *   - To enable it, edit the `php.ini` file and uncomment the line `extension=curl`, then restart your web server (e.g., Apache or Nginx).
 * 
 * Installation Instructions (for XAMPP users):
 * 1. Ensure XAMPP is installed and running on your system.
 *    - Start the Apache server from the XAMPP control panel.
 * 2. Verify that the `cURL` extension is enabled in PHP:
 *    - Open the `php.ini` file in the XAMPP installation directory (e.g., `C:\xampp\php\php.ini`).
 *    - Find the line `;extension=curl` and remove the `;` to uncomment it, making it `extension=curl`.
 *    - Save the changes and restart the Apache server via the XAMPP control panel.
 * 3. Save this script as a `.php` file (e.g., `rest_client.php`) in the `htdocs` directory of your XAMPP installation (e.g., `C:\xampp\htdocs\rest_client.php`).
 * 4. Open a browser and navigate to `http://localhost/rest_client.php` to run the script.
 * 
 * Key Components:
 * - {@code $url}: The REST API endpoint URL.
 * - {@code curl_init()}: Initializes a new cURL session.
 * - {@code curl_setopt()}: Sets options for the cURL session, such as returning the response as a string and setting request headers.
 * - {@code curl_exec()}: Executes the cURL session to make the HTTP request.
 * - {@code json_decode()}: Decodes the JSON response into a PHP associative array for easier processing.
 * - {@code curl_close()}: Closes the cURL session to release system resources.
 * 
 * Notes:
 * - Ensure the API responds with JSON and includes appropriate CORS headers if accessed from a different domain.
 * - Use proper error handling in production to handle API failures gracefully.
 * - If additional authentication is required (e.g., API keys, tokens), add headers accordingly using `curl_setopt()` with the `CURLOPT_HTTPHEADER` option.
 * 
 * Example API Response:
 * If the API responds with the following JSON:
 * <pre>
 * {
 *   "authors": [
 *     { "id": 1, "name": "Author A" },
 *     { "id": 2, "name": "Author B" }
 *   ]
 * }
 * </pre>
 * The script will output:
 * <pre>
 * Array
 * (
 *     [authors] => Array
 *         (
 *             [0] => Array
 *                 (
 *                     [id] => 1
 *                     [name] => Author A
 *                 )
 *             [1] => Array
 *                 (
 *                     [id] => 2
 *                     [name] => Author B
 *                 )
 *         )
 * )
 * </pre>
 * 
 * @author Alan Ryan
 * @version 1.0
 */


$url = "http://localhost:8080/api/v2/authors";

// Initialize cURL session
$ch = curl_init($url);

// Set options
curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
curl_setopt($ch, CURLOPT_HTTPHEADER, ['Accept: application/json']);

// Execute cURL session
$response = curl_exec($ch);

// Check for errors
if (curl_errno($ch)) {
    echo 'Request Error: ' . curl_error($ch);
} else {
    // Decode JSON response as associative array
    $authors = json_decode($response, true);

    // Use $authors data as needed
    print_r($authors);
}

// Close cURL session
curl_close($ch);
?>
