package fr.etu.jeu.model;

import fr.etu.jeu.model.cases.BasDroite;
import fr.etu.jeu.model.cases.BasGauche;
import fr.etu.jeu.model.cases.HautDroite;
import fr.etu.jeu.model.cases.HautGauche;
import fr.etu.jeu.model.cases.Case;

public class Plateau {
	Case basDroite;
	Case basGauche;
	Case hautDroite;
	Case hautGauche;
	
	private Plateau() {
		this.basDroite = new BasDroite();
		basGauche = new BasGauche();
		hautDroite = new HautDroite();
		hautGauche = new HautGauche();
	}
	
	private static class PlateauHolder
	{		
		private final static Plateau plateau = new Plateau();
	}
 
	public static Plateau getInstance()
	{
		return PlateauHolder.plateau;
	}

	public Case getBasDroite() {
		return this.basDroite;
	}

	public void setBasDroite(Case basDroite) {
		this.basDroite = basDroite;
	}

	public Case getBasGauche() {
		return this.basGauche;
	}

	public void setBasGauche(Case basGauche) {
		this.basGauche = basGauche;
	}

	public Case getHautDroite() {
		return this.hautDroite;
	}

	public void setHautDroite(Case hautDroite) {
		this.hautDroite = hautDroite;
	}

	public Case getHautGauche() {
		return this.hautGauche;
	}

	public void setHautGauche(Case hautGauche) {
		this.hautGauche = hautGauche;
	}
	
	
}
