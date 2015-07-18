package de.codecrafters.sortabletableview.example;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import de.codecrafters.sortabletableview.R;
import de.codecrafters.sortabletableview.SortableTableView;
import de.codecrafters.sortabletableview.TableDataAdapter;
import de.codecrafters.sortabletableview.adapters.SimpleTableHeaderAdapter;
import de.codecrafters.sortabletableview.example.Car;
import de.codecrafters.sortabletableview.example.CarProducer;
import de.codecrafters.sortabletableview.listeners.TableDataClickListener;


public class MainActivity extends Activity {

    private static List<Car> CAR_LIST = new ArrayList<>();

    static {
        CarProducer audi = new CarProducer(R.mipmap.audi, "Audi");
        Car audiA1 = new Car(audi, "A1", 150, 25000);
        Car audiA3 = new Car(audi, "A3", 120, 35000);
        Car audiA4 = new Car(audi, "A4", 210, 42000);
        Car audiA5 = new Car(audi, "S5", 333, 60000);
        Car audiA6 = new Car(audi, "A6", 250, 55000);
        Car audiA7 = new Car(audi, "A7", 420, 87000);
        Car audiA8 = new Car(audi, "A8", 320, 110000);

        CAR_LIST.add(audiA3);
        CAR_LIST.add(audiA1);
        CAR_LIST.add(audiA7);
        CAR_LIST.add(audiA6);
        CAR_LIST.add(audiA8);
        CAR_LIST.add(audiA4);
        CAR_LIST.add(audiA5);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SortableTableView<Car> tableView = (SortableTableView) findViewById(R.id.tableView);
        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this, "Hersteller", "Typ", "PS", "Preis"));
        tableView.setDataAdapter(new CarTableDataAdapter(this, CAR_LIST));
        tableView.setColumnWeight(0, 1);
        tableView.setColumnWeight(1, 2);
        tableView.setColumnWeight(2, 1);
        tableView.setColumnWeight(3, 2);
        tableView.setComparator(1, new CarNameComparator());
        tableView.setComparator(2, new CarPsComparator());
        tableView.setComparator(3, new CarPriceComparator());
        tableView.addTableDataClickListener(new CarClickListener());
    }


    private class CarClickListener implements TableDataClickListener<Car> {

        @Override
        public void onDataClicked(int rowIndex, Car clickedData) {
            String carString = clickedData.getProducer().getName() + " " + clickedData.getName();
            Toast.makeText(getApplicationContext(), carString, Toast.LENGTH_SHORT).show();
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

    private static class CarTableDataAdapter extends TableDataAdapter<Car> {

        private static final int TEXT_SIZE = 14;
        private static final NumberFormat PRICE_FORMATTER = NumberFormat.getNumberInstance();

        public CarTableDataAdapter(Context context, List<Car> data) {
            super(context, data);
        }

        @Override
        public View getCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
            Car data = getItem(rowIndex);
            View renderedView = null;

            switch (columnIndex) {
                case 0:
                    renderedView = renderProducerLogo(data);
                    break;
                case 1:
                    renderedView = renderCatName(data);
                    break;
                case 2:
                    renderedView = renderPs(data);
                    break;
                case 3:
                    renderedView = renderPrice(data);
                    break;
            }

            return renderedView;
        }

        private View renderPrice(Car data) {
            return renderString(PRICE_FORMATTER.format(data.getPrice()) + " €");
        }

        private View renderPs(Car car) {
            return renderString(String.valueOf(car.getPs()));
        }

        private View renderCatName(Car car) {
            return renderString(car.getName());
        }

        private View renderProducerLogo(Car car) {
            return renderDrawableRes(car.getProducer().getLogo());
        }

        private View renderDrawableRes(int drawableRes) {
            ImageView imageView = new ImageView(getContext());
            imageView.setImageResource(drawableRes);
            imageView.setPadding(20, 10, 20, 10);

            return imageView;
        }

        private View renderString(String value) {
            TextView textView = new TextView(getContext());
            textView.setText(value);
            textView.setPadding(20, 10, 20, 10);
            textView.setTextSize(TEXT_SIZE);

            return textView;
        }

    }

}
