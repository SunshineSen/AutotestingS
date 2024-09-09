package org.example;

public class FileSystemComposite {
    public static void main(String[] args) {
        // Создание файлов
        File file1 = new File("File1.txt", 10);
        File file2 = new File("File2.txt", 20);
        File file3 = new File("File3.txt", 15);

        // Создание директорий и добавление файлов в них
        Directory dir1 = new Directory("Directory1");
        dir1.add(file1);

        Directory dir2 = new Directory("Directory2");
        dir2.add(file2);
        dir2.add(file3);

        // Создание основной директории и добавление в нее других директорий и файлов
        Directory mainDir = new Directory("MainDirectory");
        mainDir.add(dir1);
        mainDir.add(dir2);
        mainDir.add(new File("MainFile.txt", 30));

        // Вывод структуры файловой системы
        mainDir.showDetails();
    }
}