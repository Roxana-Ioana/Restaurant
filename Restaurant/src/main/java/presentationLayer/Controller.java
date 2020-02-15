package presentationLayer;

import bussinessLayer.Order;
import bussinessLayer.Restaurant;
import bussinessLayer.products.BaseProduct;
import bussinessLayer.products.CompositeProduct;
import bussinessLayer.products.MenuItem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private AdministratorGUI administratorGUI;
    private WaiterGUI waiterGUI;
    private ChefGUI chefGUI;
    private Restaurant restaurant;

    public Controller(Restaurant restaurant, AdministratorGUI administratorGUI, WaiterGUI waiterGUI, ChefGUI chefGUI) {
        this.administratorGUI = administratorGUI;
        this.waiterGUI = waiterGUI;
        this.chefGUI = chefGUI;
        this.restaurant = restaurant;

        administratorGUI.setIsBaseProductListener(new AdminPanelListeners());
        administratorGUI.setIsCompositeProductListener(new AdminPanelListeners());
        administratorGUI.setAddBtnListener(new AdminOperations());
        administratorGUI.setAddMenuItemBtnListener(new AdminOperations());
        administratorGUI.setDeleteBtnListener(new AdminOperations());
        AdminOperations adminOperations1 = new AdminOperations();
        administratorGUI.setEditBtnListener(adminOperations1);
        administratorGUI.setSaveChangesForCompositProductListener(adminOperations1);
        administratorGUI.setAddMenuItemsBtnListener(new AddMenuItemsOperation());
        administratorGUI.setSaveChangesForBaseProductListener(new AdminOperations());
        administratorGUI.setAddMenuItemsEditListener(new AdminOperations());

        waiterGUI.setGenerateBillBtnListener(new GenerateBill());
        waiterGUI.setCreateOrderBtnListener(new CreateOrder());
        waiterGUI.setAddProductToOrderBtnListener(new AddProductToOrder());
    }

    public class AdminPanelListeners implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == administratorGUI.getIsBaseProduct()) {
                JCheckBox checkBox = (JCheckBox) e.getSource();
                if (checkBox.isSelected()) {
                    administratorGUI.setVisibleBaseProduct(true);
                } else if (!checkBox.isSelected()) {
                    administratorGUI.setVisibleBaseProduct(false);
                }
            } else {
                if (e.getSource() == administratorGUI.getIsCompositeProduct()) {
                    JCheckBox checkBox = (JCheckBox) e.getSource();
                    if (checkBox.isSelected()) {
                        administratorGUI.setVisibleCompositeProduct(true);
                    } else if (!checkBox.isSelected()) {
                        administratorGUI.setVisibleCompositeProduct(false);
                    }
                }
            }
        }
    }

    public class AdminOperations implements ActionListener {

        private CompositeProduct product;

        public AdminOperations() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            //add base product
            if (e.getSource() == administratorGUI.getAddProductBtn()) {

                JCheckBox bp = administratorGUI.getIsBaseProduct();
                JCheckBox cp = administratorGUI.getIsCompositeProduct();

                if (!bp.isSelected() && !cp.isSelected())
                {
                    JOptionPane.showMessageDialog(administratorGUI, "Please select the type of product", "ERROR", JOptionPane.ERROR_MESSAGE);

                } else {
                    String productName = administratorGUI.getName1Tf().getText();
                    String productWeight = administratorGUI.getWeight1Tf().getText();
                    double productPrice = 0;

                    if (bp.isSelected()) {
                        try {
                            productPrice = Double.parseDouble(administratorGUI.getPrice1Tf().getText());
                        } catch (Exception exception) {
                            JOptionPane.showMessageDialog(administratorGUI, "Please introduce a valid price", "ERROR", JOptionPane.ERROR_MESSAGE);
                        }
                        if (productName.equals("") || productWeight.equals("")) {
                            JOptionPane.showMessageDialog(administratorGUI, "Please introduce all necessary data", "ERROR", JOptionPane.ERROR_MESSAGE);
                        } else {

                            BaseProduct newProduct = new BaseProduct(productName, productWeight, productPrice);
                            restaurant.createMenuItem(newProduct);
                            waiterGUI.fillComboBoxWithMenuItems();
                            administratorGUI.createTable();
                        }
                    }
                }

            } else
                //delete selected product
                if (e.getSource() == administratorGUI.getDeleteProductBtn()) {

                    restaurant.deleteMenuItem(administratorGUI.getSelectedRow());
                    JOptionPane.showMessageDialog(administratorGUI, "The product was removed", "ERROR", JOptionPane.ERROR_MESSAGE);
                    waiterGUI.fillComboBoxWithMenuItems();
                    administratorGUI.createTable();

                } else
                    //edit selected product
                    if (e.getSource() == administratorGUI.getEditProductBtn()) {
                        System.out.println("PRODUSUL SELECTAT: " + restaurant.getMenuItems().get(administratorGUI.getSelectedRow()));
                        //if the selected product is composite
                        if (administratorGUI.compositeProductTableSelect()) {
                            administratorGUI.setEditBaseProductVisibility(false);
                            administratorGUI.setEditCompositProductVisibility(true);
                            administratorGUI.setEditCompositeProductTextFields();

                            this.product = (CompositeProduct) restaurant.getMenuItems().get(administratorGUI.getSelectedRow());
                            administratorGUI.setDeleteMenuItemListener(new EditOperations(product));
                            administratorGUI.setAddMenuItems2EditListener(new EditOperations(product));

                        } else {
                            //if the selected product is base
                            administratorGUI.setEditCompositProductVisibility(false);
                            administratorGUI.setEditBaseProductVisibility(true);
                            administratorGUI.setEditBaseProductTextFields();
                        }
                    } else if (e.getSource() == administratorGUI.getAddMenuItemBtn()) {
                        CompositeProduct newProduct = new CompositeProduct(administratorGUI.getName1Tf().getText(), administratorGUI.getWeight1Tf().getText());
                        administratorGUI.createMenuItemsFrame();
                    } else
                        if (e.getSource() == administratorGUI.getSaveChangesForBaseProduct()) {
                            BaseProduct editedProduct = new BaseProduct(administratorGUI.getName2Tf().getText(), administratorGUI.getWeight2Tf().getText(), Double.parseDouble(administratorGUI.getPrice2Tf().getText()));
                            restaurant.editMenuItem(administratorGUI.getSelectedRow(), editedProduct);
                            JOptionPane.showMessageDialog(administratorGUI, "The product was edited", "INFO", JOptionPane.INFORMATION_MESSAGE);
                            administratorGUI.createTable();
                            waiterGUI.fillComboBoxWithMenuItems();
                        } else if (e.getSource() == administratorGUI.getSaveChangesForCompositProduct()) {
                            for (MenuItem m : product.getComponentProducts()) {
                                System.out.println(m.toString());
                            }
                            CompositeProduct editedProduct = new CompositeProduct(administratorGUI.getName2Tf().getText(), administratorGUI.getWeight2Tf().getText(), product.getComponentProducts());
                            restaurant.editMenuItem(administratorGUI.getSelectedRow(), editedProduct);
                            JOptionPane.showMessageDialog(administratorGUI, "The product was edited", "INFO", JOptionPane.INFORMATION_MESSAGE);
                            administratorGUI.createTable();
                            waiterGUI.fillComboBoxWithMenuItems();
                        } else if (e.getSource() == administratorGUI.getAddMenuItemsEdit()) {
                            administratorGUI.createNewFrameForEdit();
                        }

        }
    }

    public class EditOperations implements ActionListener {

        private CompositeProduct compositeProduct;

        public EditOperations(CompositeProduct compositeProduct) {
            this.compositeProduct = compositeProduct;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == administratorGUI.getDeleteMenuItem()) {

                int selectedIndex = administratorGUI.getSelectedIndexInCombo();
                compositeProduct.removeMenuItem(selectedIndex);
                administratorGUI.fillComboBoxWithMenuItems();

            } else if (e.getSource() == administratorGUI.getAddMenuItems2Edit()) {
                int i = 0;
                administratorGUI.getCheckBoxes();
                for (JCheckBox c : administratorGUI.getCheckBoxes()) {

                    if (c.isSelected()) {
                        System.out.println(c.getText());
                        compositeProduct.addComponent(restaurant.getMenuItems().get(i));
                    }
                    i++;
                }
                System.out.println("ce contine produsul meu");
                for (MenuItem m : compositeProduct.getComponentProducts()) {
                    System.out.println(m.toString());
                }
                administratorGUI.fillComboBoxWithMenuItems();
                JOptionPane.showMessageDialog(administratorGUI, "Menu Items were added", "INFO", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    public class AddMenuItemsOperation implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == administratorGUI.getAddMenuItemsBtn()) {
                List<MenuItem> selectedItems = new ArrayList<>();
                int i = 0;
                administratorGUI.getCheckBoxes();
                for (JCheckBox c : administratorGUI.getCheckBoxes()) {
                    if (c.isSelected()) {
                        selectedItems.add(restaurant.getMenuItems().get(i));
                    }
                    i++;
                }
                restaurant.createMenuItem(new CompositeProduct(administratorGUI.getName1Tf().getText(), administratorGUI.getWeight1Tf().getText(), selectedItems));
                JOptionPane.showMessageDialog(administratorGUI, "Product added to the restaurant", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                waiterGUI.fillComboBoxWithMenuItems();
                administratorGUI.createTable();
            }
        }
    }

    public class GenerateBill implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Order order = new Order(Integer.parseInt(waiterGUI.getOrderIdTf().getText()), waiterGUI.getDateTf().getText(), Integer.parseInt(waiterGUI.getTableTf().getText()));
            if (restaurant.getOrders().containsKey(order)) {
                String fileName = order.getOrderId() + ".txt";
                restaurant.generateBill(order, fileName);
                JOptionPane.showMessageDialog(administratorGUI, "The bill was created", "BILL GENERATED", JOptionPane.INFORMATION_MESSAGE);

            } else {
                JOptionPane.showMessageDialog(administratorGUI, "Order not found", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public class CreateOrder implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Order order = new Order(Integer.parseInt(waiterGUI.getOrderIdTf().getText()), waiterGUI.getDateTf().getText(), Integer.parseInt(waiterGUI.getTableTf().getText()));
            List<MenuItem> menuItems = new ArrayList<>();
            restaurant.createNewOrder(order, menuItems);
            waiterGUI.createTable();
        }
    }

    public class AddProductToOrder implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Order order = new Order(Integer.parseInt(waiterGUI.getOrderIdTf().getText()), waiterGUI.getDateTf().getText(), Integer.parseInt(waiterGUI.getTableTf().getText()));
            restaurant.addMenuItemsToOrder(order, restaurant.getMenuItems().get(waiterGUI.getSelectedIndexInCombo()));
            waiterGUI.createTable();
        }
    }
}
