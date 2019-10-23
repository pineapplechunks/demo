package no.kristiania.http;

import no.kristiania.jdbc.ProductDao;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;


public class ProductDaoTest {

    private ProductDao productDao;

    @BeforeEach
    void setUp() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:product-test;DB_CLOSE_DELAY=-1");

        Flyway.configure().dataSource(dataSource).load().migrate();

        productDao = new ProductDao(dataSource);
    }

    @Test
    void shouldRetrieveSavedProduct() {
        Product product = sampleProduct();
        assertThat(product).hasNoNullFieldsOrPropertiesExcept("id");

        long id = productDao.insert(product);
        assertThat(productDao.retrieve(id))
                .isEqualToComparingFieldByField(product);
    }

    @Test
    void shouldListAllProduct() {
        Product product1 = sampleProduct();
        productDao.insert(product1);
        Product product2 = sampleProduct();
        productDao.insert(product2);

        assertThat(productDao.listAll())
                .extracting(Product::getName)
                .contains(product1.getName(), product1.getName());
    }

    private Product sampleProduct() {
        Product product = new Product();
        product.setName(pickOne(new String[] {"apples", "bananas", "pears", "grapes"}));
    }

    private Random random = new Random();

    private String pickOne(String[] alternatives) {
        return alternatives[random.nextInt(alternatives.length)];
    }
}
