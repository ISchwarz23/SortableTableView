package de.codecrafters.tableview;

import android.view.View;

import java.util.Set;

import de.codecrafters.tableview.listeners.TableHeaderClickListener;


/**
 * A internal {@link View.OnClickListener} on the header views that will forward clicks to the
 * registered {@link TableHeaderClickListener}.
 *
 * @author ISchwarz
 */
class InternalHeaderClickListener implements View.OnClickListener {

    private Set<TableHeaderClickListener> listeners;
    private int columnIndex;

    public InternalHeaderClickListener(int columnIndex, Set<TableHeaderClickListener> listeners) {
        this.columnIndex = columnIndex;
        this.listeners = listeners;
    }

    @Override
    public void onClick(View view) {
        informHeaderListeners();
    }

    private void informHeaderListeners() {
        for (TableHeaderClickListener listener : listeners) {
            try {
                listener.onHeaderClicked(columnIndex);
            } catch (Throwable t) {
                t.printStackTrace();
                // continue calling listeners
            }
        }
    }
}
