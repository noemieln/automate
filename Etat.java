import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Etat {
    private String name;
    private boolean isInitial;
    private boolean isFinal;
    private List<Transition> transitions;

    public Etat(String name, boolean isInitial, boolean isFinal) {
        this.name = name;
        this.isInitial = isInitial;
        this.isFinal = isFinal;
        this.transitions = new ArrayList<>();
    }

    public void addTransition(Transition transition) {
        transitions.add(transition);
    }

    public List<Transition> getTransitions() {
        return transitions;
    }

    public boolean isInitial() {
        return isInitial;
    }

    public void setInitial(boolean initial) {
        isInitial = initial;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public void setFinal(boolean aFinal) {
        isFinal = aFinal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}