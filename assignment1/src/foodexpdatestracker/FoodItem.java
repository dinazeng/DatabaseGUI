package foodexpdatestracker;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * foodexpdatestracker.FoodItem class describes information about a food item,
 * Data includes: name of food, notes about the food,
 * the price of the food, and its expiry date.
 */
public class FoodItem {

    private final String name;
    private final String notes;
    private final double price;
    private final LocalDateTime expiryDate;

    /**
     * Constructor
     * @param name, name of the FoodItem object
     * @param notes, notes of the FoodItem object
     * @param price, price of the FoodItem object
     * @param expiryDate, the expiry date of the FoodItem object in a LocalDateTime object
     */
    public FoodItem(String name, String notes, double price, LocalDateTime expiryDate) {
        this.name = name;
        this.notes = notes;
        this.price = price;
        this.expiryDate = expiryDate;
    }

    /**
     * @return the name of the food item
     */
    public String getName() {
        return name;
    }

    /**
     * @return the expiry date of the food item
     */
    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    /**
     * @return a string that describes the food item's time to expiry to shorten toString()
     */
    private String timeToExpiry(){
        int daysToExpiry = (int)LocalDateTime.now().until(expiryDate, ChronoUnit.DAYS);
        if (expiryDate.isBefore(LocalDateTime.now())){
            return "This food item is expired for " + (-1)*daysToExpiry + " day(s).";
        } else if (expiryDate.isAfter(LocalDateTime.now())){
            return "This food item will expire in " + daysToExpiry + " day(s).";
        } else{
            return "This food item will expire today.";
        }
    }

    /**
     * @return a string with the food item's information
     * when object is printed, this method is called
     */
    @Override
    public String toString(){
        // when the object is passed into System.out.print, will print all available information about food item
        return "Name: " + name + "\n" +
               "Notes: " + notes + "\n" +
               "Price: " + String.format("%.2f", price) + "\n" +
               "Expiry Date: " + expiryDate.toLocalDate()+  "\n" +
                timeToExpiry() + "\n";
    }

}
