package de.codecrafters.tableview;

import android.content.Context;
import android.content.res.Resources;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.codecrafters.tableview.model.TableColumnModel;
import de.codecrafters.tableview.providers.SortStateViewProvider;
import de.codecrafters.tableview.toolkit.SortStateViewProviders;


/**
 * Extension of the {@link TableHeaderView} that will show sorting indicators at the start of the header.
 *
 * @author ISchwarz
 */
class SortableTableHeaderView extends TableHeaderView {

    private final SparseArray<ImageView> sortViews = new SparseArray<>();
    private final SparseArray<SortState> sortStates = new SparseArray<>();

    private SortStateViewProvider sortStateViewProvider = SortStateViewProviders.darkArrows();

    private int iconPosition = SortableTableView.SORT_ICON_LEFT;

    /**
     * Creates a new SortableTableHeaderView.
     *
     * @param context The context that shall be used.
     */
    public SortableTableHeaderView(final Context context) {
        super(context);
    }

    /**
     * Will set all sort views to state "sortable".
     */
    public void resetSortViews() {
        for (int i = 0; i < sortStates.size(); i++) {
            final int columnIndex = sortStates.keyAt(i);

            SortState columnSortState = sortStates.get(columnIndex);
            if (columnSortState != SortState.NOT_SORTABLE) {
                columnSortState = SortState.SORTABLE;
            }
            sortStates.put(columnIndex, columnSortState);
        }

        for (int i = 0; i < sortStates.size(); i++) {
            final int columnIndex = sortStates.keyAt(i);
            final ImageView sortView = sortViews.get(columnIndex);
            final SortState sortState = sortStates.get(columnIndex);
            setSortStateToView(sortState, sortView);
        }
    }

    @Override
    public void setAdapter(TableHeaderAdapter adapter) {
        super.setAdapter(new SortStateArrayAdapter(adapter));
    }

    /**
     * Sets the {@link SortState} of the SortView of the column with the given index.
     *
     * @param columnIndex The index of the column for which the given {@link SortState}
     *                    will be set.
     * @param sortState   The {@link SortState} that shall be set to the sort view at the column with
     *                    the given index.
     */
    public void setSortState(final int columnIndex, final SortState sortState) {
        sortStates.put(columnIndex, sortState);
        invalidate();
    }

    private void setSortStateToView(final SortState state, final ImageView view) {

        if (view != null) {
            final int imageRes = sortStateViewProvider.getSortStateViewResource(state);
            view.setImageResource(imageRes);
            if (imageRes == 0) {
                view.setVisibility(GONE);
            } else {
                view.setVisibility(VISIBLE);
            }
        }
    }

    /**
     * Gives the current {@link SortStateViewProvider} of this SortableTableHeaderView.
     *
     * @return The {@link SortStateViewProvider} that is currently used to render the sort views.
     */
    public SortStateViewProvider getSortStateViewProvider() {
        return sortStateViewProvider;
    }

    /**
     * Sets the given {@link SortStateViewProvider} to this SortableTableHeaderView.
     *
     * @param provider The {@link SortStateViewProvider} that shall be used to render the sort views.
     */
    public void setSortStateViewProvider(final SortStateViewProvider provider) {
        sortStateViewProvider = provider;
        invalidate();
    }

    @Override
    public void invalidate() {
        if (getAdapter() != null) {
            getAdapter().notifyDataSetChanged();
        }
        super.invalidate();
    }

    @Override
    public TableHeaderAdapter getAdapter() {
        if (super.getAdapter() instanceof SortStateArrayAdapter) {
            return ((SortStateArrayAdapter) super.getAdapter()).delegate;
        }
        return super.getAdapter();
    }

    public void setIconPosition(int iconPosition) {
        this.iconPosition = iconPosition;
    }

    private class SortStateArrayAdapter extends TableHeaderAdapter {

        private final TableHeaderAdapter delegate;

        public SortStateArrayAdapter(final TableHeaderAdapter delegate) {
            super(delegate.getContext());
            this.delegate = delegate;
        }

        @Override
        public Context getContext() {
            return delegate.getContext();
        }

        @Override
        public LayoutInflater getLayoutInflater() {
            return delegate.getLayoutInflater();
        }

        @Override
        public Resources getResources() {
            return delegate.getResources();
        }

        @Override
        public TableColumnModel getColumnModel() {
            return delegate.getColumnModel();
        }

        @Override
        public void setColumnModel(TableColumnModel columnModel) {
            delegate.setColumnModel(columnModel);
        }

        @Override
        public int getColumnCount() {
            return delegate.getColumnCount();
        }

        @Override
        public void setColumnCount(int columnCount) {
            delegate.setColumnCount(columnCount);
        }

        @Override
        public int getCount() {
            return delegate.getCount();
        }

        @Override
        public View getHeaderView(final int columnIndex, final ViewGroup parentView) {

            // create column header layout
            // check if icon position is left or right
            final LinearLayout headerLayout = (LinearLayout)
                    (iconPosition == 0 ?
                            delegate.getLayoutInflater().inflate(R.layout.sortable_header, parentView, false)
                            : delegate.getLayoutInflater().inflate(R.layout.sortable_header_right, parentView, false));
            headerLayout.setOnClickListener(new InternalHeaderClickListener(columnIndex, getHeaderClickListeners()));

            // create header
            View headerView = delegate.getHeaderView(columnIndex, headerLayout);
            if (headerView == null) {
                headerView = new TextView(getContext());
            }

            // add the header view to the header layout
            ((FrameLayout) headerLayout.findViewById(R.id.container)).addView(headerView);

            // get the sort view
            ImageView sortView = (ImageView) headerLayout.findViewById(R.id.sort_view);
            sortViews.put(columnIndex, sortView);

            // get the sort state image
            SortState sortState = sortStates.get(columnIndex);
            if (sortState == null) {
                sortState = SortState.NOT_SORTABLE;
                sortStates.put(columnIndex, sortState);
            }
            setSortStateToView(sortState, sortView);

            return headerLayout;
        }
    }

}
