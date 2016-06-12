package de.codecrafters.tableviewexample.data;


/**
 * Data object representing a car producer.
 *
 * @author ISchwarz
 */
public class CarProducer {

    private final String name;
    private final int logoRes;

    public CarProducer(final int logoRes, final String name) {
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
