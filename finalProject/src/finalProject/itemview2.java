package finalProject;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.swing.*;
import javax.swing.event.ChangeEvent;

public class itemview2 extends JPanel {

    private int quantity = 1;
    private final int price = 7800; // Price per item in LKR
    private String clotheId="5";
    private int total = 0; // To store the total price
    
    String url = "jdbc:mysql://localhost:3306/fashionstore";
    String username = "root";
    String password = "";

    
    public itemview2(String email) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 800));

        // Left Panel for Image
        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(400, 600));
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setLayout(new BorderLayout());

        String imagePath = "C:\\Users\\Chamikara\\eclipse-workspace\\finalProject\\src\\finalProject\\t5.png"; // Ensure correct image path
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

        // Right Panel for Details
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setPreferredSize(new Dimension(400, 600));
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 20);

        // Title Label
        JLabel titleLabel = new JLabel("Classic Green Popcorn Shirt");
        titleLabel.setFont(new Font("Sans-serif", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        rightPanel.add(titleLabel, gbc);

        // Price Label
        JLabel priceLabel = new JLabel("LKR " + price + ".00");
        priceLabel.setFont(new Font("Sans-serif", Font.BOLD, 20));
        gbc.gridy = 1;
        rightPanel.add(priceLabel, gbc);

        // Discount Information
        JLabel discountLabel = new JLabel("Wholesale 5+ pieces, extra 10% off");
        discountLabel.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        discountLabel.setForeground(Color.RED);
        gbc.gridy = 2;
        rightPanel.add(discountLabel, gbc);

        // Rating and Reviews
        JLabel ratingLabel = new JLabel("★★★★☆ 4.0 | 3 Reviews | 237 sold");
        ratingLabel.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        gbc.gridy = 3;
        rightPanel.add(ratingLabel, gbc);

        // Description Label
        JLabel detailsLabel = new JLabel("<html>Old Money Dark Green Shirt - epitome of timeless sophistication.</html>");
        detailsLabel.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        gbc.gridy = 4;
        gbc.insets = new Insets(10, 0, 20, 20);
        rightPanel.add(detailsLabel, gbc);

        // Size Selection
        JLabel sizeLabel = new JLabel("Size:");
        sizeLabel.setFont(new Font("Sans-serif", Font.BOLD, 16));
        gbc.gridy = 5;
        gbc.insets = new Insets(0, 0, 5, 20);
        rightPanel.add(sizeLabel, gbc);

        JPanel sizePanel = new JPanel();
        sizePanel.setLayout(new BoxLayout(sizePanel, BoxLayout.X_AXIS));
        sizePanel.setBackground(Color.WHITE);
        sizePanel.add(Box.createHorizontalGlue());

        String[] sizes = {"S", "M", "L", "XL"};
        for (String size : sizes) {
            JButton sizeButton = new JButton(size);
            sizeButton.setPreferredSize(new Dimension(70, 30));
            sizeButton.setFont(new Font("Sans-serif", Font.BOLD, 14));
            sizePanel.add(sizeButton);
            sizePanel.add(Box.createRigidArea(new Dimension(5, 0)));
        }
        gbc.gridy = 6;
        rightPanel.add(sizePanel, gbc);

        // Quantity Label and Spinner
        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setFont(new Font("Sans-serif", Font.BOLD, 16));
        gbc.gridy = 7;
        gbc.insets = new Insets(10, 0, 5, 20);
        rightPanel.add(quantityLabel, gbc);

        JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        quantitySpinner.setPreferredSize(new Dimension(70, 30));
        quantitySpinner.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        gbc.gridy = 8;
        rightPanel.add(quantitySpinner, gbc);
        quantitySpinner.addChangeListener((ChangeEvent e) -> quantity = (int) quantitySpinner.getValue());

        // Buttons for Buy Now, Add to Cart, and Back
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(Color.WHITE);
        GridBagConstraints buttonGbc = new GridBagConstraints();
        buttonGbc.insets = new Insets(5, 5, 5, 5);
        
        JButton backButton = new JButton("Back");
        JButton buyNowButton = new JButton("Buy now");
      //  JButton addToCartButton = new JButton("Add to cart");

        backButton.setPreferredSize(new Dimension(150, 40));
        backButton.setFont(new Font("Sans-serif", Font.BOLD, 14));
        buttonGbc.gridx = 0;
        buttonPanel.add(backButton, buttonGbc);

        buyNowButton.setPreferredSize(new Dimension(150, 40));
        buyNowButton.setBackground(new Color(210, 180, 140));
        buyNowButton.setFont(new Font("Sans-serif", Font.BOLD, 14));
        buttonGbc.gridx = 1;
        buttonPanel.add(buyNowButton, buttonGbc);

    //    addToCartButton.setPreferredSize(new Dimension(150, 40));
    //    addToCartButton.setFont(new Font("Sans-serif", Font.BOLD, 14));
        buttonGbc.gridx = 2;
  //      buttonPanel.add(addToCartButton, buttonGbc);

        gbc.gridy = 9;
        gbc.insets = new Insets(10, 0, 0, 20);
        rightPanel.add(buttonPanel, gbc);

        // Buy Now Action Listener
        buyNowButton.addActionListener(e -> {
            insertOrderIntoDatabase(clotheId,quantity,getTotalPrice());
            int totPrice = getTotalPrice();
           // View view2 = new View(totPrice);
          //    view2.setVisible(true);
            OrderTableView otv = new OrderTableView();
            otv.setVisible(true);
            JOptionPane.showMessageDialog(this, "Total Price: LKR " + totPrice, "Purchase Summary", JOptionPane.INFORMATION_MESSAGE);
        });

        // Back Button Action Listener
        backButton.addActionListener(e -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.getContentPane().removeAll();
            topFrame.add(new store(email)); // Replace Shore with the name of the previous view's class
            topFrame.revalidate();
            topFrame.repaint();
        });

        // Add panels to main layout
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
    }

    public int getTotalPrice() {
        total = quantity * price;
        return total;
    }
    
    private void insertOrderIntoDatabase(String clotheid, int qunt,int price2)  {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection connection = DriverManager.getConnection(url, username, this.password);
                 PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO orders( clotheId, quantity,price) VALUES (?, ?, ?)")) {
                insertStatement.setString(1, clotheid);
                insertStatement.setInt(2, qunt);
                insertStatement.setInt(3, price2);
                insertStatement.executeUpdate();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Database connection failed: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}    
