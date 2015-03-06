package by.vduzh.ysura.garage.model;

import java.util.Calendar;
import java.util.Date;

/**
 * This class represents a parking level.
 * <p/>
 * User: Viktar Duzh
 */
public class ParkingLevel {
    // reference to the garage object
    private ParkingGarage garage;

    // the number of parking level
    private int id;

    // the collection of parking spaces on the level
    private ParkingSpace[] parkingSpaces;

    /**
     * Creates a level with the number of parking spaces specified.
     *
     * @param garage            the reference to the garage
     * @param id                the number of the level
     * @param carCapacity       the number of parking spaces for cars
     * @param motorbikeCapacity the number of parking spaces for motorbikes
     */
    public ParkingLevel(ParkingGarage garage, int id, int carCapacity, int motorbikeCapacity) {
        this.garage = garage;
        this.id = id;

        // Create parking lots;
        parkingSpaces = new ParkingSpace[carCapacity + motorbikeCapacity];

        int j = 0;

        // For cars first
        for (int i = 0; i < carCapacity; i++, j++) {
            parkingSpaces[j] = new ParkingSpace(this, j + 1, VehicleType.CAR);
        }

        // and motorbikes then
        for (int i = 0; i < motorbikeCapacity; i++, j++) {
            parkingSpaces[j] = new ParkingSpace(this, j + 1, VehicleType.MOTORBIKE);
        }
    }

    /**
     * Assigns the parking space for the vehicle if any.
     *
     * @param vehicle a vehicle to park
     * @return parking ticket or null if there is no space available
     */
    public String assignParkingPlace(Vehicle vehicle) {
        for (ParkingSpace parkingSpace : parkingSpaces) {
            if (parkingSpace.getType() == vehicle.getType() && !parkingSpace.isOccupied()) {
                // get monitor of the parking space
                synchronized (parkingSpace) {
                    // and check that the space is still not occupied
                    if (!parkingSpace.isOccupied()) {
                        // it is free so we use it!!!
                        parkingSpace.setVehicle(vehicle);
                        parkingSpace.setParkingDate(Calendar.getInstance().getTime());
                        parkingSpace.setOccupied(true);

                        // return parking space
                        return parkingSpace.getTicket().toString();
                    }
                }
            }
        }

        // there is no free parking space available
        return null;
    }

    /**
     * Frees the parking lot with the ticket specified.
     *
     * @param ticket parking ticket
     * @return vehicle if any otherwise null is returned
     */
    public Vehicle leave(ParkingTicket ticket) {
        // Get the space to leave
        ParkingSpace space = getParkingSpaces()[ticket.getSpace() - 1];

        // check to see that the parking is expired
        if (space.isOccupied()) {
            synchronized (space) {
                // check again more precisely
                if (space.isOccupied() && ticket.toString().equals(space.getTicket().toString())) {
                    Vehicle vehicle = space.getVehicle();

                    space.setOccupied(false);
                    space.setVehicle(null);
                    space.setParkingDate(null);

                    return vehicle;
                }
            }
        }
        return null;
    }

    /**
     * Returns the number of parking level
     *
     * @return the number of parking level
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the collection of parking spaces on the level
     *
     * @return the parking spaces
     */
    public ParkingSpace[] getParkingSpaces() {
        return parkingSpaces;
    }
}
