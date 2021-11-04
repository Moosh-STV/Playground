package data;

import java.nio.file.Path;
import java.util.List;
import java.util.PriorityQueue;

public class HeapFilesMerger implements SortedFilesMerger {

    //read from each file insert to min heap
    //poll min, append to output file writer, add to heap from the same file if not done
    @Override
    public boolean mergeFiles(List<String> filenames, String inputFile) {
//        PriorityQueue<String> minHeap = new PriorityQueue<>(filenames.size());

        return true;
    }
}
