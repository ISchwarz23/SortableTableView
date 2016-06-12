package de.codecrafters.tableviewexample.data;

import java.util.ArrayList;
import java.util.List;

import de.codecrafters.tableviewexample.R;

/**
 * A factory that provides data for demonstration porpuses.
 *
 * @author ISchwarz
 */
public final class DataFactory {

    /**
     * Creates a list of cars.
     *
     * @return The created list of cars.
     */
    public static List<Car> createCarList() {
        final CarProducer audi = new CarProducer(R.mipmap.audi, "Audi");
        final Car audiA1 = new Car(audi, "A1", 150, 25000);
        final Car audiA3 = new Car(audi, "A3", 120, 35000);
        final Car audiA4 = new Car(audi, "A4", 210, 42000);
        final Car audiA5 = new Car(audi, "S5", 333, 60000);
        final Car audiA6 = new Car(audi, "A6", 250, 55000);
        final Car audiA7 = new Car(audi, "A7", 420, 87000);
        final Car audiA8 = new Car(audi, "A8", 320, 110000);

        final CarProducer bmw = new CarProducer(R.mipmap.bmw, "BMW");
        final Car bmw1 = new Car(bmw, "1er", 170, 25000);
        final Car bmw3 = new Car(bmw, "3er", 230, 42000);
        final Car bmwX3 = new Car(bmw, "X3", 230, 45000);
        final Car bmw4 = new Car(bmw, "4er", 250, 39000);
        final Car bmwM4 = new Car(bmw, "M4", 350, 60000);
        final Car bmw5 = new Car(bmw, "5er", 230, 46000);

        final CarProducer porsche = new CarProducer(R.mipmap.porsche, "Porsche");
        final Car porsche911 = new Car(porsche, "911", 280, 45000);
        final Car porscheCayman = new Car(porsche, "Cayman", 330, 52000);
        final Car porscheCaymanGT4 = new Car(porsche, "Cayman GT4", 385, 86000);

        final List<Car> cars = new ArrayList<>();
        cars.add(audiA3);
        cars.add(audiA1);
        cars.add(porscheCayman);
        cars.add(audiA7);
        cars.add(audiA8);
        cars.add(audiA4);
        cars.add(bmwX3);
        cars.add(porsche911);
        cars.add(bmw1);
        cars.add(audiA6);
        cars.add(audiA5);
        cars.add(bmwM4);
        cars.add(bmw5);
        cars.add(porscheCaymanGT4);
        cars.add(bmw3);
        cars.add(bmw4);

        return cars;
    }

}
