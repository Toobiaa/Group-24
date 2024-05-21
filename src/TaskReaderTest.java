import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.nio.file.Files;
import java.util.List;

public class TaskReaderTest {
    private Path tempFile;

    @BeforeEach
    void setUp(@TempDir Path tempDir) throws Exception {
        tempFile = tempDir.resolve("task.csv");
        // 设置 TaskReader 使用的文件路径
        TaskReader.setFilePath(tempFile.toString());
        // 准备测试数据
        List<String> lines = List.of(
                "1,Math Homework,50,123,true,Alice,false",
                "2,Science Project,75,124,false,Bob,true",
                "3,English Essay,30,123,true,Alice,true"
        );
        Files.write(tempFile, lines);
    }

    @Test
    void testGetTaskInfoAll() throws Exception {
        List<task> tasks = TaskReader.getTaskInfoAll(123);
        assertEquals(2, tasks.size()); // Expecting two tasks for familyId 123
    }

    @Test
    void testGetTaskInfoPersonal() throws Exception {
        List<task> tasks = TaskReader.getTaskInfoPersonal(123, "Alice");
        assertEquals(2, tasks.size()); // Expecting two tasks for Alice in familyId 123
        assertEquals("Math Homework", tasks.get(0).getTaskName());
        assertEquals(true, tasks.get(0).isSubmitted());
    }

    @Test
    void testGetTaskInfo() throws Exception {
        List<task> tasks = TaskReader.getTaskInfo();
        assertEquals(3, tasks.size()); // Expecting three tasks in total
    }
}
