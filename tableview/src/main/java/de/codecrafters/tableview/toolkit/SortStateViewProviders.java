package de.codecrafters.tableview.toolkit;

import de.codecrafters.tableview.R;
import de.codecrafters.tableview.SortState;
import de.codecrafters.tableview.providers.SortStateViewProvider;


/**
 * A factory providing different implementations of the {@link SortStateViewProvider}.
 *
 * @author ISchwarz
 */
public final class SortStateViewProviders {


    /**
     * Gives a {@link SortStateViewProvider} that will provide dark (transparent black) arrows.
     *
     * @return The described {@link SortStateViewProvider}.
     */
    public static SortStateViewProvider darkArrows() {
        return new DarkSortStateViewProvider();
    }

    /**
     * Gives a {@link SortStateViewProvider} that will provide bright (transparent white) arrows.
     *
     * @return The described {@link SortStateViewProvider}.
     */
    public static SortStateViewProvider brightArrows() {
        return new BrightSortStateViewProvider();
    }


    private static class DarkSortStateViewProvider implements SortStateViewProvider {

        @Override
        public int getSortStateViewResource(final SortState state) {
            switch (state) {
                case SORTABLE:
                    return R.mipmap.ic_dark_sortable;
                case SORTED_ASC:
                    return R.mipmap.ic_dark_sorted_asc;
                case SORTED_DESC:
                    return R.mipmap.ic_dark_sorted_desc;
                default:
                    return NO_IMAGE;
            }
        }
    }


    private static class BrightSortStateViewProvider implements SortStateViewProvider {

        @Override
        public int getSortStateViewResource(final SortState state) {
            switch (state) {
                case SORTABLE:
                    return R.mipmap.ic_light_sortable;
                case SORTED_ASC:
                    return R.mipmap.ic_light_sorted_asc;
                case SORTED_DESC:
                    return R.mipmap.ic_light_sorted_desc;
                default:
                    return NO_IMAGE;
            }
        }
    }

}
