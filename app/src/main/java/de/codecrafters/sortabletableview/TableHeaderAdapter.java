package de.codecrafters.sortabletableview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by Ingo on 17.07.2015.
 */
public abstract class TableHeaderAdapter {

    private final Context context;
    private TableColumnModel columnModel;

    public TableHeaderAdapter(Context context, int columnCount) {
        this(context, new TableColumnModel(columnCount));
    }

    public TableHeaderAdapter(Context context, TableColumnModel columnModel) {
        this.context = context;
        this.columnModel = columnModel;
    }

    public Context getContext() {
        return context;
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

    public void setColumnWeight(int columnIndex, int columnWeight) {
        columnModel.setColumnWeight(columnIndex, columnWeight);
    }

    public int getColumnWeightSum() {
        return columnModel.getColumnWeightSum();
    }

    public int getColumnWeight(int columnIndex) {
        return columnModel.getColumnWeight(columnIndex);
    }

    public abstract View getHeaderView(int columnIndex, ViewGroup parentView);

}
