package de.codecrafters.tableview.toolkit;

import de.codecrafters.tableview.colorizers.TableDataRowColoriser;


/**
 * Factory for different {@link TableDataRowColoriser}.
 *
 * @author ISchwarz
 */
public class TableDataRowColorisers {

    /**
     * Gives an implementation of {link TableDataRowColoriser} that will return the given color
     * for every row.
     *
     * @param color
     *         The color that shall be applied to every table data row.
     * @return The described {link TableDataRowColoriser}.
     */
    public static TableDataRowColoriser<Object> similarRowColor(int color) {
        return new SimpleTableDataRowColoriser(color);
    }

    /**
     * Gives an implementation of {link TableDataRowColoriser} that will return alternately the two
     * given colors.
     *
     * @param color1
     *         The color that will be returned for rows with an even index.
     * @param color2
     *         The color that will be returned for rows with an odd index.
     * @return The described {link TableDataRowColoriser}.
     */
    public static TableDataRowColoriser<Object> alternatingRows(int color1, int color2) {
        return new AlternatingTableDataRowColoriser(color1, color2);
    }


    /**
     * An implementation of {link TableDataRowColoriser} that will return the given color
     * for every row.
     *
     * @author ISchwarz
     */
    private static class SimpleTableDataRowColoriser implements TableDataRowColoriser<Object> {

        private int color;

        public SimpleTableDataRowColoriser(int color) {
            this.color = color;
        }

        @Override
        public int getRowColor(int rowIndex, Object rowData) {
            return color;
        }
    }

    /**
     * An implementation of {link TableDataRowColoriser} that will return alternately the two
     * given colors.
     *
     * @author ISchwarz
     */
    private static class AlternatingTableDataRowColoriser implements TableDataRowColoriser<Object> {

        private final int firstColor;
        private final int secondColor;

        public AlternatingTableDataRowColoriser(int firstColor, int secondColor) {
            this.firstColor = firstColor;
            this.secondColor = secondColor;
        }

        @Override
        public int getRowColor(int rowIndex, Object rowData) {
            if (rowIndex % 2 == 0) {
                return firstColor;
            }
            return secondColor;
        }
    }

}
