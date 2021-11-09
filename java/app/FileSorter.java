package app;

import data.FileSplitter;
import data.SortedFilesMerger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileSorter {

    private final FileSplitter sortingFileSplitter;
    private final SortedFilesMerger sortedFilesMerger;

    public FileSorter(FileSplitter fileSplitter, SortedFilesMerger sortedFilesMerger) {
        this.sortingFileSplitter = fileSplitter;
        this.sortedFilesMerger = sortedFilesMerger;
    }


    public void sortBigFile(String inputFile) throws IOException {
        List<String> outputFilenames = sortingFileSplitter.splitFile(inputFile);
        sortedFilesMerger.mergeFiles(outputFilenames, inputFile);
        deleteTmpFiles();
    }

    private void deleteTmpFiles() {
        try {
            Files.walk(Paths.get("resources/tmp/"))
                    .map(Path::toFile)
                    .forEach(File::delete);
            Files.delete(Paths.get("resources/tmp/"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
