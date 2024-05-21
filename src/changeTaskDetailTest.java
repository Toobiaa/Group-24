import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.List;

public class changeTaskDetailTest {
    private Path testCsv;

    @BeforeEach
    void setUp(@TempDir Path tempDir) throws Exception {
        testCsv = tempDir.resolve("testTasks.csv");
        List<String> lines = List.of(
                "TaskId,TaskName,Bonus,FamilyId,IsSubmitted,ChildName,IsDone",
                "1,coding,1000,35327784,true,wuboheng,false",
                "2,cooking,3200,35327784,true,wuboheng,true"
        );
        Files.write(testCsv, lines);
        // Update the filePath in the changeTaskDetail class to use the temp file
        // This assumes you have modified changeTaskDetail to accept file path changes.
    }

    @Test
    void testChangeSubState() throws Exception {
        // Change submission state for TaskId 1
        changeTaskDetail.changeSubState(1, false);

        // Verify the changes
        List<String> lines = Files.readAllLines(testCsv);
        assertEquals("1,coding,1000,35327784,true,wuboheng,false", lines.get(1));
    }

    @Test
    void testChangeDoneState() throws Exception {
        // Change done state for TaskId 2
        changeTaskDetail.changeDoneState(2, false);

        // Verify the changes
        List<String> lines = Files.readAllLines(testCsv);
        assertEquals("2,cooking,3200,35327784,true,wuboheng,true", lines.get(2));
    }
}
