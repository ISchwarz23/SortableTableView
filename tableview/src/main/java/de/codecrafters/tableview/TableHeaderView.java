package de.codecrafters.tableview;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.codecrafters.tableview.listeners.TableHeaderClickListener;


/**
 * This view represents the header of a table. The given {@link TableHeaderAdapter} is used to fill
 * this view with data.
 *
 * @author ISchwarz
 */
class TableHeaderView extends LinearLayout {

    protected TableHeaderAdapter adapter;

    private Set<TableHeaderClickListener> listeners = new HashSet<>();

    /**
     * Creates a new TableHeaderView.
     *
     * @param context
     *         The context that shall be used.
     */
    public TableHeaderView(Context context) {
        super(context);
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);

        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(layoutParams);
    }

    /**
     * Sets the {@link TableHeaderAdapter} that is used to render the header views of every single column.
     *
     * @param adapter
     *         The {@link TableHeaderAdapter} that should be set.
     */
    public void setAdapter(TableHeaderAdapter adapter) {
        this.adapter = adapter;
        renderHeaderViews();
    }

    @Override
    public void invalidate() {
        renderHeaderViews();
        super.invalidate();
    }

    /**
     * This method renders the header views for every single column.
     */
    protected void renderHeaderViews() {
        removeAllViews();

        for (int columnIndex = 0; columnIndex < adapter.getColumnCount(); columnIndex++) {
            View headerView = adapter.getHeaderView(columnIndex, this);
            if (headerView == null) {
                headerView = new TextView(getContext());
            }
            headerView.setOnClickListener(new InternalHeaderClickListener(columnIndex, getHeaderClickListeners()));

            int width = 0;
            int height = LayoutParams.WRAP_CONTENT;
            int weight = adapter.getColumnWeight(columnIndex);
            LayoutParams headerLayoutParams = new LayoutParams(width, height, weight);
            addView(headerView, headerLayoutParams);
        }
    }

    protected Set<TableHeaderClickListener> getHeaderClickListeners() {
        return listeners;
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

}
