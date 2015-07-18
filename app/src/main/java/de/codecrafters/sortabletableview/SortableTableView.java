package de.codecrafters.sortabletableview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.codecrafters.sortabletableview.listeners.TableHeaderClickListener;

/**
 * Extension of the {@link TableView} that gives the possibility to sort the table by every single
 * column. For this purpose implementations of {@link Comparator} are used. If there is a comparator
 * set for a column the {@link SortableTableView} will automatically display an ImageView at the start
 * of the header indicating to the user, that this column is sortable.
 * If the user clicks this header the given comparator will used to sort the table ascending by the
 * content of this column. If the user clicks this header again, the table is sorted descending
 * by the content of this column.
 *
 * @author ISchwarz
 */
public class SortableTableView<T> extends TableView<T> {

    private static final String LOG_TAG = SortableTableView.class.getName();

    private SortableTableHeaderView sortableTableHeaderView;
    private SortingController sortingController;


    /**
     * Creates a new SortableTableView with the given context.\n
     * (Has same effect like calling {@code new SortableTableView(context, null, 0})
     * @param context The context that shall be used.
     */
    public SortableTableView(Context context) {
        this(context, null);
    }

    /**
     * Creates a new SortableTableView with the given context.\n
     * (Has same effect like calling {@code new SortableTableView(context, attrs, 0})
     * @param context The context that shall be used.
     * @param attributes The attributes that shall be set to the view.
     */
    public SortableTableView(Context context, AttributeSet attributes) {
        this(context, attributes, 0);
    }

    /**
     * Creates a new SortableTableView with the given context.
     * @param context The context that shall be used.
     * @param attributes The attributes that shall be set to the view.
     * @param styleAttributes The style attributes that shall be set to the view.
     */
    public SortableTableView(Context context, AttributeSet attributes, int styleAttributes) {
        super(context, attributes, styleAttributes);

        sortableTableHeaderView = new SortableTableHeaderView(context);
        setTableHeaderView(sortableTableHeaderView);

        sortingController = new SortingController();
        sortableTableHeaderView.addHeaderClickListener(sortingController);
    }

    /**
     * Sets the given {@link Comparator} for the column at the given index. The comparator will be used for
     * sorting the given column.
     * @param columnIndex The index of the column the given {@link Comparator} shall be set to.
     * @param columnComparator The {@link Comparator} that shall be set to the column at the given index.
     */
    public void setColumnComparator(int columnIndex, Comparator<T> columnComparator) {
        sortingController.setComparator(columnIndex, columnComparator);
    }

    /**
     * Gives the {@link Comparator} of the column at the given index.
     * @param columnIndex The index of the column to receive the applied {@link Comparator}.
     * @return The {@link Comparator} of the column at the given index.
     */
    public Comparator<T> getColumnComparator(int columnIndex) {
        return sortingController.getRawComparator(columnIndex);
    }

    /**
     * Adds the given {@link TableHeaderClickListener} to this table.
     * @param listener The listener that shall be added to this table.
     */
    public void addTableHeaderListener(TableHeaderClickListener listener) {
        sortableTableHeaderView.addHeaderClickListener(listener);
    }

    /**
     * Removes the given {@link TableHeaderClickListener} from this table.
     * @param listener The listener that shall be removed from this table.
     */
    public void removeTableHeaderListener(TableHeaderClickListener listener) {
        sortableTableHeaderView.removeHeaderClickListener(listener);
    }

    /**
     * Sorts the table by the values of the column with the given index.\n
     * This method has the same effect like a click of the user to the table header of the given column. (This means
     * calling this method twice on the same column index will cause a descending ordering). Better practice for
     * doing programmatically ordering of the table is to call the method {@code sort(Comparator<T>}.
     * @param columnIndex The index of the column on which the sorting shall be executed.
     */
    public void sort(int columnIndex) {
        sortingController.onHeaderClicked(columnIndex);
    }

    /**
     * Sorts the table using the given {@link Comparator}.
     * @param comparator The {@link Comparator} that shall be used to sort the table.
     */
    public void sort(Comparator<T> comparator) {
        sortingController.sortDataSFCT(comparator);
    }


    /**
     * A controller managing all actions that are in the context of sorting.
     *
     * @author ISchwarz
     */
    private class SortingController implements TableHeaderClickListener {

        private Map<Integer, Comparator<T>> comparators = new HashMap<>();
        private int sortedColumnIndex = -1;
        private boolean isSortedUp;

        @Override
        public void onHeaderClicked(int columnIndex) {

            if (!comparators.containsKey(columnIndex)) {
                Log.i(LOG_TAG, "Unable to sort column with index " + columnIndex + ". Reason: no comparator set.");
                return;
            }

            Comparator<T> comparator = getComparator(columnIndex);
            sortDataSFCT(comparator);
            setSortView(columnIndex);

            sortedColumnIndex = columnIndex;
        }

        private void setSortView(int columnIndex) {
            sortableTableHeaderView.resetSortViews();
            if (isSortedUp) {
                sortableTableHeaderView.showSortView(columnIndex, SortableTableHeaderView.SortViewPresentation.SORT_UP);
            } else {
                sortableTableHeaderView.showSortView(columnIndex, SortableTableHeaderView.SortViewPresentation.SORT_DOWN);
            }
        }

        private void sortDataSFCT(Comparator<T> comparator) {
            List<T> data = tableDataAdapter.getData();
            Collections.sort(data, comparator);
            tableDataAdapter.notifyDataSetChanged();
        }

        private Comparator<T> getRawComparator(int columnIndex) {
            return comparators.get(columnIndex);
        }

        private Comparator<T> getComparator(int columnIndex) {
            Comparator<T> columnComparator = comparators.get(columnIndex);

            Comparator<T> comparator;
            if (sortedColumnIndex == columnIndex) {
                if (isSortedUp) {
                    comparator = Collections.reverseOrder(columnComparator);
                } else {
                    comparator = columnComparator;
                }
                isSortedUp = !isSortedUp;
            } else {
                comparator = columnComparator;
                isSortedUp = true;
            }

            return comparator;
        }

        public void setComparator(int columnIndex, Comparator<T> columnComparator) {
            if (columnComparator == null) {
                comparators.remove(columnIndex);
                sortableTableHeaderView.showSortView(columnIndex, SortableTableHeaderView.SortViewPresentation.NONE);
            } else {
                comparators.put(columnIndex, columnComparator);
                sortableTableHeaderView.showSortView(columnIndex, SortableTableHeaderView.SortViewPresentation.SORTABLE);
            }
        }

    }

}
