import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class changeAccountDetailTest {

    private Path testCsv;

    @BeforeEach
    void setUp(@TempDir Path tempDir) throws Exception {
        testCsv = tempDir.resolve("testAccounts.csv");
        List<String> lines = List.of(
                "FamilyId,ChildName,SavingAccount,CurrentAccount,Goal",
                "35327784,wuboheng,11311,862,88888888",
                "35327784,wangxincheng,2959,682,1000"
        );
        Files.write(testCsv, lines);
    }

    @Test
    void testChangeSavingAccount() throws Exception {
        // 修改 Alice 的 SavingAccount
        changeAccountDetail.changeSavingAccount(35327784, "wuboheng", 11311);

        // 验证文件内容
        List<String> lines = Files.readAllLines(testCsv);
        assertTrue(lines.contains("35327784,wuboheng,11311,862,88888888"));
    }

    @Test
    void testChangeCurrentAccount() throws Exception {
        // 修改 Alice 的 CurrentAccount
        changeAccountDetail.changeCurrentAccount(35327784, "wuboheng", 862);

        // 验证文件内容
        List<String> lines = Files.readAllLines(testCsv);
        assertTrue(lines.contains("35327784,wuboheng,11311,862,88888888"));
    }

    @Test
    void testChangeGoal() throws Exception {
        // 修改 wuboheng 的 Goal
        changeAccountDetail.changeGoal(35327784, "wuboheng", 888888881);

        // 验证文件内容
        List<String> lines = Files.readAllLines(testCsv);
        assertTrue(!lines.contains("35327784,wuboheng,11311,862,888888881"));
    }
}
