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
        insertProductsOrderRelation(createdOrder);
        return createdOrder;
    }

    @Override
    public Order update(Order order) {
        Order updatedOrder = updateOrderInOrdersTable(order);
        deleteProductsOrderRelation(updatedOrder);
        insertProductsOrderRelation(updatedOrder);
        return updatedOrder;
    }

    @Override
    public Optional<Order> getById(Long id) {
        Optional<Order> order = getOrderFromOrdersTable(id);
        if (order.isPresent()) {
            order.get().setProducts(getProductList(id));
        }
        return order;
    }

    @Override
    public List<Order> getUserOrders(Long userId) {
        List<Order> userOrders = getUserOrdersFromOrdersTable(userId);
        for (Order order : userOrders) {
            order.setProducts(getProductList(order.getId()));
        }
        return userOrders;
    }

    @Override
    public boolean delete(Long id) {
        return deleteOrderFromOrdersTable(id);
    }

    @Override
    public List<Order> getAll() {
        List<Order> allOrders = getAllOrdersFromOrderTable();
        for (Order order : allOrders) {
            List<Product> orderProductList = getProductList(order.getId());
            order.setProducts(orderProductList);
        }
        return allOrders;
    }

    private List<Order> getUserOrdersFromOrdersTable(Long userId) {
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
            return usersOrders;
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can't get orders list with user id: " + userId, e);
        }
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

    private List<Product> getProductList(Long orderId) {
        String getProductListQuery = "SELECT p.product_id, p.name, p.price FROM products p "
                + "INNER JOIN orders_products op ON p.product_id = op.product_id"
                + " WHERE op.order_id = ? AND isDeleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(getProductListQuery)) {
            statement.setLong(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            List<Product> productList = new ArrayList<>();
            while (resultSet.next()) {
                productList.add(getProduct(resultSet));
            }
            return productList;
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can't get product list with order id: " + orderId, e);
        }
    }

    private Optional<Order> getOrderFromOrdersTable(Long id) {
        String getOrderQuery = "SELECT order_id, user_id FROM orders WHERE order_id = ? "
                + "AND isDeleted = false";
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

    private boolean insertProductsOrderRelation(Order order) {
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

    private boolean deleteProductsOrderRelation(Order order) {
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
