import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

public class UserReaderTest {
    @TempDir
    Path tempDir;

    Path testFile;

    @BeforeEach
    void setUp() throws Exception {
        testFile = tempDir.resolve("user.csv");
        UserReader.setFilePath(testFile.toString());
        List<String> lines = List.of(
                "35327784,wangbowen,parent,11111111",
                "35327784,wuboheng,child,22222222",
                "65780374,aaaaaaaa,parent,11111111"
        );
        Files.write(testFile, lines);
    }

    @Test
    void testGetUserDetailsFound() throws Exception {
        user userDetails = UserReader.getUserDetails(35327784, "wangbowen");
        assertNotNull(userDetails);
        assertEquals(35327784, userDetails.getFamilyId());
        assertEquals("wangbowen", userDetails.getName());
        assertEquals("parent", userDetails.getIdentity());
        assertEquals("11111111", userDetails.getPassword());
    }


    @Test
    void testGetAllFamilyIds() {
        int[] ids = UserReader.getAllFamilyIds();
        assertEquals(2, ids.length);
        assertTrue(Arrays.stream(ids).anyMatch(id -> id == 35327784));
        assertTrue(Arrays.stream(ids).anyMatch(id -> id == 65780374));
    }
}
