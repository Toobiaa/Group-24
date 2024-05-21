import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUIchangeGoal extends JFrame {
    private ArrayList<account> accounts;
    private int selectedFamilyId;
    private String selectedChildName;
    private int currentGoal;

    private JLabel currentGoalLabel;
    private JLabel currentGoalText;
    private JTextField newGoalField;
    private JButton okButton;
    private JButton backButton;

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

    public GUIchangeGoal(ArrayList<account> accounts, int familyId, String childName) {
        this.accounts = accounts;
        this.selectedFamilyId = familyId;
        this.selectedChildName = childName;

        // Set the page
        setTitle("Change Goal");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Make the elements in the center

        // 调用 showAccount 方法获取目标值并赋给 currentGoal
        ArrayList<account> accountList = AccountReader.showAccount(selectedFamilyId, selectedChildName);
        currentGoal = accountList.get(0).getGoal();

        // 使用自定义背景面板
        BackgroundPanel backgroundPanel = new BackgroundPanel("backgd.jpg");
        backgroundPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 5, 5); // 设置组件之间的间距

        // 添加顶部标签
        JLabel titleLabel = new JLabel("Set New Goal");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24)); // 设置字体样式
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // 横跨两列
        gbc.anchor = GridBagConstraints.NORTH;
        backgroundPanel.add(titleLabel, gbc);

        // 创建界面组件
        currentGoalLabel = new JLabel("Current goal:");
        currentGoalLabel.setFont(new Font("Arial", Font.BOLD, 18));
        currentGoalText = new JLabel(" " + currentGoal);
        currentGoalText.setFont(new Font("Arial", Font.BOLD, 18));
        newGoalField = new JTextField(10);
        okButton = new JButton("OK");
        okButton.setFont(new Font("Arial", Font.BOLD, 18));
        backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 18));
        JLabel newGoalLabel = new JLabel("New goal:");
        newGoalLabel.setFont(new Font("Arial", Font.BOLD, 18)); // 设置字体样式
        newGoalField.setPreferredSize(new Dimension(20, 30));
        gbc.gridy = 1;
        gbc.gridwidth = 2; // 横跨两列
        gbc.weighty = 1.0; // 设置垂直方向上的权重，使空白区域可以扩展
        backgroundPanel.add(Box.createVerticalStrut(20), gbc);

        // 设置组件位置
        gbc.gridy = 2;
        gbc.gridwidth = 1; // 恢复默认值
        gbc.gridx = 0;
        backgroundPanel.add(currentGoalLabel, gbc);

        gbc.gridy = 2;
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        backgroundPanel.add(currentGoalText, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        backgroundPanel.add(newGoalLabel, gbc);

        gbc.gridx = 1;
        backgroundPanel.add(newGoalField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.LINE_START;
        backgroundPanel.add(okButton, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        backgroundPanel.add(backButton, gbc);

        // 添加事件监听器
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newGoalText = newGoalField.getText();
                if (!newGoalText.isEmpty()) {
                    if (newGoalText.matches("\\d+")) {
                        double newGoal = Double.parseDouble(newGoalText);
                        if(newGoal < 100000){
                            int newGoal0 = (int)newGoal;
                            changeAccountDetail.changeGoal(selectedFamilyId, selectedChildName, newGoal0);
                            JOptionPane.showMessageDialog(null, "Goal changed successfully!");
                        }else {
                            JOptionPane.showMessageDialog(null, "Goal should be less than 100000!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Goal should be a positive number!", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Please enter a new goal!");
                }
            }
        });

        backButton.addActionListener(e -> {
            new GUIChild(familyId, childName, "child").setVisible(true);
            this.setVisible(false);
        });

        // 添加背景面板到框架
        setContentPane(backgroundPanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        // 这里假设你有一个 account 类和 AccountReader 类可以使用
        ArrayList<account> accounts = new ArrayList<>();
        // 假设添加了一些示例账户到 accounts 列表中
        new GUIchangeGoal(accounts, 1, "ChildName");
    }
}

