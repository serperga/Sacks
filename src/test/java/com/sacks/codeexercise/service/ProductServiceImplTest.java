package com.sacks.codeexercise.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.sacks.codeexercise.error.ProductNotFoundException;
import com.sacks.codeexercise.model.ProductStockInformation;
import com.sacks.codeexercise.model.entities.Product;
import com.sacks.codeexercise.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    private ProductServiceImpl productService;

    @BeforeEach
    void init(){
        productService = new ProductServiceImpl(productRepository);
    }

    @Test
    void givenACallToRetrieveAllProductWhenServiceIsExecutedThenAListOfProductsIsReturned(){
        List<Product> productList = createProductsForTesting();
        lenient().when(productRepository.findAll()).thenReturn(productList);

        List<ProductStockInformation> productStockInformationList = productService.getAllProducts();
        assertThat(productStockInformationList.size()).isEqualTo(2);

        ProductStockInformation product1 = productStockInformationList.get(0);
        assertThat(product1.getProductId()).isEqualTo(1);
        assertThat(product1.getName()).isEqualTo("Product 1");
        assertThat(product1.getPrice()).isEqualTo(10.0);
        assertThat(product1.getQuantity()).isEqualTo(10);
        ProductStockInformation product2 = productStockInformationList.get(1);
        assertThat(product2.getProductId()).isEqualTo(2);
        assertThat(product2.getName()).isEqualTo("Product 2");
        assertThat(product2.getPrice()).isEqualTo(20.0);
        assertThat(product2.getQuantity()).isEqualTo(20);
    }

    @Test
    public void givenAProductIdNonExistingWhenServiceTriesToRetrieveInformationThenProductNotFoundExceptionIsThrown(){

        Optional<Product> productInformation = Optional.empty();
        lenient().when(productRepository.findProductByProductId(100L)).thenReturn(productInformation);

        Exception exception = assertThrows(ProductNotFoundException.class, () -> {
            productService.getProduct(100L);
        });

        String expectedMessage = "Product is not found";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void givenAProductIdExistingInStoreWhenServiceTriesToRetrieveInformationThenProductInformationIsRetrieved(){

        Product product = createProductInformationForTesting();
        lenient().when(productRepository.findProductByProductId(1L)).thenReturn(Optional.of(product));

        ProductStockInformation productStockInformation = productService.getProduct(1L);
        assertThat(productStockInformation.getProductId()).isEqualTo(1);
        assertThat(productStockInformation.getName()).isEqualTo("Product 1");
        assertThat(productStockInformation.getPrice()).isEqualTo(10.0);
        assertThat(productStockInformation.getQuantity()).isEqualTo(10);
    }

    private List<Product> createProductsForTesting(){
        List<Product> productList = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId(1);
        product1.setPrice(10.0);
        product1.setQuantity(10);
        product1.setName("Product 1");

        Product product2 = new Product();
        product2.setProductId(2);
        product2.setPrice(20.0);
        product2.setQuantity(20);
        product2.setName("Product 2");

        productList.add(product1);
        productList.add(product2);

        return productList;
    }

    private Product createProductInformationForTesting(){
        Product product1 = new Product();
        product1.setProductId(1);
        product1.setPrice(10.0);
        product1.setQuantity(10);
        product1.setName("Product 1");

        return product1;
    }
}
