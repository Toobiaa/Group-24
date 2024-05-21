import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GUIChild extends JFrame {
    private user currentUser; // 保存用户对象
    private JLabel welcomeLabel;
    private int goal;
    private int currentAccount;
    private int savingAccount;

    public GUIChild(int familyId, String name, String identity) {
        // 通过familyId和name创建用户对象
        this.currentUser = new user(familyId, name, identity);
        ArrayList<account> accounts = AccountReader.showAccount(familyId, name);
        for (account acc : accounts) {
            this.goal = acc.getGoal();
            this.currentAccount = acc.getCurrentAccount();
            this.savingAccount = acc.getSavingAccount();
        }

        setTitle("Welcome, " + name + " to Child Bank");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initializeUI();
    }

    private void initializeUI() {
        // 创建主面板并设置背景图片
        JPanel mainPanel = new BackgroundPanel();
        mainPanel.setLayout(new BorderLayout());

        // Welcome label
        welcomeLabel = new JLabel(String.format(
                "<html>" +
                        "<p><big>Hello, %s</big></p>" +
                        "<p>SavingAccount: ¥%d</p>" +
                        "<p>CurrentAccount: ¥%d</p>" +
                        "<p>Goal: ¥%d</p>" +
                        "</html>",
                currentUser.getName(), savingAccount, currentAccount, goal
        ));
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);

        // Add padding around the welcome label
        JPanel welcomePanel = new JPanel(new BorderLayout());
        welcomePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        welcomePanel.setOpaque(false); // Make the panel transparent
        welcomePanel.add(welcomeLabel, BorderLayout.CENTER);

        // Add welcome panel to the top of the main panel
        mainPanel.add(welcomePanel, BorderLayout.NORTH);

        // Create the button panel with a grid layout
        JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 10, 10)); // Adjusted spacing
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        buttonPanel.setOpaque(false); // Make the panel transparent

        JButton shopButton = new JButton("Shop");
        shopButton.setFont(new Font("Arial", Font.BOLD, 16));
        JButton taskButton = new JButton("Task");
        taskButton.setFont(new Font("Arial", Font.BOLD, 16));
        JButton transactionButton = new JButton("Transaction");
        transactionButton.setFont(new Font("Arial", Font.BOLD, 16));
        JButton withdrawalButton = new JButton("Withdrawal");
        withdrawalButton.setFont(new Font("Arial", Font.BOLD, 16));
        JButton goalButton = new JButton("Goal");
        goalButton.setFont(new Font("Arial", Font.BOLD, 16));
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 16));

        shopButton.addActionListener(e -> openTestShop());
        taskButton.addActionListener(e -> openTestChildTask());
        transactionButton.addActionListener(e -> openTestTransaction());
        withdrawalButton.addActionListener(e -> openTestWithdraw());
        goalButton.addActionListener(e -> openTestSetGoal());
        logoutButton.addActionListener(e -> logout());

        // Set preferred size for all buttons to be appropriately sized
        Dimension buttonSize = new Dimension(160, 40); // Adjusted button size
        shopButton.setPreferredSize(buttonSize);
        taskButton.setPreferredSize(buttonSize);
        transactionButton.setPreferredSize(buttonSize);
        withdrawalButton.setPreferredSize(buttonSize);
        goalButton.setPreferredSize(buttonSize);
        logoutButton.setPreferredSize(buttonSize);

        // Add buttons to the button panel
        buttonPanel.add(createButtonPanel(shopButton));
        buttonPanel.add(createButtonPanel(taskButton));
        buttonPanel.add(createButtonPanel(transactionButton));
        buttonPanel.add(createButtonPanel(withdrawalButton));
        buttonPanel.add(createButtonPanel(goalButton));
        buttonPanel.add(createButtonPanel(logoutButton));

        // Add button panel to the center of the main panel
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private void openTestShop() {
        new GUIshoppingcentre(currentUser).setVisible(true);
        this.setVisible(false); // Hide the login screen
    }

    private void openTestChildTask() {
        new GUITasksChild(currentUser.getFamilyId(), currentUser.getName()).setVisible(true);
        this.setVisible(false); // Hide the login screen
    }

    private void openTestTransaction() {
        new GUITransactions(currentUser.getFamilyId(), currentUser.getName(), "child").setVisible(true);
        this.setVisible(false); // Hide the login screen
    }

    private void openTestWithdraw() {
        new GUIwithdraw(currentUser.getFamilyId(), currentUser.getName(), "child").setVisible(true);
        this.setVisible(false); // Hide the login screen
    }

    private void openTestSetGoal() {
        ArrayList<account> account = AccountReader.showAccount(currentUser.getFamilyId(), currentUser.getName());
        new GUIchangeGoal(account, currentUser.getFamilyId(), currentUser.getName()).setVisible(true);
        this.setVisible(false); // Hide the login screen
    }

    private void logout() {
        new GUILogin().setVisible(true);
        this.dispose(); // Close the current window
    }

    private JPanel createButtonPanel(Component component) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false); // Make the panel transparent
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Adjust insets to control spacing around the buttons
        gbc.fill = GridBagConstraints.BOTH; // Fill both horizontally and vertically
        panel.add(component, gbc);
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GUIChild(35327784, "wangxincheng", "child").setVisible(true));
    }

    // Custom JPanel for background image
    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel() {
            try {
                backgroundImage = new ImageIcon(getClass().getResource("child.jpg")).getImage();
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

