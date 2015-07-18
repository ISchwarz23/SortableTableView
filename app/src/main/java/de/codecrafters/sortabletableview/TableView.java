package de.codecrafters.sortabletableview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
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

import de.codecrafters.sortabletableview.listeners.TableDataClickListener;


/**
 * A view that is able to display data as a table. For bringing the data to the view the {@link TableDataAdapter} can be used.
 * For formatting the table headers the {@link TableHeaderAdapter} can be used.
 *
 * @author ISchwarz
 */
public class TableView<T> extends LinearLayout {

    private static final int DEFAULT_COLUMN_COUNT = 4;

    private TableHeaderView tableHeaderView;
    private ListView tableDataView;

    private TableColumnModel columnModel;

    private TableHeaderAdapter tableHeaderAdapter;
    protected TableDataAdapter<T> tableDataAdapter;

    private Set<TableDataClickListener<T>> dataClickListeners = new HashSet<>();


    public TableView(Context context) {
        this(context, null);
    }

    public TableView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(LinearLayout.VERTICAL);
        setAttributes(context, attrs);
        setupTableHeaderView();
        setupTableDataView();
    }

    private void setupTableHeaderView() {
        if (isInEditMode()) {
            tableHeaderAdapter = new EditModeTableHeaderAdapter(getContext());
        } else {
            tableHeaderAdapter = new DefaultTableHeaderAdapter(getContext());
        }
        tableHeaderView = new TableHeaderView(getContext());
        tableHeaderView.setAdapter(tableHeaderAdapter);

        addView(tableHeaderView);
    }

    private void setupTableDataView() {
        if (isInEditMode()) {
            tableDataAdapter = new EditModeTableDataAdapter(getContext());
        } else {
            tableDataAdapter = new DefaultTableDataAdapter(getContext());
        }
        tableDataView = new ListView(getContext());
        tableDataView.setAdapter(tableDataAdapter);
        tableDataView.setOnItemClickListener(new InternalDataClickListener());

        LayoutParams dataViewLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        tableDataView.setLayoutParams(dataViewLayoutParams);

        addView(tableDataView);
    }

    protected void setTableHeaderView(TableHeaderView headerView) {
        this.tableHeaderView = headerView;
        tableHeaderView.setAdapter(tableHeaderAdapter);
        removeViewAt(0);
        addView(tableHeaderView, 0);
        forceRefresh();
    }

    public void addTableDataClickListener(TableDataClickListener<T> listener) {
        dataClickListeners.add(listener);
    }

    public void removeTableDataClickListener(TableDataClickListener<T> listener) {
        dataClickListeners.remove(listener);
    }

    public void setHeaderAdapter(TableHeaderAdapter headerAdapter) {
        tableHeaderAdapter = headerAdapter;
        tableHeaderAdapter.setColumnModel(columnModel);
        tableHeaderView.setAdapter(tableHeaderAdapter);
        forceRefresh();
    }

    public void setDataAdapter(TableDataAdapter<T> dataAdapter) {
        tableDataAdapter = dataAdapter;
        tableDataAdapter.setColumnModel(columnModel);
        tableDataView.setAdapter(tableDataAdapter);
        forceRefresh();
    }

    public void setColumnCount(int columnCount) {
        columnModel.setColumnCount(columnCount);
        forceRefresh();
    }

    public void setColumnWeight(int columnIndex, int columnWeight) {
        columnModel.setColumnWeight(columnIndex, columnWeight);
        forceRefresh();
    }

    public int getColumnWeight(int columnIndex) {
        return columnModel.getColumnWeight(columnIndex);
    }

    private void forceRefresh() {
        tableHeaderView.invalidate();
        tableDataView.invalidate();
    }

    private void setAttributes(Context context, AttributeSet attributes) {
        TypedArray styledAttributes = context.obtainStyledAttributes(attributes, R.styleable.TableView);

        for (int i = 0; i < styledAttributes.getIndexCount(); ++i) {
            int attribute = styledAttributes.getIndex(i);
            switch (attribute) {
                case R.styleable.TableView_columnCount:
                    int columnCount = styledAttributes.getInt(attribute, DEFAULT_COLUMN_COUNT);
                    columnModel = new TableColumnModel(columnCount);
                    break;
            }
        }
        styledAttributes.recycle();
    }


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

    private class DefaultTableDataAdapter extends TableDataAdapter<T> {

        public DefaultTableDataAdapter(Context context) {
            super(context, columnModel, new ArrayList<T>());
        }

        @Override
        public View getCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
            return new TextView(getContext());
        }
    }

    private class EditModeTableDataAdapter extends TableDataAdapter<T> {

        private static final float TEXT_SIZE = 16;

        public EditModeTableDataAdapter(Context context) {
            super(context, columnModel, null);
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
            return 150;
        }
    }

    private class DefaultTableHeaderAdapter extends TableHeaderAdapter {

        public DefaultTableHeaderAdapter(Context context) {
            super(context, columnModel);
        }

        @Override
        public View getHeaderView(int columnIndex, ViewGroup parentView) {
            return new TextView(getContext());
        }
    }

    private class EditModeTableHeaderAdapter extends TableHeaderAdapter {

        private static final float TEXT_SIZE = 18;

        public EditModeTableHeaderAdapter(Context context) {
            super(context, columnModel);
        }

        @Override
        public View getHeaderView(int columnIndex, ViewGroup parentView) {
            Log.d("HeaderAdapter", "Header" + columnIndex);
            TextView textView = new TextView(getContext());
            textView.setText("Header " + columnIndex);
            textView.setPadding(20, 30, 20, 30);
            textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
            textView.setTextSize(TEXT_SIZE);
            return textView;
        }
    }

}
