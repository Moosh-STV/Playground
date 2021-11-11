package data;

public class SortingFileSplitter extends CapacityFileSplitter {

    public SortingFileSplitter() {
        super(new FilePartPostProcessSorting());
    }
}
