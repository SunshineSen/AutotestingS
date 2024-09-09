package org.example;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FileNegativeTest {

        @Test    public void testInvalidFileSize() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                new File("InvalidFile.txt", -10);
            });
            assertEquals("File size cannot be negative", exception.getMessage());
        }

        @Test
        public void testNullFileName() {
            NullPointerException exception = assertThrows(NullPointerException.class, () -> {
                new File(null, 10);
            });
            assertEquals("File name cannot be null", exception.getMessage());
        }
}
