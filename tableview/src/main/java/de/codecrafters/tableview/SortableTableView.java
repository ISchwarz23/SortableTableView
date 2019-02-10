package de.codecrafters.tableview;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import de.codecrafters.tableview.listeners.SortingStatusChangeListener;
import de.codecrafters.tableview.listeners.TableHeaderClickListener;
import de.codecrafters.tableview.providers.SortStateViewProvider;

import java.util.*;

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

    private static final String SAVED_STATE_SUPER_STATE = "SAVED_STATE_SUPER";
    private static final String SAVED_STATE_SORTED_DIRECTION = "SAVED_STATE_SORTED_DIRECTION";
    private static final String SAVED_STATE_SORTED_COLUMN = "SAVED_STATE_SORTED_COLUMN";

    private final SortableTableHeaderView sortableTableHeaderView;
    private final SortingController sortingController;

    public static final int SORT_ICON_LEFT = 0;
    public static final int SORT_ICON_RIGHT = 1;
    private int sortIconPosition;

    /**
     * Creates a new SortableTableView with the given context.\n
     * (Has same effect like calling {@code new SortableTableView(context, null, android.R.attr.listViewStyle})
     *
     * @param context The context that shall be used.
     */
    public SortableTableView(final Context context) {
        this(context, null);
    }

    /**
     * Creates a new SortableTableView with the given context.\n
     * (Has same effect like calling {@code new SortableTableView(context, attrs, android.R.attr.listViewStyle})
     *
     * @param context    The context that shall be used.
     * @param attributes The attributes that shall be set to the view.
     */
    public SortableTableView(final Context context, final AttributeSet attributes) {
        this(context, attributes, android.R.attr.listViewStyle);
    }

    /**
     * Creates a new SortableTableView with the given context.
     *
     * @param context         The context that shall be used.
     * @param attributes      The attributes that shall be set to the view.
     * @param styleAttributes The style attributes that shall be set to the view.
     */
    public SortableTableView(final Context context, final AttributeSet attributes, final int styleAttributes) {
        super(context, attributes, styleAttributes);
        setCustomAttributes(attributes);
        sortableTableHeaderView = new SortableTableHeaderView(context);
        sortableTableHeaderView.setBackgroundColor(0xFFCCCCCC);
        sortableTableHeaderView.setIconPosition(sortIconPosition);
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
     * @param columnIndex      The index of the column the given {@link Comparator} shall be set to.
     * @param columnComparator The {@link Comparator} that shall be set to the column at the given index.
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
     * @param provider The {@link SortStateViewProvider} that shall be used to render the sort views in the header.
     */
    public void setHeaderSortStateViewProvider(final SortStateViewProvider provider) {
        sortableTableHeaderView.setSortStateViewProvider(provider);
    }

    /**
     * Gives the {@link Comparator} of the column at the given index.
     *
     * @param columnIndex The index of the column to receive the applied {@link Comparator}.
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
     * @param columnIndex The index of the column on which the sorting shall be executed.
     */
    public void sort(final int columnIndex) {
        sortingController.onHeaderClicked(columnIndex);
    }

    /**
     * Sorts the table by the values of the column of the given index.
     *
     * @param columnIndex   The index of the column for which the sorting shall be executed.
     * @param sortAscending Indicates whether the table was sorted ascending {@code TRUE} or descending {@code FALSE}.
     * @deprecated Use {@link SortableTableView#sort(int, SortingOrder)} instead.
     */
    @Deprecated
    public void sort(final int columnIndex, final boolean sortAscending) {
        final SortingOrder sortingOrder;
        if (sortAscending) {
            sortingOrder = SortingOrder.ASCENDING;
        } else {
            sortingOrder = SortingOrder.DESCENDING;
        }
        sortingController.sort(columnIndex, sortingOrder);
    }

    /**
     * Sorts the table by the values of the column of the given index.
     *
     * @param columnIndex  The index of the column for which the sorting shall be executed.
     * @param sortingOrder Indicates whether the table was sorted ascending or descending.
     */
    public void sort(final int columnIndex, final SortingOrder sortingOrder) {
        sortingController.sort(columnIndex, sortingOrder);
    }

    /**
     * Gives the sorting status of this table.
     *
     * @return The sorting status of this table.
     */
    public SortingStatus getSortingStatus() {
        return sortingController.sortingStatus;
    }

    /**
     * Adds the given {@link SortingStatusChangeListener} to this {@link SortableTableView}.
     *
     * @param listener The {@link SortingStatusChangeListener} that shall be added to this {@link SortableTableView}.
     * @return A boolean indicating if the adding of the {@link SortingStatusChangeListener} has been successful.
     */
    public boolean addSortingStatusChangeListener(final SortingStatusChangeListener listener) {
        return sortingController.sortingStatusListeners.add(listener);
    }

    /**
     * Removes the given {@link SortingStatusChangeListener} from this {@link SortableTableView}.
     *
     * @param listener The {@link SortingStatusChangeListener} that shall be removed from this {@link SortableTableView}.
     * @return A boolean indicating if the removal of the {@link SortingStatusChangeListener} has been successful.
     */
    public boolean removeSortingStatusChangeListener(final SortingStatusChangeListener listener) {
        return sortingController.sortingStatusListeners.remove(listener);
    }

    /**
     * Sorts the table using the given {@link Comparator}.
     *
     * @param comparator The {@link Comparator} that shall be used to sort the table.
     */
    public void sort(final Comparator<T> comparator) {
        sortingController.sortDataSFCT(comparator);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        final Bundle state = new Bundle();
        state.putParcelable(SAVED_STATE_SUPER_STATE, super.onSaveInstanceState());
        state.putSerializable(SAVED_STATE_SORTED_DIRECTION, sortingController.sortingStatus.getSortedOrder());
        state.putInt(SAVED_STATE_SORTED_COLUMN, sortingController.sortingStatus.getSortedColumnIndex());
        return state;
    }

    @Override
    protected void onRestoreInstanceState(final Parcelable state) {
        if (state instanceof Bundle) {
            final Bundle savedState = (Bundle) state;
            final Parcelable superState = savedState.getParcelable(SAVED_STATE_SUPER_STATE);
            final SortingOrder sortingOrder = (SortingOrder) savedState.getSerializable(SAVED_STATE_SORTED_DIRECTION);
            final int sortedColumnIndex = savedState.getInt(SAVED_STATE_SORTED_COLUMN, -1);

            super.onRestoreInstanceState(superState);
            if (sortedColumnIndex != -1) {
                sortingController.sort(sortedColumnIndex, sortingOrder);
            }
        }
    }

    private void setCustomAttributes(AttributeSet attributes) {
        final TypedArray styledAttributes = getContext().obtainStyledAttributes(attributes, R.styleable.SortableTableView);
        sortIconPosition = styledAttributes.getInt(R.styleable.SortableTableView_sortIconPosition, SORT_ICON_LEFT);
    }


    /**
     * A controller managing all actions that are in the context of sorting.
     *
     * @author ISchwarz
     */
    private class SortingController implements TableHeaderClickListener {

        private final Set<SortingStatusChangeListener> sortingStatusListeners = new HashSet<>();
        private final SparseArray<Comparator<T>> comparators = new SparseArray<>();
        private final SortingStatus sortingStatus = new SortingStatus();

        private Comparator<T> sortedColumnComparator;

        @Override
        public void onHeaderClicked(final int columnIndex) {
            if (comparators.get(columnIndex) == null) {
                Log.i(LOG_TAG, "Unable to sort column with index " + columnIndex + ". Reason: no comparator set for this column.");
                return;
            }

            final SortingOrder sortingOrder = getSortingOrder(columnIndex);
            sortedColumnComparator = getComparator(columnIndex, sortingOrder);

            sortingStatus.setSortedColumnIndex(columnIndex);
            sortingStatus.setSortedOrder(sortingOrder);

            sortDataSFCT(sortedColumnComparator);
            setSortView(columnIndex);

            notifySortingStatusListeners();
        }

        private void notifySortingStatusListeners() {
            for (final SortingStatusChangeListener sortingStatusListener : sortingStatusListeners) {
                sortingStatusListener.onSortingStatusChanged(sortingStatus);
            }
        }

        private SortingOrder getSortingOrder(int columnIndex) {
            if (sortingStatus.getSortedColumnIndex() == columnIndex && sortingStatus.getSortedOrder() == SortingOrder.ASCENDING) {
                return SortingOrder.DESCENDING;
            }
            return SortingOrder.ASCENDING;
        }

        public void sort(final int columnIndex, final SortingOrder sortingOrder) {
            if (comparators.get(columnIndex) == null) {
                Log.i(LOG_TAG, "Unable to sort column with index " + columnIndex + ". Reason: no comparator set for this column.");
                return;
            }

            Comparator<T> columnComparator = comparators.get(columnIndex);
            if (sortingOrder == SortingOrder.DESCENDING) {
                columnComparator = Collections.reverseOrder(columnComparator);
            }

            sortedColumnComparator = columnComparator;
            sortingStatus.setSortedColumnIndex(columnIndex);
            sortingStatus.setSortedOrder(sortingOrder);

            sortDataSFCT(columnComparator);
            setSortView(columnIndex);
        }

        private void setSortView(final int columnIndex) {
            sortableTableHeaderView.resetSortViews();
            if (sortingStatus.getSortedOrder() == SortingOrder.ASCENDING) {
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
                final List<T> data = getDataAdapter().getData();
                Collections.sort(data, comparator);
                getDataAdapter().notifyDataSetChanged();
            }
        }

        private Comparator<T> getRawComparator(final int columnIndex) {
            return comparators.get(columnIndex);
        }

        private Comparator<T> getComparator(final int columnIndex, final SortingOrder sortingOrder) {
            final Comparator<T> columnComparator = comparators.get(columnIndex);

            if (sortingOrder == SortingOrder.ASCENDING) {
                return columnComparator;
            } else {
                return Collections.reverseOrder(columnComparator);
            }
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
