package fr.etu.jeu.vue.pieces;

public class Piece3 extends Piece {
	private static final long serialVersionUID = 1374711905182919067L;

	private Piece3() {
		super(true);
		this.pieceModel = new fr.etu.jeu.model.pieces.Piece3();
		this.label.setIcon(this.pieceModel.getImage());
		this.labelMax.setIcon(this.pieceModel.getImageMaxi());
	}
	
	private static class PieceHolder {		
		private final static Piece p = new Piece3();
	}
 
	public static Piece getInstance() {
		return PieceHolder.p;
	}
}
