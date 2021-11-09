package app;

import data.HeapFilesMerger;
import data.PairwiseFilesMerger;
import data.SortingFileSplitter;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AppRunnerTest {

    @Test
    public void testSort() throws IOException {
        FileSorter fileSorter = new FileSorter(new SortingFileSplitter(), new PairwiseFilesMerger());
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
    public void testSortWithMinHeap() throws IOException {
        FileSorter fileSorter = new FileSorter(new SortingFileSplitter(), new HeapFilesMerger());
        fileSorter.sortBigFile("test/resources/input.txt");

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
}