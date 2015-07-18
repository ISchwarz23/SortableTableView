package de.codecrafters.sortabletableview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    public SortableTableView(Context context) {
        this(context, null);
    }

    public SortableTableView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SortableTableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        sortableTableHeaderView = new SortableTableHeaderView(context);
        setTableHeaderView(sortableTableHeaderView);

        sortingController = new SortingController();
        sortableTableHeaderView.addHeaderClickListener(sortingController);
    }

    public void setComparator(int columnIndex, Comparator<T> columnComparator) {
        sortingController.setComparator(columnIndex, columnComparator);
    }

    private class SortingController implements HeaderClickListener {

        private Map<Integer, Comparator<T>> comparators = new HashMap<>();
        private int sortedColumnIndex = -1;
        private boolean isSortedUp;

        @Override
        public void onHeaderClicked(int columnIndex) {

            if (comparators.containsKey(columnIndex)) {
                Log.i(LOG_TAG, "Unable to sort column with index " + columnIndex + ". Reason: no comparator set.");
                return;
            }

            Comparator<T> comparator = getComparator(columnIndex);
            sortDataSFCT(comparator);
            setSortView(columnIndex);

            sortedColumnIndex = columnIndex;
            tableDataAdapter.notifyDataSetChanged();
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

    /**
     * Listener interface to listen for clicks on table headers of a {@link SortableTableView}.
     */
    public interface HeaderClickListener {

        /**
         * This method is called of a table header was clicked.
         *
         * @param columnIndex
         */
        void onHeaderClicked(int columnIndex);

    }

}
