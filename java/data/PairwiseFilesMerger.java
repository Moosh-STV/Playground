package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import static data.Constants.*;

public class PairwiseFilesMerger implements SortedFilesMerger {

    @Override
    public boolean mergeFiles(List<String> filenames, String inputFile) {
        try {
            mergeFilesInPairs(filenames, inputFile);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void mergeFilesInPairs(List<String> filenames, String outputFilename) throws IOException {
        int moveToListIdx, mergedFilenameCounter = 1, filesToMergeCount = filenames.size();
        FileWriter writer = new FileWriter();

        while (filesToMergeCount > 1) {
            moveToListIdx = 0;

            for (int i = 0; i < filesToMergeCount; i += 2) {
                if (isLastFileInOddNumberOfFiles(filesToMergeCount, i)) {
                    filenames.set(moveToListIdx, filenames.get(i));
                    break;
                }

                try (BufferedReader br = new BufferedReader(new FileReader(filenames.get(i)));
                     BufferedReader br2 = new BufferedReader(new FileReader(filenames.get(i + 1)))) {

                    String line = br.readLine();
                    String line2 = br2.readLine();
                    String outputFile = OUTPUT_MERGED_FILENAME_PREFIX + mergedFilenameCounter + OUTPUT_FILENAME_TYPE;
                    mergedFilenameCounter++;
                    filenames.set(moveToListIdx++, outputFile);

                    while (line != null || line2 != null) {
                        List<String> linesChunk = new ArrayList<>(READ_LINES_LIMIT);

                        for (int count = 0; count < READ_LINES_LIMIT && (line != null || line2 != null); count++) {
                            if (line == null) {
                                linesChunk.add(line2);
                                line2 = br2.readLine();
                            } else if (line2 == null) {
                                linesChunk.add(line);
                                line = br.readLine();
                            } else {
                                if (line.compareTo(line2) <= 0) {
                                    linesChunk.add(line);
                                    line = br.readLine();
                                } else {
                                    linesChunk.add(line2);
                                    line2 = br2.readLine();
                                }
                            }
                        }

                        writer.writeFile(outputFile, linesChunk);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            filesToMergeCount = calculateNewCount(filesToMergeCount);
        }
        renameAndMoveOutputFileToDestination(filenames, outputFilename);
    }

    private void renameAndMoveOutputFileToDestination(List<String> filenames, String outputFilename) throws IOException {
        String sortedOutputFilename = RESULT_FILENAME_PREFIX + (Paths.get(outputFilename).getFileName()).toString();
        Path outputPath = Files.move(Paths.get(filenames.get(0)), Paths.get(filenames.get(0)).getParent().resolve(Paths.get(sortedOutputFilename)));
        Files.move(outputPath, Paths.get(outputFilename).getParent().resolve(outputPath.getFileName()), StandardCopyOption.REPLACE_EXISTING);
    }

    private int calculateNewCount(int filesToMergeCount) {
        return (filesToMergeCount & 1) == 0 ? filesToMergeCount / 2 : (filesToMergeCount / 2) + 1;
    }

    private boolean isLastFileInOddNumberOfFiles(int filesToMergeCount, int i) {
        return i + 1 == filesToMergeCount;
    }
}
