package ru.home.aglar.market.cart.backend.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableConfigurationProperties({CoreServiceIntegrationsProperties.class, TimeoutsCoreServiceIntegrationsProperties.class})
@RequiredArgsConstructor
public class CartConfig {
//    @Value("${integration.core-service.url}")
//    private String coreServiceUrl;
    private final CoreServiceIntegrationsProperties integrationProperties;

    @Bean
    public WebClient productServiceWebClient() {
        TcpClient tcpClient = TcpClient
                .create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, integrationProperties.getTimeouts().getConnection())
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(integrationProperties.getTimeouts().getReading(), TimeUnit.MILLISECONDS));
                    connection.addHandlerLast(new WriteTimeoutHandler(integrationProperties.getTimeouts().getWriting(), TimeUnit.MILLISECONDS));
                });
        return WebClient.builder()
                .baseUrl(integrationProperties.getUrl())
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
                .build();
    }
}