package com.example.l7_ssl.filters;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * A filter that redirects all incoming HTTP requests to HTTPS.
 *
 * <p>
 * This filter intercepts every request and checks if it is using the HTTP protocol.
 * If so, it constructs the equivalent HTTPS URL and performs a redirection.
 * This ensures that all communication is secured using HTTPS.
 * </p>
 *
 * <p>
 * The redirection occurs only if the request is detected as HTTP.
 * If the request is already using HTTPS, it is processed normally.
 * </p>
 */
@Component
public class HttpsRedirectFilter implements Filter {

    /**
     * Intercepts all incoming requests and redirects HTTP traffic to HTTPS.
     *
     * <p>
     * This method inspects the request scheme and, if it is "http",
     * constructs a new URL using "https" and redirects the client to it.
     * </p>
     *
     * @param request  the incoming {@link ServletRequest}
     * @param response the outgoing {@link ServletResponse}
     * @param chain    the {@link FilterChain} to pass the request along if no redirection occurs
     * @throws IOException      if an I/O error occurs during redirection
     * @throws ServletException if a servlet error occurs
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        //Check if the request is using HTTP
        if (req.getScheme().equals("http")) {
            String httpsUrl = "https://" + req.getServerName() + ":8443" + req.getRequestURI();
            if (req.getQueryString() != null) {
                httpsUrl += "?" + req.getQueryString();
            }
            System.out.println("Redirecting to: " + httpsUrl);
            res.sendRedirect(httpsUrl);

            //the return statement stops execution of the filter immediately after the redirect
            return;
        }

        //Continue processing if already HTTPS
        chain.doFilter(request, response);
    }
}
