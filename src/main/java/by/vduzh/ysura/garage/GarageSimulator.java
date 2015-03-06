package by.vduzh.ysura.garage;

import by.vduzh.ysura.garage.model.Configuration;
import by.vduzh.ysura.garage.model.ConfigurationEntry;
import by.vduzh.ysura.garage.model.ParkingGarage;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * This class simulates the work of a parking garage. It creates threads to enter and exit the garage.
 * <p/>
 * User: Viktar Duzh
 */
public class GarageSimulator {
    // logger
    private static Logger log = Logger.getLogger(GarageSimulator.class.getName());

    // keeps the reference to the garage object
    private ParkingGarage garage;

    // the simulator event listener
    private SimulatorListener listener;

    /**
     * Creates an instance of simulator
     */
    public GarageSimulator() {
        garage = new ParkingGarage(buildConfiguration());
    }

    /**
     * Returns the simulator event listener
     *
     * @param listener the simulator event listener if any, otherwise null
     */
    public void setListener(SimulatorListener listener) {
        this.listener = listener;
    }

    /**
     * Kicks of the process.
     */
    public void start() {
        // Create a thread for each entrance
        for (int i = 1; i <= garage.getConfig().getNumberOfEntrances(); i++) {
            new EnterThread(this, i, garage).start();
        }

        // Create a thread for each exit
        for (int i = 1; i <= garage.getConfig().getNumberOfExits(); i++) {
            new ExitThread(this, i, garage).start();
        }
    }

    /**
     * Load the garage configuration.
     * <p/>
     * Configuration is stored in application.properties file.
     *
     * @return Configuration object with params
     */
    private Configuration buildConfiguration() {
        Properties prop = new Properties();
        try {
            prop.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        int numberOfLevels = Integer.parseInt((String) prop.get("numberOfLevels"));
        ConfigurationEntry[] levelCapacities = new ConfigurationEntry[numberOfLevels];
        for (int i = 0; i < levelCapacities.length; i++) {
            ConfigurationEntry entry = new ConfigurationEntry(
                    Integer.parseInt((String) prop.get("levelCapacities." + (i + 1) + ".car")),
                    Integer.parseInt((String) prop.get("levelCapacities." + (i + 1) + ".motorbike"))
            );
            levelCapacities[i] = entry;
        }


        int numberOfEntrances = Integer.parseInt((String) prop.get("numberOfEntrances"));
        ConfigurationEntry[] vehiclesPerEntrance = new ConfigurationEntry[numberOfEntrances];
        for (int i = 0; i < vehiclesPerEntrance.length; i++) {
            ConfigurationEntry entry = new ConfigurationEntry(
                    Integer.parseInt((String) prop.get("numberOfEntrances." + (i + 1) + ".car")),
                    Integer.parseInt((String) prop.get("numberOfEntrances." + (i + 1) + ".motorbike"))
            );
            vehiclesPerEntrance[i] = entry;
        }

        int numberOfExits = Integer.parseInt((String) prop.get("numberOfExits"));

        int exitAfterSec = Integer.parseInt((String) prop.get("exitAfterSec"));

        Configuration config = new Configuration(numberOfLevels, levelCapacities, numberOfEntrances,
                vehiclesPerEntrance, numberOfExits, exitAfterSec);
        config.validate();
        return config;
    }

    /**
     * Return the reference to the garage
     *
     * @return the reference to the garage
     */
    public ParkingGarage getGarage() {
        return garage;
    }

    /**
     * Return the reference to the simulator event listener
     *
     * @return the reference to the simulator event listener
     */
    public SimulatorListener getListener() {
        return listener;
    }

    public static void main(String[] args) {
        GarageSimulator simulator = new GarageSimulator();
        simulator.start();
    }
}
