package Game.personnagess;

public class Guerrier extends Personnage {
    public Guerrier(String nom,int niveau,int PVdepart,double DGdepart,int DFdepart,int NCdepart,int PTdepart) {
        super(nom,niveau,PVdepart+18*niveau,DGdepart+2*niveau,DFdepart+2*niveau,NCdepart+niveau/30, PTdepart+niveau/20);
    }

    protected int CalculerPV(int maxpointsDeVie) {
        return maxpointsDeVie + 18;
    }

    protected double CalculerDG(double DGdepart) {
        return DGdepart +  2;
    }

    protected int CalculerDF(int DFdepat) {
        return DFdepat + 2;
    }
    @Override
    public void attaquer(Personnage cible,boolean defenseActive) {
        System.out.println(nom + " attaque " + cible.getNom() + " !");
        double degatsInfliges = degats;
        if (defenseActive==true) {
            System.out.println(cible.nom+ " a defendu votre coup");
            degatsInfliges -= cible.defense;
            if (degatsInfliges < 0) degatsInfliges = 0;
        }
        cible.recevoirDegats(degatsInfliges);
    }

    @Override
    public void utiliserCompetence(Personnage cible,boolean defenseActive) {
        if (CompetenceUsed>0) {
            double degatsInfliges = degats;
            System.out.println(nom + " utilise sa compétence spéciale : Coup de rage !");
            if (defenseActive==true) {
                System.out.println(cible.nom+ " a defendu votre coup");
                degatsInfliges -= cible.defense;
                if (degatsInfliges < 0) degatsInfliges = 0;
            }
            cible.recevoirDegats(degatsInfliges * 1.2);
            CompetenceUsed--;
        }
        else {
            System.out.println(nom + " ne peut plus utiliser d'attaque spéciale !");
        }
    }
}
