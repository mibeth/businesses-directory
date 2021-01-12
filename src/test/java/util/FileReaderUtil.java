package util;

import java.util.Scanner;

import static java.nio.charset.StandardCharsets.UTF_8;

public class FileReaderUtil {
    public static String readFile(final String filePath) {
        final var resourceStream = FileReaderUtil.class.getClassLoader().getResourceAsStream(filePath);
        return new Scanner(resourceStream, UTF_8).useDelimiter("\\A").next();
    }
}
