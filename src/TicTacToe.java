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
    public static final String SYMBOLE_INVALIDE = "Le symbole saisi ne respectent pas l'intervalle donnée!";

    public static void afficherMenu(){
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

    public static String saisirNomJoueur(Scanner scan, int noJoueur){
//        String nomJoueur = "";
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

    public static char saisirSymbole(Scanner scan, String nomJoueur) {
        char symbole = ' ';
        try {
            System.out.println();
            System.out.printf(MESSAGE_SYMBOLE, nomJoueur);
            System.out.println(LEGENDE_SYMBOLE);
            symbole = scan.next().charAt(0);
            while (symbole > 'A' && symbole > 'Z') {
                System.out.println();
                System.out.println(SYMBOLE_INVALIDE);
                System.out.println();
                System.out.printf(MESSAGE_SYMBOLE, nomJoueur);
                System.out.println(LEGENDE_SYMBOLE);
                symbole = scan.next().charAt(0);
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println();
            System.out.println(SYMBOLE_INVALIDE);
            System.out.println();
            System.out.println(MESSAGE_SYMBOLE);
            System.out.println(LEGENDE_SYMBOLE);
            symbole = scan.next().charAt(0);
        } return symbole;
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
        List<Integer> rangeeHaut = Arrays.asList(1, 2, 3);
        List<Integer> rangeeMilieu = Arrays.asList(4, 5, 6);
        List<Integer> rangeeBas = Arrays.asList(7, 8, 9);
        List<Integer> colonneGauche = Arrays.asList(1, 4, 7);
        List<Integer> colonneMilieu = Arrays.asList(2, 5, 8);
        List<Integer> colonneDroite = Arrays.asList(3, 6, 9);
        List<Integer> diagonale1 = Arrays.asList(1, 5, 9);
        List<Integer> diagonale2 = Arrays.asList(3, 5, 7);

        List<List<Integer>> gagnant = new ArrayList<>();
        gagnant.add(rangeeHaut);
        gagnant.add(rangeeMilieu);
        gagnant.add(rangeeBas);
        gagnant.add(colonneGauche);
        gagnant.add(colonneMilieu);
        gagnant.add(colonneDroite);
        gagnant.add(diagonale1);
        gagnant.add(diagonale2);

        for (List<Integer> l : gagnant) {
            if (joueur1.getPositions().containsAll(l)) {
                joueur1.incrementerNbVictoire();
                return String.format("%s à gagné", joueur1.getNom());
            } else if (joueur2.getPositions().containsAll(l)) {
                joueur2.incrementerNbVictoire();
                return String.format("%s à gagné", joueur2.getNom());
            } else if (joueur1.getPositions().size() + joueur2.getPositions().size() == 9) {
                return "Match nul!";
            }
        }
        return "";
    }

    public static void main(String[] args) {
        //Déclaration des variables
        Scanner scan = new Scanner(System.in);
        int choixMenu = 0;
        String nomJoueur1 = "";
        String nomJoueur2 = "";
        char symbole1 = ' ';
        char symbole2 = ' ';
        char[][] gameBoard = {{' ', '|', ' ', '|', ' '}, {'-', '+', '-', '+', '-'}, {' ', '|', ' ', '|', ' '}, {'-', '+', '-', '+', '-'}, {' ', '|', ' ', '|', ' '}};

        afficherMenu();

        choixMenu = saisirOptionsMenu(scan);

        switch (choixMenu) {
            case 1:
                nomJoueur1 = saisirNomJoueur(scan, 1);
                symbole1 = saisirSymbole(scan, nomJoueur1);
                Joueur joueur1 = new Joueur(nomJoueur1, symbole1);

                Joueur joueur2 = new Joueur("Ordinateur", 'O');

                imprimerGameBoard(gameBoard);

                while (true) {
                    //position joueur
                    System.out.println("Entrez votre position (1-9)");
                    int positionJoueur = scan.nextInt();
                    while (joueur1.getPositions().contains(positionJoueur) || joueur2.getPositions().contains(positionJoueur)) {
                        System.out.println("La position est deja prise! Entrez une position disponible :");
                        positionJoueur = scan.nextInt();
                        System.out.println();
                    }
                    System.out.println(positionJoueur);

                    placerSymbole(gameBoard, positionJoueur, joueur1);

                    String resultat = determinerGagnant(joueur1, joueur2);
                    if (resultat.length() > 0) {
                        System.out.println();
                        imprimerGameBoard(gameBoard);
                        System.out.println(resultat);
                    }
                    //position joueur
                    System.out.println("Entrez votre position (1-9)");
                    int positionJoueur1 = scan.nextInt();
                    while (joueur1.getPositions().contains(positionJoueur1) || joueur2.getPositions().contains(positionJoueur)) {
                        System.out.println("La position est deja prise! Entrez une position disponible :");
                        positionJoueur = scan.nextInt();
                        System.out.println();
                    }
                    //position ordinateur
                    Random aleatoire = new Random();
                    int positionOrdinateur = aleatoire.nextInt(9) + 1;
                    while (joueur1.getPositions().contains(positionOrdinateur) || joueur2.getPositions().contains(positionOrdinateur)) {
                        positionOrdinateur = aleatoire.nextInt(9) + 1;
                    }
                    placerSymbole(gameBoard, positionOrdinateur, joueur2);

                    imprimerGameBoard(gameBoard);

                    resultat = determinerGagnant(joueur1, joueur2);
                    if (resultat.length() > 0) {
                        System.out.println();
                        imprimerGameBoard(gameBoard);
                        System.out.println(resultat);
                    }
                    System.out.println(resultat);
                }

            case 2:
                nomJoueur1 = saisirNomJoueur(scan, 1);
                nomJoueur2 = saisirNomJoueur(scan, 2);

                symbole1 = saisirSymbole(scan, nomJoueur1);
                symbole2 = saisirSymbole(scan, nomJoueur2);

                joueur1 = new Joueur(nomJoueur1, symbole1);
                joueur2 = new Joueur(nomJoueur2, symbole2);

                imprimerGameBoard(gameBoard);

                while (true) {
                    //position joueur
                    System.out.println("Entrez votre position (1-9)");
                    int positionJoueur = scan.nextInt();
                    while (joueur1.getPositions().contains(positionJoueur) || joueur2.getPositions().contains(positionJoueur)) {
                        System.out.println("La position est deja prise! Entrez une position disponible :");
                        positionJoueur = scan.nextInt();
                        System.out.println();
                    }
                    System.out.println(positionJoueur);

                    placerSymbole(gameBoard, positionJoueur, joueur1);

                    imprimerGameBoard(gameBoard);

                    String resultat = determinerGagnant(joueur1, joueur2);
                    if (resultat.length() > 0) {
                        System.out.println();
                        imprimerGameBoard(gameBoard);
                        System.out.println(resultat);
                    }

                }
           }
    }
}
