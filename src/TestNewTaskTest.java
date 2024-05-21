import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;

public class TestNewTaskTest {

    private TestNewTask testNewTask;
    private user testUser;

    @BeforeEach
    public void setUp() {
        // Assume user is a valid object that you would have created
        testUser = new user(1, "Test User", "testIdentity", "password"); // 假设的用户构造
        testNewTask = new TestNewTask(testUser);
    }

    @AfterEach
    public void tearDown() {
        testNewTask.dispose(); // Close the frame after each test
    }

    @Test
    public void testFrameInitialization() {
        assertEquals("C", testNewTask.getTitle());
        assertEquals(700, testNewTask.getSize().width);
        assertEquals(450, testNewTask.getSize().height);
        assertEquals(JFrame.EXIT_ON_CLOSE, testNewTask.getDefaultCloseOperation());
    }

    @Test
    public void testMainPanelAdded() {
        Component[] components = testNewTask.getContentPane().getComponents();
        assertTrue(components.length > 0);
        assertTrue(components[0] instanceof JPanel);
    }
}
