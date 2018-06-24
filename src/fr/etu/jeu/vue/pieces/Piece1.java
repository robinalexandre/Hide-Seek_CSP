package fr.etu.jeu.vue.pieces;

public class Piece1 extends Piece {
	private static final long serialVersionUID = 5282371446036523171L;

	private Piece1() {
		super(true);
		this.pieceModel = new fr.etu.jeu.model.pieces.Piece1();
		this.label.setIcon(this.pieceModel.getImage());
		this.labelMax.setIcon(this.pieceModel.getImageMaxi());
	}
	
	private static class PieceHolder {		
		private final static Piece p = new Piece1();
	}
 
	public static Piece getInstance() {
		return PieceHolder.p;
	}
}
