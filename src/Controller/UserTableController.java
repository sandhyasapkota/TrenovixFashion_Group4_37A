package controller;

import dao.UserTableDao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.UserTable;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import view.Login;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author sandhya sapkota
 */
public class UserTableController {
    private final UserTable view;
    private final UserTableDao dao = new UserTableDao();
    private final UserTable usertable = new UserTable();
    private final Login login = new Login();

    public UserTableController(UserTable view) {
        this.view = view;
    }

    public void loadUsers() {
        List<Object[]> users = dao.getAllUsers();
        DefaultTableModel model = (DefaultTableModel) view.getTable().getModel();
        model.setRowCount(0);
        for (Object[] row : users) {
            model.addRow(row);
        }
    }
    class LogoutBtnActionPerformed implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.dispose(); // Use the actual view
            Login login = new Login();
            login.setVisible(true);
            LoginController loginController = new LoginController(login);
            loginController.open();
        }
    }
     class LogoutBtn2ActionPerformed implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.dispose(); // Use the actual view
            Login login = new Login();
            login.setVisible(true);
            LoginController loginController = new LoginController(login);
            loginController.open();
        }
     }
    
}
