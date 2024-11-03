package finalProject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class OrderTableView extends JFrame {

    String url = "jdbc:mysql://localhost:3306/fashionstore?useUnicode=true&characterEncoding=UTF-8";
    String username = "root";
    String password = "";

    public OrderTableView() {
        setTitle("Order Table");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel for the table
        JPanel tablePanel = new JPanel(new BorderLayout());
        add(tablePanel, BorderLayout.CENTER);

        // Table model for the orders data
        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"Order_id", "Clotheid", "quantity", "Price"}, 0);
        JTable orderTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(orderTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        // Panel for buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        tablePanel.add(buttonPanel, BorderLayout.SOUTH);
        
        JButton deleteButton = new JButton("Delete Selected");
        deleteButton.setPreferredSize(new Dimension(150, 30));
        deleteButton.setFont(new Font("Sans-serif", Font.PLAIN, 12));
        buttonPanel.add(deleteButton);

        JButton countButton = new JButton("Total Price");
        countButton.setPreferredSize(new Dimension(150, 30));
        countButton.setFont(new Font("Sans-serif", Font.PLAIN, 12));
        buttonPanel.add(countButton);

        // Action listener to delete the selected row
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = orderTable.getSelectedRow();
                if (selectedRow != -1) { // Check if a row is selected
                    int orderId = (int) tableModel.getValueAt(selectedRow, 0);

                    // Confirm deletion
                    int confirmation = JOptionPane.showConfirmDialog(OrderTableView.this, 
                        "Are you sure you want to delete this order?", "Confirm Delete", JOptionPane.YES_NO_OPTION);

                    if (confirmation == JOptionPane.YES_OPTION) {
                        deleteOrder(orderId);
                        tableModel.removeRow(selectedRow); // Remove row from JTable
                    }
                } else {
                    JOptionPane.showMessageDialog(OrderTableView.this, "Please select a row to delete.", 
                        "No Row Selected", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // Action listener to display total price
        countButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double totalPrice = 0.0;
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    int price = (int) tableModel.getValueAt(i, 3); // Get price
                    totalPrice += price;
                }
                JOptionPane.showMessageDialog(OrderTableView.this, "Total Price: LKR." + totalPrice, 
                        "Total Price", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Fetch data from the orders table and populate the JTable
        loadOrdersData(tableModel);
    }

    private void loadOrdersData(DefaultTableModel tableModel) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection connection = DriverManager.getConnection(url, username, password);
                 Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery("SELECT * FROM orders")) {

                while (resultSet.next()) {
                    int order_id = resultSet.getInt("order_id");
                    String clotheid = resultSet.getString("clotheid");
                    int quantity = resultSet.getInt("quantity");
                    int Price = resultSet.getInt("Price");

                    tableModel.addRow(new Object[]{order_id, clotheid, quantity, Price});
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Database connection failed: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteOrder(int orderId) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection connection = DriverManager.getConnection(url, username, password);
                 PreparedStatement statement = connection.prepareStatement("DELETE FROM orders WHERE order_id = ?")) {
                
                statement.setInt(1, orderId);
                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Order deleted successfully.", "Delete Successful", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Order deletion failed.", "Delete Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Failed to delete order: " + ex.getMessage(), "Delete Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            OrderTableView orderTableView = new OrderTableView();
            orderTableView.setVisible(true);
        });
    }
}  
