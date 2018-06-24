package fr.etu.jeu.controleur.listener;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;

import fr.etu.jeu.vue.MainFrame;
import fr.etu.jeu.vue.cases.Case;
import fr.etu.jeu.vue.pieces.Piece;

public class CaseListener implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		Case c = (Case) e.getComponent();
		if(c.getPiece().getClass().equals(new Piece().getClass()) && !MainFrame.getInstance().getPieceSelected().getClass().equals(new Piece().getClass())) {
			MainFrame.getInstance().getPieceSelected().setBorder(BorderFactory.createEmptyBorder());
			c.setPiece(MainFrame.getInstance().getPieceSelected());
			MainFrame.getInstance().setPieceSelected(new Piece());
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {
		Case c = (Case) e.getComponent();
		if(!MainFrame.getInstance().getPieceSelected().getClass().equals(new Piece().getClass()) && c.getPiece().getClass().equals(new Piece().getClass())) {
			c.setBorder(BorderFactory.createLineBorder(Color.RED, 4));
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		Case c = (Case) e.getComponent();
		c.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2), BorderFactory.createLineBorder(new Color(79,106,90), 4, true)));
	}
}
