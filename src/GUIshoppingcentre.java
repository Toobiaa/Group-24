import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUIshoppingcentre extends JFrame {

    private Child currentUser;
    private int CurrentAcount;

    // 自定义 JPanel 用于绘制背景图片
    private class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String fileName) {
            try {
                backgroundImage = new ImageIcon(fileName).getImage();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    public GUIshoppingcentre(user User) {
        this.currentUser = new Child(User.getFamilyId(), User.getName(), User.getIdentity());
        ArrayList<account> account = AccountReader.showAccount(User.getFamilyId(), User.getName());
        for (account t : account) {
            CurrentAcount = t.getCurrentAccount();
        }
        this.setTitle("Child Shopping Page");
        this.setSize(700, 450);
        setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 使用自定义背景面板
        BackgroundPanel backgroundPanel = new BackgroundPanel("backgd.jpg");
        backgroundPanel.setLayout(new BorderLayout());
        this.add(backgroundPanel);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        backgroundPanel.add(panel, BorderLayout.CENTER);
        placeComponents(panel);

        // Setup and add the Home button
        JButton homeButton = new JButton("Home");
        homeButton.setPreferredSize(new Dimension(100, 40)); // Set the preferred size for the Home button
        homeButton.addActionListener(this::homeButtonAction); // Adding action listener for the Home button
        homeButton.setFont(new Font("Arial", Font.BOLD, 18));
        JPanel buttonPanel = new JPanel(); // Using JPanel to control the button layout
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(new EmptyBorder(10, 50, 10, 50)); // Adding margins to the button panel
        buttonPanel.add(homeButton); // Adding the Home button to the panel

        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH); // Adding the button panel to the frame at the bottom

        this.setVisible(true);
    }

    private ImageIcon images(String type) {
        String[] validType = {"pen", "ruler", "eraser", "glue"};
        boolean isValid = false;
        for (String s : validType) {
            if (s.equals(type)) { isValid = true; }
        }
        if (!isValid) return null;

        ImageIcon originalIcon = new ImageIcon(
                CSVFileWriter.filePath + "//" + type + ".png");
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    private void placeComponents(JPanel panel) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 1;
        constraints.weighty = 1;

        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.gridwidth = 4;
        JLabel welcomeLabel = new JLabel("Welcome to shopping center!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(welcomeLabel, constraints);

        constraints.gridwidth = 1;
        constraints.gridy = 1;
        constraints.gridx = 0;
        constraints.anchor = GridBagConstraints.CENTER;
        JButton penButton = new JButton("  Pen 5 yuan  ");
        penButton.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(penButton, constraints);

        constraints.gridx = 1;
        constraints.anchor = GridBagConstraints.WEST;
        JLabel penLabel = new JLabel(images("pen"));
        panel.add(penLabel, constraints);

        constraints.gridx = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        JButton rulerButton = new JButton("Ruler 4 yuan");
        rulerButton.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(rulerButton, constraints);

        constraints.gridx = 3;
        constraints.anchor = GridBagConstraints.WEST;
        JLabel rulerLabel = new JLabel(images("ruler"));
        panel.add(rulerLabel, constraints);

        constraints.gridy = 2;
        constraints.gridx = 0;
        constraints.anchor = GridBagConstraints.CENTER;
        JButton eraserButton = new JButton("Eraser 2 yuan");
        eraserButton.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(eraserButton, constraints);

        constraints.gridx = 1;
        constraints.anchor = GridBagConstraints.WEST;
        JLabel eraserLabel = new JLabel(images("eraser"));
        panel.add(eraserLabel, constraints);

        constraints.gridx = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        JButton glueButton = new JButton("Glue 3 yuan");
        glueButton.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(glueButton, constraints);

        constraints.gridx = 3;
        constraints.anchor = GridBagConstraints.WEST;
        JLabel glueLabel = new JLabel(images("glue"));
        panel.add(glueLabel, constraints);

        penButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (CurrentAcount >= 5) {
                    currentUser.consumption(5, "pen");
                    CurrentAcount -= 5;
                    displayPurchaseStatus("Successfully purchased Pen!");
                } else {
                    displayInsufficientFunds();
                }
            }
        });

        rulerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (CurrentAcount >= 4) {
                    currentUser.consumption(4, "ruler");
                    CurrentAcount -= 4;
                    displayPurchaseStatus("Successfully purchased Ruler!");
                } else {
                    displayInsufficientFunds();
                }
            }
        });

        eraserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (CurrentAcount >= 2) {
                    currentUser.consumption(2, "eraser");
                    CurrentAcount -= 2;
                    displayPurchaseStatus("Successfully purchased Eraser!");
                } else {
                    displayInsufficientFunds();
                }
            }
        });

        glueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (CurrentAcount >= 3) {
                    currentUser.consumption(3, "glue");
                    CurrentAcount -= 3;
                    displayPurchaseStatus("Successfully purchased Glue!");
                } else {
                    displayInsufficientFunds();
                }
            }
        });
    }

    private void homeButtonAction(ActionEvent e) {
        // This method will handle the navigation back to the GUIChild window
        new GUIChild(currentUser.getFamilyId(), currentUser.getName(), "child").setVisible(true);
        this.dispose(); // Close the current window
    }

    private void displayPurchaseStatus(String message) {
        JOptionPane.showMessageDialog(null, message + "\nRemaining balance: $" + CurrentAcount, "Purchase Status", JOptionPane.INFORMATION_MESSAGE);
    }

    private void displayInsufficientFunds() {
        JOptionPane.showMessageDialog(null, "You don't have sufficient funds.", "Purchase Status", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        user zwy = new user(111, "jjj", "child");
        SwingUtilities.invokeLater(() -> new GUIshoppingcentre(zwy).setVisible(true));
    }
}
