import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class GUITasksParent extends JFrame {
    private JScrollPane scrollPane;
    private JLabel taskTitleLabel;
    private JPanel taskHeader;
    private JButton homeButton;
    private int FamilyId;
    private String Name;
    private Image topBottomBackgroundImage;
    private Image centerBackgroundImage;

    public GUITasksParent(int familyId, String name) {
        try {
            topBottomBackgroundImage = ImageIO.read(new File("backgd.jpg"));
            centerBackgroundImage = ImageIO.read(new File("function.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.initialize(familyId, name);
    }

    public void initialize(int familyId, String name) {
        this.Name = name;
        this.FamilyId = familyId;
        this.setTitle("View Tasks and Give Rewards");
        this.setSize(900, 400);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.getContentPane().setLayout(new BorderLayout());

        // Top panel with background
        this.taskHeader = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (topBottomBackgroundImage != null) {
                    for (int y = 0; y < getHeight(); y += topBottomBackgroundImage.getHeight(this)) {
                        for (int x = 0; x < getWidth(); x += topBottomBackgroundImage.getWidth(this)) {
                            g.drawImage(topBottomBackgroundImage, x, y, this);
                        }
                    }
                }
            }
        };
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.insets = new Insets(5, 5, 5, 5);

        // Title label
        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.gridwidth = 5;
        this.taskTitleLabel = new JLabel("Tasks", JLabel.CENTER);
        this.taskTitleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        taskHeader.add(taskTitleLabel, constraints);

        // Table headers
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        String[] headers = {"childName", "taskName", "bonus", "action", "status"};
        for (int i = 0; i < headers.length; i++) {
            constraints.gridx = i;
            JLabel headerLabel = new JLabel(headers[i], JLabel.CENTER);
            headerLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            taskHeader.add(headerLabel, constraints);
        }
        this.getContentPane().add(BorderLayout.NORTH, taskHeader);

        // Create task UI
        this.updateTasksUI(familyId);

        // Home button with background
        homeButton = new JButton("Home");
        homeButton.setPreferredSize(new Dimension(100, 40));
        homeButton.setFont(new Font("Arial", Font.BOLD, 18));
        JPanel buttonPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (topBottomBackgroundImage != null) {
                    for (int y = 0; y < getHeight(); y += topBottomBackgroundImage.getHeight(this)) {
                        for (int x = 0; x < getWidth(); x += topBottomBackgroundImage.getWidth(this)) {
                            g.drawImage(topBottomBackgroundImage, x, y, this);
                        }
                    }
                }
            }
        };
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(homeButton);
        homeButton.addActionListener(e -> {
            new GUIParent(familyId, name, "parent").setVisible(true);
            this.setVisible(false);
        });
        this.getContentPane().add(BorderLayout.SOUTH, buttonPanel);
    }

    private void updateTasksUI(int familyId) {
        if (scrollPane != null) {
            this.getContentPane().remove(scrollPane);
        }
        this.scrollPane = createScrollPane(familyId);
        this.getContentPane().add(BorderLayout.CENTER, this.scrollPane);
        this.revalidate();
        this.repaint();
    }

    private JScrollPane createScrollPane(int familyId) {
        JPanel containerPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (centerBackgroundImage != null) {
                    for (int y = 0; y < getHeight(); y += centerBackgroundImage.getHeight(this)) {
                        for (int x = 0; x < getWidth(); x += centerBackgroundImage.getWidth(this)) {
                            g.drawImage(centerBackgroundImage, x, y, this);
                        }
                    }
                }
            }
        };
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.weightx = 1;
        constraints.weighty = 1;

        ArrayList<task> tasks = task.viewTasks(familyId);

        for (task t : tasks) {
            constraints.gridy = GridBagConstraints.RELATIVE;
            constraints.gridx = 0;
            constraints.anchor = GridBagConstraints.CENTER;
            JLabel childNameLabel = new JLabel(t.getChildName(), JLabel.CENTER);
            childNameLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            containerPanel.add(childNameLabel, constraints);

            constraints.gridx = 1;
            constraints.anchor = GridBagConstraints.CENTER;
            JLabel taskNameLabel = new JLabel(t.getTaskName(), JLabel.CENTER);
            taskNameLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            containerPanel.add(taskNameLabel, constraints);

            constraints.gridx = 2;
            constraints.anchor = GridBagConstraints.CENTER;
            JLabel bonusLabel = new JLabel(Integer.toString(t.getBonus()), JLabel.CENTER);
            bonusLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            containerPanel.add(bonusLabel, constraints);

            constraints.gridx = 3;
            constraints.anchor = GridBagConstraints.CENTER;
            constraints.weightx = 0.3;
            JButton permitButton = new JButton("Permit");
            permitButton.setPreferredSize(new Dimension(80, 30));
            if (t.isSubmitted()) {
                if (t.isDone()) {
                    permitButton.setForeground(Color.GRAY);
                    permitButton.setEnabled(false);
                } else {
                    permitButton.setForeground(Color.BLACK);
                }
            } else {
                permitButton.setForeground(Color.GRAY);
                permitButton.setEnabled(false);
            }
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            buttonPanel.add(permitButton);
            containerPanel.add(buttonPanel, constraints);

            permitButton.addActionListener(e -> {
                parent p = new parent(this.FamilyId, this.Name, "parent");
                p.PermitTask(t.getTaskId());
                this.updateTasksUI(familyId);
            });

            constraints.gridx = 4;
            constraints.anchor = GridBagConstraints.CENTER;
            JLabel statusLabel = new JLabel();
            if (t.isSubmitted()) {
                if (t.isDone()) {
                    statusLabel.setText("Done");
                    statusLabel.setForeground(Color.GREEN);
                } else {
                    statusLabel.setText("Submitted");
                    statusLabel.setForeground(Color.RED);
                }
            } else {
                statusLabel.setText("Not Submitted");
                statusLabel.setForeground(Color.GRAY);
            }
            statusLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            statusLabel.setHorizontalAlignment(JLabel.CENTER);
            containerPanel.add(statusLabel, constraints);
        }

        JScrollPane scrollPane = new JScrollPane(containerPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(500, 300));
        scrollPane.setBorder(new EmptyBorder(10, 0, 10, 0));

        if (tasks.isEmpty()) {
            containerPanel.removeAll();
            JLabel reminderLabel = new JLabel("No Tasks");
            reminderLabel.setFont(new Font("Arial", Font.ITALIC, 30));
            reminderLabel.setHorizontalAlignment(JLabel.CENTER);
            containerPanel.setLayout(new BorderLayout());
            containerPanel.add(reminderLabel, BorderLayout.CENTER);
            scrollPane.setBorder(new EmptyBorder(10, 0, 10, 0));
        }

        return scrollPane;
    }

    // main仅用于测试
    public static void main(String[] args) {
        GUITasksParent myTasks = new GUITasksParent(89106409, "明泽");
        myTasks.setVisible(true);
    }
}





