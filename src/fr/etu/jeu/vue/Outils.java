package fr.etu.jeu.vue;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.Border;

import fr.etu.jeu.controleur.listener.Corrige;
import fr.etu.jeu.controleur.listener.Valider;
import fr.etu.jeu.model.Defi;
import fr.etu.jeu.model.animaux.Antilope;
import fr.etu.jeu.model.animaux.Elephant;
import fr.etu.jeu.model.animaux.Lion;
import fr.etu.jeu.model.animaux.Rhinoceros;
import fr.etu.jeu.model.animaux.Zebre;
import fr.etu.jeu.vue.cases.CaseBasDroite;
import fr.etu.jeu.vue.cases.CaseBasGauche;
import fr.etu.jeu.vue.cases.CaseHautDroite;
import fr.etu.jeu.vue.cases.CaseHautGauche;
import fr.etu.jeu.vue.pieces.Piece;
import fr.etu.jeu.vue.pieces.Piece1;
import fr.etu.jeu.vue.pieces.Piece2;
import fr.etu.jeu.vue.pieces.Piece3;
import fr.etu.jeu.vue.pieces.Piece4;

public class Outils extends JPanel {
	private static final long serialVersionUID = -8914897973027320162L;
	
	private JPanel panelDefi;
	private JPanel panelPieces;
	private JButton valider;
	
	private Outils() {
		super();
		this.setPreferredSize(new Dimension(130, (int)Plateau.getInstance().getSize().getHeight()));
		
		final JLabel timer = new JLabel("Temps écoulé: " + 0);
		final long begin = System.currentTimeMillis();
		class TimerListener implements ActionListener {
		    public void actionPerformed(ActionEvent e) {
			    long time = (System.currentTimeMillis() - begin)/1000;
			    String text = "";
			    	text += String.format ("%02d", time/60) + ":" + String.format ("%02d", time%60);
			    	timer.setText("Temps écoulé: " + text);
		    }
		}
		new Timer(1000, new TimerListener()).start();
		this.add(timer);
		
		this.valider = new JButton("<html>&nbsp;&nbsp;&nbsp;Valider<br>la solution<html>");
		this.valider.setBackground(new Color(234, 241, 250));
		this.valider.addActionListener(new Valider());
		this.valider.setPreferredSize(new Dimension(130, 40));
		this.valider.setBorder(new RoundedBorder(10));
		this.add(valider);
		
		JButton correction = new JButton("<html>&nbsp;&nbsp;&nbsp;Corriger<html>");
		correction.setBackground(new Color(234, 241, 250));
		correction.addActionListener(new Corrige());
		correction.setPreferredSize(new Dimension(130, 20));
		correction.setBorder(new RoundedBorder(10));
		this.add(correction);

		
		GridLayout grid = new GridLayout(5, 2);
		grid.setVgap(0);
		grid.setHgap(0);
		this.panelDefi = new JPanel(grid);
		this.panelDefi.setPreferredSize(new Dimension(130, 200));
		this.add(this.panelDefi);
		this.panelDefi.setVisible(true);

		GridLayout grid2 = new GridLayout(4, 1);
		this.panelPieces = new JPanel(grid2);
		this.panelPieces.add(Piece1.getInstance());
		this.panelPieces.add(Piece2.getInstance());
		this.panelPieces.add(Piece3.getInstance());
		this.panelPieces.add(Piece4.getInstance());
		this.panelPieces.setOpaque(false);
		this.add(this.panelPieces);
	}
	
	private static class OutilsHolder {		
		private static Outils outils = new Outils();
	}
 
	public static Outils getInstance() {
		return OutilsHolder.outils;
	}
	
	public static void resetInstance() {
		OutilsHolder.outils = new Outils();
		if(!CaseBasDroite.getInstance().getPiece().getClass().equals(new Piece().getClass()))
			CaseBasDroite.getInstance().removePiece();
		if(!CaseBasGauche.getInstance().getPiece().getClass().equals(new Piece().getClass()))
			CaseBasGauche.getInstance().removePiece();
		if(!CaseHautDroite.getInstance().getPiece().getClass().equals(new Piece().getClass()))
			CaseHautDroite.getInstance().removePiece();
		if(!CaseHautGauche.getInstance().getPiece().getClass().equals(new Piece().getClass()))
			CaseHautGauche.getInstance().removePiece();
	}
	
	public void setPanelDefi(Defi defi) {
		this.panelDefi.add(new JLabel("X" + defi.getLion()));
		this.panelDefi.add(new JLabel(new Lion().getImageMini()));
		this.panelDefi.add(new JLabel("X" + defi.getZebre()));
		this.panelDefi.add(new JLabel(new Zebre().getImageMini()));
		this.panelDefi.add(new JLabel("X" + defi.getAntilope()));
		this.panelDefi.add(new JLabel(new Antilope().getImageMini()));
		this.panelDefi.add(new JLabel("X" + defi.getElephant()));
		this.panelDefi.add(new JLabel(new Elephant().getImageMini()));
		this.panelDefi.add(new JLabel("X" + defi.getRhinoceros()));
		this.panelDefi.add(new JLabel(new Rhinoceros().getImageMini()));
		this.updateUI();
	}

	public JPanel getPanelPieces() {
		return panelPieces;
	}

	public void setPanelPieces(JPanel panelPieces) {
		this.panelPieces = panelPieces;
	}
	
	private static class RoundedBorder implements Border {

	    private int radius;


	    RoundedBorder(int radius) {
	        this.radius = radius;
	    }


	    public Insets getBorderInsets(Component c) {
	        return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
	    }


	    public boolean isBorderOpaque() {
	        return true;
	    }


	    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
	        g.drawRoundRect(x, y, width-1, height-1, radius, radius);
	    }
	}
}
