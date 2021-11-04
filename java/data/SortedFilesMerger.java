package data;

import java.util.List;

public interface SortedFilesMerger {
    boolean mergeFiles(List<String> filenames, String inputFile);
}
