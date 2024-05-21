import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.*;
import java.io.*;
import javax.imageio.ImageIO;

public class GUITasksChild extends JFrame {
    private JLabel taskTitleLabel;
    private JScrollPane scrollPane;
    private JButton homeButton;
    private Image backgroundImage;
    private Image childBackgroundImage;

    public void initialize(int familyId, String childName) {
        this.setTitle("Submit tasks and get bonus!");
        this.setSize(700, 450);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        try {
            backgroundImage = ImageIO.read(new File("function.png"));
            childBackgroundImage = ImageIO.read(new File("backgd.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        BackgroundPanel backgroundPanel = new BackgroundPanel(childBackgroundImage);
        backgroundPanel.setLayout(new BorderLayout());
        this.setContentPane(backgroundPanel);

        // 创建并设置任务标题标签，添加适当的边距
        this.taskTitleLabel = new JLabel("Task", SwingConstants.CENTER);
        this.taskTitleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        taskTitleLabel.setBorder(new EmptyBorder(10, 0, 10, 0));  // 上下各10像素的边距
        backgroundPanel.add(BorderLayout.NORTH, this.taskTitleLabel);

        // 创建按钮并设置边距和大小
        homeButton = new JButton("Home");
        homeButton.setPreferredSize(new Dimension(100, 40));  // 设置按钮的首选大小
        homeButton.setFont(new Font("Arial", Font.BOLD, 18));
        JPanel buttonPanel = new JPanel();  // 使用JPanel来控制按钮的布局
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(new EmptyBorder(10, 50, 10, 50));  // 为按钮面板添加边距
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));  // 居中布局
        buttonPanel.add(homeButton);
        // 暂定逻辑：返回kid主界面<=>隐藏task界面
        homeButton.addActionListener(e -> {
            new GUIChild(familyId, childName, "child").setVisible(true);
            this.setVisible(false);
        });
        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);

        // 初始化任务UI
        this.updateTasksUI(familyId, childName);
    }

    // 更新UI的方法，删除原有的，并绘制新的滚动窗口
    private void updateTasksUI(int familyId, String childName) {
        if (scrollPane != null) {
            this.getContentPane().remove(scrollPane);
        }
        this.scrollPane = this.createScrollPane(familyId, childName);
        this.getContentPane().add(scrollPane, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }

    // 绘制滚动窗口的方法，左中右分别放置 任务名称，奖励，提交按钮
    private JScrollPane createScrollPane(int familyId, String childName) {
        BackgroundPanel containerPanel = new BackgroundPanel(backgroundImage, true);
        containerPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL; // 水平填充
        constraints.insets = new Insets(20, 10, 20, 10); // 设置每个格子的 上左下右 边距
        constraints.weightx = 1; // 横向缩放比，1代表格子大小对齐任意大小的窗口
        constraints.weighty = 1; // 纵向缩放比，同上

        // 调用task类的方法，获取该小孩的全部task信息
        ArrayList<task> tasks = task.viewTasks(familyId, childName);

        // 绘制待提交的任务列表
        boolean hasUnsubmittedTask = false; // 标志，判断是否存在未提交的任务，对应于两种页面
        for (task t : tasks) {
            if (!t.isSubmitted()) {
                hasUnsubmittedTask = true;
                JLabel nameLabel = new JLabel(t.getTaskName());
                nameLabel.setFont(new Font("Arial", Font.PLAIN, 18));
                JLabel bonusLabel = new JLabel(Integer.toString(t.getBonus()));
                bonusLabel.setFont(new Font("Arial", Font.PLAIN, 18));
                JButton submitButton = new JButton("Submit");
                submitButton.setPreferredSize(new Dimension(80, 30));

                // 对按钮绑定事件监听器，点击提交按钮触发 提交 & 刷新UI
                submitButton.addActionListener(e -> {
                    task.submit(t.getTaskId());
                    this.updateTasksUI(familyId, childName);
                });

                // 使用FlowLayout使按钮居中
                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                buttonPanel.setOpaque(false);
                buttonPanel.add(submitButton);

                constraints.gridx = 0; // taskName: left
                constraints.gridy = GridBagConstraints.RELATIVE;
                constraints.anchor = GridBagConstraints.WEST; // task名称在小格靠左
                containerPanel.add(nameLabel, constraints);

                constraints.gridx = 1; // bonus: center
                containerPanel.add(bonusLabel, constraints);

                constraints.gridx = 2; // submit button: right
                constraints.fill = GridBagConstraints.NONE;  // 不让按钮水平填充
                constraints.anchor = GridBagConstraints.CENTER; // 按钮在小格居中
                containerPanel.add(buttonPanel, constraints);
            }
        }

        // 创建纵向滚动的列表，大小为全屏宽度 & 边距 上10，左10，下10，右10
        JScrollPane scrollPane = new JScrollPane(containerPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));

        // 所有任务已经提交，显示一行字提示
        if (!hasUnsubmittedTask) {
            containerPanel.removeAll();
            JLabel reminderLabel = new JLabel("No Unsubmitted Tasks");
            reminderLabel.setFont(new Font("Arial", Font.ITALIC, 30));
            reminderLabel.setHorizontalAlignment(JLabel.CENTER);
            containerPanel.setLayout(new BorderLayout());
            containerPanel.add(reminderLabel, BorderLayout.CENTER);
            scrollPane.setBorder(new EmptyBorder(10, 0, 10, 0));
        }

        return scrollPane;
    }

    public GUITasksChild(int familyId, String childName) {
        this.initialize(familyId, childName);
    }

    // main仅用于测试
    public static void main(String[] args) {
        // 给出了调用的一般顺序：实例化一个GUITasksChild对象，为initialize()传入参数，setVisible
        GUITasksChild myTasks = new GUITasksChild(89106409, "1111");
        myTasks.setVisible(true);
    }
}

// Custom JPanel to paint the background image
class BackgroundPanel extends JPanel {
    private Image backgroundImage;
    private boolean isScrollablePanel;

    public BackgroundPanel(Image backgroundImage) {
        this(backgroundImage, false);
    }

    public BackgroundPanel(Image backgroundImage, boolean isScrollablePanel) {
        this.backgroundImage = backgroundImage;
        this.isScrollablePanel = isScrollablePanel;
        setOpaque(!isScrollablePanel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
