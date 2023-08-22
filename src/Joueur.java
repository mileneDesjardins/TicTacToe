import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class Joueur {
    private static String ENTRER_POSITION = "%s Entrez votre position (1-9): ";

    private String nom;
    private int nbVictoire;
    private char symbole;
    private ArrayList<Integer> positions;

    public Joueur(String nom, char symbole){
        setNom(nom);
        this.nbVictoire = 0;
        this.symbole = symbole;
        this.positions = new ArrayList<>();
    }

    public void setNom(String nom){
        if(nom.length() > TicTacToe.MAX_VALEURS || nom.length() < TicTacToe.MIN_VALEURS) {
            throw new RuntimeException(TicTacToe.LEGENDE_NOM_JOUEUR);
        } else {
            this.nom = nom;
        }
    }

    public String getNom(Joueur joueur){
        return nom;
    }

    public void setSymbole(char symbole) {
        if (symbole > 'A' && symbole > 'Z') {
            this.symbole = symbole;
        }
    }

    public void ajouterPosition(int position) {
        this.positions.add(position);
    }

    public int entrerPosition(Joueur joueur, Scanner scan) {
        System.out.printf(ENTRER_POSITION, nom);
        int positionJoueur = scan.nextInt();
        while (this.getPositions().contains(positionJoueur) || joueur.getPositions().contains(positionJoueur)) {
            System.out.println("La position est deja prise! Entrez une position disponible :");
            positionJoueur = scan.nextInt();
            System.out.println();
        }
        return positionJoueur;
    }





    public void incrementerNbVictoire() {
        this.nbVictoire += 1;
    }

    public int getNbVictoire(){
        return this.nbVictoire;
    }

    public String getNom(){
        return this.nom;
    }

    public char getSymbole(){
        return this.symbole;
    }

    public ArrayList<Integer> getPositions() {
        return this.positions;
    }

}

