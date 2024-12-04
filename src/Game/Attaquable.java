package Game;

public interface Attaquable {
	void attaquer(Personnage cible,boolean defenceActive);
	void utiliserCompetence(Personnage cible,boolean defenceActive);
}
