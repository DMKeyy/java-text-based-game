package Game.maps;

import java.util.Random;

import Game.personnagess.Personnage;

public class mapElectric implements map{
    Random rand = new Random();
        
    public void eventRound(Personnage a,Personnage b){
        double x=Math.random();
        Personnage player;
        int r= rand.nextInt(2)+1;
        switch (r) {
            case 1: player=a;
                break;
            case 2: player=b;
                break;
            default: player=a;
                break;
        }

        if (x<=0.2) {
            r=player.getPointsDeVie()/10+ rand.nextInt(20)+1;
            player.setPointsDeVie(player.getPointsDeVie()-r);
            System.out.println(player.getNom()+" a subi "+ r +" degats causés par une decharge electrique .");
            player.afficherHP();
        }
    }

}
