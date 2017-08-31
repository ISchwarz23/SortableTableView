package de.codecrafters.tableview.listeners;

import de.codecrafters.tableview.SortingStatus;

/**
 * Definition of a listener that is notified when the {@link SortingStatus} has changed.
 */
public interface SortingStatusChangeListener {

    /**
     * Callback method that is called when the {@link SortingStatus} has changed.
     *
     * @param newSortingStatus The new {@link SortingStatus}.
     */
    void onSortingStatusChanged(final SortingStatus newSortingStatus);
}
