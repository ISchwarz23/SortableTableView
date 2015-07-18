package de.codecrafters.sortabletableview;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ingo on 17.07.2015.
 */
public class TableColumnModel {

    private static final int DEFAULT_COLUMN_WEIGHT = 1;

    private final Map<Integer, Integer> columnWeights;
    private int columnCount;

    public TableColumnModel(int columnCount) {
        this.columnWeights = new HashMap<>();
        this.columnCount = columnCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }

    public void setColumnWeight(int columnIndex, int columnWeight) {
        columnWeights.put(columnIndex, columnWeight);
    }

    public int getColumnWeight(int columnIndex) {
        Integer columnWeight = columnWeights.get(columnIndex);
        if(columnWeight == null) {
            columnWeight = DEFAULT_COLUMN_WEIGHT;
        }
        return columnWeight;
    }

    public int getColumnWeightSum() {
        int weightSum = 0;

        for(int columnIndex=0; columnIndex<columnCount; columnIndex++) {
            weightSum += getColumnWeight(columnIndex);
        }

        return weightSum;
    }

}
