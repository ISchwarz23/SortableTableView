package de.codecrafters.tableviewexample;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Toast;

import java.util.Comparator;

import de.codecrafters.tableview.SortableTableView;
import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;
import de.codecrafters.tableview.toolkit.SortStateViewProviders;
import de.codecrafters.tableview.toolkit.TableDataRowColorisers;
import de.codecrafters.tableviewexample.data.Car;


public class SortableCarTableView extends SortableTableView<Car> {


    public SortableCarTableView(Context context) {
        this(context, null);
    }

    public SortableCarTableView(Context context, AttributeSet attributes) {
        this(context, attributes, 0);
    }

    public SortableCarTableView(Context context, AttributeSet attributes, int styleAttributes) {
        super(context, attributes, styleAttributes);

        SimpleTableHeaderAdapter simpleTableHeaderAdapter = new SimpleTableHeaderAdapter(context, "Hersteller", "Bezeichung", "Leistung", "Preis");
        simpleTableHeaderAdapter.setTextColor(context.getResources().getColor(R.color.table_header_text));
        setHeaderAdapter(simpleTableHeaderAdapter);

        int rowColorEven = context.getResources().getColor(R.color.table_data_row_even);
        int rowColorOdd = context.getResources().getColor(R.color.table_data_row_odd);
        setDataRowColoriser(TableDataRowColorisers.alternatingRows(rowColorEven, rowColorOdd));
        setHeaderSortStateViewProvider(SortStateViewProviders.brightArrows());

        setColumnWeight(0, 2);
        setColumnWeight(1, 4);
        setColumnWeight(2, 3);
        setColumnWeight(3, 2);

        setColumnComparator(0, new CarProducerComparator());
        setColumnComparator(1, new CarNameComparator());
        setColumnComparator(2, new CarPsComparator());
        setColumnComparator(3, new CarPriceComparator());
        addDataClickListener(new CarClickListener());
    }


    private class CarClickListener implements TableDataClickListener<Car> {

        @Override
        public void onDataClicked(int rowIndex, Car clickedData) {
            String carString = clickedData.getProducer().getName() + " " + clickedData.getName();
            Toast.makeText(getContext(), carString, Toast.LENGTH_SHORT).show();
        }
    }

    private static class CarProducerComparator implements Comparator<Car> {

        @Override
        public int compare(Car car1, Car car2) {
            return car1.getProducer().getName().compareTo(car2.getProducer().getName());
        }
    }

    private static class CarPsComparator implements Comparator<Car> {

        @Override
        public int compare(Car car1, Car car2) {
            return car1.getPs() - car2.getPs();
        }
    }

    private static class CarNameComparator implements Comparator<Car> {

        @Override
        public int compare(Car car1, Car car2) {
            return car1.getName().compareTo(car2.getName());
        }
    }

    private static class CarPriceComparator implements Comparator<Car> {

        @Override
        public int compare(Car car1, Car car2) {
            if (car1.getPrice() < car2.getPrice()) return -1;
            if (car1.getPrice() > car2.getPrice()) return 1;
            return 0;
        }
    }

}
