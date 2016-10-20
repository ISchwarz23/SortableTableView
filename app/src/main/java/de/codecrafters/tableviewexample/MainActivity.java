package de.codecrafters.tableviewexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;
import de.codecrafters.tableview.listeners.SwipeToRefreshListener;
import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.listeners.TableDataLongClickListener;
import de.codecrafters.tableviewexample.data.Car;
import de.codecrafters.tableviewexample.data.DataFactory;

import java.util.List;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        final SortableCarTableView carTableView = (SortableCarTableView) findViewById(R.id.tableView);
        if (carTableView != null) {
            final CarTableDataAdapter carTableDataAdapter = new CarTableDataAdapter(this, DataFactory.createCarList(), carTableView);
            carTableView.setDataAdapter(carTableDataAdapter);
            carTableView.addDataClickListener(new CarClickListener());
            carTableView.addDataLongClickListener(new CarLongClickListener());
            carTableView.setSwipeToRefreshEnabled(true);
            carTableView.setSwipeToRefreshListener(new SwipeToRefreshListener() {
                @Override
                public void onRefresh(final RefreshIndicator refreshIndicator) {
                    carTableView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            final Car randomCar = getRandomCar();
                            carTableDataAdapter.getData().add(randomCar);
                            carTableDataAdapter.notifyDataSetChanged();
                            refreshIndicator.hide();
                            Toast.makeText(MainActivity.this, "Added: " + randomCar, Toast.LENGTH_SHORT).show();
                        }
                    }, 3000);
                }
            });
        }
    }

    private Car getRandomCar() {
        final List<Car> carList = DataFactory.createCarList();
        final int randomCarIndex = Math.abs(new Random().nextInt() % carList.size());
        return carList.get(randomCarIndex);
    }

    private class CarClickListener implements TableDataClickListener<Car> {

        @Override
        public void onDataClicked(final int rowIndex, final Car clickedData) {
            final String carString = "Click: " + clickedData.getProducer().getName() + " " + clickedData.getName();
            Toast.makeText(MainActivity.this, carString, Toast.LENGTH_SHORT).show();
        }
    }

    private class CarLongClickListener implements TableDataLongClickListener<Car> {

        @Override
        public boolean onDataLongClicked(final int rowIndex, final Car clickedData) {
            final String carString = "Long Click: " + clickedData.getProducer().getName() + " " + clickedData.getName();
            Toast.makeText(MainActivity.this, carString, Toast.LENGTH_SHORT).show();
            return true;
        }
    }
}
