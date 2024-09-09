package org.example;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DirectoryLifecycleTest {

    private Directory directory;
    private File file1;
    private File file2;

    @BeforeAll    public static void beforeAll() {
        System.out.println("Before all tests");
    }

    @AfterAll
    public static void afterAll() {
        System.out.println("After all tests");
    }

    @BeforeEach
    public void setUp() {
        System.out.println("Before each test");
        directory = new Directory("TestDirectory");
        file1 = new File("TestFile1.txt", 100);
        file2 = new File("TestFile2.txt", 200);
        directory.add(file1);
        directory.add(file2);
    }

    @AfterEach
    public void tearDown() {
        System.out.println("After each test");
        directory = null;
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
}

