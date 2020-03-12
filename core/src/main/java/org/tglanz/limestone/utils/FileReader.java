package org.tglanz.limestone.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileReader {
    public static String readContent(String path) throws IOException {
        return String.join("\n", Files.readAllLines(Paths.get(path)));
    }
}
