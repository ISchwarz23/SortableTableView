package de.codecrafters.tableview.toolkit;

import de.codecrafters.tableview.colorizers.TableDataRowColorizer;


/**
 * Factory for different {@link TableDataRowColorizer}.
 *
 * @author ISchwarz
 */
public final class TableDataRowColorizers {

    /**
     * Gives an implementation of {@link TableDataRowColorizer} that will return the given color
     * for every row.
     *
     * @param color
     *         The color that shall be applied to every table data row.
     * @return The described {@link TableDataRowColorizer}.
     */
    public static TableDataRowColorizer<Object> similarRowColor(final int color) {
        return new SimpleTableDataRowColorizer(color);
    }

    /**
     * Gives an implementation of {@link TableDataRowColorizer} that will return alternately the two
     * given colors.
     *
     * @param colorEvenRows
     *         The color that will be returned for rows with an even index.
     * @param colorOddRows
     *         The color that will be returned for rows with an odd index.
     * @return The described {@link TableDataRowColorizer}.
     */
    public static TableDataRowColorizer<Object> alternatingRows(final int colorEvenRows, final int colorOddRows) {
        return new AlternatingTableDataRowColorizer(colorEvenRows, colorOddRows);
    }


    /**
     * An implementation of {@link TableDataRowColorizer} that will return the given color
     * for every row.
     *
     * @author ISchwarz
     */
    private static class SimpleTableDataRowColorizer implements TableDataRowColorizer<Object> {

        private final int color;

        public SimpleTableDataRowColorizer(final int color) {
            this.color = color;
        }

        @Override
        public int getRowColor(final int rowIndex, final Object rowData) {
            return color;
        }
    }

    /**
     * An implementation of {@link TableDataRowColorizer} that will return alternately the two
     * given colors.
     *
     * @author ISchwarz
     */
    private static class AlternatingTableDataRowColorizer implements TableDataRowColorizer<Object> {

        private final int colorEven;
        private final int colorOdd;

        public AlternatingTableDataRowColorizer(final int colorEven, final int colorOdd) {
            this.colorEven = colorEven;
            this.colorOdd = colorOdd;
        }

        @Override
        public int getRowColor(final int rowIndex, final Object rowData) {
            if (rowIndex % 2 == 0) {
                return colorEven;
            }
            return colorOdd;
        }
    }

}
