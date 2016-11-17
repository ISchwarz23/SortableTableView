package de.codecrafters.tableview;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.codecrafters.tableview.model.TableColumnModel;
import de.codecrafters.tableview.model.TableColumnWeightModel;
import de.codecrafters.tableview.providers.TableDataRowBackgroundProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.widget.LinearLayout.LayoutParams;


/**
 * The abstract implementation of an adapter used to bring data to a {@link TableView}.
 *
 * @author ISchwarz
 */
public abstract class TableDataAdapter<T> extends ArrayAdapter<T> {

    private static final String LOG_TAG = TableDataAdapter.class.getName();
    private final List<T> data;
    private TableColumnModel columnModel;
    private TableDataRowBackgroundProvider<? super T> rowBackgroundProvider;


    /**
     * Creates a new TableDataAdapter.
     *
     * @param context The context that shall be used.
     * @param data    The data that shall be displayed.
     */
    public TableDataAdapter(final Context context, final T[] data) {
        this(context, 0, new ArrayList<>(Arrays.asList(data)));
    }

    /**
     * Creates a new TableDataAdapter.
     *
     * @param context The context that shall be used.
     * @param data    The data that shall be displayed.
     */
    public TableDataAdapter(final Context context, final List<T> data) {
        this(context, 0, data);
    }

    /**
     * Creates a new TableDataAdapter. (internally used)
     *
     * @param context     The context that shall be used.
     * @param columnCount The number of columns.
     */
    protected TableDataAdapter(final Context context, final int columnCount, final List<T> data) {
        this(context, new TableColumnWeightModel(columnCount), data);
    }

    /**
     * Creates a new TableDataAdapter. (internally used)
     *
     * @param context     The context that shall be used.
     * @param columnModel The column model to be used.
     */
    protected TableDataAdapter(final Context context, final TableColumnModel columnModel, final List<T> data) {
        super(context, -1, data);
        this.columnModel = columnModel;
        this.data = data;
    }

    /**
     * Gives the data object that shall be displayed in the row with the given index.
     *
     * @param rowIndex The index of the row to get the data for.
     * @return The data that shall be displayed in the row with the given index.
     */
    public T getRowData(final int rowIndex) {
        return getItem(rowIndex);
    }

    /**
     * Gives the data that is set to this adapter.
     *
     * @return The data this adapter is currently working with.
     */
    public List<T> getData() {
        return data;
    }

    /**
     * Gives the {@link Context} of this adapter. (Hint: use this method in the {@code getHeaderView()}-method
     * to programmatically initialize new views.)
     *
     * @return The {@link Context} of this adapter.
     */
    public Context getContext() {
        return super.getContext();
    }

    /**
     * Gives the {@link LayoutInflater} of this adapter. (Hint: use this method in the
     * {@code getHeaderView()}-method to inflate xml-layout-files.)
     *
     * @return The {@link LayoutInflater} of the context of this adapter.
     */
    public LayoutInflater getLayoutInflater() {
        return (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * Gives the {@link Resources} of this adapter. (Hint: use this method in the
     * {@code getCellView()}-method to resolve resources.)
     *
     * @return The {@link Resources} of the context of this adapter.
     */
    public Resources getResources() {
        return getContext().getResources();
    }

    /**
     * Method that gives the cell views for the different table cells.
     *
     * @param rowIndex    The index of the row to return the table cell view.
     * @param columnIndex The index of the column to return the table cell view.
     * @param parentView  The view to which the returned view will be added.
     * @return The created header view for the given column.
     */
    public abstract View getCellView(int rowIndex, int columnIndex, ViewGroup parentView);

    @Override
    public final View getView(final int rowIndex, final View convertView, final ViewGroup parent) {
        final LinearLayout rowView = new LinearLayout(getContext());

        final AbsListView.LayoutParams rowLayoutParams = new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        rowView.setLayoutParams(rowLayoutParams);
        rowView.setGravity(Gravity.CENTER_VERTICAL);

        T rowData = null;
        try {
            rowData = getItem(rowIndex);
        } catch (final IndexOutOfBoundsException e) {
            Log.w(LOG_TAG, "No row date available for row with index " + rowIndex + ". " +
                    "Caught Exception: " + e.getMessage());
        }

        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            rowView.setBackgroundDrawable(rowBackgroundProvider.getRowBackground(rowIndex, rowData));
        } else {
            rowView.setBackground(rowBackgroundProvider.getRowBackground(rowIndex, rowData));
        }


        final int tableWidth = parent.getWidth();

        for (int columnIndex = 0; columnIndex < columnModel.getColumnCount(); columnIndex++) {
            View cellView = getCellView(rowIndex, columnIndex, rowView);
            if (cellView == null) {
                cellView = new TextView(getContext());
            }

            final int cellWidth = columnModel.getColumnWidth(columnIndex, tableWidth);
            final LinearLayout.LayoutParams cellLayoutParams = new LinearLayout.LayoutParams(cellWidth, LinearLayout.LayoutParams.WRAP_CONTENT);
            cellView.setLayoutParams(cellLayoutParams);
            rowView.addView(cellView);
        }

        return rowView;
    }

    /**
     * Sets the {@link TableDataRowBackgroundProvider} that will be used to define the table data rows background.
     *
     * @param rowBackgroundProvider The {@link TableDataRowBackgroundProvider} that shall be used.
     */
    protected void setRowBackgroundProvider(final TableDataRowBackgroundProvider<? super T> rowBackgroundProvider) {
        this.rowBackgroundProvider = rowBackgroundProvider;
    }

    /**
     * Gives the {@link TableColumnWeightModel} that is currently used to render the table headers.
     */
    protected TableColumnModel getColumnModel() {
        return columnModel;
    }

    /**
     * Sets the {@link TableColumnModel} that will be used to render the table cells.
     *
     * @param columnModel The {@link TableColumnModel} that should be set.
     */
    protected void setColumnModel(final TableColumnModel columnModel) {
        this.columnModel = columnModel;
    }
}
