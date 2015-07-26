package de.codecrafters.tableviewexample;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;

import de.codecrafters.tableview.TableDataAdapter;
import de.codecrafters.tableviewexample.data.Car;


public class CarTableDataAdapter extends TableDataAdapter<Car> {

    private static final int TEXT_SIZE = 14;
    private static final NumberFormat PRICE_FORMATTER = NumberFormat.getNumberInstance();


    public CarTableDataAdapter(Context context, List<Car> data) {
        super(context, data);
    }

    @Override
    public View getCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
        Car car = getRowData(rowIndex);
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

    private View renderPrice(Car car) {
        String priceString = PRICE_FORMATTER.format(car.getPrice()) + " €";

        TextView textView = new TextView(getContext());
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

    private View renderPower(Car car, ViewGroup parentView) {
        View view = getLayoutInflater().inflate(R.layout.table_cell_power, parentView, false);
        TextView kwView = (TextView) view.findViewById(R.id.kw_view);
        TextView psView = (TextView) view.findViewById(R.id.ps_view);

        kwView.setText(car.getKw() + " kW");
        psView.setText(car.getPs() + " PS");

        return view;
    }

    private View renderCatName(Car car) {
        return renderString(car.getName());
    }

    private View renderProducerLogo(Car car, ViewGroup parentView) {
        View view = getLayoutInflater().inflate(R.layout.table_cell_image, parentView, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        imageView.setImageResource(car.getProducer().getLogo());
        return view;
    }

    private View renderString(String value) {
        TextView textView = new TextView(getContext());
        textView.setText(value);
        textView.setPadding(20, 10, 20, 10);
        textView.setTextSize(TEXT_SIZE);
        return textView;
    }

}
