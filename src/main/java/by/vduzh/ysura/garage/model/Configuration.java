package by.vduzh.ysura.garage.model;

/**
 * The object of this class holds the configuration of the garage.
 * <p/>
 * User: Viktar Duzh
 */
public class Configuration {
    // the maximum number of vehicles in the garage
    private int garageCapacity;

    // the maximum number of cars in the garage
    private int maxNumberOfCars;

    // the maximum number of motorbikes in the garage
    private int maxNumberOfMotorbikes;

    // the number of levels in the garage
    private int numberOfLevels;

    // the maximum number of vehicles (cars and motorbikes) on each level in the garage
    private ConfigurationEntry[] levelCapacities;

    // the number of entrances in the garage
    private int numberOfEntrances;

    // the number of vehicles (cars and motorbikes) at each entrance in the garage
    private ConfigurationEntry[] vehiclesPerEntrance;

    // the number of exits in the garage
    private int numberOfExits;

    // the number of second after tht the vehicle is ready to leave the parking
    private int exitAfterSec;

    /**
     * Creates a configuration object with params specified.
     *
     * @param numberOfLevels      the number of levels in the garage
     * @param levelCapacities     the maximum number of vehicles (cars and motorbikes) on each level in the garage
     * @param numberOfEntrances   the number of entrances in the garage
     * @param vehiclesPerEntrance the number of vehicles (cars and motorbikes) at each entrance in the garage
     * @param numberOfExits       number of exits in the garage
     * @param exitAfterSec        number of second after tht the vehicle is ready to leave the parking
     */
    public Configuration(int numberOfLevels, ConfigurationEntry[] levelCapacities,
                         int numberOfEntrances, ConfigurationEntry[] vehiclesPerEntrance,
                         int numberOfExits, int exitAfterSec) {
        this.numberOfLevels = numberOfLevels;
        this.levelCapacities = levelCapacities;
        this.numberOfEntrances = numberOfEntrances;
        this.vehiclesPerEntrance = vehiclesPerEntrance;
        this.numberOfExits = numberOfExits;
        this.exitAfterSec = exitAfterSec;

        // calculate capacity
        for (int i = 0; i < numberOfLevels; i++) {
            int carValue = levelCapacities[i].getCarValue();
            int motorbikeValue = levelCapacities[i].getMotorbikeValue();

            garageCapacity += carValue + motorbikeValue;
            maxNumberOfCars += carValue;
            maxNumberOfMotorbikes += motorbikeValue;
        }
    }

    /**
     * This method is used to check the configuration data.
     *
     * @throws RuntimeException in case the configuration is not valid.
     */
    public void validate() {
        // check number of levels first
        if (numberOfLevels <= 0) {
            throw new RuntimeException("numberOfLevels must be positive");
        }

        // check that each level is configured properly
        if (levelCapacities.length != numberOfLevels) {
            throw new RuntimeException(numberOfLevels + " levels must be configure instead of " +
                    levelCapacities.length);
        }
        for (int i = 0; i < levelCapacities.length; i++) {
            if (levelCapacities[i].getCarValue() < 0 || levelCapacities[i].getMotorbikeValue() < 0) {
                throw new RuntimeException("Both number of cars and number of motorbikes must be positive");
            }
        }

        // at least one entrance must be
        if (numberOfEntrances <= 0) {
            throw new RuntimeException("numberOfEntrances must be positive");
        }

        // check that each entrance is configured properly
        if (vehiclesPerEntrance.length != numberOfEntrances) {
            throw new RuntimeException(numberOfEntrances + " entrance  must be configure instead of " +
                    vehiclesPerEntrance.length);
        }
        for (int i = 0; i < vehiclesPerEntrance.length; i++) {
            if (vehiclesPerEntrance[i].getCarValue() < 0 || vehiclesPerEntrance[i].getMotorbikeValue() < 0) {
                throw new RuntimeException("Both number of cars and number of motorbikes must be positive");
            }
        }

        // at least one entrance must be
        if (numberOfExits <= 0) {
            throw new RuntimeException("numberOfExits must be positive");
        }

        // just positive vale
        if (exitAfterSec <= 0) {
            throw new RuntimeException("exitAfterSec must be more that 3");
        }

    }


    /**
     * Returns the maximum number of vehicles in the garage.
     *
     * @return the garage capacity
     */
    public int getGarageCapacity() {
        return garageCapacity;
    }

    /**
     * Returns the maximum number of cars in the garage.
     *
     * @return the cars capacity
     */
    public int getMaxNumberOfCars() {
        return maxNumberOfCars;
    }

    /**
     * Returns the maximum number of motorbikes in the garage.
     *
     * @return the motorbikes capacity
     */
    public int getMaxNumberOfMotorbikes() {
        return maxNumberOfMotorbikes;
    }

    /**
     * Returns the number of levels in the garage.
     *
     * @return the number of levels
     */
    public int getNumberOfLevels() {
        return numberOfLevels;
    }

    /**
     * Returns the maximum number of vehicles (cars and motorbikes) on each level in the garage.
     *
     * @return the capacity of each level
     */
    public ConfigurationEntry[] getLevelCapacities() {
        return levelCapacities;
    }

    /**
     * Returns the number of entrances in the garage.
     *
     * @return the number of entrances
     */
    public int getNumberOfEntrances() {
        return numberOfEntrances;
    }

    /**
     * Returns the number of vehicles (cars and motorbikes) at each entrance in the garage
     *
     * @return the number of vehicles
     */
    public ConfigurationEntry[] getVehiclesPerEntrance() {
        return vehiclesPerEntrance;
    }

    /**
     * Returns the number of exits in the garage.
     *
     * @return the number of exits
     */
    public int getNumberOfExits() {
        return numberOfExits;
    }

    /**
     * Returns the umber of second after tht the vehicle is ready to leave the parking
     *
     * @return the number of seconds
     */
    public int getExitAfterSec() {
        return exitAfterSec;
    }
}
