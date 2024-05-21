import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GUITransactions extends JFrame {
    private JScrollPane scrollPane;
    private JLabel titleLabel;
    private JPanel transHeader;
    private JButton homeButton;
    private Image backgroundImage;
    private Image childBackgroundImage;

    public void initialize(int familyId, String name, String identity) {
        this.setTitle("submit tasks and get bonus!");
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

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);
        backgroundPanel.add(mainPanel, BorderLayout.CENTER);

        this.transHeader = new JPanel(new GridBagLayout());
        this.transHeader.setOpaque(false);

        if (identity.equals("parent")) {
            GridBagConstraints parentConstraints = new GridBagConstraints();
            parentConstraints.fill = GridBagConstraints.HORIZONTAL;
            parentConstraints.weightx = 1;
            parentConstraints.insets = new Insets(5, 5, 5, 5);

            parentConstraints.gridy = 0;
            parentConstraints.gridx = 0;
            parentConstraints.gridwidth = 3;
            this.titleLabel = new JLabel("Transactions", JLabel.CENTER);
            this.titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
            this.transHeader.add(titleLabel, parentConstraints);

            parentConstraints.gridy = 1;
            parentConstraints.gridwidth = 1;
            String[] headers = {"childName", "description", "change"};
            for (int i = 0; i < headers.length; i++) {
                parentConstraints.gridx = i;
                JLabel headerLabel = new JLabel(headers[i], JLabel.CENTER);
                headerLabel.setFont(new Font("Arial", Font.PLAIN, 18));
                this.transHeader.add(headerLabel, parentConstraints);
            }
        } else if (identity.equals("child")) {
            GridBagConstraints childConstraints = new GridBagConstraints();
            childConstraints.fill = GridBagConstraints.HORIZONTAL;
            childConstraints.weightx = 1;
            childConstraints.insets = new Insets(5, 5, 5, 5);

            childConstraints.gridy = 0;
            childConstraints.gridx = 0;
            childConstraints.gridwidth = 2;
            this.titleLabel = new JLabel("Transactions", JLabel.CENTER);
            this.titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
            this.transHeader.add(titleLabel, childConstraints);

            childConstraints.gridy = 1;
            childConstraints.gridwidth = 1;
            String[] headers = {"reason", "change"};
            for (int i = 0; i < headers.length; i++) {
                childConstraints.gridx = i;
                JLabel headerLabel = new JLabel(headers[i], JLabel.CENTER);
                headerLabel.setFont(new Font("Arial", Font.PLAIN, 18));
                this.transHeader.add(headerLabel, childConstraints);
            }
        }

        mainPanel.add(this.transHeader, BorderLayout.NORTH);

        if (identity.equals("parent")) {
            mainPanel.add(this.createScrollPane(familyId), BorderLayout.CENTER);
        } else if (identity.equals("child")) {
            mainPanel.add(this.createScrollPane(familyId, name), BorderLayout.CENTER);
        }

        homeButton = new JButton("Home");
        homeButton.setPreferredSize(new Dimension(100, 40));
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(new EmptyBorder(10, 50, 10, 50));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        homeButton.setFont(new Font("Arial", Font.BOLD, 18));
        buttonPanel.add(homeButton);
        homeButton.addActionListener(e -> {
            if (identity.equals("child")) {
                new GUIChild(familyId, name, "child").setVisible(true);
                this.setVisible(false);
            } else if (identity.equals("parent")) {
                new GUIParent(familyId, name, "parent").setVisible(true);
                this.setVisible(false);
            }
        });
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    public JScrollPane createScrollPane(int familyId) {
        BackgroundPanel containerPanel = new BackgroundPanel(backgroundImage, true);
        containerPanel.setLayout(new GridBagLayout());
        containerPanel.setOpaque(false);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.weightx = 1;
        constraints.weighty = 1;

        ArrayList<Record> records = RecordReader.getRecordInfoAll(familyId);
        boolean hasRecord = false;

        for (Record r : records) {
            if (r.getFamilyId() == familyId) {
                hasRecord = true;

                constraints.gridy = GridBagConstraints.RELATIVE;
                constraints.gridx = 0;
                constraints.anchor = GridBagConstraints.CENTER;
                JLabel childNameLabel = new JLabel(r.getName(), JLabel.CENTER);
                childNameLabel.setFont(new Font("微软雅黑", Font.PLAIN, 18));
                containerPanel.add(childNameLabel, constraints);

                constraints.gridx = 1;
                constraints.anchor = GridBagConstraints.CENTER;
                JLabel descriptionLabel = new JLabel(r.getType(), JLabel.CENTER);
                descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 18));
                containerPanel.add(descriptionLabel, constraints);

                constraints.gridx = 2;
                constraints.anchor = GridBagConstraints.CENTER;
                JLabel changeLabel = new JLabel();
                if (r.getChangeMoney() > 0) {
                    changeLabel.setText("+" + Integer.toString(r.getChangeMoney()));
                    changeLabel.setForeground(Color.RED);
                } else if (r.getChangeMoney() <= 0) {
                    changeLabel.setText(Integer.toString(r.getChangeMoney()));
                    changeLabel.setForeground(Color.GREEN);
                }
                changeLabel.setHorizontalAlignment(JLabel.CENTER);
                changeLabel.setFont(new Font("Arial", Font.PLAIN, 18));
                containerPanel.add(changeLabel, constraints);
            }
        }

        JScrollPane scrollPane = new JScrollPane(containerPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);

        if (!hasRecord) {
            containerPanel.removeAll();
            JLabel reminderLabel = new JLabel("No Records");
            reminderLabel.setFont(new Font("Arial", Font.ITALIC, 30));
            reminderLabel.setHorizontalAlignment(JLabel.CENTER);
            containerPanel.setLayout(new BorderLayout());
            containerPanel.add(reminderLabel, BorderLayout.CENTER);
            scrollPane.setBorder(BorderFactory.createEmptyBorder());
        }
        return scrollPane;
    }

    public JScrollPane createScrollPane(int familyId, String name) {
        BackgroundPanel containerPanel = new BackgroundPanel(backgroundImage, true);
        containerPanel.setLayout(new GridBagLayout());
        containerPanel.setOpaque(false);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.weightx = 1;
        constraints.weighty = 1;

        ArrayList<Record> records = RecordReader.getRecordInfoAll(familyId);
        boolean hasRecord = false;

        for (Record r : records) {
            if (r.getName().equals(name)) {
                hasRecord = true;

                constraints.gridx = 0;
                constraints.anchor = GridBagConstraints.CENTER;
                JLabel descriptionLabel = new JLabel(r.getType(), JLabel.CENTER);
                descriptionLabel.setFont(new Font("微软雅黑", Font.PLAIN, 18));
                containerPanel.add(descriptionLabel, constraints);

                constraints.gridx = 1;
                constraints.anchor = GridBagConstraints.CENTER;
                JLabel changeLabel = new JLabel();
                if (r.getChangeMoney() > 0) {
                    changeLabel.setText("+" + Integer.toString(r.getChangeMoney()));
                    changeLabel.setForeground(Color.RED);
                } else if (r.getChangeMoney() <= 0) {
                    changeLabel.setText(Integer.toString(r.getChangeMoney()));
                    changeLabel.setForeground(Color.GREEN);
                }
                changeLabel.setHorizontalAlignment(JLabel.CENTER);
                changeLabel.setFont(new Font("Arial", Font.PLAIN, 18));
                containerPanel.add(changeLabel, constraints);
            }
        }

        JScrollPane scrollPane = new JScrollPane(containerPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);

        if (!hasRecord) {
            containerPanel.removeAll();
            JLabel reminderLabel = new JLabel("No Records");
            reminderLabel.setFont(new Font("Arial", Font.BOLD, 30));
            reminderLabel.setHorizontalAlignment(JLabel.CENTER);
            containerPanel.setLayout(new BorderLayout());
            containerPanel.add(reminderLabel, BorderLayout.CENTER);
            scrollPane.setBorder(BorderFactory.createEmptyBorder());
        }

        return scrollPane;
    }

    public GUITransactions(int familyId, String name, String identity) {
        this.initialize(familyId, name, identity);
    }

    private static class BackgroundPanel extends JPanel {
        private Image image;
        private boolean isTiled;

        public BackgroundPanel(Image image) {
            this(image, false);
        }

        public BackgroundPanel(Image image, boolean isTiled) {
            this.image = image;
            this.isTiled = isTiled;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (image != null) {
                if (isTiled) {
                    for (int x = 0; x < getWidth(); x += image.getWidth(null)) {
                        for (int y = 0; y < getHeight(); y += image.getHeight(null)) {
                            g.drawImage(image, x, y, this);
                        }
                    }
                } else {
                    g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                }
            }
        }
    }

    public static void main(String[] args) {
        GUITransactions myTrans = new GUITransactions(51249745, "Fanzizhou", "parent");
        myTrans.setVisible(true);
    }
}

