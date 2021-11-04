package data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static data.Constants.*;

public class SortingFileSplitter implements FileSplitter {
    @Override
    public List<String> splitFile(String inputFile) throws IOException {
        Files.createDirectories(Paths.get(OUTPUT_TMP_DIR));
        return splitFileToSortedFiles(inputFile);
    }

    private List<String> splitFileToSortedFiles(String inputFile) {
        FileWriter writer = new FileWriter();
        List<String> outputFilenames = new ArrayList<>();
        int filenameCounter = 1;

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line = br.readLine();

            while (line != null) {
                List<String> linesChunk = new ArrayList<>(LINES_PER_FILE_LIMIT);

                for (int count = 0; count < LINES_PER_FILE_LIMIT && line != null; count++) {
                    linesChunk.add(line);
                    line = br.readLine();
                }

                Collections.sort(linesChunk);
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
