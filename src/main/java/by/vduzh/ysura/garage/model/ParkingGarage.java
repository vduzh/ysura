package by.vduzh.ysura.garage.model;

import by.vduzh.ysura.garage.util.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * User: Viktar Duzh
 * Created: 28.02.15 5:01
 */
public class ParkingGarage {
    // logger
    private static Logger log = Logger.getLogger(ParkingGarage.class.getName());

    // the reference to the configuration object
    private Configuration config;

    // the collection of parking levels
    private ParkingLevel[] levels;

    // keeps the all the cars in the garage to quick access by license plate
    private Hashtable<String, String> carsInGarage = new Hashtable<String, String>();

    // keeps the number of cars in the garage
    private Counter counter = new Counter(0, 0);

    /**
     * Create an instance of garage with the configuration specified.
     *
     * @param configuration the garage configuration
     */
    public ParkingGarage(Configuration configuration) {
        this.config = configuration;

        // Create parking levels
        levels = new ParkingLevel[config.getNumberOfLevels()];
        for (int i = 0; i < config.getNumberOfLevels(); i++) {
            int carCapacity = config.getLevelCapacities()[i].getCarValue();
            int motorbikeCapacity = config.getLevelCapacities()[i].getMotorbikeValue();
            levels[i] = new ParkingLevel(this, i + 1, carCapacity, motorbikeCapacity);
        }
    }

    /**
     * Assigns a free space or reject the vehicle if there are no more free parking lots.
     *
     * @param vehicle the vehicle to park
     * @return a free space or null if there are no more free parking lots
     */
    public String enter(Vehicle vehicle) {
        log.log(Level.INFO, "Garage: Trying to park vehicle {0}.", vehicle);

        // check to see that the garage is not full.
        if ((vehicle.getType() == VehicleType.CAR && counter.getNumberOfCars() <= getConfig().getMaxNumberOfCars())
                || (vehicle.getType() == VehicleType.MOTORBIKE && counter.getNumberOfMotorbikes() <= getConfig().getMaxNumberOfMotorbikes())) {

            // if so, find the parking place
            ArrayList<Integer> ids  = Util.randIndexed(levels.length);
            for (int i = 0; i < ids.size(); i++) {
                String parkingTicket = levels[ids.get(i)].assignParkingPlace(vehicle);
                if (parkingTicket == null) {
                    continue;
                }

                // save the info about parking space in set
                carsInGarage.put(vehicle.getId(), parkingTicket);

                // and increase the number cars in the garage
                counter.inc(vehicle.getType());

                return parkingTicket;
            }
        }

        log.log(Level.INFO, "Garage: There is no place found for {0}", vehicle);
        return null;
    }

    /**
     * Removes the vehicle from the garage and frees its parking lot.
     *
     * @param text the parking ticket for vehicle as String
     * @return the vehicle that left the garage
     */
    public Vehicle exit(String text) {
        log.log(Level.INFO, "Garage: Trying to leave the garage with ticket: {0}.", text);

        // build ticket
        ParkingTicket ticket = ParkingTicket.valueOf(text);

        // get parking space
        ParkingLevel level = levels[ticket.getLevel() - 1];

        // And leave the space
        Vehicle vehicle = level.leave(ticket);
        if (vehicle != null) {
            // delete the info about parking space in set
            carsInGarage.remove(vehicle.getId());

            // and decrement the number cars in the garage
            counter.dec(vehicle.getType());

            log.log(Level.INFO, "Garage: The vehicle {0} with ticket {1} has left the garage .",
                    new Object[]{vehicle, text});
        }
        return vehicle;
    }

    /**
     * Returns the location of a specific vehicle.
     *
     * @param id - the licence plate
     * @return the the assigned parking lot
     */
    public String getLocation(String id) {
        return carsInGarage.get(id);
    }

    /**
     * Returns the number of free parking lots
     *
     * @return the number as integer.
     */
    public int getFreeLotsCount() {
        return getConfig().getGarageCapacity() - counter.getTotalVehicles();
    }

    /**
     * Returns the configuration of the garage.
     *
     * @return the configuration
     */
    public Configuration getConfig() {
        return config;
    }

    /**
     * Return the parking levels of the garage
     *
     * @return the parking levels
     */
    public ParkingLevel[] getLevels() {
        return levels;
    }

    /**
     * Return the vehicle counter.
     *
     * @return the Counter object
     */
    public Counter getCounter() {
        return counter;
    }
}
