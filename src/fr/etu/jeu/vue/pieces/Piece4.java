package fr.etu.jeu.vue.pieces;

public class Piece4 extends Piece {
	private static final long serialVersionUID = 4254640063924967616L;

	public Piece4() {
		super(true);
		this.pieceModel = new fr.etu.jeu.model.pieces.Piece4();
		this.label.setIcon(this.pieceModel.getImage());
		this.labelMax.setIcon(this.pieceModel.getImageMaxi());
	}
	
	private static class PieceHolder {		
		private final static Piece p = new Piece4();
	}
 
	public static Piece getInstance() {
		return PieceHolder.p;
	}
}
