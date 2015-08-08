package de.codecrafters.tableview.providers;

import de.codecrafters.tableview.SortState;


/**
 * Provider for images indication the sort status.
 *
 * @author ISchwarz
 */
public interface SortStateViewProvider {

    public static final int NO_IMAGE = 0;

    /**
     * Gives the drawable resource for the given {@link SortState}.
     *
     * @param state
     *         The {@link SortState} to return the drawable resource for.
     * @return The drawable resource for the given {@link SortState}.
     */
    int getSortStateViewResource(SortState state);

}
