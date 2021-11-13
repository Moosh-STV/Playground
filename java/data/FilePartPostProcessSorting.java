package data;

import java.util.Collections;
import java.util.List;

public class FilePartPostProcessSorting implements FilePartPostProcessAlgorithm {
    @Override
    public List<String> filePartPostSplitProcess(List<String> fileLines) {
        Collections.sort(fileLines);
        return fileLines;
    }
}
