package de.codecrafters.tableview.listeners;

/**
 * Listens for long clicks on table data.
 *
 * @author ISchwarz
 */
public interface TableDataLongClickListener<T> {

    /**
     * This method is called when there was a click on a certain table data.
     *
     * @param rowIndex
     *         The index of the row that has been clicked long.
     * @param clickedData
     *         The data that was clicked long.
     * @return flag indicating if this listener has "consumed" the event. When the event is "consumed"
     * the {@link TableDataClickListener}s are not informed.
     */
    boolean onDataLongClicked(final int rowIndex, final T clickedData);

}
