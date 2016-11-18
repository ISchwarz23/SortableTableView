package de.codecrafters.tableview.model;

import java.util.HashMap;
import java.util.Map;

/**
 * A {@link TableColumnModel} implementation holding absolute column widths in pixel.
 *
 * @author ISchwarz
 */
public class TableColumnPxWidthModel implements TableColumnModel {

    private static final int DEFAULT_COLUMN_WIDTH_IN_PX = 200;

    private final Map<Integer, Integer> columnWidths;
    private int columnCount;
    private int defaultColumnWidth;

    /**
     * Creates a new TableColumnModel with the given number of columns. Every column has by default
     * a width of 200px.
     *
     * @param columnCount The number of columns.
     */
    public TableColumnPxWidthModel(final int columnCount) {
        this(columnCount, DEFAULT_COLUMN_WIDTH_IN_PX);
    }

    /**
     * Creates a new TableColumnModel with the given number of columns. Every column has the given default width.
     *
     * @param columnCount            The number of columns.
     * @param defaultColumnWidthInPx The default width of columns in pixels.
     */
    public TableColumnPxWidthModel(final int columnCount, final int defaultColumnWidthInPx) {
        this.columnWidths = new HashMap<>();
        this.columnCount = columnCount;
        this.defaultColumnWidth = defaultColumnWidthInPx;
    }

    /**
     * Sets the column width for the column at the given index.
     *
     * @param columnIndex     The index of the column.
     * @param columnWidthInPx The width of the column in pixel.
     */
    public void setColumnWidth(int columnIndex, int columnWidthInPx) {
        this.columnWidths.put(columnIndex, columnWidthInPx);
    }

    @Override
    public void setColumnCount(final int columnCount) {
        this.columnCount = columnCount;
    }

    @Override
    public int getColumnCount() {
        return columnCount;
    }

    @Override
    public int getColumnWidth(int columnIndex, int tableWidthInPx) {
        final Integer columnWidth = columnWidths.get(columnIndex);
        if (columnWidth == null) {
            return defaultColumnWidth;
        }
        return columnWidth;
    }
}
