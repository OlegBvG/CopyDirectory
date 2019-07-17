import java.io.IOException;
import java.nio.file.*;

public class Main {

    public static void main(String[] args) {

        String sourceDirStr = "..\\CopyDirectory";
        String targetDirStr = "d:\\ArhCopyDirectory";

        try {
            copyDir(sourceDirStr, targetDirStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        static void copyDir(String source, String target) throws IOException{

            Path sourceDir = Paths.get(source);
            Path targetDir = Paths.get(target).resolve((sourceDir).getFileName());

            Files.walk(sourceDir).forEach(p ->
            {
                try {
                  Path q = targetDir.resolve(p).normalize();
                  Files.copy(p, q, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException ex) {
                    System.out.println("Ошибка чтения/записи");
                }
            });
         }
    }

