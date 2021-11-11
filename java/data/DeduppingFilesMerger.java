package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static data.Constants.*;

public class DeduppingFilesMerger implements FilesMerger {

    @Override
    public boolean mergeFiles(List<String> filenames, String inputFile) {
        dedupMergeFiles(filenames);
        return true;
    }

    private void dedupMergeFiles(List<String> filenames) {
        FileWriter writer = new FileWriter();
        DeduppingFileSplitter deduppingFileSplitter = new DeduppingFileSplitter();
        int numberFileParts = NUMBER_FILE_PARTS; // TODO refactor
        int i = filenames.size() - 1;

        while (i >= 0) {
            Set<String> filePart = new HashSet<>();
            String currentFilename = filenames.get(i);
            try (BufferedReader br = new BufferedReader(new FileReader(currentFilename))) {
                String line = br.readLine();

                //Can use File's length(), but it reads through the file to count bytes.
                for (int count = 0; count < LINES_MEMORY_LIMIT_COUNT && line != null; count++) {
                    filePart.add(line);
                    line = br.readLine();
                }

                filenames.remove(i); //O(1) since iterating backwards.

                if (isFileToBigToFitInMemory(line)) {
                    List<String> additionalFileParts = deduppingFileSplitter.splitFile(currentFilename, ++numberFileParts);
                    filenames.addAll(additionalFileParts);
                    i = filenames.size() - 1;
                } else {
                    i--;
                }
                writer.writeFile("test/resources/output.txt", filePart);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isFileToBigToFitInMemory(String line) {
        return line != null;
    }
}
