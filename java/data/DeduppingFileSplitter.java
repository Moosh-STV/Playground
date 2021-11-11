package data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static data.Constants.*;
import static data.Constants.OUTPUT_FILENAME_TYPE;

public class DeduppingFileSplitter implements FileSplitter {
    @Override
    public List<String> splitFile(String inputFile) throws IOException {
        Files.createDirectories(Paths.get(OUTPUT_TMP_DIR));
        return splitFiles(inputFile);
    }

    private List<String> splitFiles(String inputFile) {
        FileWriter writer = new FileWriter();
        List<String> outputFilenames = new ArrayList<>();
        int filenameCounter = 1;

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line = br.readLine();

            while (line != null) {
                Set<String> linesChunk = new HashSet<>(LINES_PER_FILE_LIMIT);

                for (int count = 0; count < LINES_PER_FILE_LIMIT && line != null; count++) {
                    linesChunk.add(line);
                    line = br.readLine();
                }

                String outputFile = OUTPUT_TMP_DIR + OUTPUT_FILENAME + filenameCounter + OUTPUT_FILENAME_TYPE;
                filenameCounter++;
                outputFilenames.add(outputFile);

                writer.writeFile(outputFile, linesChunk);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputFilenames;
    }
}
