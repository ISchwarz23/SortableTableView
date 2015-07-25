package de.codecrafters.tableview.toolkit;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import de.codecrafters.tableview.TableDataAdapter;

/**
 * A simple {@link TableDataAdapter} that allows to display 2D-String-Arrays in a {@link de.codecrafters.tableview.TableView}.
 *
 * @author ISchwarz
 */
public class SimpleTableDataAdapter extends TableDataAdapter<String[]> {

    private int paddingLeft = 20;
    private int paddingTop = 15;
    private int paddingRight = 20;
    private int paddingBottom = 15;
    private int textSize = 18;
    private int typeface = Typeface.NORMAL;
    private int textColor = 0x99000000;


    public SimpleTableDataAdapter(Context context, String[][] data) {
        super(context, data);
    }

    public SimpleTableDataAdapter(Context context, List<String[]> data) {
        super(context, data);
    }

    @Override
    public View getCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
        TextView textView = new TextView(getContext());
        textView.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
        textView.setTypeface(textView.getTypeface(), typeface);
        textView.setTextSize(textSize);
        textView.setTextColor(textColor);
        textView.setSingleLine();
        textView.setEllipsize(TextUtils.TruncateAt.END);

        try {
            String textToShow = getItem(rowIndex)[columnIndex];
            textView.setText(textToShow);
        } catch(IndexOutOfBoundsException e) {
            // Show no text
        }

        return textView;
    }

    /**
     * Sets the padding that will be used for all table cells.
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
    public void setPaddings(int left, int top, int right, int bottom) {
        paddingLeft = left;
        paddingTop = top;
        paddingRight = right;
        paddingBottom = bottom;
    }

    /**
     * Sets the padding that will be used on the left side for all table cells.
     *
     * @param paddingLeft
     *         The padding on the left side.
     */
    public void setPaddingLeft(int paddingLeft) {
        this.paddingLeft = paddingLeft;
    }

    /**
     * Sets the padding that will be used on the top side for all table cells.
     *
     * @param paddingTop
     *         The padding on the top side.
     */
    public void setPaddingTop(int paddingTop) {
        this.paddingTop = paddingTop;
    }

    /**
     * Sets the padding that will be used on the right side for all table cells.
     *
     * @param paddingRight
     *         The padding on the right side.
     */
    public void setPaddingRight(int paddingRight) {
        this.paddingRight = paddingRight;
    }

    /**
     * Sets the padding that will be used on the bottom side for all table cells.
     *
     * @param paddingBottom
     *         The padding on the bottom side.
     */
    public void setPaddingBottom(int paddingBottom) {
        this.paddingBottom = paddingBottom;
    }

    /**
     * Sets the text size that will be used for all table cells.
     *
     * @param textSize
     *         The text size that shall be used.
     */
    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    /**
     * Sets the typeface that will be used for all table cells.
     *
     * @param typeface
     *         The type face that shall be used.
     */
    public void setTypeface(int typeface) {
        this.typeface = typeface;
    }

    /**
     * Sets the text color that will be used for all table cells.
     *
     * @param textColor
     *         The text color that shall be used.
     */
    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }


}
