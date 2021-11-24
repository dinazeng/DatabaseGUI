package ca.cmpt213.a3.control;

import ca.cmpt213.a3.model.Consumable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * ca.cmpt213.a3.control.ConsumableOptions class displays and runs different options of the query menu
 * Options include: listing (all, expired, non-expired, and expiring in 7 days),
 * adding, and removing items.
 */
public class ConsumableOptions {

    private static final ArrayList<Consumable> CONSUMABLE_LIST = sortQuery(ReadWriteFiles.readJson());

    /**
     * Uses selection sort to sort the consumableList ArrayList
     * ordered by earliest expiry date to latest
     */
    public static ArrayList<Consumable> sortQuery(ArrayList<Consumable> consumableList) {
        for (int i = 0; i < consumableList.size() - 1; i++) {
            // Find the minimum element in unsorted array
            int minIndex = i;
            for (int n = i + 1; n < consumableList.size(); n++)
                if (consumableList.get(n).compareTo(consumableList.get(minIndex)) < 0) {
                    minIndex = n;
                }

            Consumable temp = consumableList.get(minIndex);
            consumableList.set(minIndex, consumableList.get(i));
            consumableList.set(i, temp);
        }
        return consumableList;
    }

    /**
     * prints all the Consumable objects in the consumableList ArrayList
     */
    public static ArrayList<Consumable> listAllItems(){
        return CONSUMABLE_LIST;
    }


    /**
     * @param item, adds a new Consumable object item into the consumableList ArrayList
     */
    public static void addItem(Consumable item){
        boolean itemAdded = false;
        for (int n = 0; n < CONSUMABLE_LIST.size(); n++){
            if (!itemAdded&&item.compareTo(CONSUMABLE_LIST.get(n)) < 0){
                CONSUMABLE_LIST.add(n, item);
                itemAdded = true;
            }
        }
        if (!itemAdded){
            CONSUMABLE_LIST.add(item);
        }
    }


    /**
     * @param item, Removes Consumable object item in the consumableList ArrayList
     * */
    public static void removeItem(Consumable item){
        CONSUMABLE_LIST.remove(item);
    }

    /**
     * prints all the Consumable objects that are expired in the consumableList ArrayList
     */
    public static ArrayList<Consumable> listExpiredItems(){
        LocalDate currentDate = LocalDateTime.now().toLocalDate();
        ArrayList<Consumable> returnArray = new ArrayList<>();
        for (Consumable consumable : CONSUMABLE_LIST) {
            LocalDate expiryDate = consumable.getExpiryDate().toLocalDate();
            if (currentDate.isAfter(expiryDate)) {
                returnArray.add(consumable);
            }
        }
        return returnArray;
    }

    /**
     * prints all the Consumable objects that are not expired in the consumableList ArrayList
     */
    public static ArrayList<Consumable> listNonExpiredItems(){
        LocalDate currentDate = LocalDateTime.now().toLocalDate();
        ArrayList<Consumable> returnArray = new ArrayList<>();
        for (Consumable consumable : CONSUMABLE_LIST) {
            LocalDate expiryDate = consumable.getExpiryDate().toLocalDate();
            if (!currentDate.isAfter(expiryDate)) {
                returnArray.add(consumable);
            }
        }
        return returnArray;
    }

    /**
     * prints all the Consumable objects that will expire in 7 days in the consumableList ArrayList
     */
    public static ArrayList<Consumable> listSoonExpiredItems(){
        LocalDate currentDate = LocalDateTime.now().toLocalDate();
        ArrayList<Consumable> returnArray = new ArrayList<>();
        for (Consumable consumable : CONSUMABLE_LIST) {
            LocalDate expiryDate = consumable.getExpiryDate().toLocalDate();
            if (!currentDate.isAfter(expiryDate)) {
                if (!currentDate.plusDays(7).isBefore(expiryDate)) {
                   returnArray.add(consumable);
                }
            }
        }
        return returnArray;
    }
}