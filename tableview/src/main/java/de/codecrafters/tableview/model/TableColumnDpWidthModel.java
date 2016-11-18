package de.codecrafters.tableview.model;

import android.content.Context;
import android.util.DisplayMetrics;

import java.util.HashMap;
import java.util.Map;

/**
 * A {@link TableColumnModel} implementation holding absolute column widths in dp.
 *
 * @author ISchwarz
 */
public class TableColumnDpWidthModel implements TableColumnModel {

    private static final int DEFAULT_COLUMN_WIDTH_IN_DP = 100;

    private final Map<Integer, Integer> columnWidths;
    private final DisplayMetrics displayMetrics;
    private int columnCount;
    private int defaultColumnWidth;

    /**
     * Creates a new TableColumnModel with the given number of columns. Every column has by default
     * a width of 100dp.
     *
     * @param context     The {@link Context} to observe the {@link DisplayMetrics} needed for calculate the pixels from the dp.
     * @param columnCount The number of columns.
     */
    public TableColumnDpWidthModel(final Context context, final int columnCount) {
        this(context, columnCount, DEFAULT_COLUMN_WIDTH_IN_DP);
    }

    /**
     * Creates a new TableColumnModel with the given number of columns. Every column has by default
     * a width of 100dp.
     *
     * @param displayMetrics The {@link DisplayMetrics} needed for calculate the pixels from the dp.
     * @param columnCount    The number of columns.
     */
    public TableColumnDpWidthModel(final DisplayMetrics displayMetrics, final int columnCount) {
        this(displayMetrics, columnCount, DEFAULT_COLUMN_WIDTH_IN_DP);
    }

    /**
     * Creates a new TableColumnModel with the given number of columns. Every column has the given default width.
     *
     * @param context                The {@link Context} to observe the {@link DisplayMetrics} needed for calculate the pixels from the dp.
     * @param columnCount            The number of columns.
     * @param defaultColumnWidthInDp The default column width in dp.
     */
    public TableColumnDpWidthModel(final Context context, final int columnCount,
                                   final int defaultColumnWidthInDp) {

        this(context.getResources().getDisplayMetrics(), columnCount, defaultColumnWidthInDp);
    }

    /**
     * Creates a new TableColumnModel with the given number of columns. Every column has the given default width.
     *
     * @param displayMetrics         The {@link DisplayMetrics} needed for calculate the pixels from the dp.
     * @param columnCount            The number of columns.
     * @param defaultColumnWidthInDp The default column width in dp.
     */
    public TableColumnDpWidthModel(final DisplayMetrics displayMetrics, final int columnCount,
                                   final int defaultColumnWidthInDp) {

        this.columnWidths = new HashMap<>();
        this.displayMetrics = displayMetrics;
        this.columnCount = columnCount;
        this.defaultColumnWidth = toPixel(defaultColumnWidthInDp);
    }

    /**
     * Sets the column width for the column at the given index.
     *
     * @param columnIndex     The index of the column.
     * @param columnWidthInDp The width of the column in dp.
     */
    public void setColumnWidth(int columnIndex, int columnWidthInDp) {
        this.columnWidths.put(columnIndex, toPixel(columnWidthInDp));
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

    private int toPixel(final int dp) {
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
