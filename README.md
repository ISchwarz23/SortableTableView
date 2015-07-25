# SortableTableView for Android
An Android library providing a TableView and a SortableTableView. 

Minimum SDK-Version: 11  
Compile SDK-Version: 22  
Current Version : 0.9  
  
## Repository Content
**tableview** - contains the android library sources and resources  
**app** - contains an example application showing how to use the SortableTableView  
  
## Features
### Layouting
#### Column Count
The provided TableView is very easy to adapt to your needs. To set the colomn count simple set the parameter inside your xml layout.  
```xml
	<de.codecrafters.tableview.TableView
        android:id="@+id/tableView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        custom:columnCount="4" />
```
A second possibility to define the column count of your TableView is to set it directly in the code.
```java
	TableView tableView = (TableView) findViewById(R.id.tableView);
	tableView.setColumnCount(4);
```

#### Column Width
To define the relative width of your columns you can define a specific *weight* for each of them (as you may know from [LinearLayout](http://developer.android.com/guide/topics/ui/layout/linear.html)). By default the weight of each column is set to 1. So every column has the same width. To make the first column (index of first column is 0) twice as wide as the other columns simple do the following call.
```java
	tableView.setColumnWeight(0, 2);
```
Because the width of an column is not given absolute but relative, the TableView will adapt to all screen sices.

### Showing Data
#### Simple Data
For displaying simple data like a 2D-String-Array you can use the SimpleTableDataAdapter. The SimpleTableHeader will turn the given Strings to TextViews and display them inside the TableView at the same position as previous in the 2D-String-Array.
```java
	public class MainActivity extends AppCompatActivity {
    
    	private static final String[][] DATA_TO_SHOW = { { "This", "is", "a", "test" }, { "and", "second", "test" } };
        
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            TableView<String[]> tableView = (TableView<String[]>) findViewById(R.id.tableView);
            tableView.setDataAdapter(new SimpleTableDataAdapter(this, dataToShow));
        }
    }        
```

#### Custom Data
For displaying more complex custom data you need to implement your own TableDataAdapter. Therefore you need to implement the `getCellView(int rowIndex, int columnIndex, ViewGroup parentView))` method. This method is called for every table cell and needs to returned the [View](http://developer.android.com/reference/android/view/View.html) that shall be displayed in the cell with the given *rowIndex* and *columnIndex*. Here is an example of an TableDataAdapter for a **Car** object.

#### Sortable Data
ToDo
#### Header Data
ToDo

### Interaction Listening
#### Header Click Listening
ToDo
#### Data Click Listening
ToDo

### Styling
#### Header Styling
ToDo
#### Data Row Styling
ToDo
  
## References
