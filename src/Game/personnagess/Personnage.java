package Game.personnagess;
import java.util.Scanner;
import java.util.Random;
public abstract class Personnage implements Attaquable {
	protected String nom;

	protected int maxpointsDeVie,pointsDeVie;

	protected double degats;
	protected int defense;

	protected int niveau,maxexp,experience;
	private static final int NIVEAU_MAX = 100;

	protected int maxpotion,potionUsed;

	protected int maxCompetence,CompetenceUsed;
	
	protected int winstreak,nbvicoire,nbdefaite;


	public Personnage(String nom, int niveau,int PVdepart,double DGdepart,int DFdepart,int NCdepart,int PTdepart)  {
		this.nom = nom;

		this.maxpointsDeVie = PVdepart;
		this.pointsDeVie = maxpointsDeVie;

		this.degats = DGdepart;
		this.defense= DFdepart; 
		this.niveau = niveau;

		this.maxexp=CalculerMXP();
		this.experience = 0;

		this.maxpotion=PTdepart;
		this.potionUsed=maxpotion;

		this.maxCompetence=NCdepart;
		this.CompetenceUsed=maxCompetence;

		this.winstreak=0;
		this.nbdefaite=0;
		this.nbvicoire=0;
		
	}


	public String getNom() {
		return nom;
	}

	public int getPointsDeVie() {
		return pointsDeVie;
	}

	public void setPointsDeVie(int pointsDeVie) {
		this.pointsDeVie = pointsDeVie;
	}

	public int getWS(){
		return winstreak;
	}

	public int getNiveau() {
		return niveau;
	}


	public double getDegats() {
		return degats;
	}
	

	public void setDegats(double degats) {
		this.degats = degats;
	}

	// Retourne une barre de vie visuelle représentant les PV restants du personnage
	public String getHPBar() {
		int pourcentageHP = (int) (((double) pointsDeVie / maxpointsDeVie) * 10);
		StringBuilder BarHP = new StringBuilder("██████████");
		for (int i = 0; i < 10 - pourcentageHP; i++) {
			BarHP.setCharAt(i + pourcentageHP, '░');
		}
		return BarHP.toString();
	}

	// Retourne une barre d'expérience visuelle représentant le progrès vers le niveau suivant
	public String getEXPBar() {
		int pourcentageEXP = (int) (((double) experience / maxexp) * 10);
		StringBuilder BarEXP = new StringBuilder("██████████");
		for (int i = 0; i < 10 - pourcentageEXP; i++) {
			BarEXP.setCharAt(i + pourcentageEXP, '░');
		}
		return BarEXP.toString();
	}

	public boolean estVivant() {
		return pointsDeVie > 0;
	}

	public String determinerClasse() {
        if (this instanceof Guerrier) {
            return "Guerrier";
        } else if (this instanceof Mage) {
            return "Mage";
        } else if (this instanceof Voleur) {
            return "Voleur";
        } else {
            return "Classe inconnue";
        }
	}

	public void afficherStats () {
		System.out.println("\n\n===========================================");
		System.out.println(
						"   ╔═╗╦  ╔═╗╦ ╦╔═╗╦═╗  ╔═╗╔╦╗╔═╗╔╦╗╔═╗\r\n" + //
						"   ╠═╝║  ╠═╣╚╦╝║╣ ╠╦╝  ╚═╗ ║ ╠═╣ ║ ╚═╗\r\n" + //
						"   ╩  ╩═╝╩ ╩ ╩ ╚═╝╩╚═  ╚═╝ ╩ ╩ ╩ ╩ ╚═╝\r\n" + //
						"");
		System.out.println("===========================================");
        System.out.println("         " + nom + " - Level "+niveau+" "+this.determinerClasse());
        System.out.println("===========================================");
        System.out.println("HP:  "+ maxpointsDeVie);
		afficherEXP ();
        System.out.println("-------------------------------------------");
        System.out.println("Competences:");
        System.out.println("- Degat   :     " + (int) Math.round(degats));
        System.out.println("- Defence:      " + defense);
		System.out.println("- Potion de soin :"+maxpotion);
		System.out.println("- Nombre d'attaque special :"+maxCompetence);
        System.out.println("-------------------------------------------");
		System.out.println("- Nombre de victoire : "+nbvicoire);
		System.out.println("- Serie de victoire : " + winstreak);
		System.out.println("- Nombre de defaite : "+ nbdefaite);
		System.out.println("-------------------------------------------\n\n");

	}

	public void afficherHP (){
		System.out.println(this.getNom()+" HP:  " + pointsDeVie + " / " + maxpointsDeVie + " \n    " + getHPBar()+"\n");
	}

	public void afficherEXP () {
		System.out.println("XP:  " + experience + " / " + maxexp + "  \n    " + getEXPBar());
	}


	protected abstract int CalculerPV(int PVdepart);
    protected abstract double CalculerDG(double DGdepart);
    protected abstract int CalculerDF(int DFdepart);

