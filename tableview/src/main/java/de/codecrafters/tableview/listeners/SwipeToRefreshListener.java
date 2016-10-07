package de.codecrafters.tableview.listeners;

/**
 * Definition of a swipe to refresh listener.
 *
 * @author ISchwarz
 */
public interface SwipeToRefreshListener {

    /**
     * Interface representing the refresh indicator shown to the user.
     */
    interface RefreshIndicator {

        /**
         * Hides the refresh indicator shown to the user.
         */
        void hide();

        /**
         * Shows the refresh indicator to the user.
         */
        void show();

        /**
         * Indicates whether or not the refresh indicator is shown to the user.
         *
         * @return Boolean indicating whether or not the refresh indicator is shown to the user.
         */
        boolean isVisible();
    }

    /**
     * Callback method when the user triggers the refresh.
     *
     * @param refreshIndicator The refresh indicator that is shown to the user.
     */
    void onRefresh(final RefreshIndicator refreshIndicator);
}
