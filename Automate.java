import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class Automate {
    private Alphabet alphabet;
    private List<Etat> states;

    public Automate() {
        alphabet = new Alphabet();
        states = new ArrayList<>();
    }



    public void loadFromFile(String fileName) {
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);

            // Lire le nombre de symboles dans l'alphabet
            int numSymbols = Integer.parseInt(scanner.nextLine().trim());

            // Ajouter chaque symbole à l'alphabet (a, b, ..., correspondant au nombre de symboles)
            for (int i = 0; i < numSymbols; i++) {
                char symbol = (char) ('a' + i);
                alphabet.addCharacter(symbol);
            }

            // Lire le nombre d'états
            int numStates = Integer.parseInt(scanner.nextLine().trim());

            // Créer les états et les ajouter à la liste des états
            for (int i = 0; i < numStates; i++) {
                Etat state = new Etat(Integer.toString(i), false, false); // Créer un état avec l'indice i comme identifiant
                states.add(state);
            }

            // Lire les états initiaux
            String[] initialStatesLine = scanner.nextLine().trim().split(" ");
            int numInitialStates = Integer.parseInt(initialStatesLine[0]);
            for (int i = 1; i <= numInitialStates; i++) {
                int stateIndex = Integer.parseInt(initialStatesLine[i]);
                states.get(stateIndex).setInitial(true);
            }

            // Lire les états terminaux
            String[] finalStatesLine = scanner.nextLine().trim().split(" ");
            int numFinalStates = Integer.parseInt(finalStatesLine[0]);
            if (numFinalStates > 0) {
                for (int i = 1; i <= numFinalStates; i++) {
                    int stateIndex = Integer.parseInt(finalStatesLine[i]);
                    states.get(stateIndex).setFinal(true);
                }
            }

            // Lire le nombre de transitions
            int numTransitions = Integer.parseInt(scanner.nextLine().trim());

            // Lire les transitions
            for (int i = 0; i < numTransitions; i++) {
                String transitionLine = scanner.nextLine().trim();
                int startStateIndex = Integer.parseInt(transitionLine.substring(0, 1));
                char symbol = transitionLine.charAt(1);
                int endStateIndex = Integer.parseInt(transitionLine.substring(2));

                // Ajouter la transition à l'état de départ correspondant
                Etat startState = states.get(startStateIndex);
                Etat endState = states.get(endStateIndex);
                startState.addTransition(new Transition(endState, symbol));
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Fichier introuvable : " + fileName);
            e.printStackTrace();
        }
    }


    //===========================================================================================================//


    public boolean isDeterministic() {
        for (Etat state : states) {
            Set<Character> symbols = new HashSet<>();
            for (Transition transition : state.getTransitions()) {
                if (symbols.contains(transition.getSymbol())) {
                    return false; // Il existe plusieurs transitions pour le même symbole
                }
                symbols.add(transition.getSymbol());
            }
        }
        return true; // Aucun état ne contient plusieurs transitions pour le même symbole
    }

    public boolean isStandard() {
        int initialCount = 0;
        int finalCount = 0;
        for (Etat state : states) {
            if (state.isInitial()) {
                initialCount++;
                if (initialCount > 1) {
                    return false; // Plusieurs états initiaux
                }
            }
            if (state.isFinal()) {
                finalCount++;
                if (finalCount > 1) {
                    return false; // Plusieurs états finaux
                }
            }
        }
        return initialCount == 1 && finalCount == 1;
    }

    public boolean isComplete() {
        for (Etat state : states) {
            Set<Character> symbols = new HashSet<>();
            for (Transition transition : state.getTransitions()) {
                symbols.add(transition.getSymbol());
            }
            if (symbols.size() != alphabet.getCharacters().size()) {
                return false; // L'état n'a pas de transition sortante pour chaque symbole
            }
        }
        return true;
    }

    public void standardize() {
        int initialCount = 0;
        int finalCount = 0;
        for (Etat state : states) {
            if (state.isInitial()) {
                initialCount++;
                if (initialCount > 1) {
                    state.setInitial(false); // Plusieurs états initiaux, définir le surplus comme non initial
                }
            }
            if (state.isFinal()) {
                finalCount++;
                if (finalCount > 1) {
                    state.setFinal(false); // Plusieurs états finaux, définir le surplus comme non final
                }
            }
        }
    }

    public boolean isDeterministicComplete() {
        return isDeterministic() && isComplete();
    }



    public void printStandardAutomate() {
        System.out.println("Automate standardisé :\n");
        for (Etat state : states) {
            System.out.println("Nom de l'état : " + state.getName());
            System.out.println("État initial : " + (state.isInitial() ? "Oui" : "Non"));
            System.out.println("État terminal : " + (state.isFinal() ? "Oui" : "Non"));
            System.out.println("Transitions :");
            for (Transition transition : state.getTransitions()) {
                System.out.println("\t" + transition);
            }
            System.out.println();
        }
    }



    public void displayTransitionsTable() {
        // Récupérer tous les symboles de l'alphabet
        Set<Character> alphabetSymbols = alphabet.getCharacters();

        // Déterminer la largeur de chaque colonne
        int stateColumnWidth = 10; // Largeur de la colonne des états
        int symbolColumnWidth = 10; // Largeur de la colonne des symboles

        // En-tête du tableau
        System.out.println("-------------------------------------------------------------");
        System.out.printf("| %-" + stateColumnWidth + "s |", "État");

        for (char symbol : alphabetSymbols) {
            System.out.printf("%-" + symbolColumnWidth + "s |", symbol);
        }
        System.out.println("\n-------------------------------------------------------------");

        // Affichage des transitions
        for (Etat state : states) {
            StringBuilder stateLine = new StringBuilder("|");

            // Formatage de l'état avec les flèches
            String stateName = state.getName();
            if (state.isInitial() && state.isFinal()) {
                stateName = "<-->" + stateName;
            } else if (state.isInitial()) {
                stateName = "-->" + stateName;
            } else if (state.isFinal()) {
                stateName = "<--" + stateName;
            }

            // Ajouter l'état à la ligne
            stateLine.append(String.format(" %-" + stateColumnWidth + "s |", stateName));

            // Créer une map pour stocker les transitions de l'état
            Map<Character, String> transitionMap = new HashMap<>();
            for (Transition transition : state.getTransitions()) {
                transitionMap.put(transition.getSymbol(), transition.getTargetState().getName());
            }

            // Ajouter les transitions pour chaque symbole de l'alphabet
            for (char symbol : alphabetSymbols) {
                String targetState = transitionMap.getOrDefault(symbol, "P");
                stateLine.append(String.format(" %-" + symbolColumnWidth + "s |", targetState));
            }

            // Afficher la ligne de l'état avec ses transitions
            System.out.println(stateLine);
        }

        // Ajouter la ligne pour l'état P
        StringBuilder statePLine = new StringBuilder("|");
        statePLine.append(String.format(" %-" + stateColumnWidth + "s |", "P"));

        for (char symbol : alphabetSymbols) {
            statePLine.append(String.format(" %-" + symbolColumnWidth + "s |", "P"));
        }

        // Afficher la ligne de l'état P
        System.out.println(statePLine);

        // Bas du tableau
        System.out.println("-------------------------------------------------------------");
    }
}