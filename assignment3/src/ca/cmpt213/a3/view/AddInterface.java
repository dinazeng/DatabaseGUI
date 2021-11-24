package ca.cmpt213.a3.view;

import ca.cmpt213.a3.control.ConsumableOptions;
import ca.cmpt213.a3.model.Consumable;
import ca.cmpt213.a3.model.ConsumableFactory;
import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * ca.cmpt213.a3.view.AddInterface extends JDialog implements ActionListener handles the UI when adding an item
 */
public class AddInterface extends JDialog implements ActionListener {
    private final JDialog addDialog;
    private static final int WIDTH = 700;
    private static final int HEIGHT = 400;
    private final JComboBox<String> selectItemType = new JComboBox<>(new String[] {
            "Select an Item Type", "Food Item", "Drink Item"});
    private JPanel infoPanel = new JPanel();
    private DatePicker datePicker;

    private JTextField nameText;
    private JTextField notesText;
    private JTextField priceText;
    private JTextField amountText;

    private String typeString = "Amount:";
    private String itemType = "";

    /**
     * defines addDialog as the UI handling adding a Consumable object
     * @param programFrame, the child component of Field JDialog addDialog
     */
    public AddInterface(JFrame programFrame){
        addDialog = new JDialog(programFrame, "Add An Item");
        addDialog.setSize(WIDTH*4/5, HEIGHT);
        addDialog.setVisible(true);
        addDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        selectItemType.setVisible(true);
        selectItemType.addActionListener(this);

        addDialog.add(selectItemType, BorderLayout.NORTH);
        addDialog.add(infoPanel, BorderLayout.CENTER);

        JButton addButton = new JButton ("Add Item");
        addButton.addActionListener(this);
        JButton cancelButton = new JButton ("Cancel");
        cancelButton.addActionListener(this);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);

        addDialog.add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * sets up the UI to hold user input + the Consumable object information being added
     */
    private void addItem(){
        addDialog.remove(infoPanel);
        infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        JLabel emptyLabel = new JLabel("----");
        infoPanel.add(emptyLabel);

        JPanel namePanel = new JPanel();
        JLabel nameLabel = new JLabel ("Name: ");
        namePanel.add(nameLabel);
        nameText = new JTextField("",20);
        namePanel.add(nameText);
        infoPanel.add(namePanel);

        JPanel notesPanel = new JPanel();
        JLabel notesLabel = new JLabel ("Notes: ");
        notesPanel.add(notesLabel);
        notesText = new JTextField("",20);
        notesPanel.add(notesText);
        infoPanel.add(notesPanel);

        JPanel pricePanel = new JPanel();
        JLabel priceLabel = new JLabel ("Price: ");
        pricePanel.add(priceLabel);
        priceText = new JTextField("",20);
        pricePanel.add(priceText);
        infoPanel.add(pricePanel);

        JPanel amountPanel = new JPanel();
        JLabel amountLabel = new JLabel (typeString);
        amountPanel.add(amountLabel);
        amountText = new JTextField("",20);
        amountPanel.add(amountText);
        infoPanel.add(amountPanel);

        JPanel datePanel = new JPanel();
        JLabel dateLabel = new JLabel ("Expiry Date: ");
        datePanel.add(dateLabel);
        datePicker = new DatePicker();
        datePanel.add(datePicker);
        infoPanel.add(datePanel);
    }

    /**
     * waits for a user action and does one of the following:
     * changes the display of the measurement field
     * gets information that will be used to create a new Consumable object
     * handles information errors
     * disposes the class object
     * @param e, the action that has been performed by the user
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == selectItemType){
            String boxMessage = (String)selectItemType.getSelectedItem();
            assert boxMessage != null;
            if (boxMessage.equals("Food Item")){
                typeString = "Weight:";
                itemType = "FOOD";
            } else if (boxMessage.equals("Drink Item")){
                typeString = "Volume:";
                itemType = "DRINK";
            } else {
                typeString = "Amount:";
                itemType = "";
            }
            addItem();
            addDialog.add(infoPanel, BorderLayout.CENTER);
            addDialog.revalidate();
            addDialog.repaint();

        } else if (e.getActionCommand().equals("Add Item")){
            try {
                if (!itemType.equals("FOOD") && !itemType.equals("DRINK")) {
                    throw new IllegalComponentStateException();
                }
                String itemName = nameText.getText();
                if (nameText.getText().isBlank() || nameText.getText().isEmpty()) {
                    throw new AssertionError();
                }
                String itemNotes = notesText.getText();
                double itemPrice = Double.parseDouble(priceText.getText());
                double itemAmount = Double.parseDouble(amountText.getText());
                if (itemPrice < 0 || itemAmount < 0) {
                    throw new NegativeArraySizeException();
                }
                LocalTime fillerTime = LocalTime.of(23, 59, 59);
                LocalDate dateOfExpiry = datePicker.getDate();
                LocalDateTime itemExpiryDate = LocalDateTime.of(dateOfExpiry, fillerTime);
                Consumable newConsumable = ConsumableFactory.getInstance(itemType, itemName, itemNotes,
                        itemPrice, itemAmount, itemExpiryDate);

                ConsumableOptions.addItem(newConsumable);
                addDialog.dispose();
            } catch (IllegalComponentStateException error) {
                JOptionPane.showMessageDialog(addDialog, "Please select a valid item type.",
                        "Invalid Entry", JOptionPane.WARNING_MESSAGE);
            } catch (AssertionError error){
                JOptionPane.showMessageDialog(addDialog, "Please enter the item name.",
                        "Invalid Entry", JOptionPane.WARNING_MESSAGE);
            } catch (IllegalArgumentException error){
                JOptionPane.showMessageDialog(addDialog, "Please enter a valid value.",
                        "Invalid Entry", JOptionPane.WARNING_MESSAGE);
            } catch (NegativeArraySizeException error){
                JOptionPane.showMessageDialog(addDialog, "Negative values are not allowed. Please try again :)",
                        "Invalid Entry", JOptionPane.ERROR_MESSAGE);
            } catch (NullPointerException error){
                JOptionPane.showMessageDialog(addDialog, "Please enter the item expiry date.",
                        "Invalid Entry", JOptionPane.WARNING_MESSAGE);
            }
        } else if (e.getActionCommand().equals("Cancel")){
            addDialog.dispose();
        }
    }
}
