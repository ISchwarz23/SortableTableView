package de.codecrafters.tableview.toolkit;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import de.codecrafters.tableview.providers.TableDataRowBackgroundProvider;


/**
 * A collection of predefined {@link TableDataRowBackgroundProvider}s.
 *
 * @author ISchwarz
 */
public final class TableDataRowBackgroundProviders {

    private TableDataRowBackgroundProviders() {
        //no instance
    }

    /**
     * Gives an implementation of {@link TableDataRowBackgroundProvider} that will return alternately the two
     * given colors.
     *
     * @param colorEvenRows The color that will be returned for rows with an even index.
     * @param colorOddRows  The color that will be returned for rows with an odd index.
     * @return A {@link TableDataRowBackgroundProvider} with the described behaviour.
     */
    public static TableDataRowBackgroundProvider<Object> alternatingRowColors(final int colorEvenRows, final int colorOddRows) {
        return new AlternatingTableDataRowColorProvider(colorEvenRows, colorOddRows);
    }

    /**
     * Gives an implementation of {@link TableDataRowBackgroundProvider} that will return alternately the two
     * given colors.
     *
     * @param drawableEvenRows The {@link Drawable} that will be returned for rows with an even index.
     * @param drawableOddRows  The {@link Drawable}  that will be returned for rows with an odd index.
     * @return A {@link TableDataRowBackgroundProvider} with the described behaviour.
     */
    public static TableDataRowBackgroundProvider<Object> alternatingRowDrawables(final Drawable drawableEvenRows, final Drawable drawableOddRows) {
        return new AlternatingTableDataRowDrawableProvider(drawableEvenRows, drawableOddRows);
    }

    /**
     * Gives an implementation of {@link TableDataRowBackgroundProvider} that will return the given color for each row.
     *
     * @param color The color that will be used for each row.
     * @return A {@link TableDataRowBackgroundProvider} with the described behaviour.
     */
    public static TableDataRowBackgroundProvider<Object> similarRowColor(final int color) {
        return new SimpleTableDataRowColorProvider(color);
    }

    /**
     * Gives an implementation of {@link TableDataRowBackgroundProvider} that will return the given color for each row.
     *
     * @param drawable The {@link Drawable} that will be used for each row.
     * @return A {@link TableDataRowBackgroundProvider} with the described behaviour.
     */
    public static TableDataRowBackgroundProvider<Object> similarRowDrawable(final Drawable drawable) {
        return new SimpleTableDataRowDrawableProvider(drawable);
    }

    /**
     * An implementation of {@link TableDataRowBackgroundProvider} that will return a {@link ColorDrawable} for the
     * given color for every row.
     *
     * @author ISchwarz
     */
    private static class SimpleTableDataRowColorProvider implements TableDataRowBackgroundProvider<Object> {

        private final Drawable colorDrawable;

        public SimpleTableDataRowColorProvider(final int color) {
            this.colorDrawable = new ColorDrawable(color);
        }

        @Override
        public Drawable getRowBackground(final int rowIndex, final Object rowData) {
            return colorDrawable;
        }
    }

    /**
     * An implementation of {@link TableDataRowBackgroundProvider} that will return a {@link ColorDrawable} for the
     * given color for every row.
     *
     * @author ISchwarz
     */
    private static class SimpleTableDataRowDrawableProvider implements TableDataRowBackgroundProvider<Object> {

        private final Drawable drawable;

        public SimpleTableDataRowDrawableProvider(final Drawable drawable) {
            this.drawable = drawable;
        }

        @Override
        public Drawable getRowBackground(final int rowIndex, final Object rowData) {
            return drawable;
        }
    }

    /**
     * An implementation of {@link TableDataRowBackgroundProvider} that will return alternately the two
     * given colors as {@link ColorDrawable}.
     *
     * @author ISchwarz
     */
    private static class AlternatingTableDataRowColorProvider implements TableDataRowBackgroundProvider<Object> {

        private final Drawable colorDrawableEven;
        private final Drawable colorDrawableOdd;

        public AlternatingTableDataRowColorProvider(final int colorEven, final int colorOdd) {
            this.colorDrawableEven = new ColorDrawable(colorEven);
            this.colorDrawableOdd = new ColorDrawable(colorOdd);
        }

        @Override
        public Drawable getRowBackground(final int rowIndex, final Object rowData) {
            if (rowIndex % 2 == 0) {
                return colorDrawableEven;
            }
            return colorDrawableOdd;
        }
    }

    /**
     * An implementation of {@link TableDataRowBackgroundProvider} that will return alternately the two
     * given colors as {@link ColorDrawable}.
     *
     * @author ISchwarz
     */
    private static class AlternatingTableDataRowDrawableProvider implements TableDataRowBackgroundProvider<Object> {

        private final Drawable drawableEven;
        private final Drawable drawableOdd;

        public AlternatingTableDataRowDrawableProvider(final Drawable drawableEven, final Drawable drawableOdd) {
            this.drawableEven = drawableEven;
            this.drawableOdd = drawableOdd;
        }

        @Override
        public Drawable getRowBackground(final int rowIndex, final Object rowData) {
            if (rowIndex % 2 == 0) {
                return drawableEven;
            }
            return drawableOdd;
        }
    }

}
