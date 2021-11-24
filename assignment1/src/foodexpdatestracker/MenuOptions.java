package foodexpdatestracker;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * foodexpdatestracker.FoodItem class displays and runs different options of the query menu
 * Options include: listing (all, expired, non-expired, and expiring in 7 days),
 * adding, and removing items.
 */
public class MenuOptions {
    private static final int LIST_ALL_CODE = 1;
    private static final int ADD_CODE = 2;
    private static final int REMOVE_CODE = 3;
    private static final int LIST_EXPIRED_CODE = 4;
    private static final int LIST_NON_EXPIRED_CODE = 5;
    private static final int LIST_SOON_EXPIRED_CODE = 6;
    private static final int EXIT_CODE = 7;

    private static final String[] MENU_OPTIONS = {"List Items",
                                                  "Add Item",
                                                  "Remove Item",
                                                  "List Expired Items",
                                                  "List Non-Expired Items",
                                                  "List Items Expiring in 7 Days",
                                                  "Exit"};

    private ArrayList<FoodItem> foodRecord;
    private final String title;

    /**
     * Starts the query program
     * @param title, a string of the title of the menu
     * @param foodRecord, an ArrayList containing all the FoodItems
     */
    public MenuOptions(String title, ArrayList<FoodItem> foodRecord) {
        this.title = title;
        this.foodRecord = foodRecord;

        printMenuTitle();
        sortQuery();
        runQuery();
    }

    /**
     * prints the title of the menu
     */
    private void printMenuTitle(){
        for (int n = 0; n < title.length() + 4; n++){
            System.out.print("#");
        }
        System.out.println();
        System.out.println("# " + title + " #");
        for (int n = 0; n < title.length() + 4; n++){
            System.out.print("#");
        }
        System.out.println();
        System.out.println("Today is: " + LocalDateTime.now().toLocalDate());
    }

    /**
     * Asks user for a menu option until user exits or program crashes
     *@throws NumberFormatException if user enters the wrong data type when prompted
     */
    private void runQuery(){
        Scanner scan = new Scanner(System.in);
        int userChoice = 0;
        while (userChoice != EXIT_CODE){
            printMenu();
            userChoice = Integer.parseInt(scan.nextLine());
            System.out.println();
            switch(userChoice){
                case LIST_ALL_CODE:
                    listAllItems();
                    break;
                case ADD_CODE:
                    addItem();
                    break;
                case REMOVE_CODE:
                    removeItem();
                    break;
                case LIST_EXPIRED_CODE:
                    listExpiredItems();
                    break;
                case LIST_NON_EXPIRED_CODE:
                    listNonExpiredItems();
                    break;
                case LIST_SOON_EXPIRED_CODE:
                    listSoonExpiredItems();
                    break;
                case EXIT_CODE:
                    System.out.println("Thank you for choosing us! Have a great day <3");
                    break;
                default:
                    System.out.println("Enter a valid choice >:((");
                    break;
            }
        }
    }

    /**
     * prints all the menu options
     */
    private static void printMenu(){
        for (int n = 1; n <= MENU_OPTIONS.length; n++){
            System.out.println(n + ": " + MENU_OPTIONS[n-1]);
        }
        System.out.print("Choose an option by entering 1-7: ");
    }

    /**
     * Uses selection sort to sort the foodRecord ArrayList
     * ordered by earliest expiry date to latest
     */
    private void sortQuery() {
        for (int i = 0; i < foodRecord.size() - 1; i++) {
            // Find the minimum element in unsorted array
            int minIndex = i;
            for (int n = i + 1; n < foodRecord.size(); n++)
                if (foodRecord.get(n).getExpiryDate().isBefore(foodRecord.get(minIndex).getExpiryDate())) {
                    minIndex = n;
                }

            FoodItem temp = foodRecord.get(minIndex);
            foodRecord.set(minIndex, foodRecord.get(i));
            foodRecord.set(i, temp);
        }
    }

    /**
     * prints all the food items in the foodRecord ArrayList
     */
    private void listAllItems(){
        System.out.println();
        if (foodRecord.size() == 0){
            System.out.println("No food items to show.\n");
            return;
        }
        for (int n = 0; n < foodRecord.size(); n++){
            System.out.println("Food Item #" + (n+1));
            System.out.println(foodRecord.get(n));
        }
        System.out.println();
    }

