package de.codecrafters.sortabletableview.example;

/**
 * Created by Ingo on 18.07.2015.
 */
public class Car {

    private final CarProducer producer;
    private final String name;
    private final int ps;
    private final double price;

    public Car(CarProducer producer, String name, int ps, double price) {
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

    public int getPs() {
        return ps;
    }

    public double getPrice() {
        return price;
    }
}
