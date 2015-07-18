package de.codecrafters.sortabletableview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Ingo on 17.07.2015.
 */
public class TableView<T> extends LinearLayout {

    private static final int DEFAULT_COLUMN_COUNT = 4;

    private TableHeaderView tableHeaderView;
    private ListView tableDataView;

    private TableColumnModel columnModel;

    private TableHeaderAdapter tableHeaderAdapter;
    private TableDataAdapter<T> tableDataAdapter;


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

        if(isInEditMode()) {
            tableHeaderAdapter = new EditModeTableHeaderAdapter(context);
        } else {
            tableHeaderAdapter = new DefaultTableHeaderAdapter(context);
        }
        tableHeaderView = new TableHeaderView(context);
        tableHeaderView.setAdapter(tableHeaderAdapter);

        if(isInEditMode()) {
            tableDataAdapter = new EditModeTableDataAdapter(context);
        } else {
            tableDataAdapter = new DefaultTableDataAdapter(context);
        }
        tableDataView = new ListView(context);
        tableDataView.setAdapter(tableDataAdapter);

        LayoutParams dataViewLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        tableDataView.setLayoutParams(dataViewLayoutParams);


        addView(tableHeaderView);
        addView(tableDataView);
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

    private class DefaultTableDataAdapter extends TableDataAdapter<T> {

        public DefaultTableDataAdapter(Context context) {
            super(context, new ArrayList<T>(), columnModel);
        }

        @Override
        public View getCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
            return new TextView(getContext());
        }
    }

    private class EditModeTableDataAdapter extends TableDataAdapter<T> {

        private static final float TEXT_SIZE = 16;

        public EditModeTableDataAdapter(Context context) {
            super(context, null, columnModel);
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
            TextView textView = new TextView(getContext());
            textView.setText("Header " + columnIndex);
            textView.setPadding(20, 30, 20, 30);
            textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
            textView.setTextSize(TEXT_SIZE);
            return textView;
        }
    }

}
