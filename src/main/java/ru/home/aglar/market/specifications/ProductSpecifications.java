package ru.home.aglar.market.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.home.aglar.market.entities.Product;

public class ProductSpecifications {
    public static Specification<Product> priceGreaterOrEqualsThen(Integer minPrice) {
        return (Specification<Product>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    public static Specification<Product> priceLesserOrEqualsThen(Integer maxPrice) {
        return (Specification<Product>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
    }
}
