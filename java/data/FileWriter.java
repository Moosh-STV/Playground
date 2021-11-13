package data;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collection;

public class FileWriter {
    public void writeFile(String outputFile, Collection<String> linesChunk) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputFile), StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            for (String line : linesChunk) {
                if (!line.isEmpty()) {
                    writer.append(line);
                    writer.newLine();
                }
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
