/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

// import controller.ProductController.AddProductListener; // Removed unused and incorrect import
import dao.UserDao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Product;
import view.ProductCard;
import view.ProductDetails;
import view.Signup;

/**
 *
 * @author sandhya sapkota
 */

public class PdetailsController {
    private final UserDao userDao = new UserDao();
    private final ProductCard userView;
    private final Validation validation = new Validation();

    public PdetailsController(ProductCard userView) {
        this.userView = userView;
        userView.addProductListener(new addProductListener());
    }
    public void open() {
        userView.setVisible(true);
    }

    public void close() {
        // Close or hide the ProductCard view
        userView.setVisible(false);
    }

    class addProductListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                // TODO: Add your action handling code here
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            Product product = null;
            ProductDetails details = new ProductDetails(product);
            details.setVisible(true);
            PdetailsController controller = new PdetailsController(userView);
            controller.open();
        }
    }
    public void showProductDetails(Product product) {
//       ProductDetails details = new ProductDetails(product);
//        details.setVisible(true);
//        PdetailsController controller = new PdetailsController(userView);
//        controller.open();
        
    }
}

