package fr.etu.jeu.model;

import fr.etu.jeu.model.cases.CasePiece;

public class PlateauPieces {

	CasePiece basDroite;
	CasePiece basGauche;
	CasePiece hautDroite;
	CasePiece hautGauche;
	
	public PlateauPieces(CasePiece basDroite, CasePiece basGauche, CasePiece hautDroite, CasePiece hautGauche) {
		this.basDroite = basDroite;
		this.basGauche = basGauche;
		this.hautDroite = hautDroite;
		this.hautGauche = hautGauche;
	}

	public CasePiece getBasDroite() {
		return this.basDroite;
	}

	public void setBasDroite(CasePiece basDroite) {
		this.basDroite = basDroite;
	}

	public CasePiece getBasGauche() {
		return this.basGauche;
	}

	public void setBasGauche(CasePiece basGauche) {
		this.basGauche = basGauche;
	}

	public CasePiece getHautDroite() {
		return this.hautDroite;
	}

	public void setHautDroite(CasePiece hautDroite) {
		this.hautDroite = hautDroite;
	}

	public CasePiece getHautGauche() {
		return this.hautGauche;
	}

	public void setHautGauche(CasePiece hautGauche) {
		this.hautGauche = hautGauche;
	}
	 
	public boolean compare(PlateauPieces plateau) {
		for(int i = 0; i < 3; ++i) {
			for(int j = 0; j < 3; ++j) {
			if(this.basDroite.getPiece().getMatricePiece()[i][j] != plateau.basDroite.getPiece().getMatricePiece()[i][j] || this.basGauche.getPiece().getMatricePiece()[i][j] != plateau.basGauche.getPiece().getMatricePiece()[i][j] || 
					this.hautDroite.getPiece().getMatricePiece()[i][j] != plateau.hautDroite.getPiece().getMatricePiece()[i][j] || this.hautGauche.getPiece().getMatricePiece()[i][j] != plateau.hautGauche.getPiece().getMatricePiece()[i][j])
				return false;
			}
		}
		return true;
	}
	
	public int compareCaseParCase(PlateauPieces plateau) {
		int nb = 4;
		boolean hg = true;
		boolean hd = true;
		boolean bg = true;
		boolean bd = true;
		for(int i = 0; i < 3; ++i) {
			for(int j = 0; j < 3; ++j) {
				if(this.basDroite.getPiece().getMatricePiece()[i][j] != plateau.basDroite.getPiece().getMatricePiece()[i][j])
					bd = false;
				if(this.basGauche.getPiece().getMatricePiece()[i][j] != plateau.basGauche.getPiece().getMatricePiece()[i][j])
					bg = false;
				if(this.hautDroite.getPiece().getMatricePiece()[i][j] != plateau.hautDroite.getPiece().getMatricePiece()[i][j])
					hd = false;
				if(this.hautGauche.getPiece().getMatricePiece()[i][j] != plateau.hautGauche.getPiece().getMatricePiece()[i][j])
					hg = false;
			}
		}
		if(!hg)
			--nb;
		if(!hd)
			--nb;
		if(!bg)
			--nb;
		if(!bd)
			--nb;
		return nb;
	}
	
	public String toString() {
		String plateau = "\n-----------------------Plateau de Pièces-----------------------\n";
		String hautG = "Piece Haut Gauche";
		String hautD = "Piece Haut Droite";
		String basG = "Piece Bas Gauche";
		String basD = "Piece Bas Droite";
	    for (int i = 0; i < 3*3; ++i) {
	    		if(i%3 == 0) {
			    hautG += "\n";
			    hautD += "\n";
			    basG += "\n";
			    basD += "\n";
	    		}
	    		hautG += this.getHautGauche().getPiece().getMatricePiece()[i/3][i%3] + " ";
	    		hautD += this.getHautDroite().getPiece().getMatricePiece()[i/3][i%3] + " ";
	    		basG += this.getBasGauche().getPiece().getMatricePiece()[i/3][i%3] + " ";
	    		basD += this.getBasDroite().getPiece().getMatricePiece()[i/3][i%3] + " ";
	    }
	    plateau += hautG + "\n" + hautD + "\n" + basG + "\n" + basD;
	    return plateau;
	}
}
