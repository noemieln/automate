import java.util.*;

public class Main {
    private static final String TEST_AUTOMATES_FOLDER = "Tests";
    public static void main(String[] args) {

        /*  MENU BLAINA

        Scanner scanner = new Scanner(System.in);
        Automate automate = new Automate();

        // Étape 1 : Charger l'automate depuis un fichier
        automate.loadFromFile("automate.txt");

        // Étape 2 : Afficher les informations sur l'automate
        System.out.println("Informations sur l'automate :");
        System.out.println("Déterministe : " + automate.isDeterministic());
        System.out.println("Standard : " + automate.isStandard());
        System.out.println("Complet : " + automate.isComplete());

        // Étape 3 : Standardiser l'automate si nécessaire
        if (!automate.isStandard()) {
            System.out.println("L'automate n'est pas standard.");
            System.out.print("Voulez-vous le standardiser ? (Oui/Non) : ");
            String choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("Oui")) {
                automate.standardize();
                System.out.println("L'automate a été standardisé avec succès !");
            } else {
                System.out.println("L'automate reste inchangé.");
            }
        }

        // Étape 4 : Obtention de l'automate déterministe complet équivalent
        if (!automate.isDeterministicComplete()) {
            System.out.println("L'automate n'est pas déterministe complet.");
            System.out.print("Voulez-vous obtenir l'automate déterministe complet équivalent ? (Oui/Non) : ");
            String choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("Oui")) {
                Automate deterministicCompleteAutomate = automate.toDeterministicCompleteAutomate();
                System.out.println("Automate déterministe complet équivalent obtenu :");
                System.out.println(deterministicCompleteAutomate);
            } else {
                System.out.println("L'automate reste inchangé.");
            }
        }

        scanner.close();

         */

        // ===================================================== MENU TEST ==========================================


        Scanner scanner = new Scanner(System.in);
        Automate automate = null;

        // Menu interactif pour choisir et tester un automate
        boolean exit = false;
        while (!exit) {
            System.out.println("\nMenu - Test d'automate");
            System.out.println("1. Sélectionner un automate à tester");
            System.out.println("2. Afficher les informations sur l'automate");
            System.out.println("3. Standardiser l'automate si nécessaire");
            System.out.println("4. Afficher la table des transitions");
            System.out.println("5. Quitter");
            System.out.print("Choix : ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Pour consommer le saut de ligne

            switch (choice) {
                case 1:
                    int automateNumber = chooseAutomateNumber(scanner);
                    automate = loadAutomate(automateNumber);
                    break;
                case 2:
                    displayAutomateInfo(automate);
                    break;
                case 3:
                    standardizeAutomateIfNeeded(automate);
                    break;
                case 4:
                    automate.displayTransitionsTable();
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez choisir une option valide.");
            }
        }

        scanner.close();
    }

    private static int chooseAutomateNumber(Scanner scanner) {
        System.out.println("Quel automate voulez-vous tester ? (1-40)");
        System.out.print("Numéro de l'automate : ");
        return scanner.nextInt();
    }

    private static Automate loadAutomate(int automateNumber) {
        String automateFileName = String.format("C:\\Users\\WolfoS\\IdeaProjects\\Projet Automates finis\\src\\%s\\D5-%d.txt", TEST_AUTOMATES_FOLDER, automateNumber);
        Automate automate = new Automate();
        automate.loadFromFile(automateFileName);
        System.out.println("Automate chargé : D5-" + automateNumber);
        return automate;
    }

    private static void displayAutomateInfo(Automate automate) {
        if (automate == null) {
            System.out.println("Aucun automate chargé.");
        } else {
            System.out.println("Informations sur l'automate :");
            System.out.println("Déterministe : " + automate.isDeterministic());
            System.out.println("Standard : " + automate.isStandard());
            System.out.println("Complet : " + automate.isComplete());
        }
    }

    private static void standardizeAutomateIfNeeded(Automate automate) {
        if (automate == null) {
            System.out.println("Aucun automate chargé.");
        } else {
            if (!automate.isStandard()) {
                System.out.println("L'automate n'est pas standard.");
                System.out.print("Voulez-vous le standardiser ? (Oui/Non) : ");
                Scanner scanner = new Scanner(System.in);
                String choice = scanner.nextLine();
                if (choice.equalsIgnoreCase("Oui")) {
                    automate.standardize();
                    System.out.println("L'automate a été standardisé avec succès !");
                } else {
                    System.out.println("L'automate reste inchangé.");
                }
            } else {
                System.out.println("L'automate est déjà standard.");
            }
        }
    }
}

/*IMPLEMENTATION DU MENU : FINIR STANDARDISATION. VERIFIER DETERMINISATION ET AUTRES FONCTIONS
AFFICHAGE OK

Syntaxe des fichiers txt :

Ligne 1 : nombre de symboles dans l’alphabet de l’automate.
Ligne 2 : nombre d’états.
Ligne 3 : nombre d’états initiaux, suivi de leurs numéros.
Ligne 4 : nombre d’états terminaux, suivi de leurs numéros.
Ligne 5 : nombre de transitions.
Lignes 6 et suivantes : transitions sous la forme
<état de départ><symbole><état d’arrivée>

*/