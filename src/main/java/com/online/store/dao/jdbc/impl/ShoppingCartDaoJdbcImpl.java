package com.online.store.dao.jdbc.impl;

import com.online.store.dao.ShoppingCartDao;
import com.online.store.exceptions.DataProcessingException;
import com.online.store.lib.Dao;
import com.online.store.model.Product;
import com.online.store.model.ShoppingCart;
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
public class ShoppingCartDaoJdbcImpl implements ShoppingCartDao {
    @Override
    public ShoppingCart create(ShoppingCart cart) {
        ShoppingCart createdShoppingCart = addShoppingCartToShoppingCartsTable(cart);
        setProductsToShoppingCartInDb(createdShoppingCart);
        return createdShoppingCart;
    }

    @Override
    public ShoppingCart update(ShoppingCart cart) {
        ShoppingCart updatedShoppingCart = updateShoppingCartInShoppingCartTable(cart);
        deleteProductsFromShoppingCartInDb(cart);
        setProductsToShoppingCartInDb(cart);
        return updatedShoppingCart;
    }

    @Override
    public Optional<ShoppingCart> getById(Long id) {
        Optional<ShoppingCart> shoppingCart = getFromShoppingCartTableById(id);
        return getAndSetProductList(shoppingCart);
    }

    @Override
    public Optional<ShoppingCart> getByUserId(Long userId) {
        Optional<ShoppingCart> shoppingCart = getFromShoppingCartTableByUserId(userId);
        return getAndSetProductList(shoppingCart);
    }

    @Override
    public boolean delete(Long id) {
        deleteProductsFromShoppingCartInDb(getById(id).get());
        return deleteFromShoppingCartTable(id);
    }

    @Override
    public List<ShoppingCart> getAll() {
        List<ShoppingCart> allShoppingCarts = getAllFromShoppingCartsTable();
        for (ShoppingCart cart : allShoppingCarts) {
            getAndSetProductList(Optional.of(cart));
        }
        return allShoppingCarts;
    }

    private List<ShoppingCart> getAllFromShoppingCartsTable() {
        String getAllShoppingCartsQuery = "SELECT * FROM shopping_carts WHERE isDeleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(getAllShoppingCartsQuery)) {
            List<ShoppingCart> allShoppingCarts = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                allShoppingCarts.add(getShoppingCart(resultSet));
            }
            return allShoppingCarts;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all shopping carts", e);
        }
    }

    private ShoppingCart addShoppingCartToShoppingCartsTable(ShoppingCart cart) {
        String createShoppingCartQuery = "INSERT INTO shopping_carts (user_id) VALUES (?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(createShoppingCartQuery,
                                Statement.RETURN_GENERATED_KEYS)) {
            ShoppingCart createdShoppingCart = new ShoppingCart(cart.getUserId());
            createdShoppingCart.setProducts(cart.getProducts());
            statement.setLong(1, cart.getUserId());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                createdShoppingCart.setId(generatedKeys.getLong(1));
            }
            return createdShoppingCart;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create shopping cart ", e);
        }
    }

    private ShoppingCart updateShoppingCartInShoppingCartTable(ShoppingCart cart) {
        String updateShoppingCartQuery = "UPDATE shopping_carts SET user_id = ? "
                + "WHERE shopping_cart_id = ? AND isDeleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(updateShoppingCartQuery)) {
            statement.setLong(1, cart.getUserId());
            statement.setLong(2, cart.getId());
            int numberOfUpdatedRows = statement.executeUpdate();
            if (numberOfUpdatedRows != 0) {
                return cart;
            }
            throw new DataProcessingException(
                    "Can't find shopping cart with id: " + cart.getId() + "to update it");
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can't update shopping cart with id: " + cart.getId(), e);
        }
    }

    private Optional<ShoppingCart> getAndSetProductList(Optional<ShoppingCart> cart) {
        if (cart.isEmpty()) {
            return Optional.empty();
        }
        String getProductList = "SELECT p.product_id, p.name, p.price FROM products p "
                + "INNER JOIN shopping_carts_products sp ON p.product_id = sp.product_id"
                + " WHERE sp.shopping_cart_id = ? AND isDeleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(getProductList)) {
            statement.setLong(1, cart.get().getId());
            ResultSet resultSet = statement.executeQuery();
            List<Product> productList = new ArrayList<>();
            while (resultSet.next()) {
                productList.add(getProduct(resultSet));
            }
            cart.get().setProducts(productList);
            return cart;
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can't get products from shopping cart with id: " + cart.get().getId(), e);
        }
    }

    private boolean deleteFromShoppingCartTable(Long id) {
        String deleteShoppingCartQuery = "UPDATE shopping_carts SET isDeleted = true"
                + " WHERE shopping_cart_id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(deleteShoppingCartQuery)) {
            statement.setLong(1, id);
            int numberOfUpdatedRows = statement.executeUpdate();
            return numberOfUpdatedRows != 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete shopping cart with id: " + id, e);
        }
    }

    private Optional<ShoppingCart> getFromShoppingCartTableByUserId(Long userId) {
        String getShoppingCartByUserIdQuery = "SELECT shopping_cart_id, user_id "
                + "FROM shopping_carts WHERE user_id = ? AND isDeleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(getShoppingCartByUserIdQuery)) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getShoppingCart(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can't get shopping cart with user id: " + userId, e);
        }
    }

    private Optional<ShoppingCart> getFromShoppingCartTableById(Long id) {
        String getShoppingCartByIdQuery = "SELECT shopping_cart_id, user_id FROM shopping_carts "
                + "WHERE shopping_cart_id = ? AND isDeleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(getShoppingCartByIdQuery)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getShoppingCart(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get shopping cart with id: " + id, e);
        }
    }

    private boolean deleteProductsFromShoppingCartInDb(ShoppingCart cart) {
        String deleteProductsQuery = "DELETE FROM shopping_carts_products "
                + "WHERE shopping_cart_id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(deleteProductsQuery)) {
            statement.setLong(1, cart.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete products from shopping cart ", e);
        }
    }

    private boolean setProductsToShoppingCartInDb(ShoppingCart cart) {
        String setProductsQuery = "INSERT INTO shopping_carts_products "
                + "(shopping_cart_id, product_id) VALUES (?,?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(setProductsQuery)) {
            int totalNumberOfUpdatedRows = 0;
            statement.setLong(1, cart.getId());
            for (Product product : cart.getProducts()) {
                statement.setLong(2, product.getId());
                int numberOfUpdatedRows = statement.executeUpdate();
                totalNumberOfUpdatedRows += numberOfUpdatedRows;
            }
            if (totalNumberOfUpdatedRows == cart.getProducts().size()) {
                return true;
            }
            throw new DataProcessingException(
                    "Not all products have been assigned to the shopping cart");
        } catch (SQLException e) {
            throw new DataProcessingException("Can't set products to shopping cart ", e);
        }
    }

    private ShoppingCart getShoppingCart(ResultSet resultSet) {
        try {
            long shoppingCartId = resultSet.getLong("shopping_cart_id");
            long userId = resultSet.getLong("user_id");
            ShoppingCart cart = new ShoppingCart(userId);
            cart.setId(shoppingCartId);
            return cart;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get shopping cart from resultSet", e);
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

