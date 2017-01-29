[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-SortableTableView-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/2200) [![API](https://img.shields.io/badge/API-11%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=11) [![Build Status](https://travis-ci.org/ISchwarz23/SortableTableView.svg?branch=master)](https://travis-ci.org/ISchwarz23/SortableTableView)  
# SortableTableView for Android
An Android library providing a TableView and a SortableTableView. 

![SortableTableView Example](https://raw.githubusercontent.com/ISchwarz23/SortableTableView/develop/README/SortableTableView-Example.gif)

**Minimum SDK-Version:** 11  |  **Compile SDK-Version:** 25  |  **Latest Library Version:** 2.4.3  

## Repository Content
**tableview** - contains the android library sources and resources  
**app** - contains an example application showing how to use the SortableTableView  

[![Example App](http://www.clintonfitch.com/wp-content/uploads/2015/06/Google-Play-Button.jpg)](https://play.google.com/store/apps/details?id=de.codecrafters.tableviewexample)

## Setup
To use the this library in your project simply add the following dependency to your *build.gradle* file.
```
    dependencies {
        ...
        compile 'de.codecrafters.tableview:tableview:2.4.3'
        ...
    }
```
  
## Features
### Layouting
#### Column Count
The provided TableView is very easy to adapt to your needs. To set the column count simple set the parameter inside your XML layout.  
```xml
	<de.codecrafters.tableview.TableView
		xmlns:table="http://schemas.android.com/apk/res-auto"
        android:id="@+id/tableView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        table:tableView_columnCount="4" />
```
A second possibility to define the column count of your TableView is to set it directly in the code.
```java
	TableView tableView = (TableView) findViewById(R.id.tableView);
	tableView.setColumnCount(4);
```

#### Column Width
To define the column widths you can set a `TableColumnModel` that defines the width for each column. You can use a
predefined `TableColumnModel` or implement your custom one.

**TableColumnWeightModel**  
This model defines the column widths in a relative manner. You can define a weight for each column index.
The default column weight is 1.
```java
TableColumnWeightModel columnModel = new TableColumnWeightModel(4);
columnModel.setColumnWeight(1, 2);
columnModel.setColumnWeight(2, 2);
tableView.setColumnModel(columnModel);
```

**TableColumnDpWidthModel**  
This model defines the column widths in a absolute manner. You can define a width in density-independent pixels for each column index.
The default column width is 100dp. You can pass a different default to the constructor.
```java
TableColumnDpWidthModel columnModel = new TableColumnDpWidthModel(context, 4, 200);
columnModel.setColumnWidth(1, 300);
columnModel.setColumnWidth(2, 250);
tableView.setColumnModel(columnModel);
```

**TableColumnPxWidthModel**  
This model defines the column widths in a absolute manner. You can define a width in pixels for each column index.
The default column width is 200px. You can pass a different default to the constructor.
```java
TableColumnPxWidthModel columnModel = new TableColumnPxWidthModel(4, 350);
columnModel.setColumnWidth(1, 500);
columnModel.setColumnWidth(2, 600);
tableView.setColumnModel(columnModel);
```

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
To listen for clicks on data items you can register a `TableDataClickListener`. The`TableView` provides a method called `addDataClickListener()` to register this listeners.
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

#### Long Data Click Listening
To listen for clicks on data items you can register a `TableDataLongClickListener`. The`TableView` provides a method called `addDataLongClickListener()` to register this columns.
```java
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_main);
        // ...
        tableView.addDataLongClickListener(new CarLongClickListener());
    }

	private class CarLongClickListener implements TableDataLongClickListener<Car> {
        @Override
        public boolean onDataLongClicked(int rowIndex, Car clickedCar) {
            String clickedCarString = clickedCar.getProducer().getName() + " " + clickedCar.getName();
            Toast.makeText(getContext(), clickedCarString, Toast.LENGTH_SHORT).show();
            return true;
        }
    }
```
The main difference to the `TableDataClickListener#onDataClicked()` method is, that the `onDataLongClicked()` method has a boolean as return value. This boolean indicates, if the `TableDataLongClickListener` has "consumed" the click event. If none of the registered `TableDataLongClickListeners` has consumed the click event, the `TableDataClickListeners` are informed in addition.

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
The table view provides several possibilities to style its header. One possibility is to set a **color** for the header. Therefore you can adapt the XML file or add it to your code.
```xml
    <de.codecrafters.tableview.TableView
		xmlns:table="http://schemas.android.com/apk/res-auto"
        android:id="@+id/tableView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        table:tableView_headerColor="@color/primary" />
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
		xmlns:table="http://schemas.android.com/apk/res-auto"
        android:id="@+id/tableView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        table:tableView_headerElevation="10" />
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
        sortableTableView.setHeaderSortStateViewProvider(new MySortStateViewProvider());
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
In general you can do all your styling of data content in your custom `TableDataAdapter`. But if you want to add a background for the whole table rows you can use the `TableDataRowBackgroundProvider`. There are already some implementations of the `TableDataRowBackgroundProvider` existing in the library. You can get them by using the Factory class `TableDataRowBackgroundProviders`.  
This Factory contains for example an alternating-table-data-row-row provider that will color rows with even index different from rows with odd index.
```java
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_main);
        // ...
    	int colorEvenRows = getResources().getColor(R.color.white);
    	int colorOddRows = getResources().getColor(R.color.gray);
    	tableView.setDataRowBackgroundProvider(TableDataRowBackgroundProviders.alternatingRowColors(colorEvenRows, colorOddRows));
    }
```
If the implementations of `TableDataRowBackgroundProvider` contained in the `TableDataRowBackgroundProviders` factory don't fulfil you needs you can create your own implementation of `TableDataRowBackgroundProvider`. Here is a small example of how to do so.
```java
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_main);
        // ...
        tableView.setDataRowBackgroundProvider(new CarPriceRowColorProvider());
    }
    
    private static class CarPriceRowColorProvider implements TableDataRowBackgroundProviders<Car> {
        @Override
        public Drawable getRowBackground(final int rowIndex, final Car car) {
            int rowColor = getResources(R.color.white);
            
            if(car.getPrice() < 50000) {
                rowColor = getResources(R.color.light_green);
            } else if(car.getPrice() > 100000) {
                rowColor = getResources(R.color.light_red);
            }
                
            return new ColorDrawable(rowColor);
        }
    }
```
This background provider will set the background color of each row corresponding to the price of the car that is displayed at in this row. Cheap cars (less then 50,000) get a green background, expensive cars (more then 100,000) get a red background and all other cars get a white background.
  
#### Seperator Styling  
If you want to have a seperator between the data rows you can do so by specifying it in the XML like known from the `ListView`.
```java
    <de.codecrafters.tableview.TableView
        android:id="@+id/tableView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:divider="@color/black"
        android:dividerHeight="1dip"
        ...  /> 
```
As for the `ListView` you can specify `divider` as a drawable and `dividerHeight` as the vertical size of the divider.  
  
### Swipe to Refresh
The TableView has a build in SwipeToRefresh action. By default this is disabled, but you can easily enable it using the follwing line.
```java
    tableView.setSwipeToRefreshEnabled( true );
```
This enables the user to trigger the table refresh on a single swipe. To listen for this user interaction you have to set an `SwipeToRefreshListener` to your tableview.
```java
    carTableView.setSwipeToRefreshListener(new SwipeToRefreshListener() {
        @Override
        public void onRefresh(final RefreshIndicator refreshIndicator) {
            // your async refresh action goes here
        }
    });
```
The callback method has the `RefreshIndicator` that is shown to the user passed as parameter. So if you finished your refresh action simply call `RefreshIndicator.hide()`.
  
### State Persistence
The TableView as well as the SortableTableView will persist its state automatically (e.g. on orientation change). If you want to disable this behaviour you can do so using the following code snipped.
```java
    tableView.setSaveEnabled( false );
```  
  
## License
*Copyright 2015 Ingo Schwarz*  
  
Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

```
   http://www.apache.org/licenses/LICENSE-2.0
```

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
