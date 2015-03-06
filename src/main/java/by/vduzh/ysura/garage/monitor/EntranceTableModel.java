package by.vduzh.ysura.garage.monitor;

import by.vduzh.ysura.garage.model.ParkingGarage;

import javax.swing.table.AbstractTableModel;

/**
 * Implementation of the TableModel interface to the Entrance JTable.
 */
public class EntranceTableModel extends AbstractTableModel {
    private int[] entrances;

    public EntranceTableModel(ParkingGarage garage) {
        int numberOfEntrances = garage.getConfig().getNumberOfEntrances();

        this.entrances = new int[numberOfEntrances];

        for (int i = 0; i < numberOfEntrances; i++) {
            entrances[i] = garage.getConfig().getVehiclesPerEntrance()[i].getCarValue() +
                    garage.getConfig().getVehiclesPerEntrance()[i].getMotorbikeValue();
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return Integer.class;
    }

    @Override
    public int getColumnCount() {
        return entrances.length;
    }

    @Override
    public int getRowCount() {
        return 1;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return "#" + (columnIndex + 1) + " (vehicles)";
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return entrances[columnIndex];
    }

    /**
     * Decrements the number of vehicles tha are waiting in the queue.
     *
     * @param column id column to process
     */
    public void dec(int column) {
        entrances[column]--;

        fireTableCellUpdated(0, column);
    }

}
