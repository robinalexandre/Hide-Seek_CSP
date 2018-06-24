package fr.etu.jeu.vue.cases;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import fr.etu.jeu.controleur.listener.CaseListener;
import fr.etu.jeu.vue.Outils;
import fr.etu.jeu.vue.pieces.Piece;

public abstract class Case extends JLayeredPane {
	private static final long serialVersionUID = 5452542297779057991L;
	
	protected Piece piece;
	protected JPanel panel;
	protected JPanel[][] casesPanels = new JPanel[3][3];
	
	protected Case() {
		super();
		this.piece = new Piece();
		this.setPreferredSize(new Dimension(340, 351));
		
		this.panel = new JPanel();
		GridLayout grid = new GridLayout(3, 3);
		this.panel.setLayout(grid);
		for(int i = 0; i < 3; ++i) {
			for(int j = 0; j < 3; ++j) {
				this.casesPanels[i][j] = new JPanel();
				this.casesPanels[i][j].setBackground(Color.WHITE);
				this.casesPanels[i][j].setSize(new Dimension(100, 105));
				this.panel.add(this.casesPanels[i][j]);
			}	
		}
		this.add(this.panel, Integer.valueOf(0));
		this.panel.setBounds(5, 5, 330, 341);
		this.panel.setPreferredSize(this.getPreferredSize());
		Color color = new Color(79,106,90);
		this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2), BorderFactory.createLineBorder(color, 4, true)));
		this.addMouseListener(new CaseListener());
	}
	
	public void setPiece(Piece piece) {
		this.piece = piece;
		this.piece.switchSmallBig();
		this.add(this.piece, Integer.valueOf(1));
		this.updateUI();
	}
	
	public void removePiece() {
		this.remove(this.piece);
		this.piece.switchSmallBig();
		Outils.getInstance().getPanelPieces().add(this.piece);
		this.piece = new Piece();
	}
	
	public Piece getPiece() {
		return this.piece;
	}	
}
