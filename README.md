[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-SortableTableView-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/2200) [![API](https://img.shields.io/badge/API-11%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=11) [![Build Status](https://travis-ci.org/ISchwarz23/SortableTableView.svg?branch=develop)](https://travis-ci.org/ISchwarz23/SortableTableView)  
# SortableTableView for Android
An Android library providing a TableView and a SortableTableView. 

![SortableTableView Example](https://raw.githubusercontent.com/ISchwarz23/SortableTableView/develop/README/SortableTableView-Example.gif)

**Minimum SDK-Version:** 11  |  **Compile SDK-Version:** 22  |  **Latest Library Version:** 0.9.6  

## Repository Content
**tableview** - contains the android library sources and resources  
**app** - contains an example application showing how to use the SortableTableView  

## Setup
To use the this library in your project simply add the following dependency to your *build.gradle* file.
```
    dependencies {
        ...
        compile 'de.codecrafters.tableview:tableview:0.9.6'
        ...
    }
```
  
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
    
    	private static final String[][] DATA_TO_SHOW = { { "This", "is", "a", "test" }, 
                                                         { "and", "a", "second", "test" } };
        
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
        // ...
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
#### Data Click Listening
To listen for clicks on data items you can register a `TableDataClickListener`. The`TableView` provides a method called `addDataClickListener()` to do so.
to the specific column.
```java
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_main);
        // ...
        tableView.addDataClickListener(new CarClickListener());
    }

	private class CarClickListener implements TableDataClickListener<Car> {
        @Override
        public void onDataClicked(int rowIndex, Car clickedCar) {
            String clickedCarString = clickedCar.getProducer().getName() + " " + clickedCar.getName();
            Toast.makeText(getContext(), clickedCarString, Toast.LENGTH_SHORT).show();
        }
    }
```

#### Header Click Listening
To listen for clicks on headers you can register a `TableHeaderClickListner`. The `TableView` provides a method called `addHeaderClickListener()` to do so.
```java
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_main);
        // ...
        tableView.addHeaderClickListener(new MyHeaderClickListener());
    }

	private class MyHeaderClickListener implements TableHeaderClickListener {
        @Override
        public void onHeaderClicked(int columnIndex) {
            String notifyText = "clicked column " + (columnIndex+1);
            Toast.makeText(getContext(), notifyText, Toast.LENGTH_SHORT).show();
        }
    }
```

### Styling
#### Header Styling
The table view provides several possibilities to style its header. One possibility is to set a **colour** for the header. Therefore you can adapt the XML file or add it to your code.
```xml
    <de.codecrafters.tableview.TableView
        android:id="@+id/tableView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        custom:headerColor="@color/primary" />
```
```java
    tableView.setHeaderBackgroundColor(getResources().getColor(R.color.primary));
```
For more complex header styles you can also set a **drawable** as header background using the following method.
```java
    tableView.setHeaderBackground(R.drawable.linear_gradient);
```
In addition you can set an **elevation** of the table header. To achieve this you have the possibility to set the elevation in XML or alternatively set it in your code. 
```xml
    <de.codecrafters.tableview.TableView
        android:id="@+id/tableView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        custom:headerElevation="10" />
```
```java
    tableView.setHeaderElevation(10);
```
**NOTE:** *This elevation is realized with the app-compat version of elevation. So it is also applicable on pre-lollipop devices*
  
For SortableTableViews it is also possible to replace the default **sortable indicator icons** by your custom ones. To do so you need to implement the `SortStateViewProvider` and set it to your `SortableTableView`.
```java
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_main);
        // ...
        sortbaleTableView.setHeaderSortStateViewProvider(new MySortStateViewProvider());
    }

	private static class MySortStateViewProvider implements SortStateViewProvider {

        private static final int NO_IMAGE_RES = -1;

        @Override
        public int getSortStateViewResource(SortState state) {
            switch (state) {
                case SORTABLE:
                    return R.mipmap.ic_sortable;
                case SORTED_ASC:
                    return R.mipmap.ic_sorted_asc;
                case SORTED_DESC:
                    return R.mipmap.ic_sorted_desc;
                default:
                    return NO_IMAGE_RES;
            }
        }
    }
```
There is also a factory class existing called `SortStateViewProviders` where you can get some predefined implementations of the `SortStateViewProvider`.

#### Data Row Styling
In general you can do all your styling of data content in your custom `TableDataAdapter`. But if you want to add colouring of whole table rows you can use the `TableDataRowColoriser`. There are alreasy some implementations of the `TableDataRowColoriser` existing in the library. You can get the by using the Factory class `TableDataRowColorisers`.  
This Factory contains for example an alternating-table-data-row coloriser that will colour rows with even index different from rows with odd index.
```java
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_main);
        // ...
    	int colorEvenRows = getResources().getColor(R.color.white);
    	int colorOddRows = getResources().getColor(R.color.gray);
    	tableView.setDataRowColoriser(TableDataRowColorisers.alternatingRows(colorEvenRows, colorOddRows));
    }
```
If the implementations of `TableDataRowColoriser` contained in the `TableDataRowColorisers` factory don't fulfil you needs you can create your own implementation of `TableDataRowColoriser`. Here is a small example of how to do so.
```java
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_main);
        // ...
        tableView.setDataRowColoriser(new CarPriceRowColoriser());
    }
    
    private static class CarPriceRowColoriser implements TableDataRowColoriser<Car> {
        @Override
        public int getRowColor(int rowIndex, Car car) {
            int rowColor = getResources(R.color.white);
            
            if(car.getPrice() < 50000) {
                rowColor = getResources(R.color.light_green);
            } else if(car.getPrice() > 100000) {
                rowColor = getResources(R.color.light_red);
            }
                
            return rowColor;
        }
    }
```
This coloriser will set the background colour of each row corresponding to the price of the car that is displayed at in this row. Cheap cars (less then 50,000) get a green background, expensive cars (more then 100,000) get a red background and all other cars get a white background.
  
## License
*Copyright 2015 Ingo Schwarz*  
  
Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

```
   http://www.apache.org/licenses/LICENSE-2.0
```

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.