import java.util.*;

public class TicTacToe {
    //BIENVENUE ET CHOIX
    public static final String BORDURE_BIENVENUE = "---------------------------------------------------------------";
    public static final String TITRE = "\t \t \t \t \t TIC TAC TOE le jeu!";
    public static final String MESSAGE_MENU = "Veuillez choisir l'une des options suivantes :";
    public static final String DETAILS_CHOIX = "1. Un joueur \n" + "2. Deux joueurs";
    public static final int CHOIX_1 = 1;
    public static final int CHOIX_2 = 2;
    public static final String CHOIX_INVALIDE = "L'option choisie est invalide!";
    //INFORMATIONS JOUEUR
    public static final String NOM_JOUEUR = "Joueur %d veuillez saisir votre nom :";
    public static final int MAX_VALEURS = 15;
    public static final int MIN_VALEURS = 2;
    public static final String LEGENDE_NOM_JOUEUR = "Votre nom doit contenir un minimum de 1 caractère";
    //CHOIX SYMBOLES
    public static final String MESSAGE_SYMBOLE = "%s veuillez choisir la lettre que vous utiliserez durant le jeu :";
    public static final String LEGENDE_SYMBOLE = "(A à Z)";
    public static final String LEGENDE_SYMBOLE_ORDI = "(A à Z excepté 0)";
    public static final String SYMBOLE_INVALIDE = "Le symbole saisi ne respectent pas l'intervalle donnée!";
    //PARTIE
    public static final String AUTRE_PARTIE = "Souhaitez-vous jouer une autre partie?";
    public static final String LEGENDE_OUI_NON = "(O ou o pour oui, N ou n pour non)";

    public static void afficherMenu() {
        int choix;
        System.out.println(BORDURE_BIENVENUE);
        System.out.println(TITRE);
        System.out.println(BORDURE_BIENVENUE);
        System.out.println();
    }

    public static int saisirOptionsMenu(Scanner scan) {
        int choix = -1;
        do {
            try {
                System.out.println(MESSAGE_MENU);
                System.out.println(DETAILS_CHOIX);
                choix = scan.nextInt();
                while (choix != CHOIX_1 && choix != CHOIX_2) {
                    System.out.println();
                    System.out.println(CHOIX_INVALIDE);
                    System.out.println();
                    System.out.println(MESSAGE_MENU);
                    System.out.println(DETAILS_CHOIX);
                    choix = scan.nextInt();
                }
            } catch (InputMismatchException e) {
                System.out.println();
                System.out.println(CHOIX_INVALIDE);
                System.out.println();
                scan.nextLine();
            }
        } while (choix != CHOIX_1 && choix != CHOIX_2);
        scan.nextLine();
        return choix;
    }

    public static String saisirNomJoueur(Scanner scan, int noJoueur) {
        System.out.println();
        System.out.printf(NOM_JOUEUR, noJoueur);
        String nomJoueur = scan.nextLine();
        if (nomJoueur.length() > MAX_VALEURS || nomJoueur.length() < MIN_VALEURS) {
            System.out.println();
            System.out.println(LEGENDE_NOM_JOUEUR);
            System.out.println();
            System.out.printf(NOM_JOUEUR, noJoueur);
            nomJoueur = scan.nextLine();
        }
        return nomJoueur;
    }

