import java.util.*;

public class TicTacToe {
    static ArrayList<Integer> positionsJoueur = new ArrayList<>();
    static ArrayList<Integer> positionsOrdinateur = new ArrayList<>();

    public static void imprimerGameBoard(char[][] gameBoard) {
        for (char[] row : gameBoard) {
            for (char c : row) {
                System.out.print(c);
            }
            System.out.println();
        }
    }

    public static void placerLeSymbole(char[][] gameBoard, int position, String user) {
        char symbole = ' ';
        if (user.equals("joueur")) {
            symbole = 'X';
            positionsJoueur.add(position);
        } else if (user.equals("ordinateur")) {
            symbole = 'O';
            positionsOrdinateur.add(position);
        }

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

    public static String determinerGagnant() {
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
            if (positionsJoueur.containsAll(l)) {
                return "Feliciations vous avez gagne!";
            } else if (positionsOrdinateur.containsAll(l)) {
                return "Lordinateur a gagne!";
            } else if (positionsJoueur.size() + positionsOrdinateur.size() == 9) {
                return "Match nul!";
            }
        }
        return "";
    }

    public static void main(String[] args) {

        char[][] gameBoard = {{' ', '|', ' ', '|', ' '}, {'-', '+', '-', '+', '-'}, {' ', '|', ' ', '|', ' '}, {'-', '+', '-', '+', '-'}, {' ', '|', ' ', '|', ' '}};

        imprimerGameBoard(gameBoard);

        while (true) {
            //position joueur
            Scanner scan = new Scanner(System.in);
            System.out.println("Entrez votre position (1-9)");
            int positionJoueur = scan.nextInt();
            while (positionsJoueur.contains(positionJoueur) || positionsOrdinateur.contains(positionJoueur)) {
                System.out.println("La position est deja prise! Entrez une position disponible :");
                positionJoueur = scan.nextInt();
                System.out.println();
            }
            System.out.println(positionJoueur);

            placerLeSymbole(gameBoard, positionJoueur, "joueur");

            String resultat = determinerGagnant();
            if (resultat.length() > 0) {
                System.out.println();
                imprimerGameBoard(gameBoard);
                System.out.println(resultat);
                break;
            }
            //position ordinateur
            Random aleatoire = new Random();
            int positionOrdinateur = aleatoire.nextInt(9) + 1;
            while (positionsJoueur.contains(positionOrdinateur) || positionsOrdinateur.contains(positionOrdinateur)) {
                positionOrdinateur = aleatoire.nextInt(9) + 1;
            }
            placerLeSymbole(gameBoard, positionOrdinateur, "ordinateur");
            imprimerGameBoard(gameBoard);

            resultat = determinerGagnant();
            if (resultat.length() > 0) {
                System.out.println();
                imprimerGameBoard(gameBoard);
                System.out.println(resultat);
                break;
            }
            System.out.println(resultat);
        }
    }
}
