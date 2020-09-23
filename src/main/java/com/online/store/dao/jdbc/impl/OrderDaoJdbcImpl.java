package com.online.store.dao.jdbc.impl;

import com.online.store.dao.OrderDao;
import com.online.store.exceptions.DataProcessingException;
import com.online.store.lib.Dao;
import com.online.store.model.Order;
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
public class OrderDaoJdbcImpl implements OrderDao {
    @Override
    public Order create(Order order) {
        Order createdOrder = createOrderInOrdersTable(order);
        setProductsToOrder(createdOrder);
        return createdOrder;
    }

    @Override
    public Order update(Order order) {
        Order updatedOrder = updateOrderInOrdersTable(order);
        deleteProductsFromOrder(updatedOrder);
        setProductsToOrder(updatedOrder);
        return updatedOrder;
    }

    @Override
    public Optional<Order> getById(Long id) {
        Optional<Order> order = getOrderFromOrdersTable(id);
        return getAndSetProductList(order);
    }

    @Override
    public List<Order> getUserOrders(Long userId) {
        String getUserOrdersQuery = "SELECT order_id, user_id FROM orders "
                + "WHERE user_id = ? AND isDeleted = false";
        List<Order> usersOrders = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(getUserOrdersQuery)) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                usersOrders.add(getOrder(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can't get orders list with user id: " + userId, e);
        }
        for (Order order : usersOrders) {
            getAndSetProductList(Optional.of(order));
        }
        return usersOrders;
    }

    @Override
    public boolean delete(Long id) {
        deleteOrderFromOrdersTable(id);
        deleteProductsFromOrder(getById(id).get());
        return true;
    }

    @Override
    public List<Order> getAll() {
        List<Order> allOrders = getAllOrdersFromOrderTable();
        for (Order order : allOrders) {
            getAndSetProductList(Optional.of(order));
        }
        return allOrders;
    }

    private List<Order> getAllOrdersFromOrderTable() {
        String getAllOrdersQuery = "SELECT * FROM orders WHERE isDeleted = false";
        List<Order> allOrders = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(getAllOrdersQuery)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                allOrders.add(getOrder(resultSet));
            }
            return allOrders;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all orders", e);
        }
    }

    private boolean deleteOrderFromOrdersTable(Long id) {
        String deleteOrderQuery = "UPDATE orders SET isDeleted = true WHERE order_id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(deleteOrderQuery)) {
            statement.setLong(1, id);
            int numberOfDeletedRows = statement.executeUpdate();
            return numberOfDeletedRows != 0;
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can't delete order with id: " + id, e);
        }
    }

    private Optional<Order> getAndSetProductList(Optional<Order> order) {
        if (order.isEmpty()) {
            return Optional.empty();
        }
        String getProductListQuery = "SELECT p.product_id, p.name, p.price FROM products p "
                + "INNER JOIN orders_products op ON p.product_id = op.product_id"
                + " WHERE op.order_id = ? AND isDeleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(getProductListQuery)) {
            statement.setLong(1, order.get().getId());
            ResultSet resultSet = statement.executeQuery();
            List<Product> productList = new ArrayList<>();
            while (resultSet.next()) {
                productList.add(getProduct(resultSet));
            }
            order.get().setProducts(productList);
            return order;
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can't get product list with order id: " + order.get().getId(), e);
        }
    }

    private Optional<Order> getOrderFromOrdersTable(Long id) {
        String getOrderQuery = "SELECT order_id, user_id FROM orders WHERE order_id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(getOrderQuery)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getOrder(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get order with id: " + id, e);
        }
    }

    private boolean setProductsToOrder(Order order) {
        String setProductsQuery = "INSERT INTO orders_products (order_id, product_id)"
                + " VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(setProductsQuery)) {
            int totalNumberOfCreatedRows = 0;
            statement.setLong(1, order.getId());
            for (Product product : order.getProducts()) {
                statement.setLong(2, product.getId());
                int numberOfCreatedRows = statement.executeUpdate();
                totalNumberOfCreatedRows += numberOfCreatedRows;
            }
            if (totalNumberOfCreatedRows == order.getProducts().size()) {
                return true;
            }
            throw new DataProcessingException("Not all products added to order");
        } catch (SQLException e) {
            throw new DataProcessingException("Can't set products to order", e);
        }
    }

    private Order createOrderInOrdersTable(Order order) {
        String createOrderQuery = "INSERT INTO orders (user_id) VALUES (?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(createOrderQuery, Statement.RETURN_GENERATED_KEYS)) {
            Order createdOrder = new Order(order.getUserId());
            createdOrder.setProducts(order.getProducts());
            statement.setLong(1, order.getUserId());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                createdOrder.setId(generatedKeys.getLong(1));
            }
            return createdOrder;
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can't create order with user id: " + order.getUserId(), e);
        }
    }

    private boolean deleteProductsFromOrder(Order order) {
        String deleteProducts = "DELETE FROM orders_products WHERE order_id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(deleteProducts)) {
            statement.setLong(1, order.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete products from order ", e);
        }
    }

    private Order updateOrderInOrdersTable(Order order) {
        String updateOrderQuery = "UPDATE orders SET user_id = ? "
                + "WHERE order_id = ? AND isDeleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(updateOrderQuery)) {
            statement.setLong(1, order.getUserId());
            statement.setLong(2, order.getId());
            int numberOfUpdatedRows = statement.executeUpdate();
            if (numberOfUpdatedRows != 0) {
                return order;
            }
            throw new DataProcessingException(
                    "Can't find order with id: " + order.getId() + " to update it");
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can't update order with order id: " + order.getId(), e);
        }
    }

    private Order getOrder(ResultSet resultSet) {
        try {
            long orderId = resultSet.getLong("order_id");
            long userId = resultSet.getLong("user_id");
            Order order = new Order(userId);
            order.setId(orderId);
            return order;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get order from resultSet", e);
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
