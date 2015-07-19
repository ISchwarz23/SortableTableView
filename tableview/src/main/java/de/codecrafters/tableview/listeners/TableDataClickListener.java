package de.codecrafters.tableview.listeners;

/**
 * Created by Ingo on 18.07.2015.
 */
public interface TableDataClickListener<T> {

    void onDataClicked(int rowIndex, T clickedData);

}
