package de.codecrafters.tableview.model;

import de.codecrafters.tableview.TableView;

/**
 * A model holding the column information of a {@link TableView}.
 * This information consists of the number of columns as well as the weight of each column.
 *
 * @author ISchwarz
 */
public interface TableColumnModel {

    /**
     * Gives the column count of this model.
     *
     * @return The number of columns that is currently set.
     */
    int getColumnCount();

    /**
     * Sets the column count to this model.
     *
     * @param columnCount The number of columns that shall be set.
     */
    void setColumnCount(int columnCount);

    /**
     * Gives the column width of the given index.
     *
     * @param columnIndex    The index of the column.
     * @param tableWidthInPx The width of the table where it should be used.
     * @return The width of the column with the given index.
     */
    int getColumnWidth(int columnIndex, int tableWidthInPx);
}
