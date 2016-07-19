package de.codecrafters.tableview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import de.codecrafters.tableview.colorizers.TableDataRowColorizer;
import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.listeners.TableDataLongClickListener;
import de.codecrafters.tableview.listeners.TableHeaderClickListener;
import de.codecrafters.tableview.providers.TableDataRowBackgroundProvider;
import de.codecrafters.tableview.toolkit.TableDataRowBackgroundProviders;


/**
 * A view that is able to display data as a table. For bringing the data to the view the {@link TableDataAdapter} can be used.
 * For formatting the table headers the {@link TableHeaderAdapter} can be used.
 *
 * @author ISchwarz
 */
public class TableView<T> extends LinearLayout {

    private static final String LOG_TAG = TableView.class.getName();

    private static final int ID_DATA_VIEW = 101010;

    private static final int DEFAULT_COLUMN_COUNT = 4;
    private static final int DEFAULT_HEADER_ELEVATION = 1;
    private static final int DEFAULT_HEADER_COLOR = 0xFFCCCCCC;

    private final Set<TableDataLongClickListener<T>> dataLongClickListeners = new HashSet<>();
    private final Set<TableDataClickListener<T>> dataClickListeners = new HashSet<>();
    protected TableDataAdapter<T> tableDataAdapter;
    private TableColumnModel columnModel;
    private TableHeaderView tableHeaderView;
    private ListView tableDataView;
    private TableHeaderAdapter tableHeaderAdapter;
    private TableDataRowBackgroundProvider<? super T> dataRowBackgroundProvider =
            TableDataRowBackgroundProviders.similarRowColor(0x00000000);

    private int headerElevation;
    private int headerColor;


    /**
     * Creates a new TableView with the given context.\n
     * (Has same effect like calling {@code new TableView(context, null, android.R.attr.listViewStyle})
     *
     * @param context
     *         The context that shall be used.
     */
    public TableView(final Context context) {
        this(context, null);
    }

    /**
     * Creates a new TableView with the given context.\n
     * (Has same effect like calling {@code new TableView(context, attrs, android.R.attr.listViewStyle})
     *
     * @param context
     *         The context that shall be used.
     * @param attributes
     *         The attributes that shall be set to the view.
     */
    public TableView(final Context context, final AttributeSet attributes) {
        this(context, attributes, android.R.attr.listViewStyle);
    }

    /**
     * Creates a new TableView with the given context.
     *
     * @param context
     *         The context that shall be used.
     * @param attributes
     *         The attributes that shall be set to the view.
     * @param styleAttributes
     *         The style attributes that shall be set to the view.
     */
    public TableView(final Context context, final AttributeSet attributes, final int styleAttributes) {
        super(context, attributes, styleAttributes);
        setOrientation(LinearLayout.VERTICAL);
        setAttributes(attributes);
        setupTableHeaderView(attributes);
        setupTableDataView(attributes, styleAttributes);
    }

    /**
     * Replaces the default {@link TableHeaderView} with the given one.
     *
     * @param headerView
     *         The new {@link TableHeaderView} that should be set.
     */
    protected void setHeaderView(final TableHeaderView headerView) {
        this.tableHeaderView = headerView;

        tableHeaderView.setAdapter(tableHeaderAdapter);
        tableHeaderView.setBackgroundColor(headerColor);
        tableHeaderView.setId(R.id.table_header_view);

        if (getChildCount() == 2) {
            removeViewAt(0);
        }

        addView(tableHeaderView, 0);
        setHeaderElevation(headerElevation);

        forceRefresh();
    }

    /**
     * Sets the given resource as background of the table header.
     *
     * @param resId
     *         The if of the resource tht shall be set as background of the table header.
     */
    public void setHeaderBackground(final int resId) {
        tableHeaderView.setBackgroundResource(resId);
    }

    /**
     * Sets the given color as background of the table header.
     *
     * @param color
     *         The color that shall be set as background of the table header.
     */
    public void setHeaderBackgroundColor(final int color) {
        tableHeaderView.setBackgroundColor(color);
    }

    /**
     * Sets the elevation level of the header view. If you are not able to see the elevation shadow
     * you should set a background(-color) to the header.
     *
     * @param elevation
     *         The elevation that shall be set to the table header.
     */
    public void setHeaderElevation(final int elevation) {
        ViewCompat.setElevation(tableHeaderView, elevation);
    }

