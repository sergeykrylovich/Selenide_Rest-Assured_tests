package tests;

import db.entities.Product;
import org.junit.jupiter.api.Test;
import services.ProductDbService;

import static org.assertj.core.api.Assertions.assertThat;


public class DBTests {

    ProductDbService productDbService;


    @Test
    public void insertIntoDB() {
        String name = "Bread";
        String productId = "100is";
        int version = 2;
        int price = 55;
        productDbService = new ProductDbService();
        Product product = Product.builder().productId(productId).price(price).version(version).name(name).build();
        productDbService.save(product);
        Product actualProduct = productDbService.getProductByName(name);
        System.out.println(actualProduct.getName());
        assertThat(actualProduct.getName()).isEqualTo(name);
    }
}
