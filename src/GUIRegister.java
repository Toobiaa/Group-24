import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class GUIRegister extends JFrame {
    private JPanel registerPanel;
    private JComboBox<String> identityDropdown;
    private JComboBox<String> familyidDropdown;
    private JTextField nameField;
    private JPasswordField passwordField;
    private JTextField familyidField;
    private JPasswordField confirmPasswordField;
    private JButton okButton;
    private JButton backButton;
    private JLabel l1, l2, l3, l4;

    public GUIRegister() {
        initializeUI();
        setupActions();
    }

    private void setupActions() {
        familyidDropdown.addActionListener(e -> {
            if ("No".equals(familyidDropdown.getSelectedItem().toString())) {
                Random rand = new Random();
                String generatedId = String.format("%08d", rand.nextInt(100000000));
                familyidField.setText(generatedId);
                familyidField.setEnabled(false);
            } else {
                familyidField.setText("");
                familyidField.setEnabled(true);
            }
        });

        backButton.addActionListener(e -> {
            GUILogin loginScreen = new GUILogin();
            loginScreen.setVisible(true);
            dispose(); // Close the register screen
        });

        okButton.addActionListener(e -> {
            String identity = identityDropdown.getSelectedItem().toString();
            String familyId = familyidField.getText();
            int familyIdi = Integer.parseInt(familyidField.getText());
            String name = nameField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            // Check if any field is empty
            if (familyIdi == 0 || name.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Information is incomplete, please provide all details. Registration failed.", "Registration Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Check if passwords match
            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match. Registration failed.", "Registration Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if ("Yes".equals(familyidDropdown.getSelectedItem().toString())) {
                if (!familyId.matches("\\d{8}")) {
                    JOptionPane.showMessageDialog(this, "Family ID must be an 8-digit number.", "Registration Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (!CSVReader.getFamilyId(familyIdi)) {
                    JOptionPane.showMessageDialog(this, "Family ID does not exist.", "Registration Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            if (!nameField.getText().matches("^[A-Za-z]\\w{5,19}$")) {
                JOptionPane.showMessageDialog(this, "Name must start with a letter and contain 6-20 alpha-numeric characters or underscores.");
                return;
            }

            if (!new String(passwordField.getPassword()).matches("^.{8,15}$")) {
                JOptionPane.showMessageDialog(this, "Password length must be between 8-15 characters.");
                return;
            }

            // Assuming CSVFileWriter.readUser(familyId, name) checks for existing users with the same familyId and name
            // This is a placeholder for the method you might need to implement or modify according to your CSV handling
            user existingUser = UserReader.getUserDetails(familyIdi, name);
            if (existingUser != null) {
                JOptionPane.showMessageDialog(this, "A user with the same name and family ID already exists. Registration failed.", "Registration Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // If all checks are passed, proceed to write the user data
            CSVFileWriter.writeUser(familyIdi, name, identity, password);
            if (identity.equals("child")) {
                CSVFileWriter.writeAccount(familyIdi, name, 0, 0, 0);
            }
            JOptionPane.showMessageDialog(this, "Account successfully created.", "Registration Successful", JOptionPane.INFORMATION_MESSAGE);
            // Optionally close the register screen or navigate to another screen
        });
    }

    private void initializeUI() {
        setTitle("Please register a new account");
        setSize(700, 450); // Set the size as per your requirement
        setLocationRelativeTo(null); // Center on screen
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Dispose on close so it doesn't exit the app

        // Font for all components except the welcome label
        Font componentFont = new Font("Arial", Font.BOLD, 18);

        // Create the main panel with background image
        registerPanel = new BackgroundPanel();
        registerPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        // Title
        JLabel titleLabel = new JLabel("Please register a new account");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Set the font size larger
        titleLabel.setHorizontalAlignment(JLabel.CENTER); // Set text to be centered horizontally
        titleLabel.setVerticalAlignment(JLabel.CENTER); // Set text to be centered vertically

        constraints.gridwidth = GridBagConstraints.REMAINDER; // This will end the row, allowing centering
        constraints.fill = GridBagConstraints.HORIZONTAL; // This will allow the label to expand horizontally
        constraints.anchor = GridBagConstraints.CENTER; // This will center the label if it's smaller than its display area

        // Apply insets for top padding and give it some space from other components
        constraints.insets = new Insets(20, 0, 20, 0);
        registerPanel.add(titleLabel, constraints);

        // Reset constraints for other components
        constraints.gridwidth = 1;
        constraints.insets = new Insets(5, 10, 5, 10);
        constraints.fill = GridBagConstraints.HORIZONTAL;

        // Identity Dropdown
        identityDropdown = new JComboBox<>(new String[]{"parent", "child"});
        identityDropdown.setFont(componentFont);
        familyidDropdown = new JComboBox<>(new String[]{"Yes", "No"});
        familyidDropdown.setFont(componentFont);
        familyidField = new JTextField(15);
        familyidField.setFont(componentFont);
        nameField = new JTextField(15);
        nameField.setFont(componentFont);
        passwordField = new JPasswordField(15);
        passwordField.setFont(componentFont);
        confirmPasswordField = new JPasswordField(15);
        confirmPasswordField.setFont(componentFont);

        // Error labels with reduced height
        l1 = new JLabel();
        l1.setPreferredSize(new Dimension(0, 10)); // Set the height to half
        l1.setForeground(Color.RED);
        l1.setFont(new Font("Arial", Font.PLAIN, 10));

        l2 = new JLabel();
        l2.setPreferredSize(new Dimension(0, 10)); // Set the height to half
        l2.setForeground(Color.RED);
        l2.setFont(new Font("Arial", Font.PLAIN, 10));

        l3 = new JLabel();
        l3.setPreferredSize(new Dimension(0, 10)); // Set the height to half
        l3.setForeground(Color.RED);
        l3.setFont(new Font("Arial", Font.PLAIN, 10));

        l4 = new JLabel();
        l4.setPreferredSize(new Dimension(0, 10)); // Set the height to half
        l4.setForeground(Color.RED);
        l4.setFont(new Font("Arial", Font.PLAIN, 10));

        addToPanel(registerPanel, createLabel("Your Identity:", componentFont), constraints, 0, 1, 1, 3);
        addToPanel(registerPanel, identityDropdown, constraints, 1, 1, 2, 3);
        addToPanel(registerPanel, createLabel("Family ID exists?", componentFont), constraints, 0, 4, 1, 3);
        addToPanel(registerPanel, familyidDropdown, constraints, 1, 4, 2, 3);
        addToPanel(registerPanel, createLabel("Family ID:", componentFont), constraints, 0, 7, 1, 3);
        addToPanel(registerPanel, familyidField, constraints, 1, 7, 2, 3);
        addToPanel(registerPanel, l1, constraints, 0, 10, 3, 1);
        addToPanel(registerPanel, createLabel("Your name:", componentFont), constraints, 0, 11, 1, 3);
        addToPanel(registerPanel, nameField, constraints, 1, 11, 2, 3);
        addToPanel(registerPanel, l2, constraints, 0, 14, 3, 1);
        addToPanel(registerPanel, createLabel("Passwords:", componentFont), constraints, 0, 15, 1, 3);
        addToPanel(registerPanel, passwordField, constraints, 1, 15, 2, 3);
        addToPanel(registerPanel, l3, constraints, 0, 18, 3, 1);
        addToPanel(registerPanel, createLabel("Confirm your password:", componentFont), constraints, 0, 19, 1, 3);
        addToPanel(registerPanel, confirmPasswordField, constraints, 1, 19, 2, 3);
        addToPanel(registerPanel, l4, constraints, 0, 24, 3, 1);

        okButton = new JButton("OK");
        okButton.setFont(componentFont);
        backButton = new JButton("Go Back");
        backButton.setFont(componentFont);

        // back and ok buttons
        constraints.weighty = 1.0;
        constraints.anchor = GridBagConstraints.PAGE_END;
        addToPanel(registerPanel, okButton, constraints, 0, 23, 1, 3);
        addToPanel(registerPanel, backButton, constraints, 1, 23, 2, 3);

        add(registerPanel); // Add the panel to the frame

        setupFocusListeners();
    }

    private void setupFocusListeners() {
        familyidField.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                String familyId = familyidField.getText();
                int familyIdi = Integer.parseInt(familyidField.getText());
                String b = (String) familyidDropdown.getSelectedItem();
                Boolean a = CSVReader.getFamilyId(familyIdi);
                if (!familyId.matches("\\d{8}")) {
                    l1.setText("Family ID must be an 8-digit number.");
                } else if (!a && b.equals("Yes")) {
                    l1.setText("Family ID doesn't exist.");
                } else {
                    l1.setText("");
                }
            }
        });
        nameField.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                String namePattern = "^[A-Za-z]\\w{5,19}$";
                if (!nameField.getText().matches(namePattern)) {
                    l2.setText("Name must start with a letter and contain 6-20 alpha-numeric characters or underscores.");
                } else {
                    l2.setText("");
                }
            }
        });

        passwordField.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                String passwordPattern = "^.{8,15}$";
                if (!new String(passwordField.getPassword()).matches(passwordPattern)) {
                    l3.setText("Password length must be between 8-15 characters.");
                } else {
                    l3.setText("");
                }
            }
        });

        confirmPasswordField.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                if (!new String(confirmPasswordField.getPassword()).equals(new String(passwordField.getPassword()))) {
                    l4.setText("Passwords do not match.");
                } else {
                    l4.setText("");
                }
            }
        });
    }

    private void addToPanel(JPanel panel, Component component, GridBagConstraints constraints, int x, int y, int width, int height) {
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.gridwidth = width;
        constraints.gridheight = height;
        panel.add(component, constraints);
    }

    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        return label;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GUIRegister().setVisible(true);
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

