/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package trenovixfashion_group4_37a;
import database.*;
import view.Login;
import controller.LoginController;

/**
 *
 * @author NITRO V 15
 */
public class TrenovixFashion_Group4_37A {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Login loginView = new Login();
                LoginController loginController = new LoginController(loginView);
                loginView.setLocationRelativeTo(null);
                loginController.open();
            }
        });
    }
}
