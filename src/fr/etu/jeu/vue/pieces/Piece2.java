package fr.etu.jeu.vue.pieces;

public class Piece2 extends Piece {
	private static final long serialVersionUID = 6364253632379710242L;

	private Piece2() {
		super(true);
		this.pieceModel = new fr.etu.jeu.model.pieces.Piece2();
		this.label.setIcon(this.pieceModel.getImage());
		this.labelMax.setIcon(this.pieceModel.getImageMaxi());
	}
	
	private static class PieceHolder {		
		private final static Piece p = new Piece2();
	}
 
	public static Piece getInstance() {
		return PieceHolder.p;
	}
}
