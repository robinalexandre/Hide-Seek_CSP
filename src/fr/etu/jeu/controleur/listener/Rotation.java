package fr.etu.jeu.controleur.listener;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;

import fr.etu.jeu.vue.MainFrame;
import fr.etu.jeu.vue.Outils;
import fr.etu.jeu.vue.pieces.Piece;

public class Rotation implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		Piece piece = (Piece) e.getComponent().getParent().getParent();
		piece.rotateByNinetyToLeftNTimes(1);
	}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {
		Piece piece = (Piece) e.getComponent().getParent().getParent();
		if(MainFrame.getInstance().getPieceSelected().getClass().equals(new Piece().getClass()) && piece.getParent().getParent().getClass().equals(Outils.getInstance().getClass())) {
			piece.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		Piece piece = (Piece) e.getComponent().getParent().getParent();
		if(MainFrame.getInstance().getPieceSelected().getClass().equals(new Piece().getClass())) {
			piece.setBorder(BorderFactory.createEmptyBorder());
		}	
	}
}
