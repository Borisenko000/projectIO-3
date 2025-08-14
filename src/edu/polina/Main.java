package edu.polina;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Path path = Paths.get("C:\\Users\\Professional\\Documents\\task");
        Path path2 = Paths.get("C:\\Users\\Professional\\Documents\\task2");
        System.out.println("директория существует?" + Files.exists(path));
        try (Stream<Path> paths = Files.walk(path)) {
            paths
                    .filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".txt"))
                    .forEach(p -> {
                        try {
                            String filename = p.getFileName().toString();
                            long size = Files.size(p);
                            System.out.println("Имя файла:" + filename + ", размер:" + size);
                            String copyname = filename.substring(0, filename.lastIndexOf('.')) + "_backup" + filename.substring(filename.lastIndexOf('.'));
                            Path target = path2.resolve(copyname);
                            Files.copy(p, target, REPLACE_EXISTING);
                        } catch (IOException e) {
                            e.getStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.getStackTrace();
        }

        try (Stream<Path> paths = Files.walk(path2)) {
            paths.forEach(p -> System.out.println(p.toFile().getName()));
        } catch (IOException e) {
            e.getStackTrace();
        }
    }
}