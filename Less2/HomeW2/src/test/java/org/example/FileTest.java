package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FileTest {
    @Test
    public void testFileCreation() {
        File file = new File("TestFile.txt", 50);
        assertEquals("TestFile.txt", file.getName());
        assertEquals(50, file.getSize());
    }

    @Test
    public void testShowDetails() {
        File file = new File("TestFile.txt", 50);
        file.showDetails();

    }
}

