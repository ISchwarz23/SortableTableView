package de.codecrafters.tableviewexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;


public class MainActivity extends AppCompatActivity {

    private static final String[][] dataToShow = { { "This", "is", "a", "test" }, { "and", "second", "test" } };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        TableView<String[]> tableView = (TableView<String[]>) findViewById(R.id.tableView);
        tableView.setColumnWeight(3, 2);
        tableView.setDataAdapter(new SimpleTableDataAdapter(this, dataToShow));
        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this, "Header 1", "Header 2", "Header 3", "Header 4"));

    }

}
