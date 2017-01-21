package de.codecrafters.tableview;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.codecrafters.tableview.providers.SortStateViewProvider;
import de.codecrafters.tableview.toolkit.SortStateViewProviders;

import java.util.HashMap;
import java.util.Map;


/**
 * Extension of the {@link TableHeaderView} that will show sorting indicators at the start of the header.
 *
 * @author ISchwarz
 */
class SortableTableHeaderView extends TableHeaderView {

    private static final String LOG_TAG = SortableTableHeaderView.class.toString();

    private final Map<Integer, ImageView> sortViews = new HashMap<>();
    private final Map<Integer, SortState> sortStates = new HashMap<>();
    private SortStateViewProvider sortStateViewProvider = SortStateViewProviders.darkArrows();


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
        for (final int column : sortStates.keySet()) {
            SortState columnSortState = sortStates.get(column);
            if (columnSortState != SortState.NOT_SORTABLE) {
                columnSortState = SortState.SORTABLE;
            }
            sortStates.put(column, columnSortState);
        }
        for (final int column : sortStates.keySet()) {
            final ImageView sortView = sortViews.get(column);
            final SortState sortState = sortStates.get(column);
            final int imageRes = sortStateViewProvider.getSortStateViewResource(sortState);
            sortView.setImageResource(imageRes);
            if (imageRes == 0) {
                sortView.setVisibility(GONE);
            } else {
                sortView.setVisibility(VISIBLE);
            }
        }
    }

    /**
     * Sets the {@link SortState} of the SortView of the column with the given index.
     *
     * @param columnIndex The index of the column for which the given {@link SortState}
     *                    will be set.
     * @param state       The {@link SortState} that shall be set to the sort view at the column with
     *                    the given index.
     */
    public void setSortState(final int columnIndex, final SortState state) {
        final ImageView sortView = sortViews.get(columnIndex);

        if (sortView == null) {
            Log.e(LOG_TAG, "SortView not found for columnIndex with index " + columnIndex);
            return;
        }

        sortStates.put(columnIndex, state);
        final int imageRes = sortStateViewProvider.getSortStateViewResource(state);
        sortView.setImageResource(imageRes);
        if (imageRes == 0) {
            sortView.setVisibility(GONE);
        } else {
            sortView.setVisibility(VISIBLE);
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
        renderHeaderViews();
    }

    @Override
    protected void renderHeaderViews() {
        removeAllViewsInLayout();
        removeAllViews();

        int tableWidth = 0;
        if (getParent() instanceof View) {
            tableWidth = ((View) getParent()).getWidth();
        }
        Log.d(LOG_TAG, "tableWidth = " + tableWidth);

        for (int columnIndex = 0; columnIndex < adapter.getColumnCount(); columnIndex++) {

            // create column header layout
            final LinearLayout headerLayout = (LinearLayout) adapter.getLayoutInflater().inflate(R.layout.sortable_header, this, false);
            headerLayout.setOnClickListener(new InternalHeaderClickListener(columnIndex, getHeaderClickListeners()));

            // create header
            View headerView = adapter.getHeaderView(columnIndex, headerLayout);
            if (headerView == null) {
                headerView = new TextView(getContext());
            }

            // add the header view to the header layout
            ((FrameLayout) headerLayout.findViewById(R.id.container)).addView(headerView);

            // get the sort view
            ImageView sortView = (ImageView) headerLayout.findViewById(R.id.sort_view);
            sortViews.put(columnIndex, sortView);

            // get the sort state
            SortState sortState = sortStates.get(columnIndex);
            if (sortState == null) {
                sortState = SortState.NOT_SORTABLE;
                sortStates.put(columnIndex, sortState);
            }

            // get the sort image
            Log.d(LOG_TAG, "Column: " + columnIndex + ", SortState: " + sortState);
            final int imageRes = sortStateViewProvider.getSortStateViewResource(sortState);
            sortView.setImageResource(imageRes);
            if (imageRes == 0) {
                sortView.setVisibility(GONE);
            } else {
                sortView.setVisibility(VISIBLE);
            }

            // add the column header
            final int width = adapter.getColumnModel().getColumnWidth(columnIndex, tableWidth);
            final int height = LayoutParams.WRAP_CONTENT;
            addView(headerLayout, new LayoutParams(width, height));
        }
    }

}
