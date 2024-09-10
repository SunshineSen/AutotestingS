package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FileNegativeTest {

    @Test
    public void testInvalidFileSize() {
        try {
            new File("InvalidFile.txt", -10);

        } catch (Exception e) {
            fail("File size cannot be negative");
        }
    }

    @Test
    public void testNullFileName() {

        try {
            new File(null, 10);

        } catch (Exception e) {
            fail("File name cannot be null");
        }
    }
}