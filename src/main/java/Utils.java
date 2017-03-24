import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Properties;

/**
 * Created by GXP8655 on 3/23/2017.
 */
public class Utils {
    public static void deleteDirectory(String directoryFilePath) {
        Path directory = Paths.get(directoryFilePath);
        try {
            if (Files.exists(directory)) {
                Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path path, BasicFileAttributes basicFileAttributes) throws IOException {
                        Files.delete(path);
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult postVisitDirectory(Path directory, IOException ioException) throws IOException {
                        Files.delete(directory);
                        return FileVisitResult.CONTINUE;
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Fatal Error while deleting reports folder");
        }


    }

}