    /**
     * Sets the given {@link TableDataRowColorizer} that will be used to define the background color for
     * every table data row.
     *
     * @param colorizer
     *         The {@link TableDataRowColorizer} that shall be used.
     * @deprecated This method is deprecated. Use {@link TableView#setDataRowBackgroundProvider} instead.
     */
    @Deprecated
    public void setDataRowColorizer(final TableDataRowColorizer<? super T> colorizer) {
        setDataRowBackgroundProvider(new TableDataRowBackgroundColorProvider<>(colorizer));
    }

    /**
     * Sets the given {@link TableDataRowBackgroundProvider} that will be used to define the background color for
     * every table data row.
     *
     * @param backgroundProvider
     *         The {@link TableDataRowBackgroundProvider} that shall be used.
     */
    public void setDataRowBackgroundProvider(final TableDataRowBackgroundProvider<? super T> backgroundProvider) {
        dataRowBackgroundProvider = backgroundProvider;
        tableDataAdapter.setRowBackgroundProvider(dataRowBackgroundProvider);
    }

    /**
     * Adds a {@link TableDataClickListener} to this table. This listener gets notified every time the user clicks
     * a certain data item.
     *
     * @param listener
     *         The listener that should be added as click listener.
     */
    public void addDataClickListener(final TableDataClickListener<T> listener) {
        dataClickListeners.add(listener);
    }

    /**
     * Adds a {@link TableDataLongClickListener} to this table. This listener gets notified every time the user clicks
     * long on a certain data item.
     *
     * @param listener
     *         The listener that should be added as long click listener.
     */
    public void addDataLongClickListener(final TableDataLongClickListener<T> listener) {
        dataLongClickListeners.add(listener);
    }

    /**
     * Removes the given {@link TableDataClickListener} from the click listeners of this table.
     *
     * @param listener
     *         The listener that should be removed.
     * @deprecated This method has been deprecated in the version 2.2.0 for naming alignment reasons. Use the method
     * {@link TableView#removeDataClickListener(TableDataClickListener)} instead.
     */
    @Deprecated
    public void removeTableDataClickListener(final TableDataClickListener<T> listener) {
        dataClickListeners.remove(listener);
    }

    /**
     * Removes the given {@link TableDataClickListener} from the click listeners of this table.
     *
     * @param listener
     *         The listener that should be removed.
     */
    public void removeDataClickListener(final TableDataClickListener<T> listener) {
        dataClickListeners.remove(listener);
    }

    /**
     * Removes the given {@link TableDataLongClickListener} from the long click listeners of this table.
     *
     * @param listener
     *         The listener that should be removed.
     */
    public void removeDataLongClickListener(final TableDataLongClickListener<T> listener) {
        dataLongClickListeners.remove(listener);
    }

    /**
     * Adds the given {@link TableHeaderClickListener} to this table.
     *
     * @param listener
     *         The listener that shall be added to this table.
     */
    public void addHeaderClickListener(final TableHeaderClickListener listener) {
        tableHeaderView.addHeaderClickListener(listener);
    }

    /**
     * Removes the given {@link TableHeaderClickListener} from this table.
     *
     * @param listener
     *         The listener that shall be removed from this table.
     * @deprecated This method has been deprecated in the version 2.2.0 for naming alignment reasons. Use the method
     * {@link TableView#removeHeaderClickListener(TableHeaderClickListener)} instead.
     */
    @Deprecated
    public void removeHeaderListener(final TableHeaderClickListener listener) {
        tableHeaderView.removeHeaderClickListener(listener);
    }

    /**
     * Removes the given {@link TableHeaderClickListener} from this table.
     *
     * @param listener
     *         The listener that shall be removed from this table.
     */
    public void removeHeaderClickListener(final TableHeaderClickListener listener) {
        tableHeaderView.removeHeaderClickListener(listener);
    }

    /**
     * Gives the {@link TableHeaderAdapter} that is used to render the header views for each column.
     *
     * @return The {@link TableHeaderAdapter} that is currently set.
     */
    public TableHeaderAdapter getHeaderAdapter() {
        return tableHeaderAdapter;
    }

    /**
     * Sets the {@link TableHeaderAdapter} that is used to render the header views for each column.
     *
     * @param headerAdapter
     *         The {@link TableHeaderAdapter} that should be set.
     */
    public void setHeaderAdapter(final TableHeaderAdapter headerAdapter) {
        tableHeaderAdapter = headerAdapter;
        tableHeaderAdapter.setColumnModel(columnModel);
        tableHeaderView.setAdapter(tableHeaderAdapter);
        forceRefresh();
    }

