import javax.swing.*;
import java.awt.*;

public class GUIParent extends JFrame {
    private user currentUser; // 保存用户对象
    private JLabel welcomeLabel;

    // 构造方法，需要familyId与name
    public GUIParent(int familyId, String name, String identity) {
        // 通过familyId和name创建用户对象
        this.currentUser = new user(familyId, name, identity);

        setTitle("Welcome, " + name + " to Child Bank"); // 设置标题
        setSize(700, 450); // 设置窗口大小
        setLocationRelativeTo(null); // 居中显示
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 关闭窗口时退出程序

        // 初始化界面
        initializeUI();
    }

    private void initializeUI() {
        // 创建主面板并设置背景图片
        JPanel mainPanel = new BackgroundPanel();
        mainPanel.setLayout(new BorderLayout());

        // 创建欢迎标语
        welcomeLabel = new JLabel("Hello, " + currentUser.getName() + "! Welcome to Child Bank!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24)); // 设置字体和大小
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER); // 文字居中显示

        // 创建一个面板来包含欢迎标语，用于垂直居中
        JPanel welcomePanel = new JPanel(new BorderLayout());
        welcomePanel.setOpaque(false); // 使面板透明
        welcomePanel.add(welcomeLabel, BorderLayout.CENTER);

        // 创建按钮面板
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 50, 50)); // 2行2列的网格布局，间距为50
        buttonPanel.setOpaque(false); // 使面板透明
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // 设置按钮面板的边距

        // 创建按钮并添加动作监听器
        JButton approvalButton = new JButton("View kids' account profiles");
        approvalButton.setFont(new Font("Arial", Font.BOLD, 18));
        approvalButton.addActionListener(e -> launchTestApproveTask());
        JButton transactionsButton = new JButton("Transactions");
        transactionsButton.setFont(new Font("Arial", Font.BOLD, 18));
        transactionsButton.addActionListener(e -> launchTestTransaction());
        JButton launchTasksButton = new JButton("Launch the Tasks");
        launchTasksButton.setFont(new Font("Arial", Font.BOLD, 18));
        launchTasksButton.addActionListener(e -> launchTestNewTest());
        JButton viewTasksButton = new JButton("View and Approval the Tasks");
        viewTasksButton.setFont(new Font("Arial", Font.BOLD, 18));
        viewTasksButton.addActionListener(e -> launchTestParentTest());

        // Set the preferred size for buttons (wider)
        int buttonWidth = 160; // Define a suitable width
        int buttonHeight = 50; // Standard height
        approvalButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        transactionsButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        launchTasksButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        viewTasksButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));

        // 将按钮添加到按钮面板
        buttonPanel.add(approvalButton);
        buttonPanel.add(transactionsButton);
        buttonPanel.add(launchTasksButton);
        buttonPanel.add(viewTasksButton);

        // 创建一个新面板来包含按钮面板和logout按钮
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false); // 使面板透明
        bottomPanel.add(buttonPanel, BorderLayout.CENTER);

        // 创建并添加logout按钮
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 18));
        logoutButton.setPreferredSize(new Dimension(200, 40));
        logoutButton.addActionListener(e -> logout());

        JPanel logoutPanel = new JPanel(); // 使用JPanel来控制按钮的布局
        logoutPanel.setOpaque(false); // 使面板透明
        logoutPanel.add(logoutButton); // 添加logout按钮到面板

        bottomPanel.add(logoutPanel, BorderLayout.SOUTH); // 添加logout面板到底部

        // 添加欢迎面板到主面板的中部
        mainPanel.add(welcomePanel, BorderLayout.CENTER);

        // 添加底部面板到主面板的底部
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // 添加主面板到窗口
        add(mainPanel);
    }

    // 启动对应的测试窗口
    private void launchTestApproveTask() {
        new GUIViewChildDetail(currentUser.getFamilyId(), currentUser.getName()).setVisible(true);
        this.setVisible(false); // Hide the login screen
    }

    private void launchTestTransaction() {
        new GUITransactions(currentUser.getFamilyId(), currentUser.getName(), "parent").setVisible(true);
        this.setVisible(false); // Hide the login screen
    }

    private void launchTestNewTest() {
        new GUIlauchtasks(currentUser).setVisible(true);
        this.setVisible(false); // Hide the login screen
    }

    private void launchTestParentTest() {
        new GUITasksParent(currentUser.getFamilyId(), currentUser.getName()).setVisible(true);
        this.setVisible(false); // Hide the login screen
    }

    private void logout() {
        new GUILogin().setVisible(true);
        this.dispose(); // Close the current window
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GUIParent(35327784, "wangbowen", "parent").setVisible(true));
    }

    // Custom JPanel for background image
    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel() {
            try {
                backgroundImage = new ImageIcon(getClass().getResource("parent.png")).getImage();
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



