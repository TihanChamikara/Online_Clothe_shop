package finalProject;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Itemview extends JPanel {

    private int quantity = 1; 
    private final int price = 5800; 
    public int total = 0;

    // Database connection details
    String url = "jdbc:mysql://localhost:3306/fashionstore?useUnicode=true&characterEncoding=UTF-8";
    String username = "root";
    String password = "";

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                JFrame frame = new JFrame("Product Details");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().add(new Itemview("hh"));
                frame.pack();
                frame.setLocationRelativeTo(null); // Center the frame
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Itemview(String email) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 800));

        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(400, 600));
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setLayout(new BorderLayout());

        String imagePath = "C:\\Users\\Chamikara\\eclipse-workspace\\finalProject\\src\\finalProject\\llm16.jpg"; // Ensure correct image path
        ImageIcon imageIcon = new ImageIcon(imagePath);

        if (imageIcon.getIconWidth() > 0) { 
            Image image = imageIcon.getImage();
            Image scaledImage = image.getScaledInstance(350, 400, Image.SCALE_SMOOTH); 
            JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
            imageLabel.setHorizontalAlignment(JLabel.CENTER);
            leftPanel.add(imageLabel, BorderLayout.CENTER); 
        } else {
            JLabel errorLabel = new JLabel("Image not found");
            errorLabel.setHorizontalAlignment(JLabel.CENTER);
            leftPanel.add(errorLabel, BorderLayout.CENTER); 
        }

        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setPreferredSize(new Dimension(400, 600));
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.EAST; 
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 20); 

        // Title
        JLabel titleLabel = new JLabel("Streetwear Casual Shirt");
        titleLabel.setFont(new Font("Sans-serif", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        rightPanel.add(titleLabel, gbc);

        // Price
        JLabel priceLabel = new JLabel("LKR 5,800.00");
        priceLabel.setFont(new Font("Sans-serif", Font.BOLD, 20));
        gbc.gridy = 1;
        rightPanel.add(priceLabel, gbc);
        
        JLabel discountLabel = new JLabel("Wholesale 10+ pieces, extra 15% off");
        discountLabel.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        discountLabel.setForeground(Color.RED);
        gbc.gridy = 2;
        rightPanel.add(discountLabel, gbc);
        
        JLabel ratingLabel = new JLabel("★★★★☆ 4.0 | 13 Reviews | 321 sold");
        ratingLabel.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        gbc.gridy = 3;
        rightPanel.add(ratingLabel, gbc);

        // Size Buttons
        JPanel sizePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        sizePanel.setBackground(Color.WHITE);
        String[] sizes = {"S", "M", "L", "XL"};
        for (String size : sizes) {
            JButton sizeButton = new JButton(size);
            sizeButton.setPreferredSize(new Dimension(70, 30));
            sizeButton.setFont(new Font("Sans-serif", Font.BOLD, 14));
            sizePanel.add(sizeButton);
        }
        gbc.gridy = 4; // Move to the next row after description
        rightPanel.add(sizePanel, gbc);

        // Quantity label and spinner
        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setFont(new Font("Sans-serif", Font.BOLD, 16));
        gbc.gridy = 5;
        gbc.insets = new Insets(10, 0, 5, 20);
        rightPanel.add(quantityLabel, gbc);

        JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1)); // Min: 1, Max: 100, Step: 1
        quantitySpinner.setPreferredSize(new Dimension(70, 30));
        quantitySpinner.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        gbc.gridy = 6;
        rightPanel.add(quantitySpinner, gbc);

        quantitySpinner.addChangeListener(e -> quantity = (int) quantitySpinner.getValue());

        // Buttons for Buy Now and Back
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);

        JButton buyNowButton = new JButton("Buy Now");
        JButton backButton = new JButton("Back");

        buyNowButton.setPreferredSize(new Dimension(150, 40));
        buyNowButton.setBackground(new Color(210, 180, 140)); 
        buyNowButton.setFont(new Font("Sans-serif", Font.BOLD, 14));
        buttonPanel.add(buyNowButton);
        
        backButton.setPreferredSize(new Dimension(100, 40)); 
        backButton.setFont(new Font("Sans-serif", Font.BOLD, 14));
        backButton.addActionListener(e -> handleBackButtonClick()); 
        buttonPanel.add(backButton);

        gbc.gridy = 7;
        gbc.insets = new Insets(10, 0, 0, 20); 
        rightPanel.add(buttonPanel, gbc);

        buyNowButton.addActionListener(e -> {
            int clotheid = 1; // Or dynamic based on actual item selection
            int price2 = getTotalPrice();
            insertOrderIntoDatabase(clotheid, quantity, price2);
            JOptionPane.showMessageDialog(this, "Purchase successful! Total Price: LKR " + price2, "Purchase Summary", JOptionPane.INFORMATION_MESSAGE);
            openOrderTableView();
        });
        
        backButton.addActionListener(e -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.getContentPane().removeAll();
            topFrame.add(new store(email)); // Replace store with the name of the previous view's class
            topFrame.revalidate();
            topFrame.repaint();
        });

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
    }

    private void insertOrderIntoDatabase(int clotheid, int qunt, int price2) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection connection = DriverManager.getConnection(url, username, password);
                 PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO orders(clotheid, quantity, Price) VALUES (?, ?, ?)")) {
                insertStatement.setInt(1, clotheid);
                insertStatement.setInt(2, qunt);
                insertStatement.setInt(3, price2);
                insertStatement.executeUpdate();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Database connection failed: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openOrderTableView() {
        SwingUtilities.invokeLater(() -> {
            OrderTableView orderTableView = new OrderTableView();
            orderTableView.setVisible(true);
        });
    }

    private void handleBackButtonClick() {
        SwingUtilities.getWindowAncestor(this).dispose();
    }

    public int getTotalPrice() {
        total = quantity * price;
        return total;
    }
}
