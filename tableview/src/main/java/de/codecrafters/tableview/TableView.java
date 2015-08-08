package de.codecrafters.tableview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
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
import de.codecrafters.tableview.listeners.TableHeaderClickListener;
import de.codecrafters.tableview.toolkit.TableDataRowColorizers;


/**
 * A view that is able to display data as a table. For bringing the data to the view the {@link TableDataAdapter} can be used.
 * For formatting the table headers the {@link TableHeaderAdapter} can be used.
 *
 * @author ISchwarz
 */
public class TableView<T> extends LinearLayout {

    private static final int DEFAULT_COLUMN_COUNT = 4;
    private static final int DEFAULT_HEADER_ELEVATION = 1;
    private static final int DEFAULT_HEADER_COLOR = 0xFFCCCCCC;

    private Set<TableDataClickListener<T>> dataClickListeners = new HashSet<>();
    private TableColumnModel columnModel;

    private TableHeaderView tableHeaderView;
    private ListView tableDataView;

    private TableHeaderAdapter tableHeaderAdapter;
    protected TableDataAdapter<T> tableDataAdapter;

    private TableDataRowColorizer<? super T> dataRowColoriser = TableDataRowColorizers.similarRowColor(0x00000000);

    private int headerElevation;
    private int headerColor;


    /**
     * Creates a new TableView with the given context.\n
     * (Has same effect like calling {@code new TableView(context, null, 0})
     *
     * @param context
     *         The context that shall be used.
     */
    public TableView(Context context) {
        this(context, null);
    }

