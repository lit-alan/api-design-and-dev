package com.example.apigateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.util.UUID;

@Component
public class RequestLoggingFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(RequestLoggingFilter.class);

    /**
     * Correlation ID header used to track a single request across multiple services.
     *
     * If the incoming request does not include this header, the API Gateway generates
     * a unique ID and adds it before forwarding the request to downstream services.
     * Each service can then include this ID in its logs.
     *
     * This makes it possible to trace a request across multiple services and helps
     * with debugging, performance analysis, and operational monitoring.
     *
     * Note: This is not required for an API Gateway to function. It is simply a
     * common observability practice in distributed systems.
     */
    private static final String CORRELATION_HEADER = "X-Correlation-Id";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        //client request arrives at the API Gateway
        ServerHttpRequest request = exchange.getRequest();

        //Retrieve correlation ID if provided by client, otherwise generate one
        String headerId = request.getHeaders().getFirst(CORRELATION_HEADER);
        final String correlationId = (headerId != null) ? headerId : UUID.randomUUID().toString();

        // Extract basic request information
        InetSocketAddress remoteAddress = request.getRemoteAddress();
        String clientIp = remoteAddress != null
                ? remoteAddress.getAddress().getHostAddress()
                : "unknown";

        String method = request.getMethod().name();
        String path = request.getURI().getPath();

        //start timing the request
        long start = System.currentTimeMillis();

        //dttach the correlation ID before forwarding the request to the service
        ServerHttpRequest mutatedRequest = request.mutate()
                .header(CORRELATION_HEADER, correlationId)
                .build();

        //Log the incoming request at the gateway
        log.info("Incoming request method={} path={} clientIp={} correlationId={}",
                method, path, clientIp, correlationId);

        //Forward the request to the downstream service
        return chain.filter(exchange.mutate().request(mutatedRequest).build())

                //This callback runs when the response returns from the service
                .doFinally(signal -> {

                    long duration = System.currentTimeMillis() - start;

                    int status = exchange.getResponse().getStatusCode() != null
                            ? exchange.getResponse().getStatusCode().value()
                            : 0;

                    //og completion of the request once the response has been processed
                    log.info("Completed request method={} path={} status={} duration={}ms correlationId={}",
                            method, path, status, duration, correlationId);
                });
    }
    @Override
    public int getOrder() {
        return -1;
    }
}