package foodexpdatestracker;

import java.util.ArrayList;

/**
 * run foodexpdatestracker.MainMenu class to run the whole program
 */
public class MainMenu {

    /**
     * runs the program
     */
    public static void main(String[] args) {
        ArrayList<FoodItem> foodRecord = ReadWriteFiles.readJson();
        new MenuOptions("Main Menu", foodRecord);
        ReadWriteFiles.writeJson(foodRecord);
    }
}
