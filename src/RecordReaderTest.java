import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.nio.file.Files;
import java.util.List;

public class RecordReaderTest {
    private Path tempFile;

    @BeforeEach
    void setUp(@TempDir Path tempDir) throws Exception {
        tempFile = tempDir.resolve("record.csv");
        // 设定 RecordReader 使用的文件路径
        RecordReader.setFilePath(tempFile.toString());
        // 准备测试数据
        List<String> lines = List.of(
                "1,Alice,Spent on toys,-20",
                "1,Bob,Earned from chores,15",
                "2,Charlie,Sold lemonade,30"
        );
        Files.write(tempFile, lines);
    }

    @Test
    void testGetRecordInfoAll() throws Exception {
        List<Record> records = RecordReader.getRecordInfoAll(1);
        assertEquals(2, records.size()); // Expecting two records for familyId 1
    }

    @Test
    void testGetRecordInfoPersonal() throws Exception {
        List<Record> records = RecordReader.getRecordInfoPersonal(1, "Alice");
        assertEquals(1, records.size()); // Expecting one record for Alice
        Record record = records.get(0);
        assertEquals("Alice", record.getName());
        assertEquals(-20, record.getChangeMoney());
    }
}
