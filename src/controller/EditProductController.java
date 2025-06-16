package controller;

import dao.ProductDao;
import model.Product;
import view.EditProduct;

import javax.swing.*;

public class EditProductController {
    private final EditProduct view;
    private final ProductDao dao = new ProductDao();

    public EditProductController(EditProduct view) {
        this.view = view;
        this.view.addEditProductListener(e -> updateProduct());
    }

    private void updateProduct() {
        try {
            String name = view.getProductName();
            String size = view.getSelectedSize();
            String category = view.getSelectedCategory();
            String imageUrl = view.getImageUrl();
            int quantity = view.getSelectedQuantity();
            double price = Double.parseDouble(view.getPrice());
            int productId = view.getProductId();

            if (name == null || name.isEmpty() || category == null || category.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Name and Category are required.");
                return;
            }

            Product updatedProduct = new Product(productId, name, size, price, category, imageUrl, quantity);
            boolean success = dao.updateProduct(updatedProduct);

            if (success) {
                JOptionPane.showMessageDialog(view, "Product updated successfully!");
                view.dispose();
            } else {
                JOptionPane.showMessageDialog(view, "Failed to update product.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Invalid price or quantity value.");
        }
    }
}