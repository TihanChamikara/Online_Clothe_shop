package finalProject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {
    private final LoginModel model;
    private final LoginView view;

    public LoginController(LoginModel model, LoginView view) {
        this.model = model;
        this.view = view;

        view.addLoginListener(new LoginButtonListener());
    }

    class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String email = view.getEmail();
            String password = view.getPassword();

            if (email.isEmpty() || email.equals("Enter your email")) {
                view.displayMessage("Please enter an email address.");
                return;
            }
            if (!model.isValidEmail(email)) {
                view.displayMessage("Please enter a valid email address.");
                return;
            }
            if (password.isEmpty() || password.equals("Enter your password")) {
                view.displayMessage("Password is required.");
                return;
            }
            if (password.length() < 6) {
                view.displayMessage("Password must be at least 6 characters long.");
                return;
            }

            if (model.authenticate(email, password)) {
                view.displayMessage("Login Successful!");
                // Transition to the home screen
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(view);
                topFrame.getContentPane().removeAll();
                topFrame.add(new home(email));
                topFrame.revalidate();
                topFrame.repaint();
            } else {
                view.displayMessage("Invalid Username or Password.");
            }
        }
    }
}
