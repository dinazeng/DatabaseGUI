package cmpt213.a2.model;

import java.time.LocalDateTime;

/**
 * cmpt213.a2.model.ConsumableFactor class creates new Consumable Objects
 * this is done in accordance to the parameter consumableType int
 * source: https://www.tutorialspoint.com/design_pattern/factory_pattern.html
 */
public class ConsumableFactory {

    /**
     * @param consumableType, type of the Consumable instance
     * @param itemName, name of the Consumable instance
     * @param itemNotes, notes of the Consumable instance
     * @param itemPrice, price of the Consumable instance
     * @param itemAmount, amount of the Consumable instance
     * @param expiryDate, expiry date of the Consumable instance
     * @return a Consumable object
     */
    public static Consumable getInstance(String consumableType,
                                     String itemName,
                                     String itemNotes,
                                     double itemPrice,
                                     double itemAmount,
                                     LocalDateTime expiryDate){
        if(consumableType == null){
            return null;
        }
        if(consumableType.equalsIgnoreCase("FOOD")){
            return new FoodItem(itemName, itemNotes, itemPrice, itemAmount, expiryDate);

        } else if(consumableType.equalsIgnoreCase("DRINK")) {
            return new DrinkItem (itemName, itemNotes, itemPrice, itemAmount, expiryDate);
        }

        return null;
    }

    /**
     * @param consumableType, the type of the Consumable object (0-Consumable, 1-FoodItem, 2-DrinkItem)
     * @param item, a Consumable object that will be redefined to be its appropriate type
     * @return a Consumable object
     */
    public static Consumable fixType(int consumableType, Consumable item){
        if(consumableType == 0){
            return item;
        } else if(consumableType == 1) {
            return new FoodItem (item.getName(),
                                 item.getNotes(),
                                 item.getPrice(),
                                 item.getAmount(),
                                 item.getExpiryDate());
        }
        else if(consumableType == 2) {
            return new DrinkItem (item.getName(),
                                  item.getNotes(),
                                  item.getPrice(),
                                  item.getAmount(),
                                  item.getExpiryDate());
        }
        return null;
    }
}
