package de.codecrafters.tableviewexample;


/**
 * Created by Ingo on 18.07.2015.
 */
public class CarProducer {

    private final String name;
    private int logoRes;

    public CarProducer(int logoRes, String name) {
        this.logoRes = logoRes;
        this.name = name;
    }

    public int getLogo() {
        return logoRes;
    }

    public String getName() {
        return name;
    }
}
