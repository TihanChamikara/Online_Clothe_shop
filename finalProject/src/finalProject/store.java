package finalProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class store extends JPanel {
    public store(String email) {
        // Set up the JPanel properties
        setLayout(new GridBagLayout());  // Use GridBagLayout for fine-tuned control
        setPreferredSize(new Dimension(800, 800));
        setBackground(Color.LIGHT_GRAY);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  // Set padding between panels

        // Title label spanning all columns
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;  // Spanning the title across 3 columns for a 2x3 grid layout
        gbc.anchor = GridBagConstraints.CENTER;
        JLabel titleLabel = new JLabel("Gents Favourite", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setPreferredSize(new Dimension(800, 40));
        add(titleLabel, gbc);

        // Reset gridwidth for shirt panels
        gbc.gridwidth = 1;

        // First row of the collage
        gbc.gridy = 1;  // Row 1
        gbc.gridx = 0;  // First column
        add(createShirtPanel("Classic flower printed shirt", "LKR 4500", "C:\\Users\\Chamikara\\eclipse-workspace\\finalProject\\src\\finalProject\\t1.png", itemview2.class, email), gbc);

        gbc.gridx = 1;  // Second column
        add(createShirtPanel("Solid Color Casual V neck", "LKR 5700", "C:\\Users\\Chamikara\\eclipse-workspace\\finalProject\\src\\finalProject\\t2.png", Itemview.class, email), gbc);

        gbc.gridx = 2;  // Third column
        add(createShirtPanel("Classic Long Sleeve Men shirt", "LKR 6500", "C:\\Users\\Chamikara\\eclipse-workspace\\finalProject\\src\\finalProject\\t3.png", itemview2.class, email), gbc);

        // Second row of the collage
        gbc.gridy = 2;  // Row 2
        gbc.gridx = 0;  // First column in the second row
        add(createShirtPanel("Streetwear Casual shirt", "LKR 5800", "C:\\Users\\Chamikara\\eclipse-workspace\\finalProject\\src\\finalProject\\t4.png", Itemview.class, email), gbc);

        gbc.gridx = 1;  // Second column in the second row
        add(createShirtPanel("Classic Green popcorn shirt", "LKR 7800", "C:\\Users\\Chamikara\\eclipse-workspace\\finalProject\\src\\finalProject\\t5.png", itemview2.class, email), gbc);

        gbc.gridx = 2;  // Third column in the second row
        add(createShirtPanel("Classic Blue shirt", "LKR 6200", "C:\\Users\\Chamikara\\eclipse-workspace\\finalProject\\src\\finalProject\\t6.png", Itemview.class, email), gbc);
    }

    // Helper method to create individual shirt panels with a parameter to specify the item view class
    private JPanel createShirtPanel(String shirtName, String price, String imagePath, Class<?> itemViewClass, String email) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());  // Use GridBagLayout for consistent placement
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);  // Add small padding inside each panel

        // Shirt image
        ImageIcon icon = new ImageIcon(imagePath);
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        JLabel shirtImage = new JLabel(new ImageIcon(resizedImg));
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(shirtImage, gbc);

        // Shirt name
        JLabel nameLabel = new JLabel(shirtName, JLabel.CENTER);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(nameLabel, gbc);

        // Shirt price
        JLabel priceLabel = new JLabel(price, JLabel.CENTER);
        priceLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(priceLabel, gbc);

        // "View" button with a unique ActionListener for each shirt
        JButton buyButton = new JButton("View");
        buyButton.setPreferredSize(new Dimension(100, 30));

        // Set unique action for the button using an anonymous inner class
        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(store.this);
                topFrame.getContentPane().removeAll(); // Clear the current content pane

                // Create and add a new item view panel for the clicked shirt based on the itemViewClass parameter
                try {
                    JPanel itemPanel;
                    // Always pass the email when creating a new instance
                    itemPanel = (JPanel) itemViewClass.getDeclaredConstructor(String.class).newInstance(email);
                    
                    topFrame.add(itemPanel);
                    topFrame.revalidate();
                    topFrame.repaint();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        panel.add(buyButton, gbc);

        return panel;
    }
}
