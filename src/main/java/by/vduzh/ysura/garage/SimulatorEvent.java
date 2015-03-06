package by.vduzh.ysura.garage;

/**
 * Event to inform a listener about processes within the simulator
 * <p/>
 * User: Viktar Duzh
 */
public class SimulatorEvent {
    // defines a couple of constants
    public enum Command {
        Enter,
        Exit;
    }

    // command
    private Command command;

    // gate/point
    private int point;

    // level of the garage
    private int level;

    // parking lot
    private int space;

    public SimulatorEvent(Command command, int point, int level, int space) {
        this.command = command;
        this.point = point;
        this.level = level;
        this.space = space;
    }

    public Command getCommand() {
        return command;
    }

    public int getPoint() {
        return point;
    }

    public int getLevel() {
        return level;
    }

    public int getSpace() {
        return space;
    }

    @Override
    public String toString() {
        return new StringBuilder("[command=").append(command)
                .append(", point").append(point)
                .append(", level=").append(level)
                .append(", space=").append(space)
                .append("]").toString();
    }
}
