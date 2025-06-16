package controller;

import dao.UserTableDao;

import java.util.List;
import view.UserManagement;
import view.Login;
import javax.swing.table.DefaultTableModel;

public class UserTableController {
    private final UserManagement view;
    private final UserTableDao dao = new UserTableDao();
    private final Login login;

    public UserTableController(UserManagement userTable, Login login) {
        this.view = userTable;
        this.login = login;

    }

    public void loadUsers() {
        List<Object[]> users = dao.getAllUsers();
        DefaultTableModel model = (DefaultTableModel) view.getTable().getModel();
        model.setRowCount(0);
        for (Object[] row : users) {
            model.addRow(row);
        }
    }


    public void showUserTable() {
        view.setVisible(true);
        view.addLogoutListener(e -> {
            view.dispose();      // Dispose the current UserManagement window
            login.setVisible(true); // Show the login window
        });
    }

}
