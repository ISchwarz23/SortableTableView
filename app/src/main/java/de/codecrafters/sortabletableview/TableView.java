package de.codecrafters.sortabletableview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;

/**
 * Created by Ingo on 17.07.2015.
 */
public class TableView extends LinearLayout {

    private static final int DEFAULT_COLUMN_COUNT = 4;

    private TableHeaderView tableHeaderView;

    private Map<Integer, Integer> columnWeights = new HashMap<>();
    private int columnCount = DEFAULT_COLUMN_COUNT;

    private TableHeaderAdapter tableHeaderAdapter = new DefaultTableHeaderAdapter();


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

        tableHeaderView = new TableHeaderView(context);

        for(int columnIndex=0; columnIndex<columnCount; columnIndex++) {
            LayoutParams headerLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            headerLayoutParams.weight = 1;

            View headerView = tableHeaderAdapter.getHeaderView(context, columnIndex);
            headerView.setLayoutParams(headerLayoutParams);

            tableHeaderView.addView(headerView);
        }

        addView(tableHeaderView);
    }

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }

    public void setColumnWeight(int columnIndex, int columnWeight) {
        columnWeights.put(columnIndex, columnWeight);
    }

    private void setAttributes(Context context, AttributeSet attributes) {
        TypedArray styledAttributes = context.obtainStyledAttributes(attributes, R.styleable.TableView);

        for (int i = 0; i < styledAttributes.getIndexCount(); ++i) {
            int attribute = styledAttributes.getIndex(i);
            switch (attribute) {
                case R.styleable.TableView_columnCount:
                    columnCount = styledAttributes.getInt(attribute, DEFAULT_COLUMN_COUNT);
                    break;
            }
        }
        styledAttributes.recycle();
    }

    private static class DefaultTableHeaderAdapter implements TableHeaderAdapter {
        @Override
        public View getHeaderView(Context context, int columnIndex) {
            TextView textView = new TextView(context);
            textView.setText("Header " + columnIndex);
            return textView;
        }
    }

}
