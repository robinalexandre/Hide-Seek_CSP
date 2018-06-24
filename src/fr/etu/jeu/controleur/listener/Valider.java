package fr.etu.jeu.controleur.listener;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import org.chocosolver.solver.Solution;

import fr.etu.jeu.model.PlateauPieces;
import fr.etu.jeu.model.cases.CasePiece;
import fr.etu.jeu.vue.MainFrame;
import fr.etu.jeu.vue.Menu;
import fr.etu.jeu.vue.Plateau;

public class Valider implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		Plateau.getInstance().setPlateauPiece(new PlateauPieces(new CasePiece(Plateau.getInstance().getCaseBasDroite().getPiece().getPieceModel()), new CasePiece(Plateau.getInstance().getCaseBasGauche().getPiece().getPieceModel()), new CasePiece(Plateau.getInstance().getCaseHautDroite().getPiece().getPieceModel()), new CasePiece(Plateau.getInstance().getCaseHautGauche().getPiece().getPieceModel())));
		if(MainFrame.getInstance().getSolver() != null) {
			int nbPieces = 0;
			int nbPiecesTemp = 0;
			for(Solution sol : MainFrame.getInstance().getSolver().getSolutions()) {
				MainFrame.getInstance().getSolver().creerPlateauPieces(sol);
				nbPiecesTemp = MainFrame.getInstance().getSolver().getPlateauPieces().compareCaseParCase(Plateau.getInstance().getPlateauPiece());
				if(nbPiecesTemp > nbPieces)
					nbPieces = nbPiecesTemp;
			}
			
			
			
			
			int casesOk = nbPieces;
			if(casesOk == 4) {
				final Dialog frame = new Dialog(MainFrame.getInstance(), "Gagné!", true);
				
				
				
				JButton rejouer = new JButton("Rejouer");
				JButton quitter = new JButton("Quitter");
				rejouer.addActionListener(new Menu().new NewGame());
				quitter.addActionListener(new Menu().new Quitter());
				rejouer.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						frame.dispose();
					}
				});
				quitter.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						frame.dispose();
					}
				});
				JOptionPane pane = new JOptionPane("<html>Bravo! Vous avez fini ce défi!<br>Voulez vous tenter un nouveau défi?</html>", JOptionPane.INFORMATION_MESSAGE, JOptionPane.YES_NO_OPTION, null, new Object[] {rejouer, quitter}, 1);
				pane.setLayout(new GridLayout(2, 2));
				pane.add(rejouer);
				
				
				
				
				pane.add(quitter);
				frame.add(pane);
				frame.setAlwaysOnTop(true);
				frame.pack();
				Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
				frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
				frame.setVisible(true);
			} else
				JOptionPane.showMessageDialog(null, "<html>Essayez encore!<br>Vous avez " + casesOk + " pièces bien placées!<html>", "Dommage!", JOptionPane.INFORMATION_MESSAGE);

		} else
			JOptionPane.showMessageDialog(null, "Veuillez créer un nouveau défi!", "Attention!", JOptionPane.ERROR_MESSAGE);
	}
}
