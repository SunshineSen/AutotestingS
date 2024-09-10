package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DirectoryTest {

    private Directory directory;
    private File file1;
    private File file2;

    @BeforeEach
    public void setUp() {
        directory = new Directory("TestDirectory");
        file1 = new File("TestFile1.txt", 100);
        file2 = new File("TestFile2.txt", 200);
        directory.add(file1);
        directory.add(file2);
    }

    @Test
    public void testDirectoryCreation() {
        assertEquals("TestDirectory", directory.getName());
    }

    @Test
    public void testAddAndRemoveComponent() {
        directory.remove(file1);
        assertEquals(file2, directory.getChild(0));
    }

    @Test
    public void testGetChild() {
        assertEquals(file1, directory.getChild(0));
        assertEquals(file2, directory.getChild(1));
    }

    @Test
    public void testShowDetails() {
        directory.showDetails();
    }
}