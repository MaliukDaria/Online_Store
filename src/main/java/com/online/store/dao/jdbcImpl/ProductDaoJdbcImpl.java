package com.online.store.dao.jdbcImpl;

import com.online.store.dao.ProductDao;
import com.online.store.lib.Dao;
import com.online.store.model.Product;
import com.online.store.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class ProductDaoJdbcImpl implements ProductDao {
    @Override
    public Product create(Product item) {
        String query = "INSERT INTO products (name, price) VALUES (?, ?)";
        Product product = new Product(item.getName(), item.getPrice());
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection
                    .prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, item.getName());
            statement.setDouble(2, item.getPrice());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                product.setId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't create product", e);
        }
        return product;
    }

    @Override
    public Product update(Product item) {

        return null;
    }

    @Override
    public Optional<Product> getById(Long id) {
        String query = "SELECT * FROM products WHERE product_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long product_id = resultSet.getLong("product_id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                Product product = new Product(name, price);
                product.setId(product_id);
                return Optional.of(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't get product by id", e);
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public List<Product> getAll() {
        String query = "SELECT * FROM products";
        List<Product> products = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long product_id = resultSet.getLong("product_id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                Product product = new Product(name, price);
                product.setId(product_id);
                products.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't get all products", e);
        }
        return products;
    }
}
