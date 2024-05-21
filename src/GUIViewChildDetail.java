import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class GUIViewChildDetail extends JFrame {
    private int familyID;
    private String name;
    private ArrayList<account> familyaccount;
    private Image topBottomBackgroundImage;
    private Image centerBackgroundImage;

    public GUIViewChildDetail(int familyID, String name) {
        this.familyID = familyID;
        this.name = name;
        this.familyaccount = AccountReader.showFamilyAccount(familyID);

        try {
            topBottomBackgroundImage = ImageIO.read(new File("backgd.jpg"));
            centerBackgroundImage = ImageIO.read(new File("function.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setTitle("View Child Detail");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (topBottomBackgroundImage != null) {
                    g.drawImage(topBottomBackgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        this.add(mainPanel);

        JLabel detailsLabel = new JLabel("Details for: " + name + "'s family");
        detailsLabel.setFont(new Font("Arial", Font.BOLD, 24));
        detailsLabel.setHorizontalAlignment(JLabel.CENTER);
        mainPanel.add(detailsLabel, BorderLayout.NORTH);

        // Check if familyaccount is null or empty
        if (familyaccount == null || familyaccount.isEmpty()) {
            JLabel noAccountLabel = new JLabel("No child account has been added to your family");
            noAccountLabel.setFont(new Font("Arial", Font.ITALIC, 24));
            noAccountLabel.setHorizontalAlignment(JLabel.CENTER);
            mainPanel.add(noAccountLabel, BorderLayout.CENTER);
        } else {
            displayAccountDetails(mainPanel);
        }

        JButton homeButton = new JButton("Home");
        homeButton.setPreferredSize(new Dimension(100, 40)); // Set the preferred size for the Home button
        homeButton.addActionListener(e -> navigateToGUIParent());
        homeButton.setFont(new Font("Arial", Font.BOLD, 18));
        JPanel buttonPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (topBottomBackgroundImage != null) {
                    g.drawImage(topBottomBackgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        }; // Using JPanel to control the button layout
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(new EmptyBorder(10, 50, 10, 50)); // Adding margins to the button panel
        buttonPanel.add(homeButton); // Adding the Home button to the panel

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void displayAccountDetails(JPanel panel) {
        String[] columns = {"Child Name", "Current Account", "Saving Account", "Goal"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        for (account acc : familyaccount) {
            Object[] row = new Object[] {
                    acc.getChildName(), acc.getCurrentAccount(), acc.getSavingAccount(), acc.getGoal()
            };
            model.addRow(row);
        }

        JTable table = new JTable(model);
        // Set the row height to be three times the default height
        table.setRowHeight(table.getRowHeight() * 3);

        // Increase the font size for the table
        Font tableFont = new Font("Arial", Font.PLAIN, 18); // Adjust font size as needed
        table.setFont(tableFont);

        // Adjusting column widths proportionately based on the content
        adjustColumnWidths(table);

        JScrollPane scrollPane = new JScrollPane(table) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (centerBackgroundImage != null) {
                    g.drawImage(centerBackgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        scrollPane.getViewport().setOpaque(false);
        panel.add(scrollPane, BorderLayout.CENTER);
    }

    // Helper method to adjust column widths
    private void adjustColumnWidths(JTable table) {
        final TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 150; // Minimum width
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width + 1, width);
            }
            if (width > 300) {
                width = 300; // Max width for any column
            }
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }

    private void navigateToGUIParent() {
        new GUIParent(familyID, name, "parent").setVisible(true);
        this.dispose(); // Close the current window
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GUIViewChildDetail(123456, "John Doe"));
    }
}

