package de.codecrafters.tableview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;

import de.codecrafters.tableview.listeners.TableHeaderClickListener;


/**
 * This view represents the header of a table. The given {@link TableHeaderAdapter} is used to fill
 * this view with data.
 *
 * @author ISchwarz
 */
class TableHeaderView extends LinearLayout {

    private final Set<TableHeaderClickListener> listeners = new HashSet<>();
    protected TableHeaderAdapter adapter;

    /**
     * Creates a new TableHeaderView.
     *
     * @param context
     *         The context that shall be used.
     */
    public TableHeaderView(final Context context) {
        super(context);
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);

        final LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(layoutParams);
    }

    /**
     * Sets the {@link TableHeaderAdapter} that is used to render the header views of every single column.
     *
     * @param adapter
     *         The {@link TableHeaderAdapter} that should be set.
     */
    public void setAdapter(final TableHeaderAdapter adapter) {
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

            final int width = 0;
            final int height = LayoutParams.WRAP_CONTENT;
            final int weight = adapter.getColumnWeight(columnIndex);
            final LayoutParams headerLayoutParams = new LayoutParams(width, height, weight);
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
    public void addHeaderClickListener(final TableHeaderClickListener listener) {
        listeners.add(listener);
    }

    /**
     * Removes the given {@link TableHeaderClickListener} from this SortableTableHeaderView.
     *
     * @param listener
     *         The {@link TableHeaderClickListener} that shall be removed.
     */
    public void removeHeaderClickListener(final TableHeaderClickListener listener) {
        listeners.remove(listener);
    }

}
