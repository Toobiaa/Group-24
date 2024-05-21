import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUIwithdraw extends JFrame {
    private int familyId;
    private String childName;
    private String identity;
    private JLabel titleLabel, withdrawLabel;
    private JTextField withdrawField;
    private JButton okButton;
    private JButton backButton;

    private int goal;
    private int currentAccount;
    private int savingAccount;

    private JLabel savingAccountLabel;
    private JLabel currentAccountLabel;
    private JLabel goalLabel;

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

    public GUIwithdraw(int familyId, String childName, String identity) {
        this.familyId = familyId;
        this.childName = childName;
        this.identity = identity;

        ArrayList<account> accounts = AccountReader.showAccount(familyId, childName);
        for (account acc : accounts) {
            this.goal = acc.getGoal();
            this.currentAccount = acc.getCurrentAccount();
            this.savingAccount = acc.getSavingAccount();
        }

        setTitle("Withdraw");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 使用自定义背景面板
        BackgroundPanel backgroundPanel = new BackgroundPanel("backgd.jpg");
        backgroundPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 5, 5);

        titleLabel = new JLabel("Withdraw");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        backgroundPanel.add(titleLabel, gbc);

        // Add labels to show account details
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 5, 5, 5);
        savingAccountLabel = new JLabel("Saving Account: ¥" + savingAccount);
        savingAccountLabel.setFont(new Font("Arial", Font.BOLD, 18));
        currentAccountLabel = new JLabel("Current Account: ¥" + currentAccount);
        currentAccountLabel.setFont(new Font("Arial", Font.BOLD, 18));
        goalLabel = new JLabel("Goal: ¥" + goal);
        goalLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JPanel accountPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        accountPanel.setOpaque(false); // 设置面板透明，以便显示背景图片
        accountPanel.add(savingAccountLabel);
        accountPanel.add(currentAccountLabel);
        accountPanel.add(goalLabel);

        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        backgroundPanel.add(accountPanel, gbc);

        withdrawLabel = new JLabel("Withdraw amount:");
        withdrawLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        backgroundPanel.add(withdrawLabel, gbc);

        withdrawField = new JTextField(10);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        backgroundPanel.add(withdrawField, gbc);

        okButton = new JButton("OK");
        okButton.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        backgroundPanel.add(okButton, gbc);

        backButton = new JButton("Home");
        backButton.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        backgroundPanel.add(backButton, gbc);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String withdrawAmountText = withdrawField.getText();
                if (!withdrawAmountText.isEmpty()) {
                    if (withdrawAmountText.matches("\\d+")) {
                        double withdrawAmount = Double.parseDouble(withdrawAmountText);
                        account Account = new account();
                        ArrayList<account> accountList = AccountReader.showAccount(familyId, childName);
                        for (account t : accountList) {
                            Account = t;
                        }
                        if (withdrawAmount < Integer.MAX_VALUE) {
                            if (withdrawAmount == 0) {
                                JOptionPane.showMessageDialog(null, "Please enter a withdraw amount!");
                            } else if (withdrawAmount <= Account.getSavingAccount()) {
                                Child child = new Child(familyId, childName, identity);
                                child.withdraw((int) withdrawAmount);
                                savingAccount -= withdrawAmount;
                                currentAccount += withdrawAmount;
                                updateLabels();
                                JOptionPane.showMessageDialog(null, "Withdraw successful! And now, your saving account is ¥" +
                                        savingAccount + ", your current account is ¥" + currentAccount + ".");
                            } else if (withdrawAmount > Account.getSavingAccount()) {
                                JOptionPane.showMessageDialog(null, "Your money in Saving Account is not enough!");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Your money in Saving Account is not enough!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Withdraw Amount should be a positive number!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter a withdraw amount!");
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

    private void updateLabels() {
        savingAccountLabel.setText("Saving Account: ¥" + savingAccount);
        currentAccountLabel.setText("Current Account: ¥" + currentAccount);
        goalLabel.setText("Goal: ¥" + goal);
    }

    public static void main(String[] args) {
        new GUIwithdraw(1, "2", "child");
    }
}

