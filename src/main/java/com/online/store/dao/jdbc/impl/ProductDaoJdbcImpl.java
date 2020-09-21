package com.online.store.dao.jdbc.impl;

import com.online.store.dao.ProductDao;
import com.online.store.exceptions.DataProcessingException;
import com.online.store.lib.Dao;
import com.online.store.model.Product;
import com.online.store.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class ProductDaoJdbcImpl implements ProductDao {
    @Override
    public Product create(Product product) {
        String query = "INSERT INTO products (name, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            Product createdProduct = new Product(product.getName(), product.getPrice());
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                createdProduct.setId(resultSet.getLong(1));
            }
            return createdProduct;
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can't create product with name " + product.getName(), e);
        }
    }

    @Override
    public Product update(Product product) {
        String query = "UPDATE products SET name = ?, price = ? "
                + "WHERE product_id = ? AND isDeleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setLong(3, product.getId());
            int numberOfUpdatedProducts = statement.executeUpdate();
            if (numberOfUpdatedProducts != 0) {
                return product;
            }
            throw new DataProcessingException(
                    "Can't find product with id: " + product.getId() + " to update it");
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can't update product with id: " + product.getId(), e);
        }
    }

    @Override
    public Optional<Product> getById(Long id) {
        String query = "SELECT * FROM products WHERE product_id = ? AND isDeleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Product product = getProduct(resultSet);
                return Optional.of(product);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get product by id: " + id, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE products SET isDeleted = true WHERE product_id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            int numberOfDeletedProducts = statement.executeUpdate();
            return numberOfDeletedProducts != 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete product by id: " + id, e);
        }
    }

    @Override
    public List<Product> getAll() {
        String query = "SELECT * FROM products WHERE isDeleted = false";
        List<Product> products = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = getProduct(resultSet);
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all products", e);
        }
    }

    private Product getProduct(ResultSet resultSet) {
        try {
            long productId = resultSet.getLong("product_id");
            String name = resultSet.getString("name");
            double price = resultSet.getDouble("price");
            Product product = new Product(name, price);
            product.setId(productId);
            return product;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get product from resultSet", e);
        }
    }
}
