package de.codecrafters.tableview.model;

import java.util.HashMap;
import java.util.Map;


/**
 * A {@link TableColumnModel} implementation holding absolute column widths.
 *
 * @author ISchwarz
 * @deprecated This class has been deprecated in version 2.4.1. Use the {@link TableColumnPxWidthModel} instead.
 */
@Deprecated
public class TableColumnWidthModel implements TableColumnModel {

    private static final int DEFAULT_COLUMN_WIDTH = 200;

    private final Map<Integer, Integer> columnWidths;
    private int columnCount;
    private int defaultColumnWidth;

    /**
     * Creates a new TableColumnModel with the given number of columns. Every column has by default
     * a width of 200px.
     *
     * @param columnCount The number of columns.
     */
    public TableColumnWidthModel(final int columnCount) {
        this(columnCount, DEFAULT_COLUMN_WIDTH);
    }

    /**
     * Creates a new TableColumnModel with the given number of columns. Every column has the given default width.
     *
     * @param columnCount        The number of columns.
     * @param defaultColumnWidth The column width that is used, if not specified differently.
     */
    public TableColumnWidthModel(final int columnCount, final int defaultColumnWidth) {
        this.columnWidths = new HashMap<>();
        this.columnCount = columnCount;
        this.defaultColumnWidth = defaultColumnWidth;
    }

    /**
     * Sets the column width for the column at the given index.
     *
     * @param columnIndex The index of the column.
     * @param columnWidth The width of the column.
     */
    public void setColumnWidth(int columnIndex, int columnWidth) {
        this.columnWidths.put(columnIndex, columnWidth);
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
