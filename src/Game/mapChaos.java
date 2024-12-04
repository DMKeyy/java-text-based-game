package Game;

import java.util.Random;

public class mapChaos implements map{
    static Random rand = new Random();

    public void eventRound(Personnage a,Personnage b){
        double x=rand.nextDouble(2*a.getDegats());

        a.setDegats(x);

        x=rand.nextDouble(2*b.getDegats());

        b.setDegats(x);

    }

}
