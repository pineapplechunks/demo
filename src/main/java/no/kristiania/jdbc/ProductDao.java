package no.kristiania.jdbc;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class ProductDao {
    private DataSource dataSource;

    public ProductDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static void main(String[] args)
        throws IOException, SQLException
    {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl("jdbc:postgres://localhost:5432/demo");

        dataSource.setUser("demo_user");
    }

    Properties properties = new Properties();
    properties.load(new FileReader("demo.properties"));

    dataSource.setPassword(properties.getProperty("dataSource.password"));

    ProductDao productDao = new ProductDao(dataSource);

    Product product = new Product();
    product.setName("apples");
    long id = productDao.insertProduct(product);
    System.out.println("New row id:" + id);
}

private long insertProduct(Product entity) throws SQLException {
    try (Connection connection = dataSource.getConnection()) {
        String sql = "insert into products (name) values (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS))
            stmt.setString(1, entity.getName());
            stmt.executeUpdate();

            ResultSet generatedKeys();
            generatedKeys.next();
            return generatedKeys.getLong("id");
    }
}