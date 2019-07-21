import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class Main {

    public static void main(String[] args) {

        String sourceDirStr = "d:\\1\\1";
        String targetDirStr = "d:\\1\\1\\4";

        /*String deleteDirStr = "d:\\1\\1";
        Path delDir = Paths.get(deleteDirStr);
        deleteDir(delDir);*/

        try {
            copyDir(sourceDirStr, targetDirStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // удалить дерево каталогов, начиная с корневого каталога
    static void deleteDir(Path sDir) {
        try {
            Files.walkFileTree(sDir, new SimpleFileVisitor<Path>() {
                public FileVisitResult visitFile(Path file,
                                                 BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                public FileVisitResult postVisitDirectory(Path dir,
                                                          IOException e) throws IOException {
                    if (e != null) throw e;
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Копирование директории по абсолютным путям
    static void copyDir(String source, String target) throws IOException {

        Path sourceDir = Paths.get(source);
        Path targetDir = Paths.get(target).resolve((sourceDir).getFileName());

        System.out.println("Копируется директория: " + sourceDir + " в директорию: " + target);

        if (Files.notExists(targetDir)) Files.createDirectories(targetDir);

        Files.walk(sourceDir).forEach(p ->
        {
            try {
                Path q = targetDir.resolve(sourceDir.relativize(p).normalize());
//                System.out.println(target);
                if (!p.startsWith(target)) {

                    if (Files.isDirectory(p)) {
                        if (Files.notExists(q)) {
                            Files.createDirectories(q);
                            System.out.println("Создана папка: " + p + " => " + q);
                        }
                    } else {
                        Files.copy(p, q, StandardCopyOption.REPLACE_EXISTING);
                        System.out.println("Скопирован файл: " + p + " => " + q);
                    }
                }
            } catch (IOException ex) {
                System.out.println("Ошибка чтения/записи");
            }
        });
    }
}