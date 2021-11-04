package app;

import data.FileSplitter;
import data.SortedFilesMerger;

import java.io.IOException;
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
    }
}
