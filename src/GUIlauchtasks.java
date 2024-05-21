import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUIlauchtasks extends JFrame {

    private JTextField childNameField, taskNameField, bonusField;
    private ArrayList<account> familyaccount;
    private user User;

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

    public GUIlauchtasks(user user0) {
        this.User = user0;
        this.familyaccount = AccountReader.showFamilyAccount(User.getFamilyId());

        setTitle("New Task");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 450);
        setLocationRelativeTo(null);

        // 使用自定义背景面板
        BackgroundPanel backgroundPanel = new BackgroundPanel("backgd.jpg");
        backgroundPanel.setLayout(new GridBagLayout());
        this.setContentPane(backgroundPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); // 设置组件之间的间距

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setOpaque(false);
        JLabel titleLabel = new JLabel("New Task");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titlePanel.add(titleLabel);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        backgroundPanel.add(titlePanel, gbc);

        gbc.gridwidth = 1;

        JLabel childNameLabel = new JLabel("Child name:");
        childNameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        childNameField = new JTextField(20);
        JPanel childNamePanel = new JPanel();
        childNamePanel.setOpaque(false);
        childNamePanel.add(childNameLabel);
        childNamePanel.add(childNameField);
        gbc.gridy = 1;
        backgroundPanel.add(childNamePanel, gbc);

        JLabel taskNameLabel = new JLabel("Task Name:");
        taskNameField = new JTextField(20);
        taskNameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        JPanel taskNamePanel = new JPanel();
        taskNamePanel.setOpaque(false);
        taskNamePanel.add(taskNameLabel);
        taskNamePanel.add(taskNameField);
        gbc.gridy = 2;
        backgroundPanel.add(taskNamePanel, gbc);

        JLabel bonusLabel = new JLabel("Bonus:");
        bonusField = new JTextField(20);
        bonusLabel.setFont(new Font("Arial", Font.BOLD, 18));
        JPanel bonusPanel = new JPanel();
        bonusPanel.setOpaque(false);
        bonusPanel.add(bonusLabel);
        bonusPanel.add(bonusField);
        gbc.gridy = 3;
        backgroundPanel.add(bonusPanel, gbc);

        JButton launchButton = new JButton("Launch");
        launchButton.setFont(new Font("Arial", Font.BOLD, 18));
        JPanel launchPanel = new JPanel();
        launchPanel.setOpaque(false);
        launchPanel.add(launchButton);
        gbc.gridy = 4;
        backgroundPanel.add(launchPanel, gbc);

        JButton homeButton = new JButton("Home");
        homeButton.setFont(new Font("Arial", Font.BOLD, 18));
        JPanel homePanel = new JPanel();
        homePanel.setOpaque(false);
        homePanel.add(homeButton);
        gbc.gridy = 5;
        backgroundPanel.add(homePanel, gbc);

        launchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String childName = childNameField.getText();
                String taskname = taskNameField.getText();
                String bonus = bonusField.getText();
                if (!(childName.isEmpty()) && !(taskname.isEmpty()) && !(bonus.isEmpty())) {
                    if (checkChildName(childName)) {
                        if (bonus.matches("\\d+")) {
                            double bonusInt = Double.parseDouble(bonus);
                            if (bonusInt < 100000) {
                                int bonusInt0 = (int) bonusInt;
                                parent p = new parent(User.getFamilyId(), User.getName(), "parent");
                                p.releaseTask(taskname, bonusInt0, childName);
                                JOptionPane.showMessageDialog(null, "You have successfully published the mission!", "Task Status", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(GUIlauchtasks.this, "Bonus should be less than 100000 yuan!", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(GUIlauchtasks.this, "Please enter a correct bonus!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(GUIlauchtasks.this, "The child name does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(GUIlauchtasks.this, "Please enter the full content!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        homeButton.addActionListener(e -> {
            new GUIParent(User.getFamilyId(), User.getName(), "parent").setVisible(true);
            this.dispose(); // Close the current window
        });

        setVisible(true);
    }

    // Dummy method to simulate checking if child name exists
    private boolean checkChildName(String childName) {
        for (account u : familyaccount) {
            if (u.getChildName().equals(childName)) {
                return true;
            }
        }
        // Simulate that the child name exists in UserReader class
        return false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            user user = new user(); // you need to create a User object or pass it here
            new GUIlauchtasks(user).setVisible(true);
        });
    }
}
