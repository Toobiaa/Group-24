import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.nio.file.Files;
import java.util.List;

public class CSVFileWriterTest {
    private Path tempFile;

    @BeforeEach
    void setUp(@TempDir Path tempDir) throws Exception {
        tempFile = tempDir.resolve("account.csv");
        // 设定 CSVFileWriter 使用的文件路径
        CSVFileWriter.setFilePath(tempFile.toString());
    }

    @Test
    void testWriteAccount() throws Exception {
        CSVFileWriter.writeAccount(123, "Amy", 23, 0, 100);

        // 验证文件内容
        List<String> lines = Files.readAllLines(tempFile);
        assertTrue(lines.contains("123,Amy,23,0,100"));
    }
}
