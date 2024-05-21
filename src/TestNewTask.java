import javax.swing.*;

public class TestNewTask extends JFrame {
    private user currentUser;

    public TestNewTask(user currentUser) {
        this.currentUser = currentUser;

        setTitle("C");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initializeUI();
    }

    private void initializeUI() {
        // 主面板
        JPanel mainPanel = new JPanel();
        add(mainPanel);
    }
}
