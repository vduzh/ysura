package by.vduzh.ysura.garage;

import by.vduzh.ysura.garage.model.*;
import by.vduzh.ysura.garage.util.Util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class simulates an exit from the garage.
 * <p/>
 * User: Viktar Duzh
 */
public class ExitThread extends Thread {
    // logger
    private static Logger log = Logger.getLogger(ExitThread.class.getName());

    // reference to the simulator object
    private GarageSimulator simulator;

    // number of the exit
    private int exitId;

    // reference the garage object
    private ParkingGarage garage;

    /**
     * Creates an instance of the thread.
     *
     * @param simulator - garage simulator
     * @param exitId    - number of the exit
     * @param garage    - reference the garage object
     */

    public ExitThread(GarageSimulator simulator, int exitId, ParkingGarage garage) {
        this.simulator = simulator;
        this.exitId = exitId;
        this.garage = garage;

        log.log(Level.INFO, "Thread {0} created: Exit #{1}.", new Object[]{getId(), exitId});
    }

    /**
     * Return the number of the exit.
     *
     * @return the number of the exit
     */
    public int getExitId() {
        return exitId;
    }

    @Override
    public void run() {
        while (true) {
            ArrayList<Integer> ids = Util.randIndexed(garage.getConfig().getNumberOfLevels());
            for (int i = 0; i < ids.size(); i++) {
                ParkingLevel level = garage.getLevels()[i];

                ParkingSpace[] parkingSpaces = level.getParkingSpaces();
                for (int j = 0; j < parkingSpaces.length; j++) {
                    ParkingSpace space = parkingSpaces[j];

                    // check the parking date first
                    Date parkingDate = space.getParkingDate();
                    if (space.isOccupied() && parkingDate != null && isParkingExpired(parkingDate)) {
                        String ticketStr = null;
                        synchronized (space) {
                            // check again more precisely
                            if (space.isOccupied() && isParkingExpired(space.getParkingDate())) {
                                ParkingTicket ticket = space.getTicket();
                                ticketStr = ticket.toString();
                            }
                        }

                        if (ticketStr != null) {
                            log.log(Level.INFO, "Exit {0}: Selected vehicle with ticket [{1}] to leave the parking place.",
                                    new Object[]{getExitId(), ticketStr});
                            Vehicle vehicle = garage.exit(ticketStr);

                            if (vehicle != null) {
                                // Notify listener
                                if (simulator.getListener() != null) {
                                    ParkingTicket ticket = ParkingTicket.valueOf(ticketStr);

                                    simulator.getListener().handleEvent(new SimulatorEvent(SimulatorEvent.Command.Exit,
                                            getExitId(), ticket.getLevel(), ticket.getSpace()));

                                    // sleep for while
                                    try {
                                        // sleep and try to find again in 2 sec
                                        Thread.sleep(Util.randInt(500, 1200));
                                    } catch (InterruptedException e) {
                                        log.log(Level.SEVERE, e.toString());
                                        return;
                                    }
                                }
                            } else {
                                log.log(Level.INFO, "Exit {0}: Vehicle with ticket [{1}] has already left (Correct behaviour).",
                                        new Object[]{getExitId(), ticketStr});
                            }
                        }
                    }
                }
            }

            try {
                log.log(Level.INFO, "Cars if the garage: {0}.", garage.getCounter().getTotalVehicles());
                // sleep 5 sec and try to find again
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                log.log(Level.SEVERE, e.toString());
                return;
            }
        }
    }

    /**
     * Checks if the parking time expired
     *
     * @param parkingDate parking date
     * @return true if time is up, otherwise false.
     */
    public boolean isParkingExpired(Date parkingDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parkingDate);

        calendar.add(Calendar.SECOND, Util.randInt(2, garage.getConfig().getExitAfterSec()));
        return calendar.getTime().before(Calendar.getInstance().getTime());
    }

    public static void main(String[] args) {

    }


}
