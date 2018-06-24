package fr.etu.jeu.vue;

import java.awt.GridLayout;

import javax.swing.JPanel;

import fr.etu.jeu.model.PlateauPieces;
import fr.etu.jeu.model.cases.CasePiece;
import fr.etu.jeu.vue.cases.Case;
import fr.etu.jeu.vue.cases.CaseBasDroite;
import fr.etu.jeu.vue.cases.CaseBasGauche;
import fr.etu.jeu.vue.cases.CaseHautDroite;
import fr.etu.jeu.vue.cases.CaseHautGauche;

public class Plateau extends JPanel {
	private static final long serialVersionUID = 1750944126729333298L;
	
	private Case caseHautGauche;
	private Case caseHautDroite;
	private Case caseBasGauche;
	private Case caseBasDroite;
	private PlateauPieces plateauPiece;

	private Plateau() {
		super();
		this.caseHautGauche = CaseHautGauche.getInstance();
		this.caseHautDroite = CaseHautDroite.getInstance();
		this.caseBasGauche = CaseBasGauche.getInstance();
		this.caseBasDroite = CaseBasDroite.getInstance();
		GridLayout grid = new GridLayout(2, 2);
		this.setLayout(grid);
		this.add(this.caseHautGauche);
		this.add(this.caseHautDroite);
		this.add(this.caseBasGauche);
		this.add(this.caseBasDroite);
		this.setVisible(true);
		
		this.setPlateauPiece(new PlateauPieces(new CasePiece(this.caseBasDroite.getPiece().getPieceModel()), new CasePiece(this.caseBasGauche.getPiece().getPieceModel()), new CasePiece(this.caseHautDroite.getPiece().getPieceModel()), new CasePiece(this.caseHautGauche.getPiece().getPieceModel())));
	}
	
	private static class PlateauHolder {		
		private final static Plateau plateau = new Plateau();
	}
 
	public static Plateau getInstance() {
		return PlateauHolder.plateau;
	}

	public Case getCaseHautGauche() {
		return caseHautGauche;
	}

	public void setCaseHautGauche(Case caseHautGauche) {
		this.caseHautGauche = caseHautGauche;
	}

	public Case getCaseHautDroite() {
		return caseHautDroite;
	}

	public void setCaseHautDroite(Case caseHautDroite) {
		this.caseHautDroite = caseHautDroite;
	}

	public Case getCaseBasGauche() {
		return caseBasGauche;
	}

	public void setCaseBasGauche(Case caseBasGauche) {
		this.caseBasGauche = caseBasGauche;
	}

	public Case getCaseBasDroite() {
		return caseBasDroite;
	}

	public void setCaseBasDroite(Case caseBasDroite) {
		this.caseBasDroite = caseBasDroite;
	}

	public PlateauPieces getPlateauPiece() {
		return plateauPiece;
	}

	public void setPlateauPiece(PlateauPieces plateauPiece) {
		this.plateauPiece = plateauPiece;
	}
}
