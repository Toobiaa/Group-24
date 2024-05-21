import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import javax.swing.*;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestTransactionTest {

    private TestTransaction testTransaction;
    private user testUser;

    @BeforeEach
    public void setUp() {
        // 假设user是一个有效的对象，且已经创建
        testUser = new user(1, "Test User", "parent", "password123"); // 根据你的用户类构造方法假设
        testTransaction = new TestTransaction(testUser);
    }

    @AfterEach
    public void tearDown() {
        testTransaction.dispose(); // 关闭窗体以清理资源
    }

    @Test
    public void testFrameInitialization() {
        assertEquals("G", testTransaction.getTitle());
        assertEquals(new Dimension(700, 450), testTransaction.getSize());
        assertEquals(JFrame.EXIT_ON_CLOSE, testTransaction.getDefaultCloseOperation());
    }

    @Test
    public void testMainPanelAdded() {
        Component[] components = testTransaction.getContentPane().getComponents();
        assertTrue(components.length > 0, "Main panel should be added to the frame");
        assertTrue(components[0] instanceof JPanel, "First component should be a JPanel");
    }
}
