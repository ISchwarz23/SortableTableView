package de.codecrafters.tableview.providers;

import android.graphics.drawable.Drawable;

/**
 * An interface for a table data row background provider. This enables easy setting of
 * the rows background of a {@link de.codecrafters.tableview.TableView}.
 */
public interface TableDataRowBackgroundProvider<T> {

    /**
     * Gives the row background for the row with the given index holding the given data.
     *
     * @param rowIndex The index of the row to return the background {@link Drawable} for.
     * @param rowData  The data presented in the row to return the background {@link Drawable} for.
     * @return The background {@link Drawable} that shall be used for the given row.
     */
    Drawable getRowBackground(final int rowIndex, final T rowData);

}
