package finalProject;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginView extends JPanel {
    JTextField emailField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JButton loginButton = new JButton("Login");
    JLabel forgotPasswordLabel = new JLabel("Forgot password?", SwingConstants.CENTER);
    JCheckBox rememberMeCheck = new JCheckBox("Remember for 30 days");

    public LoginView() {
        setLayout(new BorderLayout());
        initializeUIComponents();
        setupPlaceholders();
    }

    private void initializeUIComponents() {
        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(400, 600));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("FASHION", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        JLabel welcomeLabel = new JLabel("Welcome back!");
        welcomeLabel.setFont(new Font("Sans-serif", Font.BOLD, 20));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel instructionLabel = new JLabel("Enter your Credentials to access your account");
        instructionLabel.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel emailPanel = createInputPanel("Email", emailField);
        JPanel passwordPanel = createInputPanel("Password", passwordField);

        loginButton.setMaximumSize(new Dimension(300, 40));
        loginButton.setBackground(new Color(0, 102, 0));
        loginButton.setForeground(Color.WHITE);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setFont(new Font("Sans-serif", Font.BOLD, 16));

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

        String imagePath = "C:\\Users\\Chamikara\\eclipse-workspace\\catfish\\src\\catfish\\leaf.png";
        ImageIcon imageIcon = new ImageIcon(imagePath);
        Image scaledImage = imageIcon.getImage().getScaledInstance(1200, 900, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
        imageLabel.setHorizontalAlignment(JLabel.CENTER);

        add(leftPanel, BorderLayout.WEST);
        add(imageLabel, BorderLayout.CENTER);
    }

    private JPanel createInputPanel(String labelText, JTextField field) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setMaximumSize(new Dimension(300, 50));
        panel.setBackground(Color.WHITE);
        panel.add(new JLabel(labelText));
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

    public String getEmail() {
        return emailField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public void addLoginListener(ActionListener listener) {
        loginButton.addActionListener(listener);
    }

    public void displayMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}

