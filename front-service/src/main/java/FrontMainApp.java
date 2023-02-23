import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@RestController
@RequiredArgsConstructor
public class FrontMainApp {
    private final RestTemplate restTemplate;

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable Long id) {
        return restTemplate.getForObject("http://product-service/api/v1/products/" + id, ProductDto.class);

    }

    @GetMapping
    public Page<ProductDto> getAllProducts(@RequestParam(name = "p", defaultValue = "1") Integer page,
                                           @RequestParam(name = "min_price", required = false) Integer minPrice,
                                           @RequestParam(name = "max_price", required = false) Integer maxPrice) {
        if (page < 1) page = 1;
        return (Page<ProductDto>) restTemplate.getForObject("http://product-service/api/v1/products/", Page.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(FrontMainApp.class, args);
    }

}