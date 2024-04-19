import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Alphabet {
    private Set<Character> characters;

    public Alphabet() {
        characters = new HashSet<>();
    }

    public void addCharacter(char newChar) {
        characters.add(newChar);
    }

    public boolean contains(char c) {
        return characters.contains(c);
    }

    public Set<Character> getCharacters() {
        return characters;
    }
}