package presentationLayer;

import bussinessLayer.Order;
import bussinessLayer.Restaurant;
import bussinessLayer.products.CompositeProduct;
import bussinessLayer.products.MenuItem;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class WaiterGUI extends JFrame {

    private JTable allOrders = new JTable(10, 4);
    private JScrollPane scrollPane;
    private JButton createOrderBtn;
    private JButton addProductToOrderBtn;
    private JComboBox allMenuItems;

    private JLabel orderIdLbl;
    private JTextField orderIdTf;

    private JLabel dateLbl;
    private JTextField dateTf;

    private JLabel tableLbl;
    private JTextField tableTf;

    private JButton generateBillBtn;
    private Restaurant restaurant;

    private int selectedRow;

    public WaiterGUI(Restaurant restaurant) {
        this.restaurant = restaurant;
        orderIdLbl = new JLabel("OrderID");
        orderIdTf = new JTextField();

        dateLbl = new JLabel("Date");
        dateTf = new JTextField();

        tableLbl = new JLabel("Table nr");
        tableTf = new JTextField();

        createOrderBtn = new JButton("Create New Order");
        generateBillBtn = new JButton("Generate Bill");
        addProductToOrderBtn = new JButton("Add Products To Order");
        scrollPane = new JScrollPane();
        allMenuItems = new JComboBox();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);

        createTable();

        scrollPane = new JScrollPane(allOrders);
        scrollPane.setPreferredSize(new Dimension(300, 300));
        scrollPane.setBounds(10, 10, 500, 300);
        allOrders.setFillsViewportHeight(true);
        mainPanel.add(scrollPane);

        JPanel panel1 = addOrderPanel();
        panel1.setBackground(new Color(169, 178, 200));
        panel1.setBounds(520, 10, 200, 300);
        mainPanel.add(panel1);

        JPanel panel2 = addProductsPanel();
        panel2.setBackground(new Color(169, 178, 200));
        panel2.setBounds(710, 10, 300, 300);
        mainPanel.add(panel2);

        this.setContentPane(mainPanel);
        this.setSize(1000, 400);
        this.setTitle("Waiter");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fillComboBoxWithMenuItems();
    }

    public void fillComboBoxWithMenuItems() {
        allMenuItems.removeAllItems();
        for (MenuItem menuItem : restaurant.getMenuItems()) {
            allMenuItems.addItem(menuItem.getName());
        }
    }

    public JPanel addOrderPanel() {
        JPanel addOrderPanel = new JPanel();
        addOrderPanel.setLayout(null);

        orderIdLbl.setBounds(10, 10, 50, 20);
        addOrderPanel.add(orderIdLbl);

        orderIdTf.setBounds(70, 10, 100, 20);
        addOrderPanel.add(orderIdTf);

        dateLbl.setBounds(10, 40, 50, 20);
        addOrderPanel.add(dateLbl);

        dateTf.setBounds(70, 40, 100, 20);
        addOrderPanel.add(dateTf);

        tableLbl.setBounds(10, 70, 50, 20);
        addOrderPanel.add(tableLbl);

        tableTf.setBounds(70, 70, 100, 20);
        addOrderPanel.add(tableTf);

        createOrderBtn.setBounds(10, 100, 150, 20);
        addOrderPanel.add(createOrderBtn);

        generateBillBtn.setBounds(10, 140, 150, 20);
        addOrderPanel.add(generateBillBtn);

        return addOrderPanel;
    }

    public JPanel addProductsPanel() {
        JPanel addProductsPanel = new JPanel();
        addProductsPanel.setLayout(null);

        addProductToOrderBtn.setBounds(10, 10, 200, 20);
        addProductsPanel.add(addProductToOrderBtn);

        allMenuItems.setBounds(10, 40, 200, 20);
        addProductsPanel.add(allMenuItems);

        return addProductsPanel;
    }

    public void createTable() {
        DefaultTableModel tableModel = new DefaultTableModel();
        String[] columnNames = {"OrderID",
                "Date",
                "Table",
                "Price"};

        int index = 0;

        tableModel.setColumnCount(4);
        tableModel.setColumnIdentifiers(columnNames);

        Map<Order, List<MenuItem>> orders = restaurant.getOrders();
        Object[][] data = new Object[orders.size()][4];

        for (Order order : orders.keySet()) {
            data[index][0] = order.getOrderId();
            data[index][1] = order.getDate();
            data[index][2] = order.getTable();
            data[index][3] = restaurant.computePrice(order);
            tableModel.addRow(data[index]);
            index++;
        }

        allOrders.setModel(tableModel);
        ListSelectionModel model = allOrders.getSelectionModel();

        model.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!model.isSelectionEmpty()) {
                    selectedRow = allOrders.getSelectedRow();
                }
            }
        });
    }

    public void setCreateOrderBtnListener(ActionListener a) {
        createOrderBtn.addActionListener(a);
    }

    public void setAddProductToOrderBtnListener(ActionListener a) {
        addProductToOrderBtn.addActionListener(a);
    }

    public void setGenerateBillBtnListener(ActionListener a) {
        generateBillBtn.addActionListener(a);
    }

    public JTextField getOrderIdTf() {
        return orderIdTf;
    }

    public JTextField getDateTf() {
        return dateTf;
    }

    public JTextField getTableTf() {
        return tableTf;
    }

    public int getSelectedIndexInCombo() {
        return allMenuItems.getSelectedIndex();
    }
}