    public static char saisirSymbole(int choixMenu, Scanner scan, String nomJoueur) {
        char symbole = ' ';
        try {
            if (choixMenu == 1) {
                System.out.println();
                System.out.printf(MESSAGE_SYMBOLE, nomJoueur);
                System.out.println(LEGENDE_SYMBOLE_ORDI);
                symbole = scan.next().charAt(0);
                while (symbole < 'A' || symbole > 'Z' || symbole == 'O') {
                    System.out.println();
                    System.out.println(SYMBOLE_INVALIDE);
                    System.out.println();
                    System.out.printf(MESSAGE_SYMBOLE, nomJoueur);
                    System.out.println(LEGENDE_SYMBOLE_ORDI);
                    symbole = scan.next().charAt(0);
                }
            } else {
                System.out.println();
                System.out.printf(MESSAGE_SYMBOLE, nomJoueur);
                System.out.println(LEGENDE_SYMBOLE);
                symbole = scan.next().charAt(0);
                while (symbole < 'A' || symbole > 'Z') {
                    System.out.println();
                    System.out.println(SYMBOLE_INVALIDE);
                    System.out.println();
                    System.out.printf(MESSAGE_SYMBOLE, nomJoueur);
                    System.out.println(LEGENDE_SYMBOLE);
                    symbole = scan.next().charAt(0);
                }
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println();
            System.out.println(SYMBOLE_INVALIDE);
            System.out.println();
            System.out.println(MESSAGE_SYMBOLE);
            System.out.println(LEGENDE_SYMBOLE);
            symbole = scan.next().charAt(0);
        }
        return symbole;
    }

    public static void imprimerGameBoard(char[][] gameBoard) {
        for (char[] row : gameBoard) {
            for (char c : row) {
                System.out.print(c);
            }
            System.out.println();
        }
    }

    public static void placerSymbole(char[][] gameBoard, int position, Joueur joueur) {
        char symbole = joueur.getSymbole();
        joueur.ajouterPosition(position);

        switch (position) {
            case 1 -> gameBoard[0][0] = symbole;
            case 2 -> gameBoard[0][2] = symbole;
            case 3 -> gameBoard[0][4] = symbole;
            case 4 -> gameBoard[2][0] = symbole;
            case 5 -> gameBoard[2][2] = symbole;
            case 6 -> gameBoard[2][4] = symbole;
            case 7 -> gameBoard[4][0] = symbole;
            case 8 -> gameBoard[4][2] = symbole;
            case 9 -> gameBoard[4][4] = symbole;
        }
    }

    public static String determinerGagnant(Joueur joueur1, Joueur joueur2) {
        String resultat = "";
        List<Integer> rangeeHaut = Arrays.asList(1, 2, 3);
        List<Integer> rangeeMilieu = Arrays.asList(4, 5, 6);
        List<Integer> rangeeBas = Arrays.asList(7, 8, 9);
        List<Integer> colonneGauche = Arrays.asList(1, 4, 7);
        List<Integer> colonneMilieu = Arrays.asList(2, 5, 8);
        List<Integer> colonneDroite = Arrays.asList(3, 6, 9);
        List<Integer> diagonale1 = Arrays.asList(1, 5, 9);
        List<Integer> diagonale2 = Arrays.asList(3, 5, 7);

        List<List<Integer>> gagnant = ajouterCombinaisonsGagnantes(rangeeHaut, rangeeMilieu, rangeeBas, colonneGauche, colonneMilieu, colonneDroite, diagonale1, diagonale2);

        for (List<Integer> l : gagnant) {
            if (joueur1.getPositions().containsAll(l)) {
                joueur1.incrementerNbVictoire();
                return String.format("%s a gagné!!! Nombre de victoire(s): %d", joueur1.getNom(),
                        joueur1.getNbVictoire());
            } else if (joueur2.getPositions().containsAll(l)) {
                joueur2.incrementerNbVictoire();
                return String.format("%s a gagné!!! Nombre de victoire(s): %d", joueur2.getNom(),
                        joueur2.getNbVictoire());
            } else if (joueur1.getPositions().size() + joueur2.getPositions().size() == 9) {
                return "Match nul!";
            }
        }
        return resultat;
    }

    private static List<List<Integer>> ajouterCombinaisonsGagnantes(List<Integer> rangeeHaut, List<Integer> rangeeMilieu, List<Integer> rangeeBas, List<Integer> colonneGauche, List<Integer> colonneMilieu, List<Integer> colonneDroite, List<Integer> diagonale1, List<Integer> diagonale2) {
        List<List<Integer>> gagnant = new ArrayList<>();
        gagnant.add(rangeeHaut);
        gagnant.add(rangeeMilieu);
        gagnant.add(rangeeBas);
        gagnant.add(colonneGauche);
        gagnant.add(colonneMilieu);
        gagnant.add(colonneDroite);
        gagnant.add(diagonale1);
        gagnant.add(diagonale2);
        return gagnant;
    }

    public static char jouerUneAutrePartie(Scanner scan) {
        System.out.println();
        char reponse = ' ';
        System.out.println(AUTRE_PARTIE);
        System.out.print(LEGENDE_OUI_NON);
        reponse = scan.next().charAt(0);
        while (reponse != 'O' && reponse != 'o' && reponse != 'N' && reponse != 'n') {
            System.out.println();
            System.out.println("La réponse entrée est invalide");
            System.out.println();
            System.out.println(AUTRE_PARTIE);
            System.out.println(LEGENDE_OUI_NON);
            reponse = scan.next().charAt(0);
        }
        return reponse;
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int choixMenu = 0;
        String nomJoueur1 = "";
        String nomJoueur2 = "";
        char symbole1 = ' ';
        char symbole2 = ' ';
        char reponse = ' ';
        String resultat = "";
        char[][] gameBoard = {{' ', '|', ' ', '|', ' '}, {'-', '+', '-', '+', '-'}, {' ', '|', ' ', '|', ' '}, {'-', '+', '-', '+', '-'}, {' ', '|', ' ', '|', ' '}};

        afficherMenu();

        choixMenu = saisirOptionsMenu(scan);

        switch (choixMenu) {
            case 1:
                nomJoueur1 = saisirNomJoueur(scan, 1);
                symbole1 = saisirSymbole(1, scan, nomJoueur1);

                Joueur joueur1 = new Joueur(nomJoueur1, symbole1);
                Joueur joueur2 = new Joueur("Ordinateur", 'O');

                imprimerGameBoard(gameBoard);

                while (reponse != 'N' && reponse != 'n') {
                    while (resultat.equals("")) {
                        int positionJoueur1 = joueur1.entrerPosition(joueur1, scan);
                        placerSymbole(gameBoard, positionJoueur1, joueur1);

                        resultat = determinerGagnant(joueur1, joueur2);
                        imprimerResultat(gameBoard, resultat);
                        if (!resultat.equals("")) {
                            break;
                        }

                        Random aleatoire = new Random();
                        int positionOrdinateur = aleatoire.nextInt(9) + 1;
                        positionOrdinateur = placerPositionOrdinateur(joueur1, joueur2, aleatoire, positionOrdinateur);
                        placerSymbole(gameBoard, positionOrdinateur, joueur2);
                        imprimerGameBoard(gameBoard);

                        resultat = determinerGagnant(joueur1, joueur2);
                        imprimerResultat(gameBoard, resultat);
                        if (!resultat.equals("")) {
                            break;
                        }
                    }
                    reponse = jouerUneAutrePartie(scan);
                    if (reponse != 'N' && reponse != 'n') {
                        resultat = "";
                        joueur1.getPositions().clear();
                        joueur2.getPositions().clear();
                        gameBoard = new char[][]{{' ', '|', ' ', '|', ' '}, {'-', '+', '-', '+', '-'}, {' ', '|', ' ', '|', ' '}, {'-', '+', '-', '+', '-'}, {' ', '|', ' ', '|', ' '}};
                    }
                }
                break;

            case 2:
                nomJoueur1 = saisirNomJoueur(scan, 1);
                nomJoueur2 = saisirNomJoueur(scan, 2);

                symbole1 = saisirSymbole(2, scan, nomJoueur1);
                symbole2 = saisirSymbole(2, scan, nomJoueur2);

                joueur1 = new Joueur(nomJoueur1, symbole1);
                joueur2 = new Joueur(nomJoueur2, symbole2);

                while (reponse != 'N' && reponse != 'n') {
                    imprimerGameBoard(gameBoard);
                    while (resultat.equals("")) {
                        int positionJoueur1 = joueur1.entrerPosition(joueur1, scan);
                        placerSymbole(gameBoard, positionJoueur1, joueur1);
                        imprimerGameBoard(gameBoard);

                        resultat = determinerGagnant(joueur1, joueur2);
                        imprimerResultat(gameBoard, resultat);
                        if (!resultat.equals("")) {
                            break;
                        }

                        int positionJoueur2 = joueur2.entrerPosition(joueur2, scan);
                        placerSymbole(gameBoard, positionJoueur2, joueur2);
                        imprimerGameBoard(gameBoard);

                        resultat = determinerGagnant(joueur1, joueur2);
                        imprimerResultat(gameBoard, resultat);
                        if (!resultat.equals("")) {
                            break;
                        }
                    }
                    if (reponse != 'N' && reponse != 'n') {
                        reponse = jouerUneAutrePartie(scan);
                        resultat = "";
                        joueur1.getPositions().clear();
                        joueur2.getPositions().clear();
                        gameBoard = new char[][]{{' ', '|', ' ', '|', ' '}, {'-', '+', '-', '+', '-'}, {' ', '|', ' ', '|', ' '}, {'-', '+', '-', '+', '-'}, {' ', '|', ' ', '|', ' '}};
                    }
                }

        }
        System.out.println();
        System.out.println("À la prochaine!!!");
    }

    private static int placerPositionOrdinateur(Joueur joueur1, Joueur joueur2, Random aleatoire, int positionOrdinateur) {
        while (joueur1.getPositions().contains(positionOrdinateur) || joueur2.getPositions().contains(positionOrdinateur)) {
            positionOrdinateur = aleatoire.nextInt(9) + 1;
        }
        return positionOrdinateur;
    }

    private static void imprimerResultat(char[][] gameBoard, String resultat) {
        if (resultat.length() > 0) {
            System.out.println();
            System.out.println(resultat);
        }
    }
}
