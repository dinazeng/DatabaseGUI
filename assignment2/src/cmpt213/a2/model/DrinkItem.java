package cmpt213.a2.model;

import java.time.LocalDateTime;

/**
 * cmpt213.a2.model.DrinkItem class extends cmpt213.a2.model.Consumable class
 * toString method is updated to describe the type of Consumable object
 * (Consumable/FoodItem/DrinkItem) and volume of the DrinkItem
 * itemType is set to ITEM_TYPE
 */
public class DrinkItem extends Consumable{

    private static final int ITEM_TYPE = 2;

    /**
     * Constructor
     * @param name, name of the DrinkItem object
     * @param notes, notes of the DrinkItem object
     * @param price, price of the DrinkItem object
     * @param amount, volume of the DrinkItem object
     * @param expiryDate, the expiry date of the DrinkItem object in a LocalDateTime object
     */
    public DrinkItem(String name, String notes, double price, double amount, LocalDateTime expiryDate) {
        super(name, notes, price, amount, expiryDate);
        setType(ITEM_TYPE);
    }

    /**
     * @return a string with the DrinkItem object's information
     * when object is printed, this method is called
     */
    @Override
    public String toString(){
        return  "This is a drink item\n" +
                "Name: " + getName() + "\n" +
                "Notes: " + getNotes() + "\n" +
                "Price: " + String.format("%.2f", getPrice()) + "\n" +
                "Volume: " + String.format("%.2f", getAmount()) + "\n" +
                "Expiry Date: " + getExpiryDate().toLocalDate()+  "\n" +
                timeToExpiry() + "\n";
    }
}
