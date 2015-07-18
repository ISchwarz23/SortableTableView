package de.codecrafters.sortabletableview;

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

import de.codecrafters.sortabletableview.listeners.TableHeaderClickListener;


/**
 * Extension of the {@link TableHeaderView} that will show sorting indicators at the start of the header.
 *
 * @author ISchwarz
 */
class SortableTableHeaderView extends TableHeaderView {

    /**
     * A enumeration containing all available SortView states of presentation.
     *
     * @author ISchwarz
     */
    public enum SortViewPresentation {
        NONE,
        SORTABLE,
        SORT_UP,
        SORT_DOWN
    }

    private static final String LOG_TAG = SortableTableHeaderView.class.toString();

    private Set<TableHeaderClickListener> listeners = new HashSet<>();
    private Map<Integer, ImageView> sortViews = new HashMap<>();


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
            sortView.setImageResource(R.mipmap.ic_sortable);
        }
    }

    /**
     * Sets the presentation of the SortView of the columnIndex with the given index.
     *
     * @param columnIndex
     *         The index of the columnIndex to which the given {@link SortViewPresentation}
     *         will be set.
     * @param presentation
     *         The presentation that shall be set to the SortView at the columnIndex with
     *         the given index.
     */
    public void setSortViewPresentation(int columnIndex, SortViewPresentation presentation) {
        ImageView sortView = sortViews.get(columnIndex);

        if (sortView == null) {
            Log.e(LOG_TAG, "SortView not found for columnIndex with index " + columnIndex);
            return;
        }

        switch (presentation) {
            case NONE:
                sortView.setVisibility(GONE);
                break;
            case SORTABLE:
                sortView.setImageResource(R.mipmap.ic_sortable);
                sortView.setVisibility(VISIBLE);
                break;
            case SORT_UP:
                sortView.setImageResource(R.mipmap.ic_sort_up);
                sortView.setVisibility(VISIBLE);
                break;
            case SORT_DOWN:
                sortView.setImageResource(R.mipmap.ic_sort_down);
                sortView.setVisibility(VISIBLE);
                break;
        }
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
