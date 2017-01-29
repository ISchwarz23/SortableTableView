package de.codecrafters.tableview.listeners;

import android.widget.AbsListView;
import android.widget.ListView;

/**
 * Definition of a {@link OnScrollListener} that can be used to listen for scroll and scroll state changes of the
 * data view (which is a {@link ListView}) of a {@link de.codecrafters.tableview.TableView}.
 *
 * @author ISchwarz
 */
public interface OnScrollListener {

    /**
     * Collection of the existing scroll states.
     */
    enum ScrollState {

        /**
         * The user had previously been scrolling using touch and had performed a fling.
         */
        FLING(AbsListView.OnScrollListener.SCROLL_STATE_FLING),
        /**
         * The view is not scrolling.
         */
        IDLE(AbsListView.OnScrollListener.SCROLL_STATE_IDLE),
        /**
         * The user is scrolling using touch, and their finger is still on the screen
         */
        TOUCH_SCROLL(AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL);

        private int value;

        ScrollState(final int value) {
            this.value = value;
        }

        /**
         * Gives the value known by the {@link android.widget.AbsListView.OnScrollListener} defined in the
         * {@link AbsListView}.
         *
         * @return The {@link AbsListView} scroll state value.
         */
        public int getValue() {
            return value;
        }

        /**
         * Creates a {@link ScrollState} from the {@link AbsListView} scroll state value.
         *
         * @param value The {@link AbsListView} scroll state value.
         * @return The created {@link ScrollState} or null if the value was invalid.
         */
        public static ScrollState fromValue(final int value) {
            switch (value) {
                case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                    return IDLE;
                case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                    return TOUCH_SCROLL;
                case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                    return FLING;
                default:
                    return null;
            }
        }
    }

    /**
     * Callback method to be invoked when the list or grid has been scrolled. This will be called after the scroll has completed.
     *
     * @param tableDataView    The view whose scroll state is being reported.
     * @param firstVisibleItem The index of the first visible row (ignore if visibleItemCount == 0).
     * @param visibleItemCount The number of visible rows.
     * @param totalItemCount   The number of items in the {@link de.codecrafters.tableview.TableDataAdapter}.
     */
    void onScroll(final ListView tableDataView, final int firstVisibleItem, final int visibleItemCount, final int totalItemCount);

    /**
     * Callback method to be invoked while the table data view is being scrolled. If the view is being scrolled, this method
     * will be called before the next frame of the scroll is rendered.
     *
     * @param tableDateView The view whose scroll state is being reported.
     * @param scrollState   he current scroll state. One of {@link ScrollState#TOUCH_SCROLL} or {@link ScrollState#IDLE}.
     */
    void onScrollStateChanged(final ListView tableDateView, final ScrollState scrollState);

}
