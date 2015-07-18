package de.codecrafters.sortabletableview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * The abstract implementation of an adapter used to bring data to a {@link TableHeaderView}.
 *
 * @author ISchwarz
 */
public abstract class TableHeaderAdapter {

    private final Context context;
    private TableColumnModel columnModel;

    public TableHeaderAdapter(Context context) {
        this(context, 0);
    }

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

    /**
     * Method used for creating the header views for the different columns.
     * @param columnIndex The index of the column to return the header view.
     * @param parentView The parent of the view that will be created by this method.
     * @return The created header view for the given column.
     */
    public abstract View getHeaderView(int columnIndex, ViewGroup parentView);

}
