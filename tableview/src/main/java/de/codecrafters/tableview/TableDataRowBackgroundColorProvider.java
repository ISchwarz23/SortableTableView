package de.codecrafters.tableview;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import de.codecrafters.tableview.colorizers.TableDataRowColorizer;
import de.codecrafters.tableview.providers.TableDataRowBackgroundProvider;


/**
 * A wrapper for a {@link TableDataRowColorizer} that implements the {@link TableDataRowBackgroundProvider} interface.
 *
 * @author ISchwarz
 */
class TableDataRowBackgroundColorProvider<T> implements TableDataRowBackgroundProvider<T> {

    private final TableDataRowColorizer<T> colorizer;

    /**
     * Creates a new {@link TableDataRowBackgroundColorProvider} using the given {@link TableDataRowColorizer}.
     *
     * @param colorizer
     *         The {@link TableDataRowColorizer} that shall be wrapped.
     */
    public TableDataRowBackgroundColorProvider(final TableDataRowColorizer<T> colorizer) {
        this.colorizer = colorizer;
    }

    @Override
    public Drawable getRowBackground(final int rowIndex, final T rowData) {
        return new ColorDrawable(colorizer.getRowColor(rowIndex, rowData));
    }

}
