package app;

import data.*;

import java.io.IOException;

import static data.Constants.INPUT_FILE;

/*
  Sort a big file that doesnt fit into RAM.
  This is written as a POC, not efficiently nor cleanly!

  Algorithm:
  1. Read input file lines one by one - not all since it doesnt fit into RAM.
  2. Split to chunks of size that fits RAM, each chunk is sorted and written to a file.
  3. Merge sorted files (in pairs or using min heap, currently merging pairs) in chunks that fits into RAM, once reached limit, flush to disk. Append as needed.
  4. Store sorted file in the same dir as input under the name: sorted_<input filename>
*/
public class AppRunner {

    public static void main(String[] args) throws IOException {
        FileProcessingManager fileProcessingManager;
        if (args[0].equals("sort")) {
            fileProcessingManager = new FileProcessingManager(new SortingFileSplitter(), new HeapFilesMerger());
            fileProcessingManager.sortBigFile(INPUT_FILE);
        } else {
            fileProcessingManager = new FileProcessingManager(new CapacityFileSplitter(new FilePartDedupPostProcessor()), new HeapFilesMerger());
            fileProcessingManager.dedupBigFile(INPUT_FILE);
        }
    }
}
