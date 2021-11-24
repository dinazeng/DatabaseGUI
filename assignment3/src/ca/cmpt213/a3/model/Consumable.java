package ca.cmpt213.a3.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * ca.cmpt213.a3.model.Consumable class implements Comparable and describes a Consumable object,
 * Data includes: name of Consumable object, notes about the Consumable object,
 * the amount of the Consumable object, the price of the Consumable object, and its expiry date.
 */
public class Consumable implements Comparable<Consumable> {

    private final String name;
    private final String notes;
    private final double price;
    private final double amount;
    private final LocalDateTime expiryDate;
    private int itemType = 0;

    /**
     * Constructor
     * @param name, name of the Consumable object
     * @param notes, notes of the Consumable object
     * @param price, price of the Consumable object
     * @param amount, amount of the Consumable object
     * @param expiryDate, the expiry date of the Consumable object in a LocalDateTime object
     */
    public Consumable(String name, String notes, double price, double amount, LocalDateTime expiryDate) {
        this.name = name;
        this.notes = notes;
        this.price = price;
        this.amount = amount;
        this.expiryDate = expiryDate;
    }

    /**
     * @return the name of the Consumable object
     */
    public String getName() {
        return name;
    }

    /**
     * @return the notes of the Consumable object
     */
    public String getNotes() {
        return notes;
    }

    /**
     * @return the price of the Consumable object
     */
    public double getPrice() {
        return price;
    }

    /**
     * @return the amount of the Consumable object
     */
    public double getAmount() {
        return amount;
    }

    /**
     * @return the expiry date of the Consumable object
     */
    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    /**
     * @return the item type of the Consumable object
     */
    public int getType() {
        return itemType;
    }

    /**
     * sets the item type of the Consumable object
     */
    public void setType(int typeCode) {
        itemType = typeCode;
    }

    /**
     * @return a string that describes the consumable item's time to expiry to shorten toString()
     */
    public String timeToExpiry(){
        int daysToExpiry = (int)LocalDate.now().until(expiryDate, ChronoUnit.DAYS);
        if (expiryDate.toLocalDate().isBefore(LocalDate.now())){
            return "This item is expired for " + (-1)*daysToExpiry + " day(s).";
        } else if (expiryDate.toLocalDate().isAfter(LocalDate.now())){
            return "This item will expire in " + daysToExpiry + " day(s).";
        } else{
            return "This item will expire today.";
        }
    }

    /**
     * @return a string with the Consumable object's information
     * when object is printed, this method is called
     */
    @Override
    public String toString(){
        return  "This is a consumable item\n" +
                "Name: " + name + "\n" +
                "Notes: " + notes + "\n" +
                "Price: " + String.format("%.2f", price) + "\n" +
                "Measurement: " + String.format("%.2f", amount) + "\n" +
                "Expiry Date: " + expiryDate.toLocalDate()+  "\n" +
                timeToExpiry() + "\n";
    }

    /**
     * Compares two Consumable objects
     * @param item Consumable object being compared to this object
     * @return an int: -1 if the expiry date is before otherwise return 1
     */
    @Override
    public int compareTo(Consumable item) {
        if (expiryDate.toLocalDate().isBefore(item.getExpiryDate().toLocalDate())){
            return -1;
        }
        return 1;
    }
}
