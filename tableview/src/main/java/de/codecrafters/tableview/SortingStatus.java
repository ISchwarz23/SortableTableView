package de.codecrafters.tableview;

/**
 * Class that represents the sorting status of a {@link SortableTableView}.
 *
 * @author ISchwarz
 */
public class SortingStatus {

    private SortingOrder sortedOrder = null;
    private int sortedColumnIndex = -1;

    /**
     * Creates a new {@link SortingStatus} object.
     */
    SortingStatus() {
        // shall not be created from outside
    }

    /**
     * Sets the new column index, by which the {@link SortableTableView} is currently sorted.
     *
     * @param sortedColumnIndex The new column index, by which the {@link SortableTableView} is currently sorted.
     */
    void setSortedColumnIndex(final int sortedColumnIndex) {
        this.sortedColumnIndex = sortedColumnIndex;
    }

    /**
     * Sots the order in which the {@link SortableTableView} is currently sorted.
     *
     * @param sortedOrder The order in which the {@link SortableTableView} is currently sorted.
     */
    void setSortedOrder(final SortingOrder sortedOrder) {
        this.sortedOrder = sortedOrder;
    }

    /**
     * Indicates whether or not the {@link SortableTableView} is currently sorted.
     *
     * @return {@code TRUE} if the {@link SortableTableView} is currently sorted, {@code FALSE} if the table
     * is currently unsorted.
     */
    public boolean isTableSorted() {
        return sortedColumnIndex != -1;
    }

    /**
     * Gives the column index, by which the {@link SortableTableView} is currently sorted.
     *
     * @return The column index, by which the {@link SortableTableView} is currently sorted or {@value -1} if the table is not sorted.
     */
    public int getSortedColumnIndex() {
        return sortedColumnIndex;
    }

    /**
     * Gives the order in which the {@link SortableTableView} is currently sorted.
     *
     * @return The order in which the {@link SortableTableView} is currently sorted or {@code NULL} if the table
     * is not sorted.
     */
    public SortingOrder getSortedOrder() {
        return sortedOrder;
    }
}
