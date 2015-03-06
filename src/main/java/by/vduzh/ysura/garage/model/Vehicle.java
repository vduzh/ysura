package by.vduzh.ysura.garage.model;

/**
 * This class represents a vehicle.
 * <p/>
 * User: Viktar Duzh
 */
public class Vehicle {
    // the licence plate
    private final String id;

    // the vehicle type - car or motorbike
    private final VehicleType type;

    /**
     * Creates an instance of the vehicle with the licence plate and type.
     *
     * @param id   licence plate
     * @param type vehicle type
     */
    public Vehicle(String id, VehicleType type) {
        this.id = id;
        this.type = type;
    }

    /**
     * Returns the licence plate
     *
     * @return the licence plate
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the vehicle type
     *
     * @return the vehicle type
     */
    public VehicleType getType() {
        return type;
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return new StringBuilder("[id=").append(id).append(", type=").append(type).append("]").toString();
    }
}
