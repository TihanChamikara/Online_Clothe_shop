package finalProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class home extends JPanel implements ActionListener {

    JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Center the logo
    JLabel logoLabel = new JLabel("FASHION");
    JPanel bannerPanel = new JPanel(new BorderLayout());
    JPanel brandsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
    JPanel newArrivalsPanel = new JPanel(new GridLayout(1, 4, 20, 20)); // 1 row, 4 columns for products
    JButton seeMoreButton = new JButton("See more");
    JPanel mainPanel = new JPanel();
    
    String email;
    public home(String email) {
    	this.email = email;
        setLayout(new BorderLayout());

        // Header with centered logo
        headerPanel.setBackground(Color.WHITE);
        logoLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
        headerPanel.add(logoLabel);

        // Banner section with centered hero image
        bannerPanel.setPreferredSize(new Dimension(800, 300));
        bannerPanel.setBackground(Color.WHITE);

        // Load and center Hero Image
        ImageIcon bannerImage = loadImage("C:\\Users\\Chamikara\\eclipse-workspace\\finalProject\\src\\finalProject\\Hero.png", 500, 300);
        JLabel bannerImageLabel = new JLabel(bannerImage);
        bannerImageLabel.setHorizontalAlignment(JLabel.CENTER);
        bannerImageLabel.setVerticalAlignment(JLabel.CENTER);
        bannerPanel.add(bannerImageLabel, BorderLayout.CENTER);

        // Brands panel with single logo
        brandsPanel.setBackground(new Color(34, 139, 34));
        brandsPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Load single large brand logo
        ImageIcon brandIcon = loadImage("C:\\Users\\Chamikara\\eclipse-workspace\\finalProject\\src\\finalProject\\Brands.png", 1300, 45);
        JLabel brandLabel = new JLabel(brandIcon);
        brandsPanel.add(brandLabel);

        // New Arrivals title section
        JPanel newArrivalsTitlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        newArrivalsTitlePanel.setBackground(Color.WHITE); // Match the background color
        JLabel newArrivalsTitle = new JLabel("NEW ARRIVALS");
        newArrivalsTitle.setFont(new Font("Arial", Font.BOLD, 18));
        newArrivalsTitlePanel.add(newArrivalsTitle); // Center the title

        newArrivalsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Product image paths and names
        String[] productImagePaths = {
            "C:\\Users\\Chamikara\\eclipse-workspace\\finalProject\\src\\finalProject\\Frame 46.png",
            "C:\\Users\\Chamikara\\eclipse-workspace\\finalProject\\src\\finalProject\\Frame 47.png",
            "C:\\Users\\Chamikara\\eclipse-workspace\\finalProject\\src\\finalProject\\Rectangle 89.png",
            "C:\\Users\\Chamikara\\eclipse-workspace\\finalProject\\src\\finalProject\\Rectangle 90.png"
        };

        String[] productNames = {
            "New summer black Shirt",
            "PARADISE SHIRTS",
            "Ash Color T-Shirt",
            "Popcorn T-Shirt"
        };

        // Adjusted product image size
        for (int i = 0; i < productImagePaths.length; i++) {
            JPanel productPanel = new JPanel(new BorderLayout());
            productPanel.setBackground(Color.WHITE);
            productPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

            ImageIcon productIcon = loadImage(productImagePaths[i], 170, 200);
            JLabel productImageLabel = new JLabel(productIcon);
            productImageLabel.setHorizontalAlignment(SwingConstants.CENTER);

            JLabel productLabel = new JLabel(productNames[i], SwingConstants.CENTER);
            productLabel.setFont(new Font("Sans-serif", Font.PLAIN, 12));
            productPanel.add(productImageLabel, BorderLayout.CENTER);
            productPanel.add(productLabel, BorderLayout.SOUTH);
            newArrivalsPanel.add(productPanel);
        }

        // See More button - move it up closer to New Arrivals section
        seeMoreButton.setPreferredSize(new Dimension(150, 40));
        seeMoreButton.setFont(new Font("Arial", Font.BOLD, 14));
        seeMoreButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Main panel organization
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(bannerPanel);
        mainPanel.add(brandsPanel);
        mainPanel.add(newArrivalsTitlePanel); // Centered title panel
        mainPanel.add(newArrivalsPanel);
        mainPanel.add(Box.createVerticalStrut(10)); // Add space between products and button
        mainPanel.add(seeMoreButton);

        // Add everything to the main panel
        add(headerPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        
        // Add action listener to the "See More" button
        seeMoreButton.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        // Navigate to the next page (e.g., 'store' panel)
        if (e.getSource() == seeMoreButton) {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.getContentPane().removeAll(); // Clear the current content pane
            topFrame.add(new store(email)); // Add the new 'store' panel
            topFrame.revalidate();
            topFrame.repaint();
        }
    }

    // Helper method to load and resize images
    private ImageIcon loadImage(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(path);
        Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }
}
