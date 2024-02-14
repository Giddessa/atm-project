package keypad;

import java.util.Scanner;

public class Keypad {

    // reads data from the command line
    private Scanner input;

    // no-argument constructor initializes the Scanner
    public Keypad() {
        input = new Scanner(System.in);
    }

    // return an integer value entered by the user
    public int getInput() {
        return input.nextInt();
    }
}
