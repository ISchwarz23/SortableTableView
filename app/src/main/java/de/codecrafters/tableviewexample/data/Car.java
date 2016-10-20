package de.codecrafters.tableviewexample.data;


/**
 * Data object representing a car.
 *
 * @author ISchwarz
 */
public class Car implements Chargable {

    private final CarProducer producer;
    private final int ps;
    private final double price;
    private String name;

    public Car(final CarProducer producer, final String name, final int ps, final double price) {
        this.producer = producer;
        this.name = name;
        this.ps = ps;
        this.price = price;
    }

    public CarProducer getProducer() {
        return producer;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getPs() {
        return ps;
    }

    public int getKw() {
        return (int) (ps / 1.36);
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return producer.getName() + " " + name;
    }
}
