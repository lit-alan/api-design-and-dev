package com.example.l7_ssl.config;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Configuration class to enable HTTP traffic on port 8080.
 * This class adds an additional Tomcat connector that allows
 * incoming HTTP requests, which can later be redirected to HTTPS.
 */
@Configuration
public class HttpToHttpsRedirectConfig {

    /**
     * Configures an additional Tomcat connector to allow HTTP traffic on port 8080.
     *
     * <p>
     * This method creates an HTTP connector and registers it with the Tomcat embedded server.
     * While HTTPS is the default protocol in Spring Boot, this configuration ensures that
     * HTTP requests can still be received, allowing for redirection to HTTPS if required.
     * </p>
     *
     * @return a customizer for the embedded Tomcat server that adds an HTTP connector.
     */
    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> servletContainer() {
        return factory -> {
            Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
            //Enable HTTP on port 8080
            connector.setPort(8080);
            connector.setScheme("http");
            connector.setSecure(false);
            factory.addAdditionalTomcatConnectors(connector);
        };
    }


}
