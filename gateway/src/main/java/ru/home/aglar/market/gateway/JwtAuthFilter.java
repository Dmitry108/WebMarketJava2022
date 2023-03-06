package ru.home.aglar.market.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthFilter extends AbstractGatewayFilterFactory<JwtAuthFilter.Config> {
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public JwtAuthFilter(JwtTokenUtil jwtTokenUtil) {
        super(Config.class);
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest httpRequest = exchange.getRequest();
            if (!isAuthMissing(httpRequest)) {
                final String token = getAuthHeader(httpRequest);
                if (jwtTokenUtil.isInvalid(token)) {
                    return this.onError(exchange, "Authorization header is invalid", HttpStatus.UNAUTHORIZED);
                }
                populateRequestWithHeaders(exchange, token);
            }
            return chain.filter(exchange);
        };
    }

    public static class Config {
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private String getAuthHeader(org.springframework.http.server.reactive.ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty("Authorization").get(0).substring(7);
    }

    private boolean isAuthMissing(org.springframework.http.server.reactive.ServerHttpRequest request) {
        if (!request.getHeaders().containsKey("Authorization")) {
            return true;
        }
        if (!request.getHeaders().getOrEmpty("Authorization").get(0).startsWith("Bearer ")) {
            return true;
        }
        return false;
    }

    private void populateRequestWithHeaders(ServerWebExchange exchange, String token) {
        exchange.getRequest().mutate()
                .header("username", jwtTokenUtil.getUsernameFromToken(token))
                .header("role", jwtTokenUtil.getRolesFromToken(token).get(0))
                .build();
    }
}