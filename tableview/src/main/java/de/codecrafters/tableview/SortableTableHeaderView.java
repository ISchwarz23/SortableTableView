package de.codecrafters.tableview;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import de.codecrafters.tableview.listeners.TableHeaderClickListener;
import de.codecrafters.tableview.providers.SortStateViewProvider;
import de.codecrafters.tableview.toolkit.SortStateViewProviders;


/**
 * Extension of the {@link TableHeaderView} that will show sorting indicators at the start of the header.
 *
 * @author ISchwarz
 */
class SortableTableHeaderView extends TableHeaderView {

    private static final String LOG_TAG = SortableTableHeaderView.class.toString();

    private Set<TableHeaderClickListener> listeners = new HashSet<>();
    private Map<Integer, ImageView> sortViews = new HashMap<>();
    private SortStateViewProvider sortStateViewProvider = SortStateViewProviders.darkArrows();


    /**
     * Creates a new SortableTableHeaderView.
     *
     * @param context
     *         The context that shall be used.
     */
    public SortableTableHeaderView(Context context) {
        super(context);
    }

    /**
     * Will set all sort views to state "sortable".
     */
    public void resetSortViews() {
        for (ImageView sortView : sortViews.values()) {
            sortView.setImageResource(sortStateViewProvider.getSortStateViewResource(SortState.SORTABLE));
        }
    }

    /**
     * Sets the {@link SortState} of the SortView of the column with the given index.
     *
     * @param columnIndex
     *         The index of the column for which the given {@link SortState}
     *         will be set.
     * @param state
     *         The {@link SortState} that shall be set to the sort view at the column with
     *         the given index.
     */
    public void setSortState(int columnIndex, SortState state) {
        ImageView sortView = sortViews.get(columnIndex);

        if (sortView == null) {
            Log.e(LOG_TAG, "SortView not found for columnIndex with index " + columnIndex);
            return;
        }

        sortView.setVisibility(VISIBLE);
        sortView.setImageResource(sortStateViewProvider.getSortStateViewResource(state));
    }

    /**
     * Adds the given {@link TableHeaderClickListener} to this SortableTableHeaderView.
     *
     * @param listener
     *         The {@link TableHeaderClickListener} that shall be added.
     */
    public void addHeaderClickListener(TableHeaderClickListener listener) {
        listeners.add(listener);
    }

    /**
     * Removes the given {@link TableHeaderClickListener} from this SortableTableHeaderView.
     *
     * @param listener
     *         The {@link TableHeaderClickListener} that shall be removed.
     */
    public void removeHeaderClickListener(TableHeaderClickListener listener) {
        listeners.remove(listener);
    }

    /**
     * Sets the given {@link SortStateViewProvider} to this SortableTableHeaderView.
     *
     * @param provider
     *         The {@link SortStateViewProvider} that shall be used to render the sort views.
     */
    public void setSortStateViewProvider(SortStateViewProvider provider) {
        sortStateViewProvider = provider;
        resetSortViews();
    }

    /**
     * Gives the current {@link SortStateViewProvider} of this SortableTableHeaderView.
     * @return The {@link SortStateViewProvider} that is currently used to render the sort views.
     */
    public SortStateViewProvider getSortStateViewProvider() {
        return sortStateViewProvider;
    }

    @Override
    protected void renderHeaderViews() {
        removeAllViews();
        headerViews.clear();

        for (int columnIndex = 0; columnIndex < adapter.getColumnCount(); columnIndex++) {
            RelativeLayout headerContainerLayout = (RelativeLayout) adapter.getLayoutInflater().inflate(R.layout.sortable_header, this, false);
            headerContainerLayout.setOnClickListener(new InternalHeaderClickListener(columnIndex));
            headerViews.add(headerContainerLayout);

            View headerView = adapter.getHeaderView(columnIndex, headerContainerLayout);
            FrameLayout headerContainer = (FrameLayout) headerContainerLayout.findViewById(R.id.container);
            headerContainer.addView(headerView);

            ImageView sortView = (ImageView) headerContainerLayout.findViewById(R.id.sort_view);
            sortView.setVisibility(GONE);
            sortViews.put(columnIndex, sortView);
        }
    }


    /**
     * A internal {@link View.OnClickListener} on the header views that will forward clicks to the
     * registered {@link TableHeaderClickListener}.
     *
     * @author ISchwarz
     */
    private class InternalHeaderClickListener implements View.OnClickListener {

        private int columnIndex;

        public InternalHeaderClickListener(int columnIndex) {
            this.columnIndex = columnIndex;
        }

        @Override
        public void onClick(View view) {
            informHeaderListeners();
        }

        private void informHeaderListeners() {
            for (TableHeaderClickListener listener : listeners) {
                try {
                    listener.onHeaderClicked(columnIndex);
                } catch (Throwable t) {
                    t.printStackTrace();
                    // continue calling listeners
                }
            }
        }

    }

}
