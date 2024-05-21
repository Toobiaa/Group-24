import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.nio.file.Files;
import java.util.List;

public class CSVReaderTest {
    private Path tempFile;

    @BeforeEach
    void setUp(@TempDir Path tempDir) throws Exception {
        tempFile = tempDir.resolve("user.csv");
        // 设定 CSVReader 使用的文件路径
        CSVReader.setFilePath(tempFile.toString());
        // 准备测试数据
        List<String> lines = List.of(
                "35327784,wangbowen,parent,11111111",
                "35327784,wuboheng,child,22222222"
        );
        Files.write(tempFile, lines);
    }

    @Test
    void testGetUserDetailsValidUser() throws Exception {
        user userDetails = CSVReader.getUserDetails(35327784, "wangbowen");
        assertEquals(35327784, userDetails.getFamilyId());
        assertEquals("wangbowen", userDetails.getName());
        assertEquals("parent", userDetails.getIdentity());
        assertEquals("11111111", userDetails.getPassword());
    }

}
