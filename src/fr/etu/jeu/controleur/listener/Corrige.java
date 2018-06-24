package fr.etu.jeu.controleur.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;

import org.chocosolver.solver.Solution;

import fr.etu.jeu.vue.MainFrame;
import fr.etu.jeu.vue.Outils;
import fr.etu.jeu.vue.Plateau;
import fr.etu.jeu.vue.cases.CaseBasDroite;
import fr.etu.jeu.vue.cases.CaseBasGauche;
import fr.etu.jeu.vue.cases.CaseHautDroite;
import fr.etu.jeu.vue.cases.CaseHautGauche;
import fr.etu.jeu.vue.pieces.Piece;
import fr.etu.jeu.vue.pieces.Piece1;
import fr.etu.jeu.vue.pieces.Piece2;
import fr.etu.jeu.vue.pieces.Piece3;
import fr.etu.jeu.vue.pieces.Piece4;

public class Corrige implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		if(MainFrame.getInstance().getSolver() != null) {
			if(CaseHautGauche.getInstance().getPiece().getLabelMax() != null) {
				CaseHautGauche.getInstance().removePiece();
				CaseHautGauche.getInstance().repaint();
			}
			if(CaseHautDroite.getInstance().getPiece().getLabelMax() != null) {
				CaseHautDroite.getInstance().removePiece();
				CaseHautDroite.getInstance().repaint();
			}
			if(CaseBasGauche.getInstance().getPiece().getLabelMax() != null) {
				CaseBasGauche.getInstance().removePiece();
				CaseBasGauche.getInstance().repaint();
			}
			if(CaseBasDroite.getInstance().getPiece().getLabelMax() != null) {
				CaseBasDroite.getInstance().removePiece();
				CaseBasDroite.getInstance().repaint();
			}
			MainFrame.getInstance().getPieceSelected().setBorder(BorderFactory.createEmptyBorder());
			MainFrame.getInstance().setPieceSelected(new Piece());
			Outils.getInstance().updateUI();
			
			Solution sol = MainFrame.getInstance().getSolver().getSolutions().get(0);
			MainFrame.getInstance().getSolver().creerPlateauPieces(sol);
			Piece[] pieces = new Piece[4];
			boolean[] piecesAssigne = new boolean[4];
			
			for(int k = 0; k < 4; ++k) {
				if(MainFrame.getInstance().getSolver().getPlateauPieces().getBasDroite().getPiece().compare(Piece1.getInstance().getPieceModel())) {
					pieces[0] = Piece1.getInstance();
					piecesAssigne[0] = true;
				}
				else if(MainFrame.getInstance().getSolver().getPlateauPieces().getBasDroite().getPiece().compare(Piece2.getInstance().getPieceModel())) {
					pieces[0] = Piece2.getInstance();
					piecesAssigne[1] = true;
				}
				else if(MainFrame.getInstance().getSolver().getPlateauPieces().getBasDroite().getPiece().compare(Piece3.getInstance().getPieceModel())) {
					pieces[0] = Piece3.getInstance();
						piecesAssigne[2] = true;
				}
				else if(MainFrame.getInstance().getSolver().getPlateauPieces().getBasDroite().getPiece().compare(Piece4.getInstance().getPieceModel())) {
					pieces[0] = Piece4.getInstance();
					piecesAssigne[3] = true;
				}
				if(MainFrame.getInstance().getSolver().getPlateauPieces().getBasGauche().getPiece().compare(Piece1.getInstance().getPieceModel())) {
					pieces[1] = Piece1.getInstance();piecesAssigne[0] = true;
				}
				else if(MainFrame.getInstance().getSolver().getPlateauPieces().getBasGauche().getPiece().compare(Piece2.getInstance().getPieceModel())) {
					pieces[1] = Piece2.getInstance();piecesAssigne[1] = true;
				}
				else if(MainFrame.getInstance().getSolver().getPlateauPieces().getBasGauche().getPiece().compare(Piece3.getInstance().getPieceModel())) {
					pieces[1] = Piece3.getInstance();		
				piecesAssigne[2] = true;
				}
				else if(MainFrame.getInstance().getSolver().getPlateauPieces().getBasGauche().getPiece().compare(Piece4.getInstance().getPieceModel())) {
					pieces[1] = Piece4.getInstance();piecesAssigne[3] = true;
				}

				if(MainFrame.getInstance().getSolver().getPlateauPieces().getHautDroite().getPiece().compare(Piece1.getInstance().getPieceModel())) {
					pieces[2] = Piece1.getInstance();piecesAssigne[0] = true;
				}
				else if(MainFrame.getInstance().getSolver().getPlateauPieces().getHautDroite().getPiece().compare(Piece2.getInstance().getPieceModel())) {
					pieces[2] = Piece2.getInstance();piecesAssigne[1] = true;
				}
				else if(MainFrame.getInstance().getSolver().getPlateauPieces().getHautDroite().getPiece().compare(Piece3.getInstance().getPieceModel())) {
					pieces[2] = Piece3.getInstance();	
				piecesAssigne[2] = true;
				}
				else if(MainFrame.getInstance().getSolver().getPlateauPieces().getHautDroite().getPiece().compare(Piece4.getInstance().getPieceModel())) {
					pieces[2] = Piece4.getInstance();piecesAssigne[3] = true;
				}

				if(MainFrame.getInstance().getSolver().getPlateauPieces().getHautGauche().getPiece().compare(Piece1.getInstance().getPieceModel())) {
					pieces[3] = Piece1.getInstance();piecesAssigne[0] = true;
				}
				else if(MainFrame.getInstance().getSolver().getPlateauPieces().getHautGauche().getPiece().compare(Piece2.getInstance().getPieceModel())) {
					pieces[3] = Piece2.getInstance();piecesAssigne[1] = true;
				}
				else if(MainFrame.getInstance().getSolver().getPlateauPieces().getHautGauche().getPiece().compare(Piece3.getInstance().getPieceModel())) {
					pieces[3] = Piece3.getInstance();		
				piecesAssigne[2] = true;
				}
				else if(MainFrame.getInstance().getSolver().getPlateauPieces().getHautGauche().getPiece().compare(Piece4.getInstance().getPieceModel())) {
					pieces[3] = Piece4.getInstance();piecesAssigne[3] = true;
				}
				
				for(int i = 0; i < 4; ++i)
					if(!piecesAssigne[i])
						if(i == 0)
							Piece1.getInstance().rotateByNinetyToLeftNTimes(1);
						else if(i == 1)
							Piece2.getInstance().rotateByNinetyToLeftNTimes(1);
						else if(i == 2)
							Piece3.getInstance().rotateByNinetyToLeftNTimes(1);
						else if(i == 3)
							Piece4.getInstance().rotateByNinetyToLeftNTimes(1);
			}
			
			Plateau.getInstance().getCaseBasDroite().setPiece(pieces[0]);
			Plateau.getInstance().getCaseBasGauche().setPiece(pieces[1]);
			Plateau.getInstance().getCaseHautDroite().setPiece(pieces[2]);
			Plateau.getInstance().getCaseHautGauche().setPiece(pieces[3]);

		}
	}
}
