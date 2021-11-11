package data;

import java.io.IOException;
import java.util.List;

public interface FilesMerger {

    boolean mergeFiles(List<String> filenames, String inputFile) throws IOException;
}
