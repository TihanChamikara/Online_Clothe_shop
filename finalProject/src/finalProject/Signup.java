package finalProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.regex.Pattern;

public class Signup extends JPanel implements ActionListener {

    // Components
    JPanel leftPanel = new JPanel();
    JLabel titleLabel = new JLabel("FASHION", SwingConstants.CENTER);
    JLabel startLabel = new JLabel("Get Started Now", SwingConstants.CENTER);
    JTextField nameField = new JTextField("Enter your name");
    JTextField emailField = new JTextField("Enter your email");
    JPasswordField passwordField = new JPasswordField("Enter your password");
    JLabel nameLabel = new JLabel("Name");
    JLabel emailLabel = new JLabel("Email");
    JLabel passwordLabel = new JLabel("Password");
    JCheckBox termsCheckBox = new JCheckBox("I agree to the terms & policy");
    JButton signupButton = new JButton("Signup");
    JLabel signInLabel = new JLabel("Have an account? Sign In", SwingConstants.CENTER);

    // Define panels for each input field
    JPanel namePanel = new JPanel();
    JPanel emailPanel = new JPanel();
    JPanel passwordPanel = new JPanel();

    String url = "jdbc:mysql://localhost:3306/fashiondb?useUnicode=true&characterEncoding=UTF-8";
    String username = "root";
    String password = "";

    public Signup() {
        setLayout(new BorderLayout());
        initializeUIComponents();
        signupButton.addActionListener(this);
    }

    private void initializeUIComponents() {
        // Left side - Signup form
        leftPanel.setPreferredSize(new Dimension(400, 600));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(Color.WHITE);

        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        startLabel.setFont(new Font("Sans-serif", Font.BOLD, 20));
        startLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        startLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Panels for each input field
        setupInputPanel(namePanel, nameLabel, nameField);
        setupInputPanel(emailPanel, emailLabel, emailField);
        setupInputPanel(passwordPanel, passwordLabel, passwordField);

        termsCheckBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        termsCheckBox.setBackground(Color.WHITE);

        signupButton.setMaximumSize(new Dimension(300, 40));
        signupButton.setBackground(new Color(0, 128, 0));
        signupButton.setForeground(Color.WHITE);
        signupButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        signupButton.setFont(new Font("Sans-serif", Font.BOLD, 16));

        signInLabel.setFont(new Font("Sans-serif", Font.PLAIN, 12));
        signInLabel.setForeground(Color.GREEN);
        signInLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add components to the left panel
        leftPanel.add(titleLabel);
        leftPanel.add(startLabel);
        leftPanel.add(namePanel);
        leftPanel.add(emailPanel);
        leftPanel.add(passwordPanel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        leftPanel.add(termsCheckBox);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        leftPanel.add(signupButton);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        leftPanel.add(signInLabel);

        // Image panel (Right Side)
        JLabel imageLabel = setupImagePanel();
        add(leftPanel, BorderLayout.WEST);
        add(imageLabel, BorderLayout.CENTER);

        setupPlaceholders();
    }

    private void setupInputPanel(JPanel panel, JLabel label, JTextField field) {
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.setMaximumSize(new Dimension(300, 50));
        panel.setBackground(Color.WHITE);
        panel.add(label);
        field.setPreferredSize(new Dimension(300, 30));
        panel.add(field);
    }

    private JLabel setupImagePanel() {
        String imagePath = "C:\\Users\\Chamikara\\eclipse-workspace\\catfish\\src\\catfish\\leaf.png";
        ImageIcon imageIcon = new ImageIcon(imagePath);
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(1200, 900, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel imageLabel = new JLabel(scaledIcon);
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setVerticalAlignment(JLabel.CENTER);
        return imageLabel;
    }

    private void setupPlaceholders() {
        setupPlaceholder(nameField, "Enter your name");
        setupPlaceholder(emailField, "Enter your email");
        setupPlaceholder(passwordField, "Enter your password");
    }

    private void setupPlaceholder(JTextField field, String placeholder) {
        field.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                }
            }
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == signupButton) {
            String name = nameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            if (!validateInput(name, email, password)) return;

            if (termsCheckBox.isSelected()) {
                insertUserIntoDatabase(name, email, password);
                JOptionPane.showMessageDialog(this, "Account created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                // Navigate to the next page (e.g., 'home' panel)
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                topFrame.getContentPane().removeAll(); // Clear the current content pane
                topFrame.add(new home(email)); // Add the 'home' panel
                topFrame.revalidate();
                topFrame.repaint();
            } else {
                JOptionPane.showMessageDialog(this, "You must agree to the terms & policy", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean validateInput(String name, String email, String password) {
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your name.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (email.isEmpty() || !isValidEmail(email)) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email address.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (password.isEmpty() || password.length() < 6) {
            JOptionPane.showMessageDialog(this, "Password must be at least 6 characters long.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void insertUserIntoDatabase(String name, String email, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection connection = DriverManager.getConnection(url, username, this.password);
                 PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO access(name, email, password) VALUES (?, ?, ?)")) {
                insertStatement.setString(1, name);
                insertStatement.setString(2, email);
                insertStatement.setString(3, password);
                insertStatement.executeUpdate();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Database connection failed: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return Pattern.matches(emailPattern, email);
    }
}
