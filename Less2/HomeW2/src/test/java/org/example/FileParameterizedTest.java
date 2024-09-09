package org.example;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

public class FileParameterizedTest {
    @ParameterizedTest    @CsvSource({
            "TestFile1.txt, 100",
            "TestFile2.txt, 200",
            "TestFile3.txt, 300"
    })
    public void testFileCreation(String name, int size) {
        File file = new File(name, size);
        assertEquals(name, file.getName());
        assertEquals(size, file.getSize());
    }
}

