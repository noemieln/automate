import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Transition {
    private Etat targetState;
    private char symbol;

    public Transition () {}

    public Transition(Etat targetState, char symbol) {
        this.targetState = targetState;
        this.symbol = symbol;
    }

    public Etat getTargetState() {
        return targetState;
    }

    public char getSymbol() {
        return symbol;
    }
}