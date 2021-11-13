package data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FilePartDedupPostProcessor implements FilePartPostProcessAlgorithm {
    @Override
    public List<String> filePartPostSplitProcess(List<String> fileLines) {
        return new ArrayList<>(Set.copyOf(fileLines));
    }
}
