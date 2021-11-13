package data;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static data.Constants.OUTPUT_TMP_DIR;

public class FileProcessingManager {

    private final FileSplitter fileSplitter;
    private final FilesMerger filesMerger;

    public FileProcessingManager(FileSplitter fileSplitter, FilesMerger filesMerger) {
        this.fileSplitter = fileSplitter;
        this.filesMerger = filesMerger;
    }

    public void sortBigFile(String inputFile) throws IOException {
        List<String> filePartsFilenames = fileSplitter.splitFile(inputFile);
        filesMerger.mergeFiles(filePartsFilenames, inputFile);
        deleteTmpFiles();
    }

    public void dedupBigFile(String inputFile) throws IOException {
        List<String> filePartsFilenames = fileSplitter.splitFile(inputFile);
        filesMerger.mergeFiles(filePartsFilenames, inputFile);
        deleteTmpFiles();
    }

    private void deleteTmpFiles() { //TODO refactor
        try {
            Files.walk(Paths.get(OUTPUT_TMP_DIR))
                    .map(Path::toFile)
                    .forEach(File::delete);
            Files.delete(Paths.get(OUTPUT_TMP_DIR));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
