package org.example;

public class File extends FileSystemComponent {
    private int size;

    public File(String name, int size) {
        super(name);
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    @Override
    public void showDetails() {
        System.out.println("File: " + name + " (Size: " + size + "KB)");
    }
}
