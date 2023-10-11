package com.company.intershop.controller;

import com.company.intershop.rest.dto.ProductRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.company.intershop.dto.GraphQLRequest;
//import com.company.intershop.dto.product.ProductSearchRequest;
//import com.company.intershop.dto.product.SearchTypeRequest;
//import com.company.intershop.enums.SearchProduct;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//import static com.company.intershop.constants.ErrorMessage.PRODUCT_NOT_FOUND;
import static com.company.intershop.constants.ErrorMessage.PRODUCT_NOT_FOUND;
import static com.company.intershop.constants.PathConstants.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/sql/create-products-before.sql", "/sql/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sql/create-products-after.sql", "/sql/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@WithUserDetails("admin")
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

//    private ProductSearchRequest filter;
//    private GraphQLRequest graphQLRequest;

    @Before
    public void init() {
//        List<Integer> prices = new ArrayList<>();
//        List<String> productrs = new ArrayList<>();
//        List<String> genders = new ArrayList<>();
//        productrs.add(ProductR_CHANEL);
//        genders.add(PRODUCT_GENDER);
//        prices.add(1);
//        prices.add(1000);
//
//        filter = new ProductSearchRequest();
//        filter.setProductrs(productrs);
//        filter.setGenders(genders);
//        filter.setPrices(prices);
//        filter.setSortByPrice(true);
//
//        graphQLRequest = new GraphQLRequest();
    }

    @Test
    public void getProducts() throws Exception {
        mockMvc.perform(get("/api/products")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[*].title").isNotEmpty())
                .andExpect(jsonPath("$[*].brand").isNotEmpty())
                .andExpect(jsonPath("$[*].poster").isNotEmpty())
                .andExpect(jsonPath("$[*].price").isNotEmpty());
    }

    @Test
    public void getProductById() throws Exception {
        mockMvc.perform(get("/api/products/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.title", equalTo("I, Tonya")));
    }

    @Test
    public void getProductById_ShouldNotFound() throws Exception {
        mockMvc.perform(get("/api/products/{id}", 1111)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andDo(print());
               // .andExpect(jsonPath("$", equalTo(PRODUCT_NOT_FOUND)));
    }

    @Test
    public void getProductsByIds() throws Exception {
        mockMvc.perform(post("/api/products/cart")
                        .content(mapper.writeValueAsString(Arrays.asList(3L, 4L, 5L)))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[*].title").isNotEmpty())
                .andExpect(jsonPath("$[*].brand").isNotEmpty())
                .andExpect(jsonPath("$[*].poster").isNotEmpty())
                .andExpect(jsonPath("$[*].price").isNotEmpty());
    }

    @Test
    public void getProductsContainingText() throws Exception {
        mockMvc.perform(get("/api/products")
                        .param("text", "Am")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    public void deleteProductById() throws Exception {
        mockMvc.perform(delete("/api/products/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)));
    }

    @Test
    @DisplayName("[200] POST /api/products/add - Add Product")
    public void addProduct() throws Exception {

        String FILE_PATH = "src/test/resources/empty.jpg";
        String FILE_NAME = "product.jpg";

        ProductRequest productRequest = new ProductRequest();
        productRequest.setBrandId(1L);
        productRequest.setTitle("Product 1");
        productRequest.setPrice(999.99f);

        FileInputStream inputFile = new FileInputStream(new File(FILE_PATH));
        MockMultipartFile multipartFile = new MockMultipartFile("file", FILE_NAME, MediaType.MULTIPART_FORM_DATA_VALUE, inputFile);
        MockMultipartFile jsonFile = new MockMultipartFile("product", "",
                MediaType.APPLICATION_JSON_VALUE,
                mapper.writeValueAsString(productRequest).getBytes());
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        mockMvc.perform(multipart("/api/products/add")
                        .file(multipartFile)
                        .file(jsonFile))
                .andExpect(status().isOk());
    }    

}

