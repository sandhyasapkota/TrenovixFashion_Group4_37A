package controller;

import dao.ProductDao;
import model.Product;
import view.AddProduct;
import view.ItemsPage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProductController {
    private final AddProduct view;
    private final ProductDao dao = new ProductDao();

    public ProductController(AddProduct view) {
        this.view = view;
        this.view.addAddProductListener(new AddProductListener());
    }

    public void open() {
        view.setVisible(true);
    }
    public void close() {
        this.view.dispose();
    }

    class AddProductListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String category = view.getSelectedCategory();
                String name = view.getProductName();
                String size = view.getSelectedDescription();
                String description = view.getDescription();
                String imageUrl = view.getImageUrl();
                int quantity = view.getSelectedStock();
                double price = Double.parseDouble(view.getPrice());

                if (name.isEmpty() || category.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Name and Category are required.");
                    return;
                }

                Product product = new Product(name, size, price, category, imageUrl, quantity,description);
                boolean success = dao.addProduct(product);

                if (success) {
                    JOptionPane.showMessageDialog(view, "Product added successfully!");
                    view.dispose();
                } else {
                    JOptionPane.showMessageDialog(view, "Failed to add product.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Invalid price or stock value.");
            }
        }
    }

    // --- Add this static helper method ---
    public static void attachAddProductToItemsPage(ItemsPage itemsPage) {
        itemsPage.addAddProductListener(e -> {
            AddProduct addProductView = new AddProduct();
            new ProductController(addProductView);

            addProductView.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    itemsPage.loadProducts();
                }
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    itemsPage.loadProducts();
                }
                
            });

            addProductView.setVisible(true);
        });
    }
}