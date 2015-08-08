package de.codecrafters.tableview;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

    private Map<Integer, ImageView> sortViews = new HashMap<>();
    private Map<Integer, SortState> sortStates = new HashMap<>();
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
        for(int column : sortStates.keySet()) {
            SortState columnSortState = sortStates.get(column);
            if(columnSortState != SortState.NOT_SORTABLE) {
                columnSortState = SortState.SORTABLE;
            }
            sortStates.put(column, columnSortState);
        }
        for (int column : sortStates.keySet()) {
            ImageView sortView = sortViews.get(column);
            SortState sortState = sortStates.get(column);
            int imageRes = sortStateViewProvider.getSortStateViewResource(sortState);
            sortView.setImageResource(imageRes);
            if(imageRes == 0) {
                sortView.setVisibility(GONE);
            } else {
                sortView.setVisibility(VISIBLE);
            }
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

        sortStates.put(columnIndex, state);
        int imageRes = sortStateViewProvider.getSortStateViewResource(state);
        sortView.setImageResource(imageRes);
        if(imageRes == 0) {
            sortView.setVisibility(GONE);
        } else {
            sortView.setVisibility(VISIBLE);
        }
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

        for (int columnIndex = 0; columnIndex < adapter.getColumnCount(); columnIndex++) {
            LinearLayout headerContainerLayout = (LinearLayout) adapter.getLayoutInflater().inflate(R.layout.sortable_header, this, false);
            headerContainerLayout.setOnClickListener(new InternalHeaderClickListener(columnIndex, getHeaderClickListeners()));

            View headerView = adapter.getHeaderView(columnIndex, headerContainerLayout);
            if (headerView == null) {
                headerView = new TextView(getContext());
            }
            FrameLayout headerContainer = (FrameLayout) headerContainerLayout.findViewById(R.id.container);
            headerContainer.addView(headerView);

            int imageRes = sortStateViewProvider.getSortStateViewResource(SortState.NOT_SORTABLE);
            ImageView sortView = (ImageView) headerContainerLayout.findViewById(R.id.sort_view);
            sortView.setImageResource(imageRes);
            if(imageRes == 0) {
                sortView.setVisibility(GONE);
            } else {
                sortView.setVisibility(VISIBLE);
            }
            sortViews.put(columnIndex, sortView);

            int width = 0;
            int height = LayoutParams.WRAP_CONTENT;
            int weight = adapter.getColumnWeight(columnIndex);
            LayoutParams headerLayoutParams = new LayoutParams(width, height, weight);
            addView(headerContainerLayout, headerLayoutParams);
        }

        resetSortViews();
    }

}
