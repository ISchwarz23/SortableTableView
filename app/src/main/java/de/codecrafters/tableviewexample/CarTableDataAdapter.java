package de.codecrafters.tableviewexample;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.LongPressAwareTableDataAdapter;
import de.codecrafters.tableviewexample.data.Car;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import static java.lang.String.format;


public class CarTableDataAdapter extends LongPressAwareTableDataAdapter<Car> {

    private static final int TEXT_SIZE = 14;
    private static final NumberFormat PRICE_FORMATTER = NumberFormat.getNumberInstance();


    public CarTableDataAdapter(final Context context, final List<Car> data, final TableView<Car> tableView) {
        super(context, data, tableView);
    }

    @Override
    public View getDefaultCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
        final Car car = getRowData(rowIndex);
        View renderedView = null;

        switch (columnIndex) {
            case 0:
                renderedView = renderProducerLogo(car, parentView);
                break;
            case 1:
                renderedView = renderCatName(car);
                break;
            case 2:
                renderedView = renderPower(car, parentView);
                break;
            case 3:
                renderedView = renderPrice(car);
                break;
        }

        return renderedView;
    }

    @Override
    public View getLongPressCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
        final Car car = getRowData(rowIndex);
        View renderedView = null;

        switch (columnIndex) {
            case 0:
                renderedView = renderProducerLogo(car, parentView);
                break;
            case 1:
                renderedView = renderEditableCatName(car);
                break;
            case 2:
                renderedView = renderPower(car, parentView);
                break;
            case 3:
                renderedView = renderPrice(car);
                break;
        }

        return renderedView;
    }

    private View renderEditableCatName(final Car car) {
        final EditText editText = new EditText(getContext());
        editText.setText(car.getName());
        editText.setPadding(20, 10, 20, 10);
        editText.setTextSize(TEXT_SIZE);
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("TextWatcher", "Before");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("TextWatcher", "OnChange");
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("TextWatcher", "After");
                car.setName(s.toString());
            }
        });
        return editText;
    }

    private View renderPrice(final Car car) {
        final String priceString = PRICE_FORMATTER.format(car.getPrice()) + " €";

        final TextView textView = new TextView(getContext());
        textView.setText(priceString);
        textView.setPadding(20, 10, 20, 10);
        textView.setTextSize(TEXT_SIZE);

        if (car.getPrice() < 50000) {
            textView.setTextColor(0xFF2E7D32);
        } else if (car.getPrice() > 100000) {
            textView.setTextColor(0xFFC62828);
        }

        return textView;
    }

    private View renderPower(final Car car, final ViewGroup parentView) {
        final View view = getLayoutInflater().inflate(R.layout.table_cell_power, parentView, false);
        final TextView kwView = (TextView) view.findViewById(R.id.kw_view);
        final TextView psView = (TextView) view.findViewById(R.id.ps_view);

        kwView.setText(format(Locale.ENGLISH, "%d %s", car.getKw(), getContext().getString(R.string.kw)));
        psView.setText(format(Locale.ENGLISH, "%d %s", car.getPs(), getContext().getString(R.string.ps)));

        return view;
    }

    private View renderCatName(final Car car) {
        return renderString(car.getName());
    }

    private View renderProducerLogo(final Car car, final ViewGroup parentView) {
        final View view = getLayoutInflater().inflate(R.layout.table_cell_image, parentView, false);
        final ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        imageView.setImageResource(car.getProducer().getLogo());
        return view;
    }

    private View renderString(final String value) {
        final TextView textView = new TextView(getContext());
        textView.setText(value);
        textView.setPadding(20, 10, 20, 10);
        textView.setTextSize(TEXT_SIZE);
        return textView;
    }

}
