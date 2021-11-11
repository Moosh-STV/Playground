package data;

public final class Constants {
    private Constants(){throw new UnsupportedOperationException("should not be instantiated");}

    public static final String INPUT_FILE = "resources/input.txt";

    public static final int READ_LINES_LIMIT = 2;
    public static final String OUTPUT_TMP_DIR = "resources/tmp/";
    public static final String OUTPUT_MERGED_FILENAME_PREFIX = "resources/tmp/merged";
    public static final String OUTPUT_FILENAME_TYPE = ".txt";
    public static final String RESULT_FILENAME_PREFIX = "sorted_";
    public static final int LINES_PER_FILE_LIMIT = 3;
    public static final String OUTPUT_FILENAME = "sorted";
    public static final String OUTPUT_FILE_PART_NAME = "filePart";
    public static final int LINES_MEMORY_LIMIT_COUNT = 3;
}
