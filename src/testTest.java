import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

import javax.swing.*;
import java.awt.*;

public class testTest {

    private test testFrame;

    @BeforeEach
    public void setUp() {
        testFrame = new test();
        testFrame.setSize(400, 300); // 设置和你主方法中相同的尺寸
        JLabel penLabel = new JLabel();
        Icon penIcon = new ImageIcon("C:\\Users\\ellipse\\Desktop\\kidsbank24\\src\\pen.png");
        penLabel.setIcon(penIcon);
        testFrame.add(penLabel);
        testFrame.setVisible(true);
        testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @AfterEach
    public void tearDown() {
        testFrame.dispose(); // 关闭窗体，清理资源
    }

    @Test
    public void testFrameSize() {
        assertEquals(new Dimension(400, 300), testFrame.getSize(), "Frame should be sized 400x300");
    }

    @Test
    public void testLabelIcon() {
        Component[] components = testFrame.getContentPane().getComponents();
        assertTrue(components.length > 0, "Should have components");
        assertTrue(components[0] instanceof JLabel, "First component should be a JLabel");
        JLabel label = (JLabel) components[0];
        assertNotNull(label.getIcon(), "Label should have an icon set");
    }
}
