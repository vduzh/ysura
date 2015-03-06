package by.vduzh.ysura.garage.monitor;

import by.vduzh.ysura.garage.GarageSimulator;
import by.vduzh.ysura.garage.SimulatorEvent;
import by.vduzh.ysura.garage.SimulatorListener;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This application is a visual too to show the process of getting into the garage and leaving it.
 */
public class Monitor extends JFrame implements ActionListener, SimulatorListener {
    // just helper to synchronize
    private Object lock = new Object();

    // the reference to the simulator
    private GarageSimulator simulator;

    // a table component to show cars waiting in the queue
    private JTable entranceTable;

    // a table component to show cars in the garage
    private JTable garageTable;

    // a table component to show cars that have left via the corresponding exit
    private JTable exitTable;

    private JTextField txtFreeParkingLots;
    private JTextField txtLicensePlate;
    private JTextField txtLicenseTicket;

    /**
     * Creates an instance of the class.
     */
    public Monitor() {
        simulator = new GarageSimulator();
        simulator.setListener(this);

        initComponents();

        simulator.start();
    }

    /**
     * Initializes all the components
     */
    private void initComponents() {
        Container contentPane = getContentPane();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        contentPane.add(mainPanel, BorderLayout.CENTER);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(0, 4, 10, 10));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel labLicensePlate = new JLabel("Enter License plate here in the format [X-XX]:");
        txtLicensePlate = new JTextField(10);
        txtLicensePlate.setText("1-20");
        labLicensePlate.setLabelFor(txtLicensePlate);
        JButton btnGetLocation = new JButton("Get location");
        btnGetLocation.setActionCommand("GetLocation");
        btnGetLocation.addActionListener(this);
        txtLicenseTicket = new JTextField(70);
        txtLicenseTicket.setEditable(false);

        topPanel.add(labLicensePlate);
        topPanel.add(txtLicensePlate);
        topPanel.add(btnGetLocation);
        topPanel.add(txtLicenseTicket);

        JButton btnGetFreeParkingLots = new JButton("Get number of FREE parking lots");
        btnGetFreeParkingLots.setActionCommand("GetNumberOfFreeParkingLots");
        btnGetFreeParkingLots.addActionListener(this);
        txtFreeParkingLots = new JTextField(20);
        txtFreeParkingLots.setEditable(false);

        topPanel.add(btnGetFreeParkingLots);
        topPanel.add(txtFreeParkingLots);

        mainPanel.add(topPanel, BorderLayout.PAGE_START);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout(20, 20));
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel entrancePanel = new JPanel(new BorderLayout(0, 5));
        entrancePanel.add(new JLabel("Entrance:"), BorderLayout.PAGE_START);
        entranceTable = new JTable(new EntranceTableModel(getSimulator().getGarage()));
        entranceTable.setPreferredScrollableViewportSize(entranceTable.getPreferredSize());
        entranceTable.setCellSelectionEnabled(false);
        entranceTable.setFocusable(false);
        entrancePanel.add(new JScrollPane(entranceTable), BorderLayout.CENTER);
        centerPanel.add(entrancePanel, BorderLayout.PAGE_START);

        JPanel garagePanel = new JPanel(new BorderLayout(0, 5));
        garagePanel.add(new JLabel("Garage:"), BorderLayout.PAGE_START);
        garageTable = new JTable(new GarageTableModel(getSimulator().getGarage()));
        garageTable.setRowSelectionAllowed(false);
        garageTable.setPreferredScrollableViewportSize(garageTable.getPreferredSize());
        garageTable.setCellSelectionEnabled(false);
        garageTable.setFocusable(false);

        TableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Object obj = value;
                if (value instanceof GarageTableModel.GarageTableData) {
                    obj = ((GarageTableModel.GarageTableData) value).getType();
                }
                Component cellComponent = super.getTableCellRendererComponent(table, obj, isSelected, hasFocus, row, column);

                if (column == 0 || value == null) {
                    cellComponent.setBackground(Color.white);
                } else {
                    if (value instanceof GarageTableModel.GarageTableData) {
                        if (((GarageTableModel.GarageTableData) value).isOccupied()) {
                            cellComponent.setBackground(Color.red);
                        } else {
                            cellComponent.setBackground(Color.green);
                        }
                    }
                }
                return cellComponent;
            }
        };
        for (int columnIndex = 0; columnIndex < garageTable.getColumnCount(); columnIndex++) {
            garageTable.getColumnModel().getColumn(columnIndex).setCellRenderer(cellRenderer);
        }

        garagePanel.add(new JScrollPane(garageTable), BorderLayout.CENTER);
        centerPanel.add(garagePanel, BorderLayout.CENTER);


        JPanel exitPanel = new JPanel(new BorderLayout(0, 5));
        exitPanel.add(new JLabel("Exit:"), BorderLayout.PAGE_START);
        exitTable = new JTable(new ExitTableModel(getSimulator().getGarage()));
        exitTable.setPreferredScrollableViewportSize(exitTable.getPreferredSize());
        exitTable.setCellSelectionEnabled(false);
        exitTable.setFocusable(false);
        exitPanel.add(new JScrollPane(exitTable), BorderLayout.CENTER);
        centerPanel.add(exitPanel, BorderLayout.PAGE_END);

        contentPane.validate();
        pack();
    }

    /**
     * Returns the reference to the simulator
     *
     * @return Simulator oject
     */
    public GarageSimulator getSimulator() {
        return simulator;
    }

    /**
     * Handles buttons in fact.
     *
     * @param e action event object
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("GetLocation")) {
            String location = simulator.getGarage().getLocation(txtLicensePlate.getText());
            txtLicenseTicket.setText(location == null ? "Car is out of the garage!" : location);
            return;
        }

        if (e.getActionCommand().equals("GetNumberOfFreeParkingLots")) {
            txtFreeParkingLots.setText(String.valueOf(simulator.getGarage().getFreeLotsCount()));
            return;
        }
    }

    /**
     * Handles event from within a simulator.
     *
     * @param e simulator event object
     */
    @Override
    public void handleEvent(final SimulatorEvent e) {
        synchronized (lock) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    GarageTableModel garageTableModel = (GarageTableModel) garageTable.getModel();
                    if (e.getCommand() == SimulatorEvent.Command.Enter) {
                        ((EntranceTableModel) entranceTable.getModel()).dec(e.getPoint() - 1);
                        garageTableModel.setOccupied(true, e.getLevel() - 1, e.getSpace());
                    } else {
                        garageTableModel.setOccupied(false, e.getLevel() - 1, e.getSpace());
                        ((ExitTableModel) exitTable.getModel()).inc(e.getPoint() - 1);
                    }
                }
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Monitor frame = new Monitor();
                frame.setTitle("Garage simulator");
                frame.setVisible(true);
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                int xSize = (screenSize.width - 840) / 2;
                int ySize = (screenSize.height - 680) / 2;
                frame.setBounds(xSize, ySize, 840, 680);
                frame.setDefaultCloseOperation(Monitor.EXIT_ON_CLOSE);
            }
        });
    }
}
