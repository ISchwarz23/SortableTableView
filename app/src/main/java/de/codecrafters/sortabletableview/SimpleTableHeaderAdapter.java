package de.codecrafters.sortabletableview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Ingo on 17.07.2015.
 */
public class SimpleTableHeaderAdapter extends TableHeaderAdapter {

    private String[] headers;

    public SimpleTableHeaderAdapter(Context context, int columnCount, String... headers) {
        super(context, columnCount);
        this.headers = headers;
    }

    @Override
    public View getHeaderView(int columnIndex, ViewGroup parentView) {
        TextView textView = new TextView(getContext());

        if(columnIndex < headers.length) {
            textView.setText(headers[columnIndex]);
        }

        return textView;
    }
}