	protected int CalculerMC(int NCdepart){
		return NCdepart+1;
	}

	protected int CalculerMP(int MPdepart) {
		return MPdepart+1;
	}

	public int CalculerMXP () {
		return 100+25*niveau;
	}

	// Réduit les PV du personnage en fonction des dégâts subis (ajustés par la défense si active)
	public void recevoirDegats(Double degats) {
		pointsDeVie -= degats;
		if (pointsDeVie < 0) {
			pointsDeVie = 0;
		}
		System.out.println(nom + " a reçu " + (int) Math.round(degats) + " points de dégâts.");
	}

	public void utiliserPotion() {
        if (potionUsed > 0) {
            this.pointsDeVie += maxpointsDeVie/10;
            if (this.pointsDeVie > maxpointsDeVie) {
                this.pointsDeVie = maxpointsDeVie;
            }
            potionUsed--;
            System.out.println(this.nom + " utilise une potion de soin et récupère "+ maxpointsDeVie/10+" points de vie !");
        } else {
            System.out.println(this.nom + " n'a plus de potions de soin !");
        }
    }

		// Méthode pour passer au niveau supérieur et améliorer les attributs du personnage
	public void niveauup () {
		niveau++;
		if (niveau%5==0) {
			AmeliorerCompetences();
		}
		if (niveau%20==0) {
			maxpotion=CalculerMP(maxpotion);
		}
		if (niveau%30==0) {
			maxCompetence=CalculerMC(maxCompetence);
		}
		maxpointsDeVie=CalculerPV(maxpointsDeVie);
		degats=CalculerDG(degats);
		defense=CalculerDF(defense);
		maxexp=CalculerMXP();
	}

	public void gagnerExperience(int xp) {
		experience += xp;
		while (experience >= maxexp&& niveau<NIVEAU_MAX) {
			experience -= maxexp;
			System.out.println(nom + " a atteint le niveau " + niveau + " !");
			niveauup();// Augmente les stats en montant de niveau
		}
		if (niveau==NIVEAU_MAX) {
			experience=maxexp; // L'XP est bloquée au maximum au niveau 100
		}


	}

	public void AmeliorerCompetences (){
		Scanner scanner = new Scanner(System.in);
		System.out.println("\n--- Amélioration des Compétences pour " + nom + " ---");
        System.out.println("1. Augmenter les points de vie max (+20)");
        System.out.println("2. Augmenter les dégâts d'attaque (+1)");
        System.out.println("3. Augmenter la défense (+2)");
        System.out.println("Choisissez une compétence à améliorer : (1-3)");
		int choix;
		do {
            while (true) {
                if (scanner.hasNextInt()) {
                    choix = scanner.nextInt();
                    break;
                } else {
                    System.out.println("input invlide . Entree un entier.");
                    scanner.next(); // Clear invalid input
                }
            }
        } while (choix<1 || choix>3);

        switch (choix) {
            case 1:
                maxpointsDeVie += 20;
				pointsDeVie+=20;
                System.out.println(nom + " a maintenant " + maxpointsDeVie + " points de vie max !");
                break;
            case 2:
                degats += 1;
                System.out.println(nom + " inflige maintenant " + degats + " dégâts par attaque !");
                break;
            case 3:
                defense += 2;
                System.out.println(nom + " a maintenant " + defense + " points de défense !");
                break;
            default:
                System.out.println("Choix invalide. Aucune compétence n'a été améliorée.");
				break;
        }
	}

	public void AIameliorercompetance () {
		Random rand = new Random();
		int choix=rand.nextInt(3)+1;
		switch (choix) {
            case 1:
                maxpointsDeVie += 10;
                break;
            case 2:
                degats += 2;
                break;
            case 3:
                defense += 2;
                break;
            default:
			break;
        }
	}

	public void victoire () {
		this.winstreak++;
		this.nbvicoire++;
		checkWS (winstreak);
	}

	public void checkWS (int winstreak) {
		switch (winstreak) {
			case 5:
				this.degats+=5;
				break;
			case 10:
				this.maxpointsDeVie+=3*niveau;
				this.degats+=5;
				break;
			case 20 :
			this.degats+=5;
			this.maxCompetence+=1;
			default:
				break;
		}
	}

	public void defaite (){
		this.nbdefaite++;
		if (winstreak>=5) {
			finWS(winstreak);
		}
		this.winstreak=0;
	}

	public void finWS (int winstreak) {

		if (winstreak<10 && winstreak >=5) {
			this.degats-=5;
		}
		else if (winstreak>=10 && winstreak<20) {
			this.degats-=10;
			this.maxpointsDeVie-=20;
		}
		else {
			this.degats-=15;
			this.maxCompetence-=1;
		}
	}

	public void reinitialiserCombat() {
        this.pointsDeVie = maxpointsDeVie;  // Reset to max health
        this.potionUsed = maxpotion;  // Reset potion count
		this.CompetenceUsed=maxCompetence; 
    }	
}

