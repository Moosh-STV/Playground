package data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import static data.Constants.READ_LINES_LIMIT;

public class HeapFilesMerger implements SortedFilesMerger {

    //read from each file insert to min heap
    //poll min, append to output file writer, add to heap from the same file if not done
    @Override
    public boolean mergeFiles(List<String> filenames, String inputFile) throws IOException {
        PriorityQueue<IndexedLine> minHeap = new PriorityQueue<>(filenames.size(), Comparator.comparing(indexedLine -> indexedLine.line));
        List<BufferedReader> fileReaders = new ArrayList<>(filenames.size());
        List<String> linesChunk = new ArrayList<>(READ_LINES_LIMIT);
        FileWriter writer = new FileWriter();

        try {
            for (int i = 0; i < filenames.size(); i++) {
                String filename = filenames.get(i);
                BufferedReader reader = new BufferedReader(new FileReader(filename));
                String line = reader.readLine();
                if (line != null) {
                    fileReaders.add(reader);
                    minHeap.offer(new IndexedLine(line, i));
                }
            }

            while (!minHeap.isEmpty()) {
                IndexedLine min = minHeap.poll();
                linesChunk.add(min.line);
                if (linesChunk.size() == READ_LINES_LIMIT) {
                    writer.writeFile("test/resources/output.txt", linesChunk); //TODO refactor
                    linesChunk = new ArrayList<>(READ_LINES_LIMIT);
                }

                String line = fileReaders.get(min.readerIdx).readLine();
                if (line != null) {
                    minHeap.offer(new IndexedLine(line, min.readerIdx));
                }
            }
            if (!linesChunk.isEmpty()) {
                writer.writeFile("test/resources/output.txt", linesChunk);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            for (BufferedReader reader : fileReaders) {
                reader.close();
            }
        }
        return true;
    }

    private static class IndexedLine {
        private final String line;
        private final int readerIdx;

        public IndexedLine(String line, int readerIdx) {
            this.line = line;
            this.readerIdx = readerIdx;
        }
    }
}
