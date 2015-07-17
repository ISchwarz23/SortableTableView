package de.codecrafters.sortabletableview;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Ingo on 17.07.2015.
 */
public class SimpleTableHeaderAdapter implements TableHeaderAdapter {

    private String[] headers;

    public SimpleTableHeaderAdapter(String... headers) {
        this.headers = headers;
    }

    @Override
    public View getHeaderView(Context context, int columnIndex) {
        TextView textView = new TextView(context);

        if(columnIndex < headers.length) {
            textView.setText(headers[columnIndex]);
        }

        return textView;
    }
}
