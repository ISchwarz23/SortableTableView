package de.codecrafters.sortabletableview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ingo on 17.07.2015.
 */
class TableHeaderView extends LinearLayout {

    private TableHeaderAdapter adapter;
    private List<View> headerViews = new ArrayList<>();

    public TableHeaderView(Context context) {
        super(context);
        setOrientation(LinearLayout.HORIZONTAL);

        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(layoutParams);
    }

    private void addViews() {
        removeAllViews();

        for(int columnIndex=0; columnIndex<adapter.getColumnCount(); columnIndex++) {
            LayoutParams headerLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            headerLayoutParams.weight = adapter.getColumnWeight(columnIndex);

            View headerView = adapter.getHeaderView(columnIndex, this);
            headerView.setLayoutParams(headerLayoutParams);

            headerViews.add(headerView);
            addView(headerView);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        removeAllViews();

        int widthUnit = (getWidth() / adapter.getColumnWeightSum());

        for(int columnIndex=0; columnIndex<headerViews.size(); columnIndex++) {
            View headerView = headerViews.get(columnIndex);

            int width = widthUnit * adapter.getColumnWeight(columnIndex);
            LayoutParams headerLayoutParams = new LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);

            headerView.setLayoutParams(headerLayoutParams);
            addView(headerView);
        }
    }

    public void setAdapter(TableHeaderAdapter adapter) {
        this.adapter = adapter;
        addViews();
        invalidate();
    }

}
