package ca.cmpt213.a3.view;

import ca.cmpt213.a3.control.ConsumableOptions;
import ca.cmpt213.a3.model.Consumable;
import ca.cmpt213.a3.control.ReadWriteFiles;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * ca.cmpt213.a3.view.UserInterface class implements ActionListener
 * program for the UI
 * handles displaying lists of Consumable objects and the UI of adding/removing Consumable objects
 * exports Consumable ArrayList into aJSON file when closing
 */
public class UserInterface implements ActionListener {

    private final JPanel mainArea;
    private final JFrame programFrame;
    private final JScrollPane scrollPane;
    private static final int WIDTH = 700;
    private static final int HEIGHT = 400;

    /**
     * Constructor of the UI of the program
     * creates the listing buttons, the JScrollPane object, and the add items button
     * When closing, the Consumable objects in consumableList will be exported into a JSON file
     */
    public UserInterface(){
        programFrame = new JFrame("Consumable Tracker <3");
        programFrame.setBounds(20,20, WIDTH, HEIGHT);
        programFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        JPanel menuPanel = new JPanel();

        JButton listButton = new JButton("All");
        menuPanel.add(listButton, BorderLayout.CENTER);
        listButton.addActionListener(this);

        JButton expiredButton = new JButton("Expired");
        menuPanel.add(expiredButton, BorderLayout.CENTER);
        expiredButton.addActionListener(this);

        JButton nonExpiredButton = new JButton("Not Expired");
        menuPanel.add(nonExpiredButton, BorderLayout.CENTER);
        nonExpiredButton.addActionListener(this);

        JButton almostExpiredButton = new JButton("Expiring in 7 Days");
        menuPanel.add(almostExpiredButton, BorderLayout.CENTER);
        almostExpiredButton.addActionListener(this);

        programFrame.add(menuPanel, BorderLayout.NORTH);

        mainArea = new JPanel();
        mainArea.setBorder(BorderFactory.createLineBorder(Color.black));
        mainArea.setLayout(new BoxLayout(mainArea, BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane(mainArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        scrollPane.getViewport().add(mainArea);

        programFrame.add(scrollPane, BorderLayout.CENTER);

        JButton addButton = new JButton("Add");
        programFrame.add(addButton, BorderLayout.SOUTH);
        addButton.addActionListener(this);

        programFrame.setVisible(true);

        programFrame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                ReadWriteFiles.writeJson(ConsumableOptions.listAllItems());
                programFrame.dispose();
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
    }

    /**
     * ActionListener for the listing consumableList buttons
     * @param e, describes which button the user has pressed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        mainArea.removeAll();
        refreshDisplay();
        scrollPane.getViewport().setViewPosition( new Point(0, 0) );
        if (e.getActionCommand().equals("All")){
            printOntoScreen(ConsumableOptions.listAllItems(),1);
        } else if (e.getActionCommand().equals("Expired")) {
            printOntoScreen(ConsumableOptions.listExpiredItems(),2);
        } else if (e.getActionCommand().equals("Not Expired")) {
            printOntoScreen(ConsumableOptions.listNonExpiredItems(),3);
        } else if (e.getActionCommand().equals("Expiring in 7 Days")) {
            printOntoScreen(ConsumableOptions.listSoonExpiredItems(),4);
        } else if (e.getActionCommand().equals("Add")){
            new AddInterface(programFrame);
        }
    }

    /**
     * displays Consumable Objects in the @param consumableList onto the GUI
     * @param consumableList, the objects that will be displayed one object per panel
     * @param listType, describes which type of Consumable objects (expired/all etc.) being displayed
     */
    private void printOntoScreen(ArrayList<Consumable> consumableList, int listType){
        if (consumableList.size() == 0){
            String displayMessage = switch (listType) {
                case 1 -> "No items to show";
                case 2 -> "No expired items to show";
                case 3 -> "No non-expired items to show";
                case 4 -> "No items expiring in 7 days to show";
                default -> "";
            };
            JLabel textResponse = new JLabel(displayMessage);
            mainArea.add(textResponse);
        } else {
            int counter = 1;
            for (Consumable item: consumableList){
                JPanel panel = new JPanel();
                panel.setLayout(new GridBagLayout());
                panel.setPreferredSize(new Dimension(WIDTH - 50, 150));
                panel.setMaximumSize(new Dimension(WIDTH - 50, 150));
                String itemType;
                String itemAmount;
                if (item.getType() == 1){
                    itemType = "FoodItem";
                    itemAmount = "Weight: ";
                } else{
                    itemType = "DrinkItem";
                    itemAmount = "Volume: ";
                }
                TitledBorder itemTitle = BorderFactory.createTitledBorder("Item #" + counter++ + " " + itemType);
                panel.setBorder(itemTitle);

                GridBagConstraints gridConstraints = new GridBagConstraints();
                gridConstraints.anchor = GridBagConstraints.WEST;

                gridConstraints.gridx = 0;
                gridConstraints.gridy = 1;
                gridConstraints.insets = new Insets(1,1,1, HEIGHT /4);
                JLabel itemName = new JLabel ("Name: " + item.getName());
                panel.add(itemName, gridConstraints);

                gridConstraints.gridy = 2;
                JLabel itemNotes = new JLabel ("Notes: " + item.getNotes());
                panel.add(itemNotes, gridConstraints);

                gridConstraints.gridy = 3;
                JLabel itemPrice = new JLabel ("Price: " + Math.round(item.getPrice()*100.0)/100.0);
                panel.add(itemPrice, gridConstraints);

                gridConstraints.gridy = 4;
                JLabel itemMeasurement = new JLabel (itemAmount + Math.round(item.getAmount()*100.0)/100.0);
                panel.add(itemMeasurement, gridConstraints);

                gridConstraints.gridy = 5;
                String formattedDate = item.getExpiryDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                JLabel itemExpiryDate = new JLabel ("Expiry Date: " + formattedDate);
                panel.add(itemExpiryDate, gridConstraints);

                gridConstraints.gridy = 6;
                JLabel timeToExpire = new JLabel (item.timeToExpiry());
                panel.add(timeToExpire, gridConstraints);

                gridConstraints.gridy = 3;
                gridConstraints.gridx = 6;
                JButton removeButton = new JButton("Remove");
                removeButton.addActionListener(e -> {
                    ConsumableOptions.removeItem(item);
                    consumableList.remove(item);
                    mainArea.removeAll();
                    refreshDisplay();
                    printOntoScreen(consumableList,listType);
                });
                panel.add(removeButton, gridConstraints);

                mainArea.add(panel);
            }
        }
        refreshDisplay();

        programFrame.validate();
        programFrame.repaint();
        programFrame.pack();
    }

    /**
     * Refreshes and updates the UI
     */
    private void refreshDisplay(){
        mainArea.revalidate();
        mainArea.repaint();
        scrollPane.getViewport().setViewPosition( new Point(0, 0) );
    }
}