    /**
     * adds a new foodItem object into the foodRecord ArrayList
     * @throws DateTimeException if day-of-month is invalid for month-year
     * @throws NumberFormatException if user enters wrong data type
     */
    private void addItem(){
        Scanner scan = new Scanner (System.in);
        System.out.print("Enter the name of the new food item: ");
        String itemName = scan.nextLine();
        while (itemName.isBlank()){
           System.out.print("NAME CANNOT BE BLANK: Enter the name of the new food item: ");
           itemName = scan.nextLine();
        }
        System.out.print("Enter the notes of the new food item: ");
        String itemNotes = scan.nextLine();

        System.out.print("Enter the price of the new food item: ");
        double itemPrice = scan.nextDouble();
        while (itemPrice < 0){
            System.out.print("Enter A VALID price of the new food item: ");
            itemPrice = scan.nextDouble();
        }
        scan.nextLine();

        LocalDate itemExpiryDate = getExpiryDate(scan);
        LocalTime itemExpiryTime = LocalTime.of(23, 59);
        LocalDateTime itemExpiry = LocalDateTime.of(itemExpiryDate, itemExpiryTime);

        foodRecord.add(new FoodItem(itemName, itemNotes, itemPrice, itemExpiry));
        System.out.println(itemName + " has been added to the list.\n");
        sortQuery();
    }

    /**
     * @return a valid expiry date represented by a LocalDate object
     * @param scan, Scanner object to read in user inputs
     * @throws DateTimeException if day-of-month is invalid for month-year
     * @throws NumberFormatException if user enters wrong data type
     */
    private LocalDate getExpiryDate(Scanner scan){
        while (true) {
            System.out.print("Enter the year of the expiry date: ");
            int itemExpiryYear = Integer.parseInt(scan.nextLine());
            while (itemExpiryYear < 0){
                System.out.print("Enter A VALID year of the expiry date: ");
                itemExpiryYear = Integer.parseInt(scan.nextLine());
            }

            System.out.print("Enter the month of the expiry date: ");
            int itemExpiryMonth = Integer.parseInt(scan.nextLine());
            while (itemExpiryMonth > 12 || itemExpiryMonth < 1){
                System.out.print("Enter A VALID month of the expiry date: ");
                itemExpiryMonth = Integer.parseInt(scan.nextLine());
            }

            System.out.print("Enter the day of the expiry date: ");
            int itemExpiryDay = Integer.parseInt(scan.nextLine());
            try {
                return LocalDate.of(itemExpiryYear, itemExpiryMonth, itemExpiryDay);
            }
            catch (DateTimeException invalidDate){
                System.out.println("Invalid Date: Please try again.");
            }
        }
    }

    /**
     * Removes a foodItem object in the foodRecord ArrayList
     * @throws NumberFormatException if user enters an invalid data type
     */
    private void removeItem(){
        listAllItems();
        if (foodRecord.size() != 0){
            System.out.print("Enter the item number you want to remove (0 to cancel): ");
            Scanner scan = new Scanner(System.in);
            int userChoice = Integer.parseInt(scan.nextLine());
            while (userChoice > foodRecord.size() || userChoice < 0){
                System.out.println("INVALID ENTRY. PLEASE TRY AGAIN: ");
                System.out.print("Enter the item number you want to remove (0 to cancel): ");
                userChoice = Integer.parseInt(scan.nextLine());
            }
            if (userChoice != 0){
                System.out.println(foodRecord.get(userChoice - 1).getName() + " has been removed.\n");
                foodRecord.remove(userChoice - 1);
            }
        }
    }

    /**
     * prints all the food items that are expired in the foodRecord ArrayList
     */
    private void listExpiredItems(){
        int counter = 1;
        LocalDate currentDate = LocalDateTime.now().toLocalDate();
        System.out.println();
        for (FoodItem foodItem : foodRecord) {
            LocalDate expiryDate = foodItem.getExpiryDate().toLocalDate();
            if (currentDate.isAfter(expiryDate)) {
                System.out.println("Food Item #" + (counter));
                System.out.println(foodItem);
                counter++;
            }
        }
        if (counter == 1){
            System.out.println("No expired food items to show.\n");
        }
    }

    /**
     * prints all the food items that are not expired in the foodRecord ArrayList
     */
    private void listNonExpiredItems(){
        int counter = 1;
        LocalDate currentDate = LocalDateTime.now().toLocalDate();
        System.out.println();
        for (FoodItem foodItem : foodRecord) {
            LocalDate expiryDate = foodItem.getExpiryDate().toLocalDate();
            if (!currentDate.isAfter(expiryDate)) {
                System.out.println("Food Item #" + (counter));
                System.out.println(foodItem);
                counter++;
            }
        }
        if (counter == 1){
            System.out.println("No non-expired food items to show.\n");
        }
    }

    /**
     * prints all the food items that will expire in 7 days in the foodRecord ArrayList
     */
    private void listSoonExpiredItems(){
        int counter = 1;
        LocalDate currentDate = LocalDateTime.now().toLocalDate();
        System.out.println();
        for (FoodItem foodItem : foodRecord) {
            LocalDate expiryDate = foodItem.getExpiryDate().toLocalDate();
            if (!currentDate.isAfter(expiryDate)) {
                if (!currentDate.plusDays(7).isBefore(expiryDate)) {
                    System.out.println("Food Item #" + (counter));
                    System.out.println(foodItem);
                    counter++;
                }
            }
        }
        if (counter == 1){
            System.out.println("No food items expiring in 7 days to show.\n");
        }
    }
}