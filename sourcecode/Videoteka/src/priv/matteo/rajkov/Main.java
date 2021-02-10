package priv.matteo.rajkov;

import priv.matteo.rajkov.ui.InputHandler;
import priv.matteo.rajkov.ui.Printer;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        Printer.printHelloText();
        do {
            Printer.printMainMenu();
            int numberCommand;
            try {
                numberCommand = Printer.askForIntegerInputUntilValid(Arrays.asList(1, 2, 3, 4, 5, 6, 9));
                InputHandler.handleUserMainInput(numberCommand);
            } catch (NumberFormatException e) {
                Printer.printErrorText();
            }
        } while (true);
    }
}
