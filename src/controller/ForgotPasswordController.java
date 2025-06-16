package controller;

import dao.Forgot;
import view.ForgotPassword;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class ForgotPasswordController {
    private final ForgotPassword forgotPasswordView;
    private final Forgot forgotDao = new Forgot();
    private final Validation validation = new Validation();

    public ForgotPasswordController(ForgotPassword forgotPasswordView) {
        this.forgotPasswordView = forgotPasswordView;
        this.forgotPasswordView.ResetPasswordListener(new ResetPasswordListener());
    }

    public void open() {
        forgotPasswordView.setVisible(true);
    }

    class ResetPasswordListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = forgotPasswordView.getUsername();
            String email = forgotPasswordView.getEmail();
            String securityAnswer = forgotPasswordView.getSecurityAnswer();
            String newPassword = forgotPasswordView.getNewPassword();

            if (username.isEmpty() || email.isEmpty() || securityAnswer.isEmpty() || newPassword.isEmpty()) {
                JOptionPane.showMessageDialog(forgotPasswordView, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!validation.isValidusername(username)) {
                JOptionPane.showMessageDialog(forgotPasswordView, "Input valid username", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!validation.isValidEmail(email)) {
                JOptionPane.showMessageDialog(forgotPasswordView, "Input valid email", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!validation.isValidPassword(newPassword)) {
                JOptionPane.showMessageDialog(forgotPasswordView, "Input valid password", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean updated = forgotDao.updatePasswordWithSecurityAnswer(username, email, securityAnswer, newPassword);
            if (updated) {
                JOptionPane.showMessageDialog(forgotPasswordView, "Password updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                forgotPasswordView.dispose();
                System.out.println(forgotPasswordView.getSecurityAnswer());

            } else {
                JOptionPane.showMessageDialog(forgotPasswordView, "Invalid details or security answer.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}