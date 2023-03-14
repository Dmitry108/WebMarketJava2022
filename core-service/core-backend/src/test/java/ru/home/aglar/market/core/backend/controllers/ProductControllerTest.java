package ru.home.aglar.market.core.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.home.aglar.market.core.api.ProductDto;
import ru.home.aglar.market.core.backend.converters.ProductConverter;
import ru.home.aglar.market.core.backend.entities.Product;
import ru.home.aglar.market.core.backend.services.ProductService;
import ru.home.aglar.market.core.backend.validations.ProductValidator;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ProductConverter productConverter;
    @Autowired
    private ProductValidator productValidator;

    @MockBean
    private ProductService productService;

    @Test
    public void getProductByIdTest() throws Exception {
        Product apple = new Product(1L, "Apple", 10);
        ProductDto appleDto = new ProductDto(1L, "Apple", 10);
        Mockito.when(productService.getProductById(1L)).thenReturn(Optional.of(apple));

        mockMvc.perform(get("/api/v1/products/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(result -> jsonPath("$", is(appleDto)));

        mockMvc.perform(get("/api/v1/products/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    public void getAllProductsTest() throws Exception {
        List<Product> products = List.of(
                new Product(1L, "Apple", 10),
                new Product(2L, "Orange", 20));
        Page<Product> page = new PageImpl<>(products);

        List<ProductDto> productDtoList = List.of(
                new ProductDto(1L, "Apple", 10),
                new ProductDto(2L, "Orange", 20));
        Page<ProductDto> pageDto = new PageImpl<>(productDtoList);
        Mockito.when(productService.getAllProducts(1, null, null))
                .thenReturn(page);

        mockMvc.perform(get("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(result -> jsonPath("$", is(productDtoList)));
    }

    @Test
    public void addNewProductTest() throws Exception {
        Product product = new Product(1L, "Apple", 10);
        ProductDto productDto = new ProductDto(1L, "Apple", 10);

        Mockito.when(productService.addProduct(ArgumentMatchers.any())).thenReturn(product);

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(result -> jsonPath("$", is(product)));
    }

    @Test
    public void deleteProductByIdTest() throws Exception {
        Mockito.doNothing().when(productService).deleteProductById(1L);
        mockMvc.perform(delete("/api/v1/products/1"))
                .andDo(print())
                .andExpect(status().isOk());
        Mockito.verify(productService, Mockito.times(1)).deleteProductById(1L);
    }
    @Test
    public void updateProductTest() throws Exception {
        Product apple = new Product(1L, "Apple", 10);
        Product notValid = new Product(1L, "Apple", -10);
        Mockito.doNothing().when(productService).updateProduct(apple);
        mockMvc.perform(put("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(apple)))
                .andDo(print())
                .andExpect(status().isOk());
        Mockito.verify(productService, Mockito.times(1)).updateProduct(ArgumentMatchers.eq(apple));

        mockMvc.perform(put("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(notValid)))
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }
}