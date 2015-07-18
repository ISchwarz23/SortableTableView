package de.codecrafters.sortabletableview;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Ingo on 17.07.2015.
 */
public class SimpleTableHeaderAdapter extends TableHeaderAdapter {

    private static final float TEXT_SIZE = 18;

    private String[] headers;

    public SimpleTableHeaderAdapter(Context context, String... headers) {
        super(context);
        this.headers = headers;
    }

    @Override
    public View getHeaderView(int columnIndex, ViewGroup parentView) {
        TextView textView = new TextView(getContext());

        if(columnIndex < headers.length) {
            textView.setText(headers[columnIndex]);
        }

        textView.setPadding(20, 30, 20, 30);
        textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
        textView.setTextSize(TEXT_SIZE);

        return textView;
    }
}
