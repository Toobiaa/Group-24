import javax.swing.*;
import java.awt.*;

public class GUILogin extends JFrame {
    private JPanel mainPanel;
    private JTextField familyIdField;
    private JTextField usernameField;
    private JTextField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    private void initializeUI() {
        setTitle("Welcome to Kids Bank");
        setSize(700, 450); // Updated the size to better match the provided UI
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Font for all components except the welcome label
        Font componentFont = new Font("Arial", Font.BOLD, 18);

        // Create the main panel with background image
        mainPanel = new BackgroundPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        // Welcome title
        JLabel welcomeLabel = new JLabel("Welcome to Kids Bank");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Set the font size larger
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER); // Set text to be centered horizontally
        welcomeLabel.setVerticalAlignment(JLabel.CENTER); // Set text to be centered vertically

        constraints.gridwidth = GridBagConstraints.REMAINDER; // This will end the row, allowing centering
        constraints.fill = GridBagConstraints.HORIZONTAL; // This will allow the label to expand horizontally
        constraints.anchor = GridBagConstraints.CENTER; // This will center the label if it's smaller than its display area

        // Apply insets for top padding and give it some space from other components
        constraints.insets = new Insets(20, 0, 20, 0);
        mainPanel.add(welcomeLabel, constraints);

        // Reset constraints for other components
        constraints.gridwidth = 1; // Reset to default
        constraints.insets = new Insets(10, 10, 10, 10); // Add padding around the components

        familyIdField = new JTextField(15);
        familyIdField.setFont(componentFont);
        usernameField = new JTextField(15);
        usernameField.setFont(componentFont);
        passwordField = new JPasswordField(15);
        passwordField.setFont(componentFont);
        loginButton = new JButton("Log in");
        loginButton.setFont(componentFont);
        registerButton = new JButton("Register a new account");
        registerButton.setFont(componentFont);

        constraints.fill = GridBagConstraints.HORIZONTAL;

        // Family ID label and text field
        addToPanel(mainPanel, createLabel("Family ID:", componentFont), constraints, 0, 1, 1);
        addToPanel(mainPanel, familyIdField, constraints, 1, 1, 2);

        // Username label and text field
        addToPanel(mainPanel, createLabel("User's name:", componentFont), constraints, 0, 2, 1);
        addToPanel(mainPanel, usernameField, constraints, 1, 2, 2);

        // Password label and text field
        addToPanel(mainPanel, createLabel("Passwords:", componentFont), constraints, 0, 3, 1);
        addToPanel(mainPanel, passwordField, constraints, 1, 3, 2);

        // Register and login buttons
        constraints.weighty = 1.0;
        constraints.anchor = GridBagConstraints.PAGE_END;
        addToPanel(mainPanel, registerButton, constraints, 0, 5, 1);
        addToPanel(mainPanel, loginButton, constraints, 1, 5, 2);

        add(mainPanel);
    }

    private void LoginActions() {
        loginButton.addActionListener(e -> {
            Integer familyId = Integer.parseInt(familyIdField.getText());
            String name = usernameField.getText();
            String password = passwordField.getText();

            // Check if any field is empty
            if (familyId == null & name.isEmpty() & password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Information is incomplete, please provide all details. Login failed.", "Login Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (familyId == null || name.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Information is incomplete, please provide all details. Login failed.", "Login Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // This is a placeholder for the method you might need to implement or modify according to your CSV handling
            user checkingUser = UserReader.getUserDetails(familyId, name);
            if (checkingUser != null) {
                if (checkingUser.getPassword().equals(password)) {
                    if (checkingUser.getIdentity().equals("parent")) {
                        GUIParent nextScreen = new GUIParent(familyId, name, "parent");
                        nextScreen.setVisible(true);
                        this.setVisible(false); // Hide the login screen
                    } else {
                        GUIChild nextScreen = new GUIChild(familyId, name, "child");
                        nextScreen.setVisible(true);
                        this.setVisible(false); // Hide the login screen
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "The password is incorrect", "Login Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
                JOptionPane.showMessageDialog(this, "There is no such person in the family", "Login Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        });
    }

    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        return label;
    }

    private void addToPanel(JPanel panel, Component component, GridBagConstraints constraints, int x, int y, int width) {
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.gridwidth = width;
        panel.add(component, constraints);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GUILogin().setVisible(true);
        });
    }

    public GUILogin() {
        initializeUI();
        setupActions();
        LoginActions();
    }

    private void setupActions() {
        registerButton.addActionListener(e -> {
            GUIRegister registerScreen = new GUIRegister();
            registerScreen.setVisible(true);
            this.setVisible(false); // Hide the login screen
        });
    }

    // Custom JPanel for background image
    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel() {
            try {
                backgroundImage = new ImageIcon(getClass().getResource("bank.jpg")).getImage();
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
}

