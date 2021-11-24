package cmpt213.a2;

import cmpt213.a2.model.Consumable;
import cmpt213.a2.model.ReadWriteFiles;
import cmpt213.a2.textui.ConsumableOptions;

import java.util.ArrayList;

/**
 * run cmpt213.a2.model.MainMenu class to run the whole program
 */
public class ConsumablesTracker {

    /**
     * entry point of the program
     */
    public static void main(String[] args) {
        ArrayList<Consumable> consumableList = ReadWriteFiles.readJson();
        new ConsumableOptions("Main Menu", consumableList);
        ReadWriteFiles.writeJson(consumableList);
    }
}
