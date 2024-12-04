package Game;

import java.util.Random;

class Voleur extends Personnage {
    public Voleur(String nom,int niveau,int PVdepart,double DGdepart,int DFdepart,int NCdepart,int PTdepart) {
        super(nom,niveau,PVdepart+niveau*14,DGdepart+niveau*3,DFdepart+niveau*1,NCdepart+niveau/30, PTdepart+niveau/20);
    }

    protected int CalculerPV(int PVdepart) {
        return PVdepart + 13;
    }

    protected double CalculerDG(double DGdepart) {
        return DGdepart + 3;
    }

    protected int CalculerDF(int DFdepart) {
        return DFdepart + 1;
    }


    @Override
    public void attaquer(Personnage cible,boolean defenseActive) {
        System.out.println(nom + " attaque furtivement " + cible.getNom() + " !");
        double degatsInfliges = degats;
        if (defenseActive) {
            System.out.println(cible.nom+ " a defendu votre coup");
            degatsInfliges -= cible.defense;
            if (degatsInfliges < 0) degatsInfliges = 0;
        }
        cible.recevoirDegats(degatsInfliges);
        // Chance d'une attaque critique
        if (Math.random() < 0.2) {
            System.out.println(nom + " inflige un coup critique !");
            cible.recevoirDegats(degatsInfliges/2);
        }
    }

    @Override
    public void utiliserCompetence(Personnage cible,boolean defenseActive) {
        if (CompetenceUsed>=0) {
            System.out.println(nom + " utilise sa compétence spéciale : Attaque rapide !");
            double degatsInfliges = degats;
            if (defenseActive) {
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
