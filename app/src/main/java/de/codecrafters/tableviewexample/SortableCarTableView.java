package de.codecrafters.tableviewexample;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import de.codecrafters.tableview.SortableTableView;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;
import de.codecrafters.tableview.toolkit.SortStateViewProviders;
import de.codecrafters.tableview.toolkit.TableDataRowColorizers;
import de.codecrafters.tableviewexample.data.Car;


/**
 * An extension of the {@link SortableTableView} that handles {@link Car}s.
 *
 * @author ISchwarz
 */
public class SortableCarTableView extends SortableTableView<Car> {

    public SortableCarTableView(final Context context) {
        this(context, null);
    }

    public SortableCarTableView(final Context context, final AttributeSet attributes) {
        this(context, attributes, android.R.attr.listViewStyle);
    }

    public SortableCarTableView(final Context context, final AttributeSet attributes, final int styleAttributes) {
        super(context, attributes, styleAttributes);

        final SimpleTableHeaderAdapter simpleTableHeaderAdapter = new SimpleTableHeaderAdapter(context, R.string.brand, R.string.model, R.string.power, R.string.price);
        simpleTableHeaderAdapter.setTextColor(ContextCompat.getColor(context, R.color.table_header_text));
        setHeaderAdapter(simpleTableHeaderAdapter);

        final int rowColorEven = ContextCompat.getColor(context, R.color.table_data_row_even);
        final int rowColorOdd = ContextCompat.getColor(context, R.color.table_data_row_odd);
        setDataRowColorizer(TableDataRowColorizers.alternatingRows(rowColorEven, rowColorOdd));
        setHeaderSortStateViewProvider(SortStateViewProviders.brightArrows());

        setColumnWeight(0, 2);
        setColumnWeight(1, 3);
        setColumnWeight(2, 3);
        setColumnWeight(3, 2);

        setColumnComparator(0, CarComparators.getCarProducerComparator());
        setColumnComparator(1, CarComparators.getCarNameComparator());
        setColumnComparator(2, CarComparators.getCarPowerComparator());
        setColumnComparator(3, CarComparators.getCarPriceComparator());
    }

}
