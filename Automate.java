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



    /* ======================== METHODE loadFromFile BLAINA


    public void loadFromFile(String fileName) {
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                String stateName = parts[0];
                boolean isInitial = parts[1].equals("I");
                boolean isFinal = parts[2].equals("F");
                Etat etat = new Etat(stateName, isInitial, isFinal);
                states.add(etat);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Fichier introuvable : " + fileName);
            e.printStackTrace();
        }
    }



    ==========================================================================================*/


    //============================== METHODE loadFromFiles RINDRA ==============================================


    /*

    public void loadFromFile(String fileName) {
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);

            // Lire le nombre de symboles dans l'alphabet
            int numSymbols = Integer.parseInt(scanner.nextLine());

            // Ajouter chaque symbole à l'alphabet (a, b, ..., correspondant au nombre de symboles)
            for (int i = 0; i < numSymbols; i++) {
                char symbol = (char) ('a' + i);
                alphabet.addCharacter(symbol);
            }

            // Lire le nombre d'états
            int numStates = Integer.parseInt(scanner.nextLine());

            // Créer les états et les ajouter à la liste des états
            for (int i = 0; i < numStates; i++) {
                Etat state = new Etat(Integer.toString(i), false, false); // Créer un état avec l'indice i comme identifiant
                states.add(state);
            }

            // Lire les états initiaux
            String[] initialStates = scanner.nextLine().split(" ");
            for (String state : initialStates) {
                int stateIndex = Integer.parseInt(state);
                if (stateIndex >= 0 && stateIndex < states.size()) {
                    states.get(stateIndex).setInitial(true);
                } else {
                    System.out.println("Indice d'état initial invalide : " + stateIndex);
                }
            }

            // Lire les états terminaux
            String[] finalStates = scanner.nextLine().split(" ");
            for (String state : finalStates) {
                int stateIndex = Integer.parseInt(state);
                if (stateIndex >= 0 && stateIndex < states.size()) {
                    states.get(stateIndex).setFinal(true);
                } else {
                    System.out.println("Indice d'état final invalide : " + stateIndex);
                }
            }

            // Lire le nombre de transitions
            int numTransitions = Integer.parseInt(scanner.nextLine());

            // Lire les transitions
            for (int i = 0; i < numTransitions; i++) {
                String transition = scanner.nextLine();

                // Analyser la transition
                String[] parts = transition.split(" ");
                if (parts.length >= 3) {
                    int startStateIndex = Integer.parseInt(parts[0]);
                    char symbol = parts[1].charAt(0);
                    int endStateIndex = Integer.parseInt(parts[2]);

                    // Vérifier la validité des indices avant l'accès
                    if (startStateIndex >= 0 && startStateIndex < states.size() &&
                            endStateIndex >= 0 && endStateIndex < states.size()) {

                        // Ajouter la transition à l'état de départ correspondant
                        Etat startState = states.get(startStateIndex);
                        Etat endState = states.get(endStateIndex);
                        startState.addTransition(new Transition(endState, symbol));
                    } else {
                        System.out.println("Indices d'état de transition non valides : " + startStateIndex + ", " + endStateIndex);
                    }
                } else {
                    System.out.println("Format de transition invalide : " + transition);
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Fichier introuvable : " + fileName);
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Format de nombre invalide dans le fichier : " + fileName);
            e.printStackTrace();
        }
    }


     */

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

    public Automate toDeterministicCompleteAutomate() {
        // Implémentez la logique pour obtenir l'automate déterministe complet équivalent
        return null;
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



    /* ===================================== METHODE FONCTIONNELLE MAIS MOCHE ======================================================


    public void displayTransitionsTable() {
        // Récupérer tous les symboles de l'alphabet
        Set<Character> alphabetSymbols = alphabet.getCharacters();

        // Créer la première ligne du tableau avec les en-têtes
        StringBuilder headerLine = new StringBuilder("------------------------------------------------------\n");
        headerLine.append("| État   |");
        for (char symbol : alphabetSymbols) {
            headerLine.append(String.format("%7c  |", symbol));
        }
        headerLine.append("\n------------------------------------------------------");

        // Afficher la première ligne avec les en-têtes
        System.out.println(headerLine);

        // Parcourir chaque état pour afficher les transitions
        for (Etat state : states) {
            // Préparer la ligne pour l'état
            StringBuilder stateLine = new StringBuilder("| ");
            if (state.isInitial() && state.isFinal()) {
                stateLine.append("<--> ");
            } else if (state.isInitial()) {
                stateLine.append("--> ");
            } else if (state.isFinal()) {
                stateLine.append("    ");
            } else {
                stateLine.append("      ");
            }
            stateLine.append(state.getName());
            if (state.isFinal()) {
                stateLine.append(" <-- ");
            } else if (state.isInitial() && !state.isFinal()) {
                stateLine.append(" --> ");
            } else {
                stateLine.append("     ");
            }
            stateLine.append("|");

            // Récupérer les transitions de l'état
            Map<Character, String> transitionMap = new HashMap<>();
            for (Transition transition : state.getTransitions()) {
                transitionMap.put(transition.getSymbol(), transition.getTargetState().getName());
            }

            // Ajouter les transitions dans l'ordre des symboles de l'alphabet
            for (char symbol : alphabetSymbols) {
                String targetState = transitionMap.getOrDefault(symbol, "");
                stateLine.append(String.format("%7s  |", targetState));
            }

            // Afficher la ligne de l'état avec ses transitions
            System.out.println(stateLine);
        }

        // Afficher la ligne de fin du tableau
        System.out.println("------------------------------------------------------");
    }


    private String getTransitionSymbol(Etat state, char symbol) {
        for (Transition transition : state.getTransitions()) {
            if (transition.getSymbol() == symbol) {
                return " " + transition.getTargetState().getName() + " ";
            }
        }
        return "      ";
    }
    ========================================================================================================
     */



    /* =================================== AFFICHAGE A MOITIE FONCTIONNEL MAIS BEAU =========================================

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
                stateName = "--> " + stateName;
            } else if (state.isFinal()) {
                stateName = stateName + " <--";
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
                String targetState = transitionMap.getOrDefault(symbol, "");
                stateLine.append(String.format(" %-" + symbolColumnWidth + "s |", targetState));
            }

            // Afficher la ligne de l'état avec ses transitions
            System.out.println(stateLine);
        }

        // Bas du tableau
        System.out.println("-------------------------------------------------------------");
    }

   ============================================================================================================*/


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

        // Bas du tableau
        System.out.println("-------------------------------------------------------------");
    }





}
