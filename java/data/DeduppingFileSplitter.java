package data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.function.BiFunction;

import static data.Constants.*;
import static data.Constants.OUTPUT_FILENAME_TYPE;

public class DeduppingFileSplitter implements FileSplitter {
    @Override
    public List<String> splitFile(String inputFile) throws IOException {
        Files.createDirectories(Paths.get(OUTPUT_TMP_DIR));
        return splitFiles(inputFile);
    }

    private List<String> splitFiles(String inputFile) throws IOException {
        FileWriter writer = new FileWriter();
        int numberFileParts = 10; // TODO FileSizeCalculator should calculate from problem constrains: 2TB/8GB = 250 + a margin = 300.
        Map<String, Set<String>> filePartToLinesChunks = new HashMap<>();
        List<String> outputFilenames = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line = br.readLine();

            while (line != null) {
                filePartToLinesChunks = new HashMap<>();

                for (int count = 0; count < LINES_MEMORY_LIMIT_COUNT && line != null; count++) {
                    String outputFile = OUTPUT_TMP_DIR + OUTPUT_FILE_PART_NAME + (line.hashCode() % numberFileParts) + OUTPUT_FILENAME_TYPE;
                    filePartToLinesChunks.merge(outputFile, Set.of(line), mergeSets());
                    line = br.readLine();
                }

                filePartToLinesChunks.forEach(writer::writeFile);
                outputFilenames.addAll(filePartToLinesChunks.keySet());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputFilenames;
    }

    private BiFunction<Set<String>, Set<String>, Set<String>> mergeSets() {
        return (s1, s2) -> {
            Set<String> lines = new HashSet<>(s1);
            lines.addAll(s2);
            return lines;
        };
    }
}
