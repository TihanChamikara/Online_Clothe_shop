package finalProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.regex.Pattern;

public class Login extends JPanel implements ActionListener {

    // UI components
    JPanel leftPanel = new JPanel();
    JLabel titleLabel = new JLabel("FASHION", SwingConstants.CENTER);
    JLabel welcomeLabel = new JLabel("Welcome back!");
    JLabel instructionLabel = new JLabel("Enter your Credentials to access your account");
    JLabel emailLabel = new JLabel("Email");
    JLabel passwordLabel = new JLabel("Password");
    JTextField emailField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JLabel forgotPasswordLabel = new JLabel("Forgot password?", SwingConstants.RIGHT);
    JButton loginButton = new JButton("Login");
    JCheckBox rememberMeCheck = new JCheckBox("Remember for 30 days");
    JLabel signUpLabel = new JLabel("Don’t have an account? Sign Up");

    // Database credentials
    String url = "jdbc:mysql://localhost:3306/fashiondb?user=root&password=&useUnicode=true&characterEncoding=UTF-8";
    String username = "root";
    String password = "";

    public Login() {
        setLayout(new BorderLayout());
        initializeUIComponents();
        setupPlaceholders();  // Initialize placeholders
        loginButton.addActionListener(this);
    }

    private void initializeUIComponents() {
        // Left side panel setup
        leftPanel.setPreferredSize(new Dimension(400, 600));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(Color.WHITE);

        // Title label setup
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        // Welcome and instruction labels
        welcomeLabel.setFont(new Font("Sans-serif", Font.BOLD, 20));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));

        instructionLabel.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        instructionLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Email and password panels
        JPanel emailPanel = createInputPanel(emailLabel, emailField);
        JPanel passwordPanel = createInputPanel(passwordLabel, passwordField);

        // Forgot password label
        forgotPasswordLabel.setFont(new Font("Sans-serif", Font.PLAIN, 12));
        forgotPasswordLabel.setForeground(Color.BLUE);
        forgotPasswordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Login button setup
        loginButton.setMaximumSize(new Dimension(300, 40));
        loginButton.setBackground(new Color(0, 102, 0));
        loginButton.setForeground(Color.WHITE);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setFont(new Font("Sans-serif", Font.BOLD, 16));

        // Remember me checkbox and sign-up label
        rememberMeCheck.setAlignmentX(Component.CENTER_ALIGNMENT);
        signUpLabel.setFont(new Font("Sans-serif", Font.PLAIN, 12));
        signUpLabel.setForeground(Color.BLUE);
        signUpLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Adding components to left panel
        leftPanel.add(titleLabel);
        leftPanel.add(welcomeLabel);
        leftPanel.add(instructionLabel);
        leftPanel.add(emailPanel);
        leftPanel.add(passwordPanel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        leftPanel.add(forgotPasswordLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        leftPanel.add(loginButton);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        leftPanel.add(rememberMeCheck);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        leftPanel.add(signUpLabel);

        // Image panel (Right side)
        String imagePath = "C:\\Users\\Chamikara\\eclipse-workspace\\catfish\\src\\catfish\\leaf.png";
        ImageIcon imageIcon = new ImageIcon(imagePath);
        Image scaledImage = imageIcon.getImage().getScaledInstance(1200, 900, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setVerticalAlignment(JLabel.CENTER);

        // Adding panels to main layout
        add(leftPanel, BorderLayout.WEST);
        add(imageLabel, BorderLayout.CENTER);
    }

    private JPanel createInputPanel(JLabel label, JTextField field) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setMaximumSize(new Dimension(300, 50));
        panel.setBackground(Color.WHITE);
        panel.add(label);
        field.setPreferredSize(new Dimension(300, 30));
        panel.add(field);
        return panel;
    }

    private void setupPlaceholders() {
        setupPlaceholder(emailField, "Enter your email");
        setupPlaceholder(passwordField, "Enter your password");
    }

    private void setupPlaceholder(JTextField field, String placeholder) {
        field.setText(placeholder);
        field.setForeground(Color.GRAY);

        // Add focus listener to handle placeholder behavior
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }
            public void focusLost(java.awt.event.FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(Color.GRAY);
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        // Validate input
        if (!validateInput(email, password)) return;

        // Attempt to authenticate user
        authenticateUser(email, password);
    }

    private boolean validateInput(String email, String password) {
        if (email.isEmpty() || email.equals("Enter your email")) {
            JOptionPane.showMessageDialog(this, "Please enter an email address.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email address.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (password.isEmpty() || password.equals("Enter your password")) {
            JOptionPane.showMessageDialog(this, "Password is required.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (password.length() < 6) {
            JOptionPane.showMessageDialog(this, "Password must be at least 6 characters long.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void authenticateUser(String email, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection connection = DriverManager.getConnection(url, username, this.password);
                 PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM access WHERE email = ? AND password = ?")) {
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, password);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    JOptionPane.showMessageDialog(this, "Login Successful!", "Message", JOptionPane.INFORMATION_MESSAGE);
                    // Transition to the next interface, e.g., home panel
                    JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                    topFrame.getContentPane().removeAll();
                    topFrame.add(new home(email)); // Add the 'home' panel
                    topFrame.revalidate();
                    topFrame.repaint();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid Username or Password.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                resultSet.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Connection Error", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return Pattern.matches(emailPattern, email);
    }
}
