package de.codecrafters.tableview;

import android.view.View;
import de.codecrafters.tableview.listeners.TableHeaderClickListener;

import java.util.Set;


/**
 * A internal {@link View.OnClickListener} on the header views that will forward clicks to the
 * registered {@link TableHeaderClickListener}.
 *
 * @author ISchwarz
 */
class InternalHeaderClickListener implements View.OnClickListener {

    private final Set<TableHeaderClickListener> listeners;
    private final int columnIndex;

    public InternalHeaderClickListener(final int columnIndex, final Set<TableHeaderClickListener> listeners) {
        this.columnIndex = columnIndex;
        this.listeners = listeners;
    }

    @Override
    public void onClick(final View view) {
        informHeaderListeners();
    }

    private void informHeaderListeners() {
        for (final TableHeaderClickListener listener : listeners) {
            try {
                listener.onHeaderClicked(columnIndex);
            } catch (final Throwable t) {
                t.printStackTrace();
                // continue calling listeners
            }
        }
    }
}
