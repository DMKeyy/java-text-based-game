package Game;

import java.util.Random;
import java.util.Scanner;

public class Combat {
    static Random rand = new Random();
    static Scanner scanner = new Scanner(System.in);

    private Personnage joueur;
    private Personnage adversaire;

    // Indique si un personnage est en mode défense (réduction des dégâts subis)
    private boolean defenceA,defenceJ;

   /*
    * Variables pour suivre le nombre d'actions réalisées pendant le combat.
    * Elles sont utilisées après le combat pour calculer les points d'expérience
    */
    private int NbAttaque,NbCompetenceSpecial,NbDefence;
    
        public Combat(Personnage joueur, Personnage adversaire) {
            this.joueur = joueur;
            this.adversaire = adversaire;
            this.NbAttaque =0;
            this.NbCompetenceSpecial=0;
            this.NbDefence=0;
        }
    
        // Méthode principale : gère le déroulement complet d'un combat
        public void lancer() throws InterruptedException {
            // Sauvegarde des dégâts initiaux pour réinitialisation après des modifications temporaires (comme les effets de map)
            double SauvegardeDegats=joueur.getDegats();
            double SauvegardeDegatsAI=adversaire.getDegats();

            // Le joueur choisit une arène, chaque map possède des effets uniques
            map MAP=choisirMap();

            // Initialisation des états de défense pour le début du combat
            defenceJ=false;
            defenceA=false;

             // Boucle principale du combat : continue tant que les deux personnages sont vivants
            while (joueur.estVivant() && adversaire.estVivant()) {
                // Réinitialisation des dégâts des personnages à leur valeur de base au début de chaque round
                joueur.setDegats(SauvegardeDegats);
                adversaire.setDegats(SauvegardeDegatsAI);

                // Appel d'un événement spécifique à la map
                MAP.eventRound(joueur, adversaire);
                
                // Réinitialisation de la défense du joueur pour ce round
                defenceJ=false;
                System.out.println("\r\n" + //
                                        "   _____ ___  _   _ ____    ____  _   _       _  ___  _   _ _____ _   _ ____  \r\n" + //
                                        "  |_   _/ _ \\| | | |  _ \\  |  _ \\| | | |     | |/ _ \\| | | | ____| | | |  _ \\ \r\n" + //
                                        "    | || | | | | | | |_) | | | | | | | |  _  | | | | | | | |  _| | | | | |_) |\r\n" + //
                                        "    | || |_| | |_| |  _ <  | |_| | |_| | | |_| | |_| | |_| | |___| |_| |  _ < \r\n" + //
                                        "    |_| \\___/ \\___/|_| \\_\\ |____/ \\___/   \\___/ \\___/ \\___/|_____|\\___/|_| \\_\\\r\n" + //
                                        "                                                                              \r\n" + //
                                        "");

                afficherAction();
                selectionAction();
                
                AfficherToutHP (joueur,adversaire);
    
                // Réinitialisation de la défense de l'adversaire pour son tour
                defenceA=false;

                 // Tour de l'adversaire si celui-ci est encore en vie
                if (adversaire.estVivant()) {
                    System.out.println("\r\n" + //
                                        "  _____ ___  _   _ ____    ____  _____   _     _    _    ______     _______ ____  ____    _    ___ ____  _____ \r\n" + //
                                        " |_   _/ _ \\| | | |  _ \\  |  _ \\| ____| | |   ( )  / \\  |  _ \\ \\   / / ____|  _ \\/ ___|  / \\  |_ _|  _ \\| ____|\r\n" + //
                                        "   | || | | | | | | |_) | | | | |  _|   | |   |/  / _ \\ | | | \\ \\ / /|  _| | |_) \\___ \\ / _ \\  | || |_) |  _|  \r\n" + //
                                        "   | || |_| | |_| |  _ <  | |_| | |___  | |___   / ___ \\| |_| |\\ V / | |___|  _ < ___) / ___ \\ | ||  _ <| |___ \r\n" + //
                                        "   |_| \\___/ \\___/|_| \\_\\ |____/|_____| |_____| /_/   \\_\\____/  \\_/  |_____|_| \\_\\____/_/   \\_\\___|_| \\_\\_____|\r\n" + //
                                        "                                                                                                               \r\n" + //
                                        "");
                    selectionActionAdversaire();

                    // Si le joueur est vaincu, annonce la défaite et met fin au combat
                    if (!joueur.estVivant()) {
                        System.out.println(joueur.getNom() + " est vaincu !");
                        joueur.defaite();// Met à jour les statistiques du joueur
                        joueur.setDegats(SauvegardeDegats);
                        joueur.gagnerExperience(8*NbAttaque+15*NbCompetenceSpecial+NbDefence*4);
                        break;
                    }

                    AfficherToutHP (joueur,adversaire);
                    Thread.sleep(1000);// Pause pour simuler un délai entre les tours
                }
                else {
                     // Si l'adversaire est vaincu, annonce la victoire et met à jour les statistiques du joueur
                    System.out.println(adversaire.getNom() + " est vaincu !");
                    joueur.setDegats(SauvegardeDegats);
                    joueur.gagnerExperience(8*NbAttaque+15*NbCompetenceSpecial+NbDefence*4);
                    joueur.gagnerExperience(joueur.getPointsDeVie());
                    joueur.victoire();
                    
                }
    
            }
        }

    public void afficherAction () {
        System.out.println("Choisissez une action :(1-3)");
            System.out.println("1. Attaquer ");
            System.out.println("2. Defendre");
            System.out.println("3. Utiliser une potion de soin");
    }

    public void selectionAction () {
        int action;

        // Validation de l'entrée pour s'assurer qu'elle est correcte et de type entier
        do {
            while (true) {
                if (scanner.hasNextInt()) {
                    action = scanner.nextInt();
                    break;
                } else {
                    System.out.println("input invlide . Entrez un entier.");
                    scanner.next(); // Clear invalid input
                }
            }
        } while (action<1 || action>3);
        switch (action) {
            case 1:
            selectionAttaque (joueur,adversaire,defenceA);
                break;
            case 2:
            defenceJ=true;
            System.out.println(joueur.nom + " a choisi de se défendre !");
            this.NbDefence++;
                break;
            case 3:
            joueur.utiliserPotion();
                break;
            default:
            System.out.println("Action invalide.");
                break;
        }
    }

    public void selectionAttaque (Personnage joueur,Personnage adversaire,boolean defenceA) {
        System.out.println("Choisissez une attaque :(1-2)");
        System.out.println("1. Attaquer un adversaire");
        System.out.println("2. Utiliser compétence spéciale");
        int choix;

        // Validation de l'entrée pour s'assurer qu'elle est correcte et de type entier
        do {
            while (true) {
                if (scanner.hasNextInt()) {
                    choix = scanner.nextInt();
                    break;
                } else {
                    System.out.println("input invalide . Entrez un entier.");
                    scanner.next(); // Clear invalid input
                }
            }
        } while (choix < 1 || choix > 2);
        switch (choix) {
            case 1:
            joueur.attaquer(adversaire,defenceA);
            this.NbAttaque++;
                break;    
            case 2:
            joueur.utiliserCompetence(adversaire,defenceA);
            this.NbCompetenceSpecial++;
                break;
            default:
            System.out.println("Action invalide.");
                break;
        }
    }

    // Gère les actions de l'adversaire de manière automatisée (IA)
    public void selectionActionAdversaire () {
        int ActionAdversaire=rand.nextInt(2)+1;
        switch (ActionAdversaire) {
        case 1:
        selectionAttaqueAdversaire (joueur,adversaire,defenceJ);
            break;
        case 2:
        defenceA=true;
        System.out.println(adversaire.nom + " a choisi de se défendre !");
            break;
        case 3:
        adversaire.utiliserPotion();
            break;
        default:
        System.out.println(adversaire.getNom() + " passe son tour.");
            break;
        }
    }

    // Choix aléatoire entre attaque normale ou spéciale
    public void selectionAttaqueAdversaire (Personnage joueur,Personnage adversaire,boolean defenceJ){

        int attaque=rand.nextInt(2)+1;
        switch (attaque) {
            case 1:
            adversaire.attaquer(joueur,defenceJ);
                break;
            case 2: 
            adversaire.utiliserCompetence(joueur,defenceJ);
                break;
            default:
            System.out.println(adversaire.getNom() + " passe son tour.");
                break;
        }
    }

    // Affiche les PV actuels des deux personnages sous forme de barre visuelle
    public void AfficherToutHP (Personnage joueur,Personnage adversaire) {
        System.out.println("\n===========================================");
        joueur.afficherHP();
        adversaire.afficherHP();
        System.out.println("===========================================\n");
    }

    public map choisirMap (){
        System.out.println("Choisissez la Map :(1-3)");
        System.out.println("1. Map par default.");
        System.out.println("2. Electric Nexus.");
        System.out.println("3. Chaos Pendulum. ");
        int choix;
        do {
            while (true) {
                if (scanner.hasNextInt()) {
                    choix = scanner.nextInt();
                    break;
                } else {
                    System.out.println("input invalide . Entrez un entier.");
                    scanner.next();
                }
            }
        } while (choix<1||choix>3);

        switch (choix) {
            case 1: return new mapDefaut();
            case 2: 
            System.out.println("Le Nexus Électrique est une arène où les joueurs peuvent être frappés par des\n"+
                                "décharges électriques aléatoires. Ces arcs électriques infligent des dégâts à\n"+
                                "ceux qui sont touchés,ajoutant un élément de risque constant à la bataille. \n"+
                                "Le terrain est marqué par des générateurs énergétiques, et l'environnement \n"+
                                "instable augmente l'imprévisibilité de chaque combat.\n\n");
            return new mapElectric();
            case 3: 
            System.out.println("La Pendule du Chaos est une arène où la réalité elle-même oscille entre ordre et désordre.\n"+
                                "Inspiré par le mouvement imprévisible d'une pendule gigantesque, cet endroit est marqué par\n"+
                                "des perturbations constantes qui mettent à l'épreuve l'endurance et l'adaptabilité des \n"+
                                "combattants. Chaque round les degats des champions changent aleatoirement.");
            return new mapChaos();
            default: return new mapDefaut();
        }

    }
    
}
