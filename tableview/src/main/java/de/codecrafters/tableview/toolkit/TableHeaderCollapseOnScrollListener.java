package de.codecrafters.tableview.toolkit;

import android.widget.ListView;
import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.listeners.OnScrollListener;

/**
 * A {@link OnScrollListener} that will collapse/show the header of a {@link TableView} on scroll.
 *
 * @author ISchwarz
 */
public class TableHeaderCollapseOnScrollListener implements OnScrollListener {

    private final TableView tableView;

    private int previousFirstVisibleItem = 0;
    private boolean headerVisible;

    private int animationDuration = 0;
    private int rowOffset = 2;

    /**
     * Creates a new {@link TableHeaderCollapseOnScrollListener} for the given {@link TableView}.
     *
     * @param tableView The {@link TableView} that contains the header that shall be collapsed/shown on scroll.
     */
    public TableHeaderCollapseOnScrollListener(final TableView tableView) {
        this.tableView = tableView;
        this.headerVisible = tableView.isHeaderVisible();
    }

    /**
     * Sets the animation duration for the collapsing/showing of the header.
     *
     * @param animationDuration The animation duration for the collapsing/showing the header. To disable the animation
     *                          just set this param to {@code 0}.
     */
    public void setAnimationDuration(final int animationDuration) {
        this.animationDuration = animationDuration;
    }

    /**
     * Sets the scroll offset of rows after which the header shall be collapsed/shown. (Default: 2)
     *
     * @param rowOffset The scroll offset of rows after which the header shall be collapsed/shown.
     */
    public void setRowOffset(final int rowOffset) {
        this.rowOffset = rowOffset;
    }

    @Override
    public void onScroll(final ListView tableDataView, final int firstVisibleItem, final int visibleItemCount, final int totalItemCount) {

        if (firstVisibleItem > previousFirstVisibleItem && firstVisibleItem - previousFirstVisibleItem >= rowOffset && headerVisible) {
            tableView.setHeaderVisible(false, animationDuration);
            headerVisible = false;
            previousFirstVisibleItem = firstVisibleItem;
        } else if (firstVisibleItem < previousFirstVisibleItem && previousFirstVisibleItem - firstVisibleItem >= rowOffset && !headerVisible) {
            tableView.setHeaderVisible(true, animationDuration);
            headerVisible = true;
            previousFirstVisibleItem = firstVisibleItem;
        }
        if (firstVisibleItem > previousFirstVisibleItem && !headerVisible) {
            previousFirstVisibleItem = firstVisibleItem;
        } else if (firstVisibleItem < previousFirstVisibleItem && headerVisible) {
            previousFirstVisibleItem = firstVisibleItem;
        }
    }

    @Override
    public void onScrollStateChanged(final ListView tableDateView, final ScrollState scrollState) {
        // do nothing
    }

}
