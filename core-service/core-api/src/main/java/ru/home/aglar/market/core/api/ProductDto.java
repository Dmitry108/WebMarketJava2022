package ru.home.aglar.market.core.api;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Model of product")
public class ProductDto {
    @Schema(description = "Id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;
    @Schema(description = "Title", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 255, minLength = 3, example = "Apple")
    private String title;
    @Schema(description = "price", requiredMode = Schema.RequiredMode.REQUIRED, example = "120.00")
    private Integer price;

    public ProductDto() {
    }

    public ProductDto(Long id, String title, Integer price) {
        this.id = id;
        this.title = title;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
