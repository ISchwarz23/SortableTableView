package de.codecrafters.sortabletableview;

import java.util.HashMap;
import java.util.Map;


/**
 * A model holding the column information of a {@link TableView}.
 * Such column information is the relative column width (alias ColumnWeight) and the number of columns
 * (alias ColumnCount).
 *
 * @author ISchwarz
 */
public class TableColumnModel {

    private static final int DEFAULT_COLUMN_WEIGHT = 1;

    private final Map<Integer, Integer> columnWeights;
    private int columnCount;


    /**
     * Creates a new TableColumnModel with the given number of columns. Every column has by default
     * a weight (relative width) of 1.
     *
     * @param columnCount
     *         The number of columns.
     */
    public TableColumnModel(int columnCount) {
        this.columnWeights = new HashMap<>();
        this.columnCount = columnCount;
    }

    /**
     * Sets the column count to this model.
     *
     * @param columnCount
     *         The number of columns that shall be set.
     */
    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }

    /**
     * Gives the column count of this model.
     *
     * @return The number of columns that is currently set.
     */
    public int getColumnCount() {
        return columnCount;
    }

    /**
     * Sets the column weight (the relative width of a column) of the column at the given index.
     *
     * @param columnIndex
     *         The index of the column to which this weight should be assigned.
     * @param columnWeight
     *         The weight that should be set to the column at the given index.
     */
    public void setColumnWeight(int columnIndex, int columnWeight) {
        columnWeights.put(columnIndex, columnWeight);
    }

    /**
     * Gives the column weight (the relative width of a column) of the column at the given index.
     *
     * @param columnIndex
     *         The index of the column to receive the column weight.
     * @return The column weight of the column at the given index.
     */
    public int getColumnWeight(int columnIndex) {
        Integer columnWeight = columnWeights.get(columnIndex);
        if (columnWeight == null) {
            columnWeight = DEFAULT_COLUMN_WEIGHT;
        }
        return columnWeight;
    }

    /**
     * Gives the overall column weight (sum of all column weights).
     *
     * @return The collumn weight sum.
     */
    public int getColumnWeightSum() {
        int weightSum = 0;

        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            weightSum += getColumnWeight(columnIndex);
        }

        return weightSum;
    }

}
