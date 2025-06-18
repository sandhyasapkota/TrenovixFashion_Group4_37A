package controller;

import dao.OrderDao;
import model.Order;
import model.OrderItem;
import model.CartManager;
import model.Product;
import view.CheckoutForm;
import view.Cart;
import view.OrderConfirmation;
import model.Session;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class CheckoutController {
    private final CheckoutForm view;
    private final Cart cartWindow;
    private final OrderDao orderDao = new OrderDao();


    public CheckoutController(CheckoutForm view, Cart cartWindow) {
        this.view = view;
        this.cartWindow = cartWindow;
        this.view.getOrderBtn().addActionListener(new PlaceOrderListener());
    }

    class PlaceOrderListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Order order = new Order();
            order.setUserId(Session.currentUserId);
            order.setFirstName(view.getFirstName().getText());
            order.setLastName(view.getLastName().getText());
            order.setPhone(view.getPhoneNumber().getText());
            order.setEmail(view.getEmail().getText());
            order.setProvince(view.getProvince().getSelectedItem().toString());
            order.setCity(view.getCity().getSelectedItem().toString());
            order.setPostalCode(view.getPostalCode().getText());
            order.setStreetAddress(view.getStreetAddress().getText());
            order.setTole(view.getTole().getText());

            // Get cart items
            List<Product> cartProducts = CartManager.getCartItems(Session.currentUserId);
            List<OrderItem> items = new ArrayList<>();
            double total = 0;
            for (Product p : cartProducts) {
                OrderItem item = new OrderItem();
                item.setProductId(p.getId());
                item.setQuantity(p.getQuantity());
                item.setPrice(p.getPrice());
                total += p.getPrice() * p.getQuantity();
                items.add(item);
            }
            order.setItems(items);

            if (orderDao.placeOrder(order)) {
                JOptionPane.showMessageDialog(view, "Order placed successfully!");
                CartManager.clearCart(Session.currentUserId);
                if (cartWindow != null) {
                    cartWindow.loadCartItems();
                }
                // Show order confirmation
                OrderConfirmation confirmation = new OrderConfirmation();
                confirmation.setOrderDetails(order, total);
                confirmation.setLocationRelativeTo(null);
                confirmation.setVisible(true);
                view.dispose();
            } else {
                JOptionPane.showMessageDialog(view, "Failed to place order.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}