package de.codecrafters.sortabletableview;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;


/**
 * Created by Ingo on 17.07.2015.
 */
public abstract class TableDataAdapter<T> extends ArrayAdapter<T> {

    private TableColumnModel columnModel;

    public TableDataAdapter(Context context, List<T> data) {
        this(context, 0, data);
    }

    protected TableDataAdapter(Context context, int columnCount, List<T> data) {
        this(context, new TableColumnModel(columnCount), data);
    }

    protected TableDataAdapter(Context context, TableColumnModel columnModel, List<T> data) {
        super(context, -1, data);
        this.columnModel = columnModel;
    }

    public LayoutInflater getLayoutInflater() {
        return (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getColumnCount() {
        return columnModel.getColumnCount();
    }

    public void setColumnModel(TableColumnModel columnModel) {
        this.columnModel = columnModel;
    }

    @Override
    public View getView(int rowIndex, View convertView, ViewGroup parent) {
        LinearLayout rowView = new LinearLayout(getContext());

        ListView.LayoutParams rowLayoutParams = new ListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT, ListView.LayoutParams.WRAP_CONTENT);
        rowView.setLayoutParams(rowLayoutParams);
        rowView.setGravity(Gravity.CENTER_VERTICAL);

        int widthUnit = (parent.getWidth() / columnModel.getColumnWeightSum());

        for(int columnIndex=0; columnIndex<getColumnCount(); columnIndex++) {
            View cellView = getCellView(rowIndex, columnIndex, rowView);
            if(cellView == null) {
                cellView = new TextView(getContext());
            }

            int width = widthUnit * columnModel.getColumnWeight(columnIndex);

            LinearLayout.LayoutParams cellLayoutParams = new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
            cellLayoutParams.weight = columnModel.getColumnWeight(columnIndex);
            cellView.setLayoutParams(cellLayoutParams);
            rowView.addView(cellView);
        }

        return rowView;
    }

    public abstract View getCellView(int rowIndex, int columnIndex, ViewGroup parentView);



}
