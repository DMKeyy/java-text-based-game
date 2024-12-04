package Game;

class Mage extends Personnage {
    public Mage(String nom,int niveau,int PVdepart,double DGdepart,int DFdepart,int NCdepart,int PTdepart) {
        super(nom,niveau,PVdepart+niveau*15,DGdepart+niveau*2,DFdepart+niveau*2,NCdepart+niveau/30, PTdepart+niveau/20);
    }


    protected int CalculerPV(int PVdepart) {
        return PVdepart +15;
    }

    protected double CalculerDG(double DGdepart) {
        return DGdepart + 2;
    }

    protected int CalculerDF(int DFdepart) {
        return DFdepart + 2;
    }

    @Override
    public void attaquer(Personnage cible,boolean defenseActive) {
        System.out.println(nom + " lance un sort sur " + cible.getNom() + " !");
        double degatsInfliges = degats;
        if (defenseActive) {
            System.out.println(cible.nom+ " a defendu votre coup");
            degatsInfliges -= cible.defense;
            if (degatsInfliges < 0) degatsInfliges = 0;
        }
        cible.recevoirDegats(degatsInfliges);
    }

    @Override
    public void utiliserCompetence(Personnage cible,boolean defenseActive) {
        if (CompetenceUsed>=0) {
            System.out.println(nom + " invoque une tempête magique !");
            double degatsInfliges = degats;
            if (defenseActive) {
                System.out.println(cible.nom+ " a defendu votre coup");
                degatsInfliges -= cible.defense;
                if (degatsInfliges < 0) degatsInfliges = 0;
            }
            cible.recevoirDegats(degatsInfliges*1.2);
            CompetenceUsed--;
        }
        else {
            System.out.println(nom + " ne peut plus utiliser d'attaque spéciale !");
        }
    }

}