    /**
     * Creates a new TableView with the given context.\n
     * (Has same effect like calling {@code new TableView(context, attrs, 0})
     *
     * @param context
     *         The context that shall be used.
     * @param attributes
     *         The attributes that shall be set to the view.
     */
    public TableView(Context context, AttributeSet attributes) {
        this(context, attributes, 0);
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
    public TableView(Context context, AttributeSet attributes, int styleAttributes) {
        super(context, attributes, styleAttributes);
        setOrientation(LinearLayout.VERTICAL);
        setAttributes(context, attributes);
        setupTableHeaderView();
        setupTableDataView();
    }

    /**
     * Replaces the default {@link TableHeaderView} with the given one.
     *
     * @param headerView
     *         The new {@link TableHeaderView} that should be set.
     */
    protected void setHeaderView(TableHeaderView headerView) {
        this.tableHeaderView = headerView;

        tableHeaderView.setAdapter(tableHeaderAdapter);
        tableHeaderView.setBackgroundColor(headerColor);

        if(getChildCount() == 2) {
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
    public void setHeaderBackground(int resId) {
        tableHeaderView.setBackgroundResource(resId);
    }

    /**
     * Sets the given color as background of the table header.
     *
     * @param color
     *         The color that shall be set as background of the table header.
     */
    public void setHeaderBackgroundColor(int color) {
        tableHeaderView.setBackgroundColor(color);
    }

    /**
     * Sets the elevation level of the header view. If you are not able to see the elevation shadow
     * you should set a background(-color) to the header.
     *
     * @param elevation
     *         The elevation that shall be set to the table header.
     */
    public void setHeaderElevation(int elevation) {
        ViewCompat.setElevation(tableHeaderView, elevation);
    }

    /**
     * Sets the given {@link TableDataRowColorizer} that will be used to define the background color for
     * every table data row.
     *
     * @param coloriser
     *         The {@link TableDataRowColorizer} that shall be used.
     */
    public void setDataRowColoriser(TableDataRowColorizer<? super T> coloriser) {
        dataRowColoriser = coloriser;
        tableDataAdapter.setRowColoriser(coloriser);
    }

    /**
     * Adds a {@link TableDataClickListener} to this table.
     *
     * @param listener
     *         The listener that should be added.
     */
    public void addDataClickListener(TableDataClickListener<T> listener) {
        dataClickListeners.add(listener);
    }

    /**
     * Removes a {@link TableDataClickListener} to this table.
     *
     * @param listener
     *         The listener that should be removed.
     */
    public void removeTableDataClickListener(TableDataClickListener<T> listener) {
        dataClickListeners.remove(listener);
    }

    /**
     * Adds the given {@link TableHeaderClickListener} to this table.
     *
     * @param listener
     *         The listener that shall be added to this table.
     */
    public void addHeaderClickListener(TableHeaderClickListener listener) {
        tableHeaderView.addHeaderClickListener(listener);
    }

    /**
     * Removes the given {@link TableHeaderClickListener} from this table.
     *
     * @param listener
     *         The listener that shall be removed from this table.
     */
    public void removeHeaderListener(TableHeaderClickListener listener) {
        tableHeaderView.removeHeaderClickListener(listener);
    }

    /**
     * Sets the {@link TableHeaderAdapter} that is used to render the header views for each column.
     *
     * @param headerAdapter
     *         The {@link TableHeaderAdapter} that should be set.
     */
    public void setHeaderAdapter(TableHeaderAdapter headerAdapter) {
        tableHeaderAdapter = headerAdapter;
        tableHeaderAdapter.setColumnModel(columnModel);
        tableHeaderView.setAdapter(tableHeaderAdapter);
        forceRefresh();
    }

    /**
     * Sets the {@link TableDataAdapter} that is used to render the data view for each cell.
     *
     * @param dataAdapter
     *         The {@link TableDataAdapter} that should be set.
     */
    public void setDataAdapter(TableDataAdapter<T> dataAdapter) {
        tableDataAdapter = dataAdapter;
        tableDataAdapter.setColumnModel(columnModel);
        tableDataAdapter.setRowColoriser(dataRowColoriser);
        tableDataView.setAdapter(tableDataAdapter);
        forceRefresh();
    }

    /**
     * Sets the number of columns of this table.
     *
     * @param columnCount
     *         The number of columns.
     */
    public void setColumnCount(int columnCount) {
        columnModel.setColumnCount(columnCount);
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
     * Sets the column weight (the relative width of the column) of the given column.
     *
     * @param columnIndex
     *         The index of the column the weight should be set to.
     * @param columnWeight
     *         The weight that should be set to the column.
     */
    public void setColumnWeight(int columnIndex, int columnWeight) {
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
    public int getColumnWeight(int columnIndex) {
        return columnModel.getColumnWeight(columnIndex);
    }

    private void forceRefresh() {
        if(tableHeaderView != null) {
            tableHeaderView.invalidate();
        }
        if(tableDataView != null) {
            tableDataView.invalidate();
        }
    }

    private void setAttributes(Context context, AttributeSet attributes) {
        TypedArray styledAttributes = context.obtainStyledAttributes(attributes, R.styleable.TableView);

        headerColor = styledAttributes.getInt(R.styleable.TableView_headerColor, DEFAULT_HEADER_COLOR);
        headerElevation = styledAttributes.getInt(R.styleable.TableView_headerElevation, DEFAULT_HEADER_ELEVATION);
        int columnCount = styledAttributes.getInt(R.styleable.TableView_columnCount, DEFAULT_COLUMN_COUNT);
        columnModel = new TableColumnModel(columnCount);

        styledAttributes.recycle();
    }

    private void setupTableHeaderView() {
        if (isInEditMode()) {
            tableHeaderAdapter = new EditModeTableHeaderAdapter(getContext());
        } else {
            tableHeaderAdapter = new DefaultTableHeaderAdapter(getContext());
        }

        TableHeaderView tableHeaderView = new TableHeaderView(getContext());
        setHeaderView(tableHeaderView);
    }

    private void setupTableDataView() {
        LayoutParams dataViewLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        if (isInEditMode()) {
            tableDataAdapter = new EditModeTableDataAdapter(getContext());
        } else {
            tableDataAdapter = new DefaultTableDataAdapter(getContext());
        }
        tableDataAdapter.setRowColoriser(dataRowColoriser);

        tableDataView = new ListView(getContext());
        tableDataView.setOnItemClickListener(new InternalDataClickListener());
        tableDataView.setLayoutParams(dataViewLayoutParams);
        tableDataView.setAdapter(tableDataAdapter);

        addView(tableDataView);
    }


    /**
     * Internal management of clicks on the data view.
     *
     * @author ISchwarz
     */
    private class InternalDataClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            informAllListeners(i);
        }

        private void informAllListeners(int rowIndex) {
            T clickedObject = tableDataAdapter.getItem(rowIndex);

            for (TableDataClickListener<T> listener : dataClickListeners) {
                try {
                    listener.onDataClicked(rowIndex, clickedObject);
                } catch (Throwable t) {
                    t.printStackTrace();
                    // continue calling listeners
                }
            }
        }

    }

    /**
     * The {@link TableHeaderAdapter} that is used by default. It contains the column model of the
     * table but no headers.
     *
     * @author ISchwarz
     */
    private class DefaultTableHeaderAdapter extends TableHeaderAdapter {

        public DefaultTableHeaderAdapter(Context context) {
            super(context, columnModel);
        }

        @Override
        public View getHeaderView(int columnIndex, ViewGroup parentView) {
            TextView view = new TextView(getContext());
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

        public DefaultTableDataAdapter(Context context) {
            super(context, columnModel, new ArrayList<T>());
        }

        @Override
        public View getCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
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

        public EditModeTableHeaderAdapter(Context context) {
            super(context, columnModel);
        }

        @Override
        public View getHeaderView(int columnIndex, ViewGroup parentView) {
            TextView textView = new TextView(getContext());
            textView.setText("Header " + columnIndex);
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

        public EditModeTableDataAdapter(Context context) {
            super(context, columnModel, new ArrayList<T>());
        }

        @Override
        public View getCellView(int rowIndex, int columnIndex, ViewGroup parent) {
            TextView textView = new TextView(getContext());
            textView.setText("Cell [" + columnIndex + ":" + rowIndex + "]");
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
