package de.codecrafters.tableview.toolkit;

import de.codecrafters.tableview.colorizers.TableDataRowColorizer;


/**
 * Factory for different {@link TableDataRowColorizer}.
 *
 * @author ISchwarz
 */
public class TableDataRowColorizers {

    /**
     * Gives an implementation of {link TableDataRowColoriser} that will return the given color
     * for every row.
     *
     * @param color
     *         The color that shall be applied to every table data row.
     * @return The described {link TableDataRowColoriser}.
     */
    public static TableDataRowColorizer<Object> similarRowColor(int color) {
        return new SimpleTableDataRowColorizer(color);
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
    public static TableDataRowColorizer<Object> alternatingRows(int color1, int color2) {
        return new AlternatingTableDataRowColorizer(color1, color2);
    }


    /**
     * An implementation of {link TableDataRowColoriser} that will return the given color
     * for every row.
     *
     * @author ISchwarz
     */
    private static class SimpleTableDataRowColorizer implements TableDataRowColorizer<Object> {

        private int color;

        public SimpleTableDataRowColorizer(int color) {
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
    private static class AlternatingTableDataRowColorizer implements TableDataRowColorizer<Object> {

        private final int firstColor;
        private final int secondColor;

        public AlternatingTableDataRowColorizer(int firstColor, int secondColor) {
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