    /**
     * Gives the {@link TableDataAdapter} that is used to render the data view for each cell.
     *
     * @return The {@link TableDataAdapter} that is currently set.
     */
    public TableDataAdapter<T> getDataAdapter() {
        return tableDataAdapter;
    }

    /**
     * Sets the {@link TableDataAdapter} that is used to render the data view for each cell.
     *
     * @param dataAdapter
     *         The {@link TableDataAdapter} that should be set.
     */
    public void setDataAdapter(final TableDataAdapter<T> dataAdapter) {
        tableDataAdapter = dataAdapter;
        tableDataAdapter.setColumnModel(columnModel);
        tableDataAdapter.setRowBackgroundProvider(dataRowBackgroundProvider);
        tableDataView.setAdapter(tableDataAdapter);
        forceRefresh();
    }

    /**
     * Gives the number of columns of this table.
     *
     * @return The current number of columns.
     */
    public int getColumnCount() {
        return columnModel.getColumnCount();
    }

    /**
     * Sets the number of columns of this table.
     *
     * @param columnCount
     *         The number of columns.
     */
    public void setColumnCount(final int columnCount) {
        columnModel.setColumnCount(columnCount);
        forceRefresh();
    }

    /**
     * Sets the column weight (the relative width of the column) of the given column.
     *
     * @param columnIndex
     *         The index of the column the weight should be set to.
     * @param columnWeight
     *         The weight that should be set to the column.
     */
    public void setColumnWeight(final int columnIndex, final int columnWeight) {
        columnModel.setColumnWeight(columnIndex, columnWeight);
        forceRefresh();
    }

    /**
     * Gives the column weight (the relative width of the column) of the given column.
     *
     * @param columnIndex
     *         The index of the column the weight should be returned.
     * @return The weight of the given column index.
     */
    public int getColumnWeight(final int columnIndex) {
        return columnModel.getColumnWeight(columnIndex);
    }

    @Override
    public void setSaveEnabled(final boolean enabled) {
        super.setSaveEnabled(enabled);
        tableHeaderView.setSaveEnabled(enabled);
        tableDataView.setSaveEnabled(enabled);
    }

    private void forceRefresh() {
        if (tableHeaderView != null) {
            tableHeaderView.invalidate();
        }
        if (tableDataView != null) {
            tableDataView.invalidate();
        }
    }

    private void setAttributes(final AttributeSet attributes) {
        final TypedArray styledAttributes = getContext().obtainStyledAttributes(attributes, R.styleable.TableView);

        headerColor = styledAttributes.getInt(R.styleable.TableView_tableView_headerColor, DEFAULT_HEADER_COLOR);
        headerElevation = styledAttributes.getInt(R.styleable.TableView_tableView_headerElevation, DEFAULT_HEADER_ELEVATION);
        final int columnCount = styledAttributes.getInt(R.styleable.TableView_tableView_columnCount, DEFAULT_COLUMN_COUNT);
        columnModel = new TableColumnModel(columnCount);

        styledAttributes.recycle();
    }

    private void setupTableHeaderView(final AttributeSet attributes) {
        if (isInEditMode()) {
            tableHeaderAdapter = new EditModeTableHeaderAdapter(getContext());
        } else {
            tableHeaderAdapter = new DefaultTableHeaderAdapter(getContext());
        }

        final TableHeaderView tableHeaderView = new TableHeaderView(getContext());
        setHeaderView(tableHeaderView);
    }

    private void setupTableDataView(final AttributeSet attributes, final int styleAttributes) {
        final LayoutParams dataViewLayoutParams = new LayoutParams(getWidthAttribute(attributes), LayoutParams.MATCH_PARENT);

        if (isInEditMode()) {
            tableDataAdapter = new EditModeTableDataAdapter(getContext());
        } else {
            tableDataAdapter = new DefaultTableDataAdapter(getContext());
        }
        tableDataAdapter.setRowBackgroundProvider(dataRowBackgroundProvider);

        tableDataView = new ListView(getContext(), attributes, styleAttributes);
        tableDataView.setOnItemClickListener(new InternalDataClickListener());
        tableDataView.setOnItemLongClickListener(new InternalDataLongClickListener());
        tableDataView.setLayoutParams(dataViewLayoutParams);
        tableDataView.setAdapter(tableDataAdapter);
        tableDataView.setId(R.id.table_data_view);

        addView(tableDataView);
    }

    private int getWidthAttribute(final AttributeSet attributes) {
        final TypedArray ta = getContext().obtainStyledAttributes(attributes, new int[]{android.R.attr.layout_width});
        final int layoutWidth = ta.getLayoutDimension(0, ViewGroup.LayoutParams.MATCH_PARENT);
        ta.recycle();
        return layoutWidth;
    }

    /**
     * Internal management of clicks on the data view.
     *
     * @author ISchwarz
     */
    private class InternalDataClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(final AdapterView<?> adapterView, final View view, final int i, final long l) {
            informAllListeners(i);
        }

        private void informAllListeners(final int rowIndex) {
            final T clickedObject = tableDataAdapter.getItem(rowIndex);

            for (final TableDataClickListener<T> listener : dataClickListeners) {
                try {
                    listener.onDataClicked(rowIndex, clickedObject);
                } catch (final Throwable t) {
                    Log.w(LOG_TAG, "Caught Throwable on listener notification: " + t.toString());
                    // continue calling listeners
                }
            }
        }
    }

    /**
     * Internal long click management of clicks on the data view.
     *
     * @author ISchwarz
     */
    private class InternalDataLongClickListener implements AdapterView.OnItemLongClickListener {

        @Override
        public boolean onItemLongClick(final AdapterView<?> parent, final View view, final int rowIndex, final long id) {
            return informAllListeners(rowIndex);
        }

        private boolean informAllListeners(final int rowIndex) {
            final T clickedObject = tableDataAdapter.getItem(rowIndex);
            boolean isConsumed = false;

            for (final TableDataLongClickListener<T> listener : dataLongClickListeners) {
                try {
                    isConsumed |= listener.onDataLongClicked(rowIndex, clickedObject);
                } catch (final Throwable t) {
                    Log.w(LOG_TAG, "Caught Throwable on listener notification: " + t.toString());
                    // continue calling listeners
                }
            }
            return isConsumed;
        }
    }

    /**
     * The {@link TableHeaderAdapter} that is used by default. It contains the column model of the
     * table but no headers.
     *
     * @author ISchwarz
     */
    private class DefaultTableHeaderAdapter extends TableHeaderAdapter {

        public DefaultTableHeaderAdapter(final Context context) {
            super(context, columnModel);
        }

        @Override
        public View getHeaderView(final int columnIndex, final ViewGroup parentView) {
            final TextView view = new TextView(getContext());
            view.setText(" ");
            view.setPadding(20, 40, 20, 40);
            return view;
        }
    }

    /**
     * The {@link TableDataAdapter} that is used by default. It contains the column model of the
     * table but no data.
     *
     * @author ISchwarz
     */
    private class DefaultTableDataAdapter extends TableDataAdapter<T> {

        public DefaultTableDataAdapter(final Context context) {
            super(context, columnModel, new ArrayList<T>());
        }

        @Override
        public View getCellView(final int rowIndex, final int columnIndex, final ViewGroup parentView) {
            return new TextView(getContext());
        }
    }

    /**
     * The {@link TableHeaderAdapter} that is used while the view is in edit mode.
     *
     * @author ISchwarz
     */
    private class EditModeTableHeaderAdapter extends TableHeaderAdapter {

        private static final float TEXT_SIZE = 18;

        public EditModeTableHeaderAdapter(final Context context) {
            super(context, columnModel);
        }

        @Override
        public View getHeaderView(final int columnIndex, final ViewGroup parentView) {
            final TextView textView = new TextView(getContext());
            textView.setText(getResources().getString(R.string.default_header, columnIndex));
            textView.setPadding(20, 40, 20, 40);
            textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
            textView.setTextSize(TEXT_SIZE);
            return textView;
        }

    }

    /**
     * The {@link TableDataAdapter} that is used while the view is in edit mode.
     *
     * @author ISchwarz
     */
    private class EditModeTableDataAdapter extends TableDataAdapter<T> {

        private static final float TEXT_SIZE = 16;

        public EditModeTableDataAdapter(final Context context) {
            super(context, columnModel, new ArrayList<T>());
        }

        @Override
        public View getCellView(final int rowIndex, final int columnIndex, final ViewGroup parent) {
            final TextView textView = new TextView(getContext());
            textView.setText(getResources().getString(R.string.default_cell, columnIndex, rowIndex));
            textView.setPadding(20, 10, 20, 10);
            textView.setTextSize(TEXT_SIZE);
            return textView;
        }

        @Override
        public int getCount() {
            return 50;
        }
    }

}
