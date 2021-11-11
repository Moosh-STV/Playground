package app;

import data.*;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AppRunnerTest {

    @Test
    public void sortSuccessfully() throws IOException {
        FileProcessingManager fileSorter = new FileProcessingManager(new SortingFileSplitter(), new PairwiseFilesMerger());
        fileSorter.sortBigFile("test/resources/input.txt");

        Path path = Path.of("test/resources/sorted_input.txt");
        assertTrue(Files.exists(path));

        try (BufferedReader bf = new BufferedReader(new FileReader(path.toFile()));
             BufferedReader bfExpected = new BufferedReader(new FileReader(Paths.get("test/resources/expected_output.txt").toFile()))) {
            String expectedLine;
            do {
                expectedLine = bfExpected.readLine();
                assertEquals(expectedLine, bf.readLine());
            } while (expectedLine != null);
        }
        Files.delete(Path.of("test/resources/sorted_input.txt"));
    }

    @Test
    public void sortSuccessfullyWithMinHeap() throws IOException {
        FileProcessingManager fileProcessingManager = new FileProcessingManager(new SortingFileSplitter(), new HeapFilesMerger());
        fileProcessingManager.sortBigFile("test/resources/input.txt");

        Path path = Path.of("test/resources/output.txt");
        assertTrue(Files.exists(path));

        try (BufferedReader bf = new BufferedReader(new FileReader(path.toFile()));
             BufferedReader bfExpected = new BufferedReader(new FileReader(Paths.get("test/resources/expected_output.txt").toFile()))) {
            String expectedLine;
            do {
                expectedLine = bfExpected.readLine();
                assertEquals(expectedLine, bf.readLine());
            } while (expectedLine != null);
        }
        Files.delete(Path.of("test/resources/output.txt"));
    }

    @Test
    public void dedupSuccessfully() throws IOException {
        FileProcessingManager fileProcessingManager = new FileProcessingManager(new DeduppingFileSplitter(), new DeduppingFilesMerger());
        fileProcessingManager.dedupBigFile("test/resources/dedup_input.txt");

        Path path = Path.of("test/resources/output.txt");
        assertTrue(Files.exists(path));

        List<String> expected = new ArrayList<>();
        List<String> actual = new ArrayList<>();

        try (BufferedReader bfExpected = new BufferedReader(new FileReader(Paths.get("test/resources/expected_dedupped_output.txt").toFile()))) {
            String line = bfExpected.readLine();

            while (line != null) {
                expected.add(line);
                line = bfExpected.readLine();
            }
        }

        try (BufferedReader bf = new BufferedReader(new FileReader(path.toFile()))) {
            String line = bf.readLine();

            while (line != null) {
                actual.add(line);
                line = bf.readLine();
            }
        }

        Files.delete(Path.of("test/resources/output.txt"));
        assertTrue(expected.containsAll(actual) && actual.containsAll(expected));
    }
}