package Game;

import java.util.Scanner;
import java.util.Random;

public class GameManager {
    Scanner scanner = new Scanner(System.in);
    Random rand = new Random();

    public void startGame() throws InterruptedException {
        Personnage joueur = selectJoueur();

        // Sélection et amélioration des compétences du joueur si son niveau est supérieur à 4
        SelectionCompetences(joueur.niveau,joueur);
        joueur.afficherStats();

        char playAgain;
        do {
            System.out.println("Voulez-vous changer de Champion ? (O/N)");
            char change = scanner.next().toUpperCase().charAt(0);
            if (change == 'O') {
                joueur = selectJoueur();
                SelectionCompetences(joueur.getNiveau(),joueur);
            }


            Personnage adversaire = selectadversaire(joueur.niveau);
            Adversaireselectioncomp (adversaire.getNiveau(),adversaire);

            // Display stats
            System.out.println("Vous allez combattre : ");
            adversaire.afficherStats();
            
            // Start combat
            Combat combat = new Combat(joueur, adversaire);
            combat.lancer();

            System.out.println("Le combat est terminé !");
            joueur.afficherStats();
            joueur.reinitialiserCombat();

            System.out.println("Voulez-vous rejouer ? (Y/N)");
            playAgain = scanner.next().toUpperCase().charAt(0);
            
        } while (playAgain == 'Y');
    }


    public Personnage selectJoueur() {
        System.out.println("Choisissez votre personnage :");
        System.out.println("1. Guerrier");
        System.out.println("2. Mage");
        System.out.println("3. Voleur");
    
        int choix;
        do {
            while (true) {
                if (scanner.hasNextInt()) {
                    choix = scanner.nextInt();
                    break;
                } else {
                    System.out.println("input invlide . Entrez un entier.");
                    scanner.next(); // Clear invalid input
                }
            }
        } while (choix < 1 || choix > 3);
    
        System.out.println("Selectionnez Votre niveau :(1-99)");
        int niveau;
        do {
            while (true) {
                if (scanner.hasNextInt()) {
                    niveau = scanner.nextInt();
                    break;
                } else {
                    System.out.println("input invlide . Entrez un entier.");
                    scanner.next(); // Clear invalid input
                }
            }
        } while (niveau < 1 || niveau>=100);
        scanner.nextLine();
        String name;
        System.out.println("Donnez un nom à votre Champion :(1-20)");
        do {
            name=scanner.nextLine();
        } while (name.length() < 1 || name.length()>20);
        
        switch (choix) {
            case 1:
                return new Guerrier(name, niveau,175,15,10,2,2);
            case 2:
                return new Mage(name, niveau,160,18,8,2,2);
            case 3:
                return new Voleur(name, niveau,140,20,6,2,2);
            default:
            System.out.println("Choix invalide, Guerrier sélectionné par défaut.");
                return new Guerrier(name, niveau,175,15,10,2,2);
        }
    }

    public void SelectionCompetences(int niveau,Personnage joueur){
        if (niveau>4) {
            System.out.println("Vous avez "+ niveau/5+ " point de competances, veuillez les utiliser :");
            for (int index = 1; index <= niveau/5; index++) {
                joueur.AmeliorerCompetences();
            }
        }
    }

    public  Personnage selectadversaire (int niveauJoueur) {
        int x=rand.nextInt(4)+1;
        int niveauAI;
        //generer le niveau de l'adversaire dans un interval [niveaujoueur-2 ; niveaujoueur+2]
        if (niveauJoueur<=2 || niveauJoueur==100) {
            niveauAI=niveauJoueur;
        } else {
            niveauAI=niveauJoueur+x-2;
        }
    switch (x) {
        case 1:
            return new Guerrier("Thor", niveauAI,175,15,10,2,2);
        case 2:
            return new Mage("Floki", niveauAI,160,18,8,2,2);
        case 3:
            return new Voleur("Loki", niveauAI,140,20,6,2,2);
        default:
            return new Guerrier("Thor", niveauAI,175,15,10,2,2);
    }
}

    public void Adversaireselectioncomp (int niveau,Personnage adversaire){
        for (int index = 1; index <= niveau/5; index++) {
            adversaire.AIameliorercompetance();
        }
    }
}
