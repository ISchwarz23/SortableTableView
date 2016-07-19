package de.codecrafters.tableview.listeners;

/**
 * Listens for clicks on table data.
 *
 * @author ISchwarz
 */
public interface TableDataClickListener<T> {

    /**
     * This method is called when there was a click on a certain table data.
     *
     * @param rowIndex
     *         The index of the row that has been clicked.
     * @param clickedData
     *         The data that was clicked.
     */
    void onDataClicked(final int rowIndex, final T clickedData);

}
