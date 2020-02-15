package presentationLayer;

import bussinessLayer.Restaurant;
import bussinessLayer.products.CompositeProduct;
import bussinessLayer.products.MenuItem;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class AdministratorGUI extends JFrame {

    private JTable listOfProducts = new JTable(10, 3);
    private JScrollPane scrollPane;

    private JButton addProductBtn;
    private JButton deleteProductBtn;
    private JButton editProductBtn;
    private JButton addMenuItemBtn;

    private JCheckBox isBaseProduct;
    private JCheckBox isCompositeProduct;

    private JLabel name1Lbl;
    private JTextField name1Tf;
    private JLabel weight1Lbl;
    private JTextField weight1Tf;
    private JLabel price1Lbl;
    private JTextField price1Tf;

    private JLabel name2Lbl;
    private JTextField name2Tf;
    private JLabel weight2Lbl;
    private JTextField weight2Tf;
    private JLabel price2Lbl;
    private JTextField price2Tf;

    private JComboBox menuItemsList;
    private JButton deleteMenuItem;

    private Restaurant restaurant;
    private ListSelectionModel model;
    private int selectedRow;

    private JButton addMenuItemsBtn;
    private JCheckBox[] checkBoxes;

    private JFrame menuItemsFrame;
    private JButton addMenuItemsEdit;
    private JButton saveChangesForBaseProduct;
    private JButton saveChangesForCompositProduct;
    private JPanel mainPanel;
    private JButton addMenuItems2Edit;

    public AdministratorGUI(Restaurant restaurant) {

        this.restaurant = restaurant;
        this.mainPanel = createMainPanel();
        this.setContentPane(mainPanel);
        this.setSize(1500, 500);
        this.setTitle("Administrator");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public JPanel createMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);

        createTable();

        scrollPane = new JScrollPane(listOfProducts);
        scrollPane.setPreferredSize(new Dimension(300, 600));
        scrollPane.setBounds(10, 10, 500, 600);
        listOfProducts.setFillsViewportHeight(true);
        mainPanel.add(scrollPane);

        addProductBtn = new JButton("Add Product");
        deleteProductBtn = new JButton("Delete Product");
        editProductBtn = new JButton("Edit Product");
        isBaseProduct = new JCheckBox("Base Product");
        isCompositeProduct = new JCheckBox("Composite Product");

        name1Lbl = new JLabel("Product name: ");
        name1Tf = new JTextField();

        weight1Lbl = new JLabel("Product weight: ");
        weight1Tf = new JTextField();

        price1Lbl = new JLabel("Product price: ");
        price1Tf = new JTextField();

        name2Lbl = new JLabel("Product name: ");
        name2Tf = new JTextField();

        weight2Lbl = new JLabel("Product weight: ");
        weight2Tf = new JTextField();

        price2Lbl = new JLabel("Product price: ");
        price2Tf = new JTextField();

        menuItemsList = new JComboBox();
        deleteMenuItem = new JButton("Delete Menu Item");

        addMenuItemBtn = new JButton("Add Menu Items");
        addMenuItemsBtn = new JButton("Add Composite Product");

        addMenuItemsEdit = new JButton("Add Menu Items");
        saveChangesForBaseProduct = new JButton("Save Changes for Base Product");
        saveChangesForCompositProduct = new JButton("Save Changes for Composit Product");
        addMenuItems2Edit = new JButton("Add Menu Items");

        JPanel panel1 = addProductPanel();
        panel1.setBounds(510, 10, 300, 600);
        mainPanel.add(panel1);

        JPanel panel2 = deleteProductPanel();
        panel2.setBounds(810, 10, 300, 600);
        mainPanel.add(panel2);

        JPanel panel3 = editProductPanel();
        panel3.setBounds(1110, 10, 400, 600);
        mainPanel.add(panel3);

        return mainPanel;
    }

    public void createTable() {
        DefaultTableModel tableModel = new DefaultTableModel();
        String[] columnNames = {"Product Name",
                "Weight",
                "Price"};
        int index = 0;

        tableModel.setColumnCount(3);
        tableModel.setColumnIdentifiers(columnNames);

        List<MenuItem> menuItems = restaurant.getMenuItems();
        Object[][] data = new Object[menuItems.size()][3];

        for (MenuItem menuItem : menuItems) {
            data[index][0] = menuItem.getName();
            data[index][1] = menuItem.getWeight();
            data[index][2] = menuItem.getPrice();
            tableModel.addRow(data[index]);
            index++;
        }

        //listOfProducts = new JTable(data, columnNames);
        //listOfProducts = new JTable(10 ,10);
        listOfProducts.setModel(tableModel);
        model = listOfProducts.getSelectionModel();

        model.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!model.isSelectionEmpty()) {
                    selectedRow = listOfProducts.getSelectedRow();
                }
            }
        });
    }

    public int getSelectedRow() {
        return selectedRow;
    }

    private JPanel addProductPanel() {
        JPanel addPPanel = new JPanel();

        addPPanel.setBackground(new Color(130, 194, 222));
        addPPanel.setLayout(null);

        addProductBtn.setBounds(40, 10, 150, 20);
        addProductBtn.setVisible(false);
        addPPanel.add(addProductBtn);

        isBaseProduct.setBounds(10, 40, 140, 20);
        addPPanel.add(isBaseProduct);

        isCompositeProduct.setBounds(150, 40, 140, 20);
        addPPanel.add(isCompositeProduct);

        name1Lbl.setBounds(10, 70, 100, 20);
        addPPanel.add(name1Lbl);

        name1Tf.setBounds(120, 70, 150, 20);
        addPPanel.add(name1Tf);

        weight1Lbl.setBounds(10, 100, 100, 20);
        addPPanel.add(weight1Lbl);

        weight1Tf.setBounds(120, 100, 150, 20);
        addPPanel.add(weight1Tf);

        //BP
        price1Lbl.setBounds(10, 130, 100, 20);
        price1Lbl.setVisible(false);
        addPPanel.add(price1Lbl);

        price1Tf.setBounds(120, 130, 150, 20);
        price1Tf.setVisible(false);
        addPPanel.add(price1Tf);

        //CP
        addMenuItemBtn.setBounds(10, 130, 250, 20);
        addMenuItemBtn.setVisible(false);
        addPPanel.add(addMenuItemBtn);

        return addPPanel;
    }

    public void setVisibleCompositeProduct(boolean visibility) {
        addMenuItemBtn.setVisible(visibility);
    }

    public void setVisibleBaseProduct(boolean visibility) {
        addProductBtn.setVisible(visibility);
        price1Lbl.setVisible(visibility);
        price1Tf.setVisible(visibility);
    }

    public void setIsBaseProductListener(ActionListener a) {
        isBaseProduct.addActionListener(a);
    }

    public void setIsCompositeProductListener(ActionListener a) {
        isCompositeProduct.addActionListener(a);
    }

    public JCheckBox getIsBaseProduct() {
        return isBaseProduct;
    }

    public JCheckBox getIsCompositeProduct() {
        return isCompositeProduct;
    }

    private JPanel deleteProductPanel() {
        JPanel deletePanel = new JPanel();
        deletePanel.setLayout(null);
        deletePanel.setBackground(new Color(137, 213, 222));
        deleteProductBtn.setBounds(40, 10, 150, 20);
        deletePanel.add(deleteProductBtn);

        return deletePanel;
    }

    private JPanel editProductPanel() {
        JPanel editPanel = new JPanel();
        editPanel.setLayout(null);
        editPanel.setBackground(new Color(130, 194, 222));
        editProductBtn.setBounds(40, 10, 150, 20);
        editPanel.add(editProductBtn);

        name2Lbl.setBounds(10, 70, 100, 20);
        editPanel.add(name2Lbl);

        name2Tf.setBounds(120, 70, 250, 20);
        editPanel.add(name2Tf);

        weight2Lbl.setBounds(10, 100, 100, 20);
        editPanel.add(weight2Lbl);

        weight2Tf.setBounds(120, 100, 250, 20);
        editPanel.add(weight2Tf);

        price2Lbl.setBounds(10, 130, 100, 20);
        price2Lbl.setVisible(false);
        editPanel.add(price2Lbl);

        saveChangesForBaseProduct.setBounds(10, 160, 100, 20);
        saveChangesForBaseProduct.setVisible(false);
        editPanel.add(saveChangesForBaseProduct);

        price2Tf.setBounds(120, 130, 250, 20);
        price2Tf.setVisible(false);
        editPanel.add(price2Tf);

        menuItemsList.setBounds(10, 130, 200, 20);
        menuItemsList.setVisible(false);
        editPanel.add(menuItemsList);

        deleteMenuItem.setBounds(220, 130, 150, 20);
        deleteMenuItem.setVisible(false);
        editPanel.add(deleteMenuItem);

        addMenuItemsEdit.setBounds(220, 160, 150, 20);
        addMenuItemsEdit.setVisible(false);
        editPanel.add(addMenuItemsEdit);

        saveChangesForCompositProduct.setBounds(220, 190, 100, 20);
        saveChangesForCompositProduct.setVisible(false);
        editPanel.add(saveChangesForCompositProduct);

        return editPanel;
    }

    public JButton getAddProductBtn() {
        return addProductBtn;
    }

    public void setAddBtnListener(ActionListener a) {
        addProductBtn.addActionListener(a);
    }

    public JButton getDeleteProductBtn() {
        return deleteProductBtn;
    }

    public JButton getAddMenuItemBtn() {
        return addMenuItemBtn;
    }

    public void setDeleteBtnListener(ActionListener a) {
        deleteProductBtn.addActionListener(a);
    }

    public JButton getEditProductBtn() {
        return editProductBtn;
    }

    public void setEditBtnListener(ActionListener a) {
        editProductBtn.addActionListener(a);
    }

    public void setAddMenuItemBtnListener(ActionListener a) {
        addMenuItemBtn.addActionListener(a);
    }

    public JTextField getName1Tf() {
        return name1Tf;
    }

    public JTextField getWeight1Tf() {
        return weight1Tf;
    }

    public JTextField getPrice1Tf() {
        return price1Tf;
    }

    public JTextField getName2Tf() {
        return name2Tf;
    }

    public JTextField getWeight2Tf() {
        return weight2Tf;
    }

    public JTextField getPrice2Tf() {
        return price2Tf;
    }

    /**
     * returns true if the selected product is composite
     * false if base
     *
     * @return
     */
    public boolean compositeProductTableSelect() {

        Double price = Double.parseDouble(listOfProducts.getModel().getValueAt(selectedRow, 2).toString());
        if (price == 0.00) {
            return true;
        } else
            return false;
    }

    public void setEditCompositProductVisibility(boolean visibility) {
        deleteMenuItem.setVisible(visibility);
        menuItemsList.setVisible(visibility);
        addMenuItemsEdit.setVisible(visibility);
        saveChangesForCompositProduct.setVisible(visibility);

        if (visibility == true) {
            fillComboBoxWithMenuItems();
        }
    }

    public void fillComboBoxWithMenuItems() {
        menuItemsList.removeAllItems();
        CompositeProduct compositeProduct = (CompositeProduct) restaurant.getMenuItems().get(selectedRow);
        for (MenuItem menuItem : compositeProduct.getComponentProducts()) {
            menuItemsList.addItem(menuItem.getName());
        }
    }

    //vizibilitatea base-producturilor
    public void setEditBaseProductVisibility(boolean visibility) {
        price2Lbl.setVisible(visibility);
        price2Tf.setVisible(visibility);
        saveChangesForBaseProduct.setVisible(visibility);

    }

    public void setEditBaseProductTextFields() {
        name2Tf.setText(listOfProducts.getModel().getValueAt(selectedRow, 0).toString());
        weight2Tf.setText(listOfProducts.getModel().getValueAt(selectedRow, 1).toString());
        price2Tf.setText(listOfProducts.getModel().getValueAt(selectedRow, 2).toString());
    }

    public void setEditCompositeProductTextFields() {
        name2Tf.setText(listOfProducts.getModel().getValueAt(selectedRow, 0).toString());
        weight2Tf.setText(listOfProducts.getModel().getValueAt(selectedRow, 1).toString());
    }

    public JButton getDeleteMenuItem() {
        return deleteMenuItem;
    }

    public void setDeleteMenuItemListener(ActionListener a) {
        deleteMenuItem.addActionListener(a);
    }

    public int getSelectedIndexInCombo() {
        return menuItemsList.getSelectedIndex();
    }

    public void createMenuItemsFrame() {
        JFrame menuItemsFrame = new JFrame();
        JPanel menuItemsPanel = new JPanel();
        menuItemsPanel.setLayout(new BoxLayout(menuItemsPanel, BoxLayout.Y_AXIS));
        checkBoxes = new JCheckBox[restaurant.getMenuItems().size()];
        int i = 0;

        for (MenuItem m : restaurant.getMenuItems()) {
            checkBoxes[i] = new JCheckBox(m.getName());
            menuItemsPanel.add(checkBoxes[i]);
            i++;
        }
        menuItemsPanel.add(addMenuItemsBtn);
        menuItemsFrame.setContentPane(menuItemsPanel);
        menuItemsFrame.pack();
        menuItemsFrame.setVisible(true);
    }

    public JButton getAddMenuItemsBtn() {
        return addMenuItemsBtn;
    }

    public void setAddMenuItemsBtnListener(ActionListener a) {
        addMenuItemsBtn.addActionListener(a);
    }

    public List<MenuItem> getSelectedItems() {

        List<MenuItem> selectedItems = new ArrayList<>();
        int i = 0;
        for (JCheckBox c : checkBoxes) {
            System.out.println(c.getText());
            if (c.isSelected()) {
                selectedItems.add(restaurant.getMenuItems().get(i));
                i++;
            }
        }
        return selectedItems;
    }

    public JCheckBox[] getCheckBoxes() {
        return checkBoxes;
    }

    public JButton getAddMenuItemsEdit() {
        return addMenuItemsEdit;
    }

    public void setAddMenuItemsEditListener(ActionListener a) {
        addMenuItemsEdit.addActionListener(a);
    }

    public JButton getSaveChangesForBaseProduct() {
        return saveChangesForBaseProduct;
    }

    public void setSaveChangesForBaseProductListener(ActionListener a) {
        saveChangesForBaseProduct.addActionListener(a);
    }

    public JTable getListOfProducts() {
        return listOfProducts;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public void createNewFrameForEdit() {
        JFrame menuItemsFrame = new JFrame();
        JPanel menuItemsPanel = new JPanel();
        menuItemsPanel.setLayout(new BoxLayout(menuItemsPanel, BoxLayout.Y_AXIS));
        checkBoxes = new JCheckBox[restaurant.getMenuItems().size()];
        int i = 0;

        for (MenuItem m : restaurant.getMenuItems()) {
            checkBoxes[i] = new JCheckBox(m.getName());
            menuItemsPanel.add(checkBoxes[i]);
            i++;
        }
        menuItemsPanel.add(addMenuItems2Edit);
        menuItemsFrame.setContentPane(menuItemsPanel);
        menuItemsFrame.pack();
        menuItemsFrame.setVisible(true);
    }

    public JButton getAddMenuItems2Edit() {
        return addMenuItems2Edit;
    }

    public void setAddMenuItems2EditListener(ActionListener a) {
        addMenuItems2Edit.addActionListener(a);
    }

    public JButton getSaveChangesForCompositProduct() {
        return saveChangesForCompositProduct;
    }

    public void setSaveChangesForCompositProductListener(ActionListener a) {
        saveChangesForCompositProduct.addActionListener(a);
    }
}
