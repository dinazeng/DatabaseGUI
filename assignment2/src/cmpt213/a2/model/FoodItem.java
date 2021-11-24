package cmpt213.a2.model;

import java.time.LocalDateTime;

/**
 * cmpt213.a2.model.FoodItem class extends cmpt213.a2.model.Consumable class
 * toString method is updated to describe the type of Consumable object
 * (Consumable/FoodItem/DrinkItem) and weight of the FoodItem
 * itemType is set to ITEM_TYPE
 */
public class FoodItem extends Consumable{

    private static final int ITEM_TYPE = 1;

    /**
     * Constructor
     * @param name, name of the FoodItem object
     * @param notes, notes of the FoodItem object
     * @param price, price of the FoodItem object
     * @param amount, weight of the FoodItem object
     * @param expiryDate, the expiry date of the FoodItem object in a LocalDateTime object
     */
    public FoodItem(String name, String notes, double price, double amount, LocalDateTime expiryDate) {
        super(name, notes, price, amount, expiryDate);
        setType(ITEM_TYPE);
    }

    /**
     * @return a string with the FoodItem object's information
     * when object is printed, this method is called
     */
    @Override
    public String toString(){
        return  "This is a food item\n" +
                "Name: " + getName() + "\n" +
                "Notes: " + getNotes() + "\n" +
                "Price: " + String.format("%.2f", getPrice()) + "\n" +
                "Weight: " + String.format("%.2f", getAmount()) + "\n" +
                "Expiry Date: " + getExpiryDate().toLocalDate()+  "\n" +
                timeToExpiry() + "\n";
    }
}
