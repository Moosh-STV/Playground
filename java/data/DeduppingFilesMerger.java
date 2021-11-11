package data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.BiFunction;

public class DeduppingFilesMerger implements FilesMerger {

    @Override
    public boolean mergeFiles(List<String> filenames, String inputFile) {
        dedupMergeFiles(filenames);
        return true;
    }

    private void dedupMergeFiles(List<String> filenames) {

        FileWriter writer = new FileWriter();
//        int numberFileParts = 10; // TODO FileSizeCalculator should calculate from problem constrains: 2TB/8GB = 250 + a margin = 300.

        for (int i = 0; i < filenames.size(); i++) {
            Set<String> filePart = new HashSet<>();
            try (BufferedReader br = new BufferedReader(new FileReader(filenames.get(i)))) {
                String line = br.readLine();

                //TODO assuming (wrongly, for now) each file part fits into memory
                while (line != null) {
//                    filePartToLinesChunks = new HashMap<>();

//                    for (int count = 0; count < LINES_MEMORY_LIMIT_COUNT && line != null; count++) {
//                        String outputFile = OUTPUT_TMP_DIR + OUTPUT_FILE_PART_NAME + (line.hashCode() % numberFileParts) + OUTPUT_FILENAME_TYPE;
//                        filePartToLinesChunks.merge(outputFile, Set.of(line), mergeSets());
                    filePart.add(line);
                    line = br.readLine();
//                    }

                }
                writer.writeFile("test/resources/output.txt", filePart);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
