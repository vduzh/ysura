package by.vduzh.ysura.garage.monitor;

import by.vduzh.ysura.garage.model.ParkingGarage;

import javax.swing.table.AbstractTableModel;

/**
 * Implementation of the TableModel interface to the Exit JTable.
 */
public class ExitTableModel extends AbstractTableModel {
    private int[] exits;

    public ExitTableModel(ParkingGarage garage) {
        this.exits = new int[garage.getConfig().getNumberOfExits()];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return Integer.class;
    }

    @Override
    public int getColumnCount() {
        return exits.length;
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
        return exits[columnIndex];
    }

    /**
     * Increments the number of vehicles that have left the garage and re-render the cell in the table.
     *
     * @param column id column to process
     */
    public void inc(int column) {
        exits[column]++;

        fireTableCellUpdated(0, column);
    }
}
