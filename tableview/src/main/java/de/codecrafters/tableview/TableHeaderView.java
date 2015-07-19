package de.codecrafters.tableview;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * This view represents the header of a table. The given {@link TableHeaderAdapter} is used to fill
 * this view with data.
 *
 * @author ISchwarz
 */
class TableHeaderView extends LinearLayout {

    protected TableHeaderAdapter adapter;
    protected List<View> headerViews = new ArrayList<>();

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
        invalidate();
    }

    /**
     * This method renders the header views for every single column.
     */
    protected void renderHeaderViews() {
        headerViews.clear();

        for (int columnIndex = 0; columnIndex < 4; columnIndex++) {
            View headerView = adapter.getHeaderView(columnIndex, this);
            headerViews.add(headerView);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        removeAllViews();

        int widthUnit = getWidth() / adapter.getColumnWeightSum();

        for (int columnIndex = 0; columnIndex < headerViews.size(); columnIndex++) {
            View headerView = headerViews.get(columnIndex);
            if (headerView == null) {
                headerView = new TextView(getContext());
            }

            int width = widthUnit * adapter.getColumnWeight(columnIndex);
            LayoutParams headerLayoutParams = new LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
            headerView.setLayoutParams(headerLayoutParams);

            addView(headerView, columnIndex);
        }
    }

}
