package by.vduzh.ysura.garage.monitor;

import by.vduzh.ysura.garage.model.ConfigurationEntry;
import by.vduzh.ysura.garage.model.ParkingGarage;

import javax.swing.table.AbstractTableModel;

/**
 * Implementation of the TableModel interface to the Garage JTable.
 */
public class GarageTableModel extends AbstractTableModel {
    public static class GarageTableData {
        private char type;
        private boolean isOccupied;

        public GarageTableData(char type, boolean occupied) {
            this.type = type;
            isOccupied = occupied;
        }

        public char getType() {
            return type;
        }

        public void setType(char type) {
            this.type = type;
        }

        public boolean isOccupied() {
            return isOccupied;
        }

        public void setOccupied(boolean occupied) {
            isOccupied = occupied;
        }
    }

    private GarageTableData[][] spaces;

    public GarageTableModel(ParkingGarage garage) {
        int rowCount = garage.getConfig().getNumberOfLevels();

        int colCount = 0;
        for (int i = 0; i < rowCount; i++) {
            ConfigurationEntry entry = garage.getConfig().getLevelCapacities()[i];
            colCount = Math.max(colCount, entry.getCarValue() + entry.getMotorbikeValue());
        }

        this.spaces = new GarageTableData[rowCount][colCount];
        for (int i = 0; i < rowCount; i++) {
            ConfigurationEntry entry = garage.getConfig().getLevelCapacities()[i];

            int j = 0;
            for (int k = 0; k < entry.getCarValue(); k++, j++) {
                spaces[i][j] = new GarageTableData('C', false);
            }

            for (int k = 0; k < entry.getMotorbikeValue(); k++, j++) {
                spaces[i][j] = new GarageTableData('M', false);
            }
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnIndex == 0 ? Integer.class : GarageTableData.class;
    }

    @Override
    public int getColumnCount() {
        return spaces[0].length + 1;
    }

    @Override
    public int getRowCount() {
        return spaces.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnIndex == 0 ? "Level/Space" : String.valueOf(columnIndex);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return rowIndex + 1;
        } else {
            return spaces[rowIndex][columnIndex - 1];
        }
    }

    public void setOccupied(boolean occupied, int row, int column) {
        if (column == 0) {
            throw new RuntimeException("the value of the column param must be more than 1");
        }
        spaces[row][column - 1].setOccupied(occupied);
        fireTableCellUpdated(row, column);
    }
}
