package de.codecrafters.sortabletableview.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.codecrafters.sortabletableview.TableHeaderAdapter;

/**
 * Simple implementation of the {@link TableHeaderAdapter}. This adapter will render the given header
 * Strings as {@link TextView}.
 *
 * @author ISchwarz
 */
public final class SimpleTableHeaderAdapter extends TableHeaderAdapter {

    private String[] headers;
    private int paddingLeft = 20;
    private int paddingTop = 30;
    private int paddingRight = 20;
    private int paddingBottom = 30;
    private int textSize = 18;
    private int typeface = Typeface.BOLD;

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

        textView.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
        textView.setTypeface(textView.getTypeface(), typeface);
        textView.setTextSize(textSize);
        textView.setSingleLine();
        textView.setEllipsize(TextUtils.TruncateAt.END);

        return textView;
    }

    public void setPaddings(int left, int top, int right, int bottom) {
        paddingLeft = left;
        paddingTop = top;
        paddingRight = right;
        paddingBottom = bottom;
    }

    public void setPaddingLeft(int paddingLeft) {
        this.paddingLeft = paddingLeft;
    }

    public void setPaddingTop(int paddingTop) {
        this.paddingTop = paddingTop;
    }

    public void setPaddingRight(int paddingRight) {
        this.paddingRight = paddingRight;
    }

    public void setPaddingBottom(int paddingBottom) {
        this.paddingBottom = paddingBottom;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public void setTypeface(int typeface) {
        this.typeface = typeface;
    }
}
