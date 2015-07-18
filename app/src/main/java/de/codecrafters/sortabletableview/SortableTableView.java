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
 * Created by Ingo on 18.07.2015.
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
            Comparator<T> columnComparator = comparators.get(columnIndex);

            if(columnComparator == null) {
                Log.i(LOG_TAG, "Unable to sort column with index " + columnIndex + ". Reason: no comparator set.");
                return;
            }

            Comparator<T> comparator;
            if(sortedColumnIndex == columnIndex) {
                if(isSortedUp) {
                    comparator = Collections.reverseOrder(columnComparator);
                } else {
                    comparator = columnComparator;
                }
                isSortedUp = !isSortedUp;
            } else {
                comparator = columnComparator;
                isSortedUp = true;
            }
            sortedColumnIndex = columnIndex;

            List<T> data = tableDataAdapter.getData();
            Collections.sort(data, comparator);

            sortableTableHeaderView.resetSortViews();
            if(isSortedUp) {
                sortableTableHeaderView.showSortView(columnIndex, SortableTableHeaderView.SortViewPresentation.SORT_UP);
            } else {
                sortableTableHeaderView.showSortView(columnIndex, SortableTableHeaderView.SortViewPresentation.SORT_DOWN);
            }

            tableDataAdapter.notifyDataSetChanged();
        }

        public void setComparator(int columnIndex, Comparator<T> columnComparator) {
            if(columnComparator == null) {
                comparators.remove(columnIndex);
                sortableTableHeaderView.showSortView(columnIndex, SortableTableHeaderView.SortViewPresentation.NONE);
            } else {
                comparators.put(columnIndex, columnComparator);
                sortableTableHeaderView.showSortView(columnIndex, SortableTableHeaderView.SortViewPresentation.SORTABLE);
            }
        }

    }

}
