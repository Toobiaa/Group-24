import javax.swing.*;
import java.awt.*;

public class test extends JFrame {
    public static void main(String[] args) {
        test t = new test();
        t.setSize(400,300);
        JLabel penLabel = new JLabel();
        Icon penIcon = new ImageIcon("C:\\Users\\ellipse\\Desktop\\kidsbank24\\src\\pen.png");
        penLabel.setIcon(penIcon);
        t.add(penLabel);
        t.setVisible(true);
        t.setDefaultCloseOperation(EXIT_ON_CLOSE);
        return;
    }
}
