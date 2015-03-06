package by.vduzh.ysura.garage.model;

/**
 * Composite counter for both cars and motorbikes.
 * <p/>
 * User: Viktar Duzh
 */
public class Counter {
    // keeps the number of cars
    private int numberOfCars;

    // keeps the number of motorbikes
    private int numberOfMotorbikes;

    // keeps the total value
    private int totalVehicles;

    /**
     * Creates an instance of the counter with the initial values specified.
     *
     * @param numberOfCars       the number of cars
     * @param numberOfMotorbikes the number of motorbikes
     */
    public Counter(int numberOfCars, int numberOfMotorbikes) {
        this.numberOfCars = numberOfCars;
        this.numberOfMotorbikes = numberOfMotorbikes;
    }

    /**
     * Increments the number of vehicles of the specified type and total amount.
     *
     * @param type the type of vehicle
     */
    public synchronized void inc(VehicleType type) {
        if (type == VehicleType.CAR) {
            numberOfCars++;
        } else {
            numberOfMotorbikes++;
        }
        totalVehicles++;

    }

    /**
     * Decrements the number of vehicles of the specified type and total amount.
     *
     * @param type the type of vehicle
     */
    public synchronized void dec(VehicleType type) {
        if (type == VehicleType.CAR) {
            numberOfCars--;
        } else {
            numberOfMotorbikes--;
        }
        totalVehicles--;
    }

    /**
     * Returns the number of cars
     *
     * @return the number of cars
     */
    public int getNumberOfCars() {
        return numberOfCars;
    }

    /**
     * Returns the number of motorbikes
     *
     * @return the number of motorbikes
     */
    public int getNumberOfMotorbikes() {
        return numberOfMotorbikes;
    }

    /**
     * Returns the number of vehicles of both types
     *
     * @return the total amount
     */
    public int getTotalVehicles() {
        return totalVehicles;
    }
}
