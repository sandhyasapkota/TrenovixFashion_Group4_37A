package model;

import dao.CartDao;
import model.Product;
import java.util.List;

public class CartManager {
    private static final CartDao cartDao = new CartDao();

    public static boolean addToCart(int userId, int productId, int quantity) {
        return cartDao.addToCart(userId, productId, quantity);
    }

    public static List<Product> getCartItems(int userId) {
        return cartDao.getCartItems(userId);
    }

    public static void removeFromCart(int userId, int productId) {
        cartDao.removeFromCart(userId, productId);
    }

    public static void clearCart(int userId) {
        cartDao.clearCart(userId);
    }

    public static void updateQuantity(int userId, int productId, int quantity) {
        cartDao.updateQuantity(userId, productId, quantity);
    }
}