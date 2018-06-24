package fr.etu.jeu.controleur.listener;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;

import fr.etu.jeu.vue.MainFrame;
import fr.etu.jeu.vue.Outils;
import fr.etu.jeu.vue.pieces.Piece;

public class PieceListener implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		Piece piece = (Piece) e.getComponent();
		for(int i = 0; i < Outils.getInstance().getPanelPieces().getComponents().length; ++i) {
			Piece pieces = (Piece)Outils.getInstance().getPanelPieces().getComponents()[i];
			if(pieces.equals(piece)) {
				if(!MainFrame.getInstance().getPieceSelected().equals(piece)) {
					piece.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
					MainFrame.getInstance().setPieceSelected(piece);
				} else {
					piece.setBorder(BorderFactory.createEmptyBorder());
					MainFrame.getInstance().setPieceSelected(new Piece());
				}
			} else
				pieces.setBorder(BorderFactory.createEmptyBorder());
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {
		Piece piece = (Piece) e.getComponent();
		if(MainFrame.getInstance().getPieceSelected().getClass().equals(new Piece().getClass()) && piece.getParent().getParent().getClass().equals(Outils.getInstance().getClass())) {
			piece.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		Piece piece = (Piece) e.getComponent();
		if(MainFrame.getInstance().getPieceSelected().getClass().equals(new Piece().getClass())) {
			piece.setBorder(BorderFactory.createEmptyBorder());
		}	
	}

}
