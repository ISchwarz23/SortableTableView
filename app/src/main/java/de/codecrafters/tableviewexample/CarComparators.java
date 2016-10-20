package de.codecrafters.tableviewexample;

import de.codecrafters.tableviewexample.data.Car;

import java.util.Comparator;


/**
 * A collection of {@link Comparator}s for {@link Car} objects.
 *
 * @author ISchwarz
 */
public final class CarComparators {

    private CarComparators() {
        //no instance
    }

    public static Comparator<Car> getCarProducerComparator() {
        return new CarProducerComparator();
    }

    public static Comparator<Car> getCarPowerComparator() {
        return new CarPowerComparator();
    }

    public static Comparator<Car> getCarNameComparator() {
        return new CarNameComparator();
    }

    public static Comparator<Car> getCarPriceComparator() {
        return new CarPriceComparator();
    }


    private static class CarProducerComparator implements Comparator<Car> {

        @Override
        public int compare(final Car car1, final Car car2) {
            return car1.getProducer().getName().compareTo(car2.getProducer().getName());
        }
    }

    private static class CarPowerComparator implements Comparator<Car> {

        @Override
        public int compare(final Car car1, final Car car2) {
            return car1.getPs() - car2.getPs();
        }
    }

    private static class CarNameComparator implements Comparator<Car> {

        @Override
        public int compare(final Car car1, final Car car2) {
            return car1.getName().compareTo(car2.getName());
        }
    }

    private static class CarPriceComparator implements Comparator<Car> {

        @Override
        public int compare(final Car car1, final Car car2) {
            if (car1.getPrice() < car2.getPrice()) return -1;
            if (car1.getPrice() > car2.getPrice()) return 1;
            return 0;
        }
    }

}
