package de.codecrafters.tableview;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * The abstract implementation of an adapter used to bring data to a {@link TableView}.
 *
 * @author ISchwarz
 */
public abstract class TableDataAdapter<T> extends ArrayAdapter<T> {

    private TableColumnModel columnModel;
    private final List<T> data;


    /**
     * Creates a new TableDataAdapter.
     *
     * @param context
     *         The context that shall be used.
     */
    public TableDataAdapter(Context context, List<T> data) {
        this(context, 0, data);
    }

    /**
     * Creates a new TableDataAdapter. (internally used)
     *
     * @param context
     *         The context that shall be used.
     * @param columnCount
     *         The number of columns.
     */
    protected TableDataAdapter(Context context, int columnCount, List<T> data) {
        this(context, new TableColumnModel(columnCount), data);
    }

    /**
     * Creates a new TableDataAdapter. (internally used)
     *
     * @param context
     *         The context that shall be used.
     * @param columnModel
     *         The column model to be used.
     */
    @SuppressWarnings("unchecked")
    protected TableDataAdapter(Context context, TableColumnModel columnModel, List<T> data) {
        super(context, -1, clone(data));
        this.columnModel = columnModel;
        this.data = (List<T>) getDataClone();
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
     * @return The {@link LayoutInflater} of this adapter.
     */
    public LayoutInflater getLayoutInflater() {
        return (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * Sets the {@link TableColumnModel} that will be used to render the table cells.
     *
     * @param columnModel
     *         The {@link TableColumnModel} that should be set.
     */
    public void setColumnModel(TableColumnModel columnModel) {
        this.columnModel = columnModel;
    }

    /**
     * Gives the {@link TableColumnModel} that is currently used to render the table headers.
     */
    public TableColumnModel getColumnModel() {
        return columnModel;
    }

    /**
     * Sets the column count which is used to render the table headers.
     *
     * @param columnCount
     *         The column count that should be set.
     */
    public void setColumnCount(int columnCount) {
        columnModel.setColumnCount(columnCount);
    }

    /**
     * Gives the column count that is currently used to render the table headers.
     *
     * @return The number of columns.
     */
    public int getColumnCount() {
        return columnModel.getColumnCount();
    }

    /**
     * Sets the column weight (the relative width of a column) of the column at the given index.
     *
     * @param columnIndex
     *         The index of the column to which this weight should be assigned.
     * @param columnWeight
     *         The weight that should be set to the column at the given index.
     */
    public void setColumnWeight(int columnIndex, int columnWeight) {
        columnModel.setColumnWeight(columnIndex, columnWeight);
    }

    /**
     * Gives the column weight (the relative width of a column) of the column at the given index.
     *
     * @param columnIndex
     *         The index of the column to receive the column weight.
     * @return The column weight of the column at the given index.
     */
    public int getColumnWeight(int columnIndex) {
        return columnModel.getColumnWeight(columnIndex);
    }

    /**
     * Gives the overall column weight (sum of all column weights).
     *
     * @return The collumn weight sum.
     */
    public int getColumnWeightSum() {
        return columnModel.getColumnWeightSum();
    }

    @Override
    public View getView(int rowIndex, View convertView, ViewGroup parent) {
        LinearLayout rowView = new LinearLayout(getContext());

        ListView.LayoutParams rowLayoutParams = new ListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT, ListView.LayoutParams.WRAP_CONTENT);
        rowView.setLayoutParams(rowLayoutParams);
        rowView.setGravity(Gravity.CENTER_VERTICAL);

        int widthUnit = (parent.getWidth() / columnModel.getColumnWeightSum());

        for (int columnIndex = 0; columnIndex < getColumnCount(); columnIndex++) {
            View cellView = getCellView(rowIndex, columnIndex, rowView);
            if (cellView == null) {
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

    /**
     * Method that gives the cell views for the different table cells.
     *
     * @param rowIndex
     *         The index of the row to return the table cell view.
     * @param columnIndex
     *         The index of the column to return the table cell view.
     * @param parentView
     *         The view to which the returned view will be added.
     * @return The created header view for the given column.
     */
    public abstract View getCellView(int rowIndex, int columnIndex, ViewGroup parentView);

    
    private static List<?> dataStore;

    private static <X> List<X> clone(List<X> data) {
        List<X> clonedData = new ArrayList<>(data);
        dataStore = clonedData;
        return clonedData;
    }

    private static List<?> getDataClone() {
        return dataStore;
    }

}
