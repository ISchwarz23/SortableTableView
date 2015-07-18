package de.codecrafters.sortabletableview;

import android.content.Context;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Ingo on 17.07.2015.
 */
class SortableTableHeaderView extends TableHeaderView {

    private static final String LOG_TAG = SortableTableHeaderView.class.toString();

    private Map<Integer, ImageView> sortViews = new HashMap<>();
    private Set<HeaderClickListener> listeners = new HashSet<>();

    public SortableTableHeaderView(Context context) {
        super(context);
    }

    @Override
    protected void renderHeaderViews() {
        removeAllViews();
        headerViews.clear();

        for (int columnIndex = 0; columnIndex < adapter.getColumnCount(); columnIndex++) {
            RelativeLayout headerContainerLayout = (RelativeLayout) adapter.getLayoutInflater().inflate(R.layout.sortable_header, this, false);
            FrameLayout headerContainer = (FrameLayout) headerContainerLayout.findViewById(R.id.container);

            ImageView sortView = (ImageView) headerContainerLayout.findViewById(R.id.sort_view);
            sortView.setVisibility(GONE);
            sortViews.put(columnIndex, sortView);

            View headerView = adapter.getHeaderView(columnIndex, headerContainerLayout);
            headerContainer.addView(headerView);

            LayoutParams headerLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            headerContainerLayout.setLayoutParams(headerLayoutParams);
            headerContainerLayout.setOnClickListener(new InternalHeaderClickListener(columnIndex));

            headerViews.add(headerContainerLayout);
        }
    }

    public void resetSortViews() {
        for (ImageView sortView : sortViews.values()) {
            sortView.setImageResource(R.mipmap.ic_sortable);
        }
    }

    public void showSortView(int columnIndex, SortViewPresentation presentation) {
        ImageView sortView = sortViews.get(columnIndex);

        if(sortView == null) {
            Log.e(LOG_TAG, "SortView not found for column with index " + columnIndex);
            return;
        }

        switch (presentation) {
            case NONE:      sortView.setVisibility(GONE);
                            break;
            case SORTABLE:  sortView.setImageResource(R.mipmap.ic_sortable);
                            sortView.setVisibility(VISIBLE);
                            break;
            case SORT_UP:   sortView.setImageResource(R.mipmap.ic_sort_up);
                            sortView.setVisibility(VISIBLE);
                            break;
            case SORT_DOWN: sortView.setImageResource(R.mipmap.ic_sort_down);
                            sortView.setVisibility(VISIBLE);
                            break;
        }
    }

    public void addHeaderClickListener(HeaderClickListener listener) {
        listeners.add(listener);
    }

    private void informHeaderListeners(int columnIndex) {
        for (HeaderClickListener listener : listeners) {
            try {
                listener.onHeaderClicked(columnIndex);
            } catch(Throwable t) {
                t.printStackTrace();
                // continue calling listeners
            }
        }
    }

    private class InternalHeaderClickListener implements View.OnClickListener {

        private int column;

        public InternalHeaderClickListener(int column) {
            this.column = column;
        }

        @Override
        public void onClick(View view) {
            informHeaderListeners(column);
        }
    }

    public enum SortViewPresentation {

        NONE,
        SORTABLE,
        SORT_UP,
        SORT_DOWN;

    }

}
