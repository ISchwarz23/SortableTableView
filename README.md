# SortableTableView for Android
[![Build Status](https://travis-ci.org/ISchwarz23/SortableTableView.svg?branch=master)](https://travis-ci.org/ISchwarz23/SortableTableView)  
An Android library providing a TableView and a SortableTableView. 

![SortableTableView Example](https://raw.githubusercontent.com/ISchwarz23/SortableTableView/develop/README/SortableTableView-Example.gif)

Minimum SDK-Version: 11  
Compile SDK-Version: 22  
Current Version : 0.9  
  
## Repository Content
**tableview** - contains the android library sources and resources  
**app** - contains an example application showing how to use the SortableTableView  
  
## Features
### Layouting
#### Column Count
The provided TableView is very easy to adapt to your needs. To set the column count simple set the parameter inside your XML layout.  
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
Because the width of an column is not given absolute but relative, the TableView will adapt to all screen sizes.

### Showing Data
#### Simple Data
For displaying simple data like a 2D-String-Array you can use the `SimpleTableDataAdapter`. The `SimpleTableDataAdapter` will turn the given Strings to [TextViews](http://developer.android.com/reference/android/widget/TextView.html) and display them inside the TableView at the same position as previous in the 2D-String-Array.
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
For displaying more complex custom data you need to implement your own `TableDataAdapter`. Therefore you need to implement the `getCellView(int rowIndex, int columnIndex, ViewGroup parentView)` method. This method is called for every table cell and needs to returned the [View](http://developer.android.com/reference/android/view/View.html) that shall be displayed in the cell with the given *rowIndex* and *columnIndex*. Here is an example of an TableDataAdapter for a **Car** object.
```java
	public class CarTableDataAdapter extends TableDataAdapter<Car> {

        public CarTableDataAdapter(Context context, List<Car> data) {
            super(context, data);
        }

        @Override
        public View getCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
            Car car = getRowData(rowIndex);
            View renderedView = null;

            switch (columnIndex) {
                case 0:
                    renderedView = renderProducerLogo(car);
                    break;
                case 1:
                    renderedView = renderCatName(car);
                    break;
                case 2:
                    renderedView = renderPower(car);
                    break;
                case 3:
                    renderedView = renderPrice(car);
                    break;
            }

            return renderedView;
        } 
        
        ...
    }
```
The `TableDataAdapter` provides several easy access methods you need to render your cell views like:
- `getRowData()`
- `getContext()`
- `getLayoutInflater()`
- `getResources()`

#### Sortable Data
If you need to make your data sortable, you should use the `SortableTableView` instead of the ordinary `TableView`. To make a table sortable by a column, all you need to do is to implement a [Comparator](http://docs.oracle.com/javase/7/docs/api/java/util/Comparator.html) and set it to the specific column.
```java
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_main);
        
        ...
        sortableTableView.setColumnComparator(0, new CarProducerComparator());
    }

	private static class CarProducerComparator implements Comparator<Car> {
      	@Override
      	public int compare(Car car1, Car car2) {
          	return car1.getProducer().getName().compareTo(car2.getProducer().getName());
      	}
	}
```
By doing so the `SortableTableView` will automatically display a sortable indicator next to the table header of the column with the index 0. By clicking this table header, the table is sorted ascending with the given Comparator. If the table header is clicked again, it will be sorted in descending order.

#### Header Data
Setting data to the header views is identical to setting data to the table cells. All you need to do is extending the `TableHeaderAdapter` which is also providing the easy access methods that are described for the `TableDataAdapter`.  
If all you want to display in the header is the column title as String (like in most cases) the `SimpleTableHeaderAdapter` will fulfil your needs.

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
