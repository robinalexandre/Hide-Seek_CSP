package fr.etu.jeu.model.cases;

import fr.etu.jeu.model.pieces.Piece;

public class CasePiece {
	
	private Piece piece;
	
	public CasePiece() {
		this.piece = new Piece();
	}
	
	public CasePiece(Piece piece) {
		this.piece = piece;
	}
	
	public void setMatricePiece(int i, int j, int valeur) {
		this.piece.setMatricePiece(i,j,valeur);
	}
	
	public Piece getPiece() {
		return this.piece;
	}

}
