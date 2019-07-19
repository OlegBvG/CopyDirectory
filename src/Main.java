import java.io.IOException;
import java.nio.file.*;

public class Main {

    public static void main(String[] args) {

        String sourceDirStr = "d:\\1\\1";
        String targetDirStr = "d:\\1\\2";

        try {
            copyDir(sourceDirStr,  targetDirStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        static void copyDir(String source, String target) throws IOException{

            Path sourceDir = Paths.get(source);
            Path targetDir = Paths.get(target).resolve((sourceDir).getFileName());

            System.out.println("Копируется директория: " + sourceDir + " в директорию: " + target);

            if (Files.notExists(targetDir)) Files.createDirectories(targetDir);

            Files.walk(sourceDir).forEach(p ->
            {
                try {
                  Path q = targetDir.resolve(sourceDir.relativize(p).normalize());

                    if (Files.isDirectory(p)) {
                        if (Files.notExists(q)){
                            Files.createDirectories(q);
                            System.out.println("Создана папка: " + p +  " => " + q);
                        }
                    }
                    else {
                        Files.copy(p, q, StandardCopyOption.REPLACE_EXISTING);
                        System.out.println("Скопирован файл: " + p +  " => " + q);
                    }
                } catch (IOException ex) {
                    System.out.println("Ошибка чтения/записи");
                }
            });
         }
    }

