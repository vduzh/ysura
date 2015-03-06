package by.vduzh.ysura.garage;

import by.vduzh.ysura.garage.model.ParkingGarage;
import by.vduzh.ysura.garage.model.ParkingTicket;
import by.vduzh.ysura.garage.model.Vehicle;
import by.vduzh.ysura.garage.model.VehicleType;
import by.vduzh.ysura.garage.util.Util;

import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class simulates an entrance into garage.
 * <p/>
 * User: Viktar Duzh
 */
public class EnterThread extends Thread {
    // logger
    private static Logger log = Logger.getLogger(EnterThread.class.getName());

    // reference to the simulator object
    private GarageSimulator simulator;

    // number of the entrance
    private int entranceId;

    // reference the garage object
    private ParkingGarage garage;

    // keeps vehicles to enter into the garage
    private Queue<Vehicle> vehicles;

    /**
     * Creates an instance of the thread. Also it dynamically creates the list of vehicles to enter into the garage
     * based on the Configuration object.
     *
     * @param simulator  - garage simulator
     * @param entranceId - number of the entrance
     * @param garage     - reference the garage object
     */
    public EnterThread(GarageSimulator simulator, int entranceId, ParkingGarage garage) {
        this.simulator = simulator;
        this.entranceId = entranceId;
        this.garage = garage;
        this.vehicles = new LinkedList<Vehicle>();

        // just use it to build a license plate
        int j = 1;

        // Add cars first
        int numberOfCarsPerEntrance = garage.getConfig().getVehiclesPerEntrance()[entranceId - 1].getCarValue();
        for (int i = 1; i <= numberOfCarsPerEntrance; i++, j++) {
            vehicles.add(new Vehicle(entranceId + "-" + j, VehicleType.CAR));
        }

        // and motorbikes then
        int numberOfMotorbikesPerEntrance = garage.getConfig().getVehiclesPerEntrance()[entranceId - 1].getMotorbikeValue();
        for (int i = 1; i <= numberOfMotorbikesPerEntrance; i++, j++) {
            vehicles.add(new Vehicle(entranceId + "-" + j, VehicleType.MOTORBIKE));
        }

        log.log(Level.INFO, "Thread {0} created: Entrance #{1}, with {2} cars and {3} motorbikes.", new Object[]{
                getId(), entranceId, numberOfCarsPerEntrance, numberOfMotorbikesPerEntrance});
    }

    /**
     * Return the number of the entrance.
     *
     * @return the number of the entrance
     */
    public int getEntranceId() {
        return entranceId;
    }

    @Override
    public void run() {
        Vehicle vehicle = vehicles.poll();
        while (vehicle != null) {
            log.log(Level.INFO, "Entrance {0}: Vehicle {1} is asking for parking space.",
                    new Object[]{getEntranceId(), vehicle});
            do {
                String parkingTicket = garage.enter(vehicle);
                if (parkingTicket != null) {
                    // the car has been parked
                    log.log(Level.INFO, "Entrance {0}: Parking space [{1}] is allocated for vehicle {2}.",
                            new Object[]{getEntranceId(), parkingTicket, vehicle});

                    // Notify listener
                    if (simulator.getListener() != null) {
                        ParkingTicket ticket = ParkingTicket.valueOf(parkingTicket);

                        simulator.getListener().handleEvent(new SimulatorEvent(SimulatorEvent.Command.Enter,
                                getEntranceId(), ticket.getLevel(), ticket.getSpace()));
                    }

                    // sleep for while
                    try {
                        // sleep and try to find again in 2 sec
                        Thread.sleep(Util.randInt(500, 1000));
                    } catch (InterruptedException e) {
                        log.log(Level.SEVERE, e.toString());
                        return;
                    }

                    // get next vehicle in the list
                    vehicle = vehicles.poll();
                    break;
                }

                // there is no space available now
                try {
                    // sleep and try to find again in 2 sec
                    log.log(Level.INFO, "Entrance {0}: : There is no more free parking space.", getEntranceId());
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    log.log(Level.SEVERE, e.toString());
                    return;
                }
            } while (true);
        }
        log.log(Level.INFO, "Entrance {0}: : All the vehicles have been parked.", getEntranceId());
    }
}
