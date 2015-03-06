package by.vduzh.ysura.garage.model;

/**
 * The object of this class holds the configuration data.
 * <p/>
 * User: Viktar Duzh
 */
public class ConfigurationEntry {
    // the value for a car
    private int carValue;

    // the value for a motorbike
    private int motorbikeValue;

    /**
     * Creates ans instance of the class.
     *
     * @param carValue       the value for a car
     * @param motorbikeValue the value for a motorbike
     */
    public ConfigurationEntry(int carValue, int motorbikeValue) {
        this.carValue = carValue;
        this.motorbikeValue = motorbikeValue;
    }

    /**
     * Returns the value for a car.
     *
     * @return car value
     */
    public int getCarValue() {
        return carValue;
    }

    /**
     * Returns the value for a motorbike.
     *
     * @return motorbike value
     */
    public int getMotorbikeValue() {
        return motorbikeValue;
    }
}
