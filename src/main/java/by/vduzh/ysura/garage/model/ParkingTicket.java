package by.vduzh.ysura.garage.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * This class represents the parking ticket.
 * <p/>
 * User: Viktar Duzh
 */
public class ParkingTicket {
    // Just a set of constants
    private static final String LBL_LEVEL = "Level-";

    private static final String LBL_SPACE = "Space-";

    private static final String LBL_ISSUED = "Issued-";

    private static final String DELIMITER = ",";

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    // the level number
    private int level;
    // the parking lot number
    private int space;
    // the parking date
    private Date date;

    /**
     * Creates an instance of the class with the param specified.
     *
     * @param level the level number
     * @param space the parking lot number
     * @param date  the parking date
     */
    public ParkingTicket(int level, int space, Date date) {
        this.level = level;
        this.space = space;
        this.date = date;
    }

    /**
     * Returns the level number.
     *
     * @return the level number
     */
    public int getLevel() {
        return level;
    }

    /**
     * Returns the parking lot number.
     *
     * @return the parking lot number
     */
    public int getSpace() {
        return space;
    }

    /**
     * Returns the parking date.
     *
     * @return the parking date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Creates an instance of the class from String.
     *
     * @param text parking ticket as text
     * @return ParkingTicket object
     */
    public static ParkingTicket valueOf(String text) {
        StringTokenizer st = new StringTokenizer(text, DELIMITER);

        // get level
        String strLevel = st.nextToken();
        int level = Integer.parseInt(strLevel.substring(strLevel.indexOf(LBL_LEVEL) + LBL_LEVEL.length()));

        // get space
        String strSpace = st.nextToken();
        int space = Integer.parseInt(strSpace.substring(strSpace.indexOf(LBL_SPACE) + LBL_SPACE.length()));

        // get date
        String strDate = st.nextToken();
        Date date = null;
        try {
            date = new SimpleDateFormat(DATE_FORMAT).parse(strDate.substring(strDate.indexOf(LBL_ISSUED) + LBL_ISSUED.length()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new ParkingTicket(level, space, date);
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return new StringBuilder(LBL_LEVEL).append(level).append(DELIMITER)
                .append(LBL_SPACE).append(space).append(DELIMITER)
                .append(LBL_ISSUED).append(new SimpleDateFormat(DATE_FORMAT).format(date))
                .toString();

    }
}
