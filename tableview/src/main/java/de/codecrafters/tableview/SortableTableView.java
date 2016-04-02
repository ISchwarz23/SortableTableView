package de.codecrafters.tableview;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.codecrafters.tableview.listeners.TableHeaderClickListener;
import de.codecrafters.tableview.providers.SortStateViewProvider;

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

    public static final String SAVED_STATE_SUPER_STATE = "SAVED_STATE_SUPER";
    public static final String SAVED_STATE_SORTED_DIRECTION = "SAVED_STATE_SORTED_DIRECTION";
    public static final String SAVED_STATE_SORTED_COLUMN = "SAVED_STATE_SORTED_COLUMN";
    private static final String LOG_TAG = SortableTableView.class.getName();
    private final SortableTableHeaderView sortableTableHeaderView;
    private final SortingController sortingController;


    /**
     * Creates a new SortableTableView with the given context.\n
     * (Has same effect like calling {@code new SortableTableView(context, null, 0})
     *
     * @param context
     *         The context that shall be used.
     */
    public SortableTableView(final Context context) {
        this(context, null);
    }

    /**
     * Creates a new SortableTableView with the given context.\n
     * (Has same effect like calling {@code new SortableTableView(context, attrs, 0})
     *
     * @param context
     *         The context that shall be used.
     * @param attributes
     *         The attributes that shall be set to the view.
     */
    public SortableTableView(final Context context, final AttributeSet attributes) {
        this(context, attributes, 0);
    }

    /**
     * Creates a new SortableTableView with the given context.
     *
     * @param context
     *         The context that shall be used.
     * @param attributes
     *         The attributes that shall be set to the view.
     * @param styleAttributes
     *         The style attributes that shall be set to the view.
     */
    public SortableTableView(final Context context, final AttributeSet attributes, final int styleAttributes) {
        super(context, attributes, styleAttributes);

        sortableTableHeaderView = new SortableTableHeaderView(context);
        sortableTableHeaderView.setBackgroundColor(0xFFCCCCCC);
        setHeaderView(sortableTableHeaderView);

        sortingController = new SortingController();
        sortableTableHeaderView.addHeaderClickListener(sortingController);
    }

    @Override
    public void setDataAdapter(final TableDataAdapter<T> dataAdapter) {
        dataAdapter.registerDataSetObserver(new RecapSortingDataSetObserver());
        super.setDataAdapter(dataAdapter);
    }

    /**
     * Sets the given {@link Comparator} for the column at the given index. The comparator will be used for
     * sorting the given column.
     *
     * @param columnIndex
     *         The index of the column the given {@link Comparator} shall be set to.
     * @param columnComparator
     *         The {@link Comparator} that shall be set to the column at the given index.
     */
    public void setColumnComparator(final int columnIndex, final Comparator<T> columnComparator) {
        sortingController.setComparator(columnIndex, columnComparator);
    }

    /**
     * Gives the current {@link SortStateViewProvider}.
     *
     * @return The {@link SortStateViewProvider} that is currently used to render the sort views in the header.
     */
    public SortStateViewProvider getHeaderSortStateViewProvider() {
        return sortableTableHeaderView.getSortStateViewProvider();
    }

    /**
     * Sets the given {@link SortStateViewProvider}.
     *
     * @param provider
     *         The {@link SortStateViewProvider} that shall be used to render the sort views in the header.
     */
    public void setHeaderSortStateViewProvider(final SortStateViewProvider provider) {
        sortableTableHeaderView.setSortStateViewProvider(provider);
    }

    /**
     * Gives the {@link Comparator} of the column at the given index.
     *
     * @param columnIndex
     *         The index of the column to receive the applied {@link Comparator}.
     * @return The {@link Comparator} of the column at the given index.
     */
    public Comparator<T> getColumnComparator(final int columnIndex) {
        return sortingController.getRawComparator(columnIndex);
    }

    /**
     * Sorts the table by the values of the column with the given index.\n
     * This method has the same effect like a click of the user to the table header of the given column. (This means
     * calling this method twice on the same column index will cause a descending ordering). Better practice for
     * doing programmatically ordering of the table is to call the method {@code sort(Comparator<T>}.
     *
     * @param columnIndex
     *         The index of the column on which the sorting shall be executed.
     */
    public void sort(final int columnIndex) {
        sortingController.onHeaderClicked(columnIndex);
    }

    /**
     * Sorts the table by the values of the column of the given index.
     *
     * @param columnIndex
     *         The index of the column for which the sorting shall be executed.
     * @param sortAscending
     *         Indicates whether the table was sorted ascending {@code TRUE} or descending {@code FALSE}.
     */
    public void sort(final int columnIndex, final boolean sortAscending) {
        sortingController.sort(columnIndex, sortAscending);
    }

    /**
     * Sorts the table using the given {@link Comparator}.
     *
     * @param comparator
     *         The {@link Comparator} that shall be used to sort the table.
     */
    public void sort(final Comparator<T> comparator) {
        sortingController.sortDataSFCT(comparator);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        final Bundle state = new Bundle();
        state.putParcelable(SAVED_STATE_SUPER_STATE, super.onSaveInstanceState());
        state.putBoolean(SAVED_STATE_SORTED_DIRECTION, sortingController.isSortedUp);
        state.putInt(SAVED_STATE_SORTED_COLUMN, sortingController.sortedColumnIndex);
        return state;
    }

    @Override
    protected void onRestoreInstanceState(final Parcelable state) {
        if (state instanceof Bundle) {
            final Bundle savedState = (Bundle) state;
            final Parcelable superState = savedState.getParcelable(SAVED_STATE_SUPER_STATE);
            final boolean wasSortedUp = savedState.getBoolean(SAVED_STATE_SORTED_DIRECTION, false);
            final int sortedColumnIndex = savedState.getInt(SAVED_STATE_SORTED_COLUMN, -1);

            super.onRestoreInstanceState(superState);
            sortingController.sort(sortedColumnIndex, wasSortedUp);
        }
    }

    /**
     * A controller managing all actions that are in the context of sorting.
     *
     * @author ISchwarz
     */
    private class SortingController implements TableHeaderClickListener {

        private final Map<Integer, Comparator<T>> comparators = new HashMap<>();
        private int sortedColumnIndex = -1;
        private Comparator<T> sortedColumnComparator;
        private boolean isSortedUp;

        @Override
        public void onHeaderClicked(final int columnIndex) {
            if (!comparators.containsKey(columnIndex)) {
                Log.i(LOG_TAG, "Unable to sort column with index " + columnIndex + ". Reason: no comparator set for this column.");
                return;
            }

            sortedColumnComparator = getComparator(columnIndex);
            sortDataSFCT(sortedColumnComparator);
            setSortView(columnIndex);

            sortedColumnIndex = columnIndex;
        }

        public void sort(final int columnIndex, final boolean sortUp) {
            if (!comparators.containsKey(columnIndex)) {
                Log.i(LOG_TAG, "Unable to sort column with index " + columnIndex + ". Reason: no comparator set for this column.");
                return;
            }

            Comparator<T> columnComparator = comparators.get(columnIndex);
            if (!sortUp) {
                columnComparator = Collections.reverseOrder(columnComparator);
            }

            sortedColumnComparator = columnComparator;
            sortedColumnIndex = columnIndex;
            isSortedUp = sortUp;

            sortDataSFCT(columnComparator);
            setSortView(columnIndex);
        }

        private void setSortView(final int columnIndex) {
            sortableTableHeaderView.resetSortViews();
            if (isSortedUp) {
                sortableTableHeaderView.setSortState(columnIndex, SortState.SORTED_ASC);
            } else {
                sortableTableHeaderView.setSortState(columnIndex, SortState.SORTED_DESC);
            }
        }

        private void recapSorting() {
            sortDataSFCT(sortedColumnComparator);
        }

        private void sortDataSFCT(final Comparator<T> comparator) {
            if (comparator != null) {
                final List<T> data = tableDataAdapter.getData();
                Collections.sort(data, comparator);
                tableDataAdapter.notifyDataSetChanged();
            }
        }

        private Comparator<T> getRawComparator(final int columnIndex) {
            return comparators.get(columnIndex);
        }

        private Comparator<T> getComparator(final int columnIndex) {
            final Comparator<T> columnComparator = comparators.get(columnIndex);

            final Comparator<T> comparator;
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

        public void setComparator(final int columnIndex, final Comparator<T> columnComparator) {
            if (columnComparator == null) {
                comparators.remove(columnIndex);
                sortableTableHeaderView.setSortState(columnIndex, SortState.NOT_SORTABLE);
            } else {
                comparators.put(columnIndex, columnComparator);
                sortableTableHeaderView.setSortState(columnIndex, SortState.SORTABLE);
            }
        }

    }


    /**
     * Implementation of {@link DataSetObserver} that will trigger the sorting of the data if the data has changed.
     *
     * @author ISchwarz
     */
    private class RecapSortingDataSetObserver extends DataSetObserver {

        private boolean initializedByMyself = false;

        @Override
        public void onChanged() {
            if (initializedByMyself) {
                initializedByMyself = false;
            } else {
                initializedByMyself = true;
                sortingController.recapSorting();
            }
        }

    }

}
