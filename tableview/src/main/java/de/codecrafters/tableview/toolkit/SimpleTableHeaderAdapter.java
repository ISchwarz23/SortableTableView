package de.codecrafters.tableview.toolkit;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.codecrafters.tableview.TableHeaderAdapter;


/**
 * Simple implementation of the {@link TableHeaderAdapter}. This adapter will render the given header
 * Strings as {@link TextView}.
 *
 * @author ISchwarz
 */
public final class SimpleTableHeaderAdapter extends TableHeaderAdapter {

    private final String[] headers;
    private int paddingLeft = 20;
    private int paddingTop = 30;
    private int paddingRight = 20;
    private int paddingBottom = 30;
    private int textSize = 18;
    private int typeface = Typeface.BOLD;
    private int textColor = 0x99000000;

    /**
     * Creates a new SimpleTableHeaderAdapter.
     *
     * @param context
     *         The context to use inside this {@link TableHeaderAdapter}.
     * @param headers
     *         The header labels that shall be rendered.
     */
    public SimpleTableHeaderAdapter(final Context context, final String... headers) {
        super(context);
        this.headers = headers;
    }

    public SimpleTableHeaderAdapter(final Context context, final int... headerStringResources) {
        super(context);
        this.headers = new String[headerStringResources.length];

        for (int i = 0; i < headerStringResources.length; i++) {
            headers[i] = context.getString(headerStringResources[i]);
        }
    }

    /**
     * Sets the padding that will be used for all table headers.
     *
     * @param left
     *         The padding on the left side.
     * @param top
     *         The padding on the top side.
     * @param right
     *         The padding on the right side.
     * @param bottom
     *         The padding on the bottom side.
     */
    public void setPaddings(final int left, final int top, final int right, final int bottom) {
        paddingLeft = left;
        paddingTop = top;
        paddingRight = right;
        paddingBottom = bottom;
    }

    /**
     * Sets the padding that will be used on the left side for all table headers.
     *
     * @param paddingLeft
     *         The padding on the left side.
     */
    public void setPaddingLeft(final int paddingLeft) {
        this.paddingLeft = paddingLeft;
    }

    /**
     * Sets the padding that will be used on the top side for all table headers.
     *
     * @param paddingTop
     *         The padding on the top side.
     */
    public void setPaddingTop(final int paddingTop) {
        this.paddingTop = paddingTop;
    }

    /**
     * Sets the padding that will be used on the right side for all table headers.
     *
     * @param paddingRight
     *         The padding on the right side.
     */
    public void setPaddingRight(final int paddingRight) {
        this.paddingRight = paddingRight;
    }

    /**
     * Sets the padding that will be used on the bottom side for all table headers.
     *
     * @param paddingBottom
     *         The padding on the bottom side.
     */
    public void setPaddingBottom(final int paddingBottom) {
        this.paddingBottom = paddingBottom;
    }

    /**
     * Sets the text size that will be used for all table headers.
     *
     * @param textSize
     *         The text size that shall be used.
     */
    public void setTextSize(final int textSize) {
        this.textSize = textSize;
    }

    /**
     * Sets the typeface that will be used for all table headers.
     *
     * @param typeface
     *         The type face that shall be used.
     */
    public void setTypeface(final int typeface) {
        this.typeface = typeface;
    }

    /**
     * Sets the text color that will be used for all table headers.
     *
     * @param textColor
     *         The text color that shall be used.
     */
    public void setTextColor(final int textColor) {
        this.textColor = textColor;
    }

    @Override
    public View getHeaderView(final int columnIndex, final ViewGroup parentView) {
        final TextView textView = new TextView(getContext());

        if (columnIndex < headers.length) {
            textView.setText(headers[columnIndex]);
        }

        textView.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
        textView.setTypeface(textView.getTypeface(), typeface);
        textView.setTextSize(textSize);
        textView.setTextColor(textColor);
        textView.setSingleLine();
        textView.setEllipsize(TextUtils.TruncateAt.END);

        return textView;
    }
}
