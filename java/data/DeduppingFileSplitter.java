package data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.BiFunction;

import static data.Constants.*;

public class DeduppingFileSplitter implements FileSplitter {
    private static int prefixCount = 0;

    @Override
    public List<String> splitFile(String inputFile) throws IOException {
        return splitFile(inputFile, NUMBER_FILE_PARTS);
    }

    public List<String> splitFile(String inputFile, int numberFileParts) throws IOException {
        Files.createDirectories(Paths.get(OUTPUT_TMP_DIR));
        return splitFiles(inputFile, numberFileParts);
    }

    private List<String> splitFiles(String inputFile, int numberFileParts) {
        prefixCount++;
        FileWriter writer = new FileWriter();
        Set<String> outputFilenames = new HashSet<>();
        Map<String, Set<String>> filePartToLinesChunks;

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line = br.readLine();

            while (line != null) {
                filePartToLinesChunks = new HashMap<>();

                for (int count = 0; count < LINES_MEMORY_LIMIT_COUNT && line != null; count++) {
                    String outputFile = OUTPUT_TMP_DIR + prefixCount + OUTPUT_FILE_PART_NAME + (line.hashCode() % numberFileParts) + OUTPUT_FILENAME_TYPE;
                    filePartToLinesChunks.merge(outputFile, Set.of(line), mergeSets());
                    line = br.readLine();
                }

                filePartToLinesChunks.forEach(writer::writeFile);
                outputFilenames.addAll(filePartToLinesChunks.keySet());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>(outputFilenames);
    }

    private BiFunction<Set<String>, Set<String>, Set<String>> mergeSets() {
        return (s1, s2) -> {
            Set<String> lines = new HashSet<>(s1);
            lines.addAll(s2);
            return lines;
        };
    }
}
