import javax.swing.*;
import java.awt.*;

public class TestTransaction extends JFrame {
    private user currentUser;

    public TestTransaction(user currentUser) {
        this.currentUser = currentUser;

        setTitle("G");
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