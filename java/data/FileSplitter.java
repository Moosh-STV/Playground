package data;

import java.io.IOException;
import java.util.List;

public interface FileSplitter {
    List<String> splitFile(String inputFile) throws IOException;
}
