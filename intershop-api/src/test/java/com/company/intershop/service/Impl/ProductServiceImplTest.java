package com.company.intershop.service.Impl;

import com.company.intershop.domain.Product;
import com.company.intershop.repository.ProductRepository;
import com.company.intershop.service.ProductServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static com.company.intershop.util.TestConstants.FILE_NAME;
import static com.company.intershop.util.TestConstants.FILE_PATH;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productService;

    @MockBean
    private ProductRepository productRepository;

    @Test
    public void findProductById() {
        Product product = new Product();
        product.setId(123L);

        when(productRepository.findById(123L)).thenReturn(java.util.Optional.of(product));
        productService.getProductById(123L);
        assertEquals(123L, product.getId());
        assertNotEquals(1L, product.getId());
        verify(productRepository, times(1)).findById(123L);
    }

    @Test
    public void findAllProducts() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product());
        productList.add(new Product());
        productService.getProducts();

        when(productRepository.findAllByOrderByTitle()).thenReturn(productList);
        assertEquals(2, productList.size());
        verify(productRepository, times(1)).findAllByOrderByTitle();
    }

    @Test
    public void findByTitleContainingIgnoreCaseOrderByTitle() {
        Product product1 = new Product();
        product1.setTitle("Product1");
        Product product2 = new Product();
        product2.setTitle("Product2");
        List<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);

        String filter = "product";

        when(productRepository.findByTitleContainingIgnoreCaseOrderByTitle(filter)).thenReturn(productList);
        productService.getProductsContainingText(filter);
        assertEquals(2, productList.size());
        assertTrue(productList.get(0).getTitle().toLowerCase().contains(filter));
        verify(productRepository, times(1)).findByTitleContainingIgnoreCaseOrderByTitle(filter);
    }

    @Test
    public void findByProductOrderByPriceDesc() {

    }


    @Test
    public void saveProduct() {
        MultipartFile multipartFile = new MockMultipartFile(FILE_NAME, FILE_NAME, "multipart/form-data", FILE_PATH.getBytes());
        Product product = new Product();
        product.setPoster(multipartFile.getOriginalFilename());

        when(productRepository.save(product)).thenReturn(product);
        productService.saveProduct(product);
        verify(productRepository, times(1)).save(product);
    }
}
