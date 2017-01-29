package de.codecrafters.tableview.toolkit;

import android.widget.ListView;
import de.codecrafters.tableview.listeners.OnScrollListener;

/**
 * A {@link OnScrollListener} that will trigger loading of further items when the end of the
 * list is reached.
 *
 * @author ISchwarz
 */
public abstract class EndlessOnScrollListener implements OnScrollListener {

    private static final int DEFAULT_THRESHOLD = 5;

    private final int rowThreshold;

    private int previousTotal = 0;
    private boolean loading = true;


    /**
     * Creates a new {@link EndlessOnScrollListener} that has a default threshold of 5 rows. This means if
     * the user scrolls up to the 5th last row, a loading of further items is triggered.
     */
    public EndlessOnScrollListener() {
        this(DEFAULT_THRESHOLD);
    }

    /**
     * Creates a new {@link EndlessOnScrollListener} with the given row threshold. The row threshold defines, when
     * the reloading is triggered. E.g. a rowThreshold of 3 would mean a loading is triggered when the user reaches
     * the 3rd last row.
     *
     * @param rowThreshold The row threshold that shall be used.
     */
    public EndlessOnScrollListener(final int rowThreshold) {
        this.rowThreshold = rowThreshold;
    }

    @Override
    public void onScroll(final ListView tableDataView, final int firstVisibleItem,
                         final int visibleItemCount, final int totalItemCount) {

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }

        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + rowThreshold)) {
            // I load the next page of gigs using a background task,
            // but you can call any function here.
            onMoreRowsRequested(firstVisibleItem, visibleItemCount, totalItemCount);
            loading = true;
        }
    }

    @Override
    public void onScrollStateChanged(final ListView view, final ScrollState scrollState) {
        // nothing to do here
    }

    /**
     * Callback method when the user reached the row threshold to load more items.
     *
     * @param firstRowItem    The index of the first visible row.
     * @param visibleRowCount The number of rows that are visible.
     * @param totalRowCount   The total number of rows.
     */
    public abstract void onMoreRowsRequested(final int firstRowItem, final int visibleRowCount, final int totalRowCount);

}
