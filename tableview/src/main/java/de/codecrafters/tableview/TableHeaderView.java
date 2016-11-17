package de.codecrafters.tableview;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.codecrafters.tableview.listeners.TableHeaderClickListener;

import java.util.HashSet;
import java.util.Set;


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
     * @param context The context that shall be used.
     */
    public TableHeaderView(final Context context) {
        super(context);
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);

        final LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(layoutParams);

        addOnLayoutChangeListener(new OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (right - left != oldRight - oldLeft) {
                    System.out.println("was resized");
                    renderHeaderViews();
                }
            }
        });
    }

    /**
     * Sets the {@link TableHeaderAdapter} that is used to render the header views of every single column.
     *
     * @param adapter The {@link TableHeaderAdapter} that should be set.
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

        int tableWidth = 0;
        if (getParent() instanceof View) {
            tableWidth = ((View) getParent()).getWidth();
        }

        for (int columnIndex = 0; columnIndex < adapter.getColumnCount(); columnIndex++) {
            View headerView = adapter.getHeaderView(columnIndex, this);
            if (headerView == null) {
                headerView = new TextView(getContext());
            }
            headerView.setOnClickListener(new InternalHeaderClickListener(columnIndex, getHeaderClickListeners()));

            final int width = adapter.getColumnModel().getColumnWidth(columnIndex, tableWidth);
            final int height = LayoutParams.WRAP_CONTENT;
            final LayoutParams headerLayoutParams = new LayoutParams(width, height);
            addView(headerView, headerLayoutParams);
        }
    }

    protected Set<TableHeaderClickListener> getHeaderClickListeners() {
        return listeners;
    }

    /**
     * Adds the given {@link TableHeaderClickListener} to this SortableTableHeaderView.
     *
     * @param listener The {@link TableHeaderClickListener} that shall be added.
     */
    public void addHeaderClickListener(final TableHeaderClickListener listener) {
        listeners.add(listener);
    }

    /**
     * Removes the given {@link TableHeaderClickListener} from this SortableTableHeaderView.
     *
     * @param listener The {@link TableHeaderClickListener} that shall be removed.
     */
    public void removeHeaderClickListener(final TableHeaderClickListener listener) {
        listeners.remove(listener);
    }

}
