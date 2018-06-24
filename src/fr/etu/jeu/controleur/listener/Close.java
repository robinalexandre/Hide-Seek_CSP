package fr.etu.jeu.controleur.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import fr.etu.jeu.vue.MainFrame;
import fr.etu.jeu.vue.Outils;
import fr.etu.jeu.vue.cases.Case;
import fr.etu.jeu.vue.pieces.Piece;

public class Close implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		Piece piece = (Piece) e.getComponent().getParent().getParent();
		Case c = (Case) piece.getParent();
		if(MainFrame.getInstance().getPieceSelected().getClass().equals(new Piece().getClass())) {
			c.removePiece();
			c.repaint();
			Outils.getInstance().updateUI();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

}
