package by.vduzh.ysura.garage.model;

import java.util.Date;

/**
 * This class represents the parking lot.
 * <p/>
 * User: Viktar Duzh
 */
public class ParkingSpace {
    // the reference to the parking level
    private ParkingLevel level;

    // the number of parking lot
    private final int id;

    // the type of vehicle in the lot
    private final VehicleType type;

    // indicate whether the lot is occupied
    private boolean occupied = false;

    // the reference to the vehicle in the lot
    private Vehicle vehicle;

    // the parking date
    private Date parkingDate;

    /**
     * Creates an instance of the parking lot.
     *
     * @param level reference to the parking level
     * @param id    the number of parking lot
     * @param type  the type of vehicle in the lot
     */
    public ParkingSpace(ParkingLevel level, int id, VehicleType type) {
        this.id = id;
        this.level = level;
        this.type = type;
    }

    /**
     * Returns the number of parking lot
     *
     * @return the number of parking lot
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the parking level
     *
     * @return the parking level
     */
    public ParkingLevel getLevel() {
        return level;
    }

    /**
     * Returns the type of vehicle in the lot
     *
     * @return the type of vehicle in the lot
     */
    public VehicleType getType() {
        return type;
    }

    /**
     * indicate whether the parking lot is occupied.
     *
     * @return true if the parking lot is occupied, otherwise false
     */
    public boolean isOccupied() {
        return occupied;
    }

    /**
     * Sets true to occupe the parking lot.
     *
     * @param occupied boolean value
     */
    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }


    /**
     * Returns the vehicle in the lot
     *
     * @return the vehicle in the lot
     */
    public Vehicle getVehicle() {
        return vehicle;
    }

    /**
     * Sets the vehicle.
     *
     * @param vehicle the vehicle object
     */
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    /**
     * Returns the parking date
     *
     * @return the parking date
     */
    public Date getParkingDate() {
        return parkingDate;
    }

    /**
     * Sets the parking date.
     *
     * @param parkingDate the parking date
     */
    public void setParkingDate(Date parkingDate) {
        this.parkingDate = parkingDate;
    }

    /**
     * Returns the parking ticket.
     *
     * @return ParkingTicket object if the space is occupied,otherwise false
     */
    public ParkingTicket getTicket() {
        return isOccupied() ? new ParkingTicket(level.getId(), this.id, parkingDate) : null;
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return new StringBuilder("[level=").append(level.getId())
                .append(", id=").append(id)
                .append(", type=").append(type)
                .append(", occupied=").append(occupied)
                .append("]").toString();
    }
}
