package ca.cmpt213.a3;

import ca.cmpt213.a3.view.UserInterface;
import javax.swing.*;

/**
 * run ca.cmpt213.a3.MainMenu class to run the whole program
 */
public class MainMenu {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                new UserInterface();
            }
        });
    }
}
