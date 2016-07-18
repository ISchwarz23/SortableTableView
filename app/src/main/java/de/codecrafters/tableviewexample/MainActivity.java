package de.codecrafters.tableviewexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.listeners.TableDataLongClickListener;
import de.codecrafters.tableviewexample.data.Car;
import de.codecrafters.tableviewexample.data.DataFactory;


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
            carTableView.setDataAdapter(new CarTableDataAdapter(this, DataFactory.createCarList()));
            carTableView.addDataClickListener(new CarClickListener());
            carTableView.addDataLongClickListener(new CarLongClickListener());
        }
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
        public boolean onDataClicked(final int rowIndex, final Car clickedData) {
            final String carString = "Long Click: " + clickedData.getProducer().getName() + " " + clickedData.getName();
            Toast.makeText(MainActivity.this, carString, Toast.LENGTH_SHORT).show();
            return true;
        }
    }
}
