package fr.etu.jeu.vue.pieces;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.etu.jeu.controleur.listener.Close;
import fr.etu.jeu.controleur.listener.PieceListener;
import fr.etu.jeu.controleur.listener.Rotation;

public class Piece extends JPanel {
	private static final long serialVersionUID = 7076941416620023243L;
	
	protected fr.etu.jeu.model.pieces.Piece pieceModel;
	protected JLabel label;
	protected JLabel labelMax;
	private JLabel rotate;
	private JLabel rotateMax;
	private JLabel close;
		
	public Piece() {
		super();
		this.pieceModel = new fr.etu.jeu.model.pieces.Piece();
	}
	
	public Piece(Boolean full) {
		this();
		this.addMouseListener(new PieceListener());
		this.setPreferredSize(new Dimension(100, 100));
		this.setOpaque(false);
		
		this.label = new JLabel();
		this.label.setOpaque(false);
		this.label.setSize(new Dimension(90, 90));
		this.add(this.label);

		this.labelMax = new JLabel();
		this.labelMax.setOpaque(false);
		this.labelMax.setSize(new Dimension(330, 330));
		
		this.rotate = new JLabel(this.pieceModel.getImageRotate());
		this.rotate.setLocation(0, 0);
		this.rotate.addMouseListener(new Rotation());
		this.rotate.setSize(new Dimension(17, 17));
		this.label.add(this.rotate);
		
		this.rotateMax = new JLabel(this.pieceModel.getImageRotateMax());
		this.rotateMax.setLocation(0, 0);
		this.rotateMax.addMouseListener(new Rotation());
		this.rotateMax.setSize(new Dimension(35, 35));
		this.labelMax.add(this.rotateMax);
		
		this.close = new JLabel(this.pieceModel.getImageClose());
		this.close.setLocation(275, 0);
		this.close.addMouseListener(new Close());
		this.close.setSize(new Dimension(35, 35));
		this.labelMax.add(this.close);
	}
	
	public fr.etu.jeu.model.pieces.Piece getPieceModel() {
		return this.pieceModel;
	}
	
	public void rotateByNinetyToLeftNTimes(int n) {
		int angle = -90*n;
		this.label.remove(this.rotate);
		this.labelMax.remove(this.rotateMax);
		this.labelMax.remove(this.close);

		int width = ((ImageIcon)this.label.getIcon()).getImage().getWidth(null);
		int height = ((ImageIcon)this.label.getIcon()).getImage().getHeight(null);
		BufferedImage bImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics graphic = bImage.createGraphics();
		Graphics2D g2d = (Graphics2D) graphic;
		g2d.rotate(Math.toRadians(angle), width/2, height/2);
		g2d.drawImage(((ImageIcon)this.label.getIcon()).getImage(), 0, 0, width, height, 0, 0, width, height, null);
		g2d.dispose();
		graphic.dispose();
		this.label.setIcon(new ImageIcon(bImage));
		this.label.add(this.rotate);
		
		width = ((ImageIcon)this.labelMax.getIcon()).getImage().getWidth(null);
		height = ((ImageIcon)this.labelMax.getIcon()).getImage().getHeight(null);
		bImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		graphic = bImage.createGraphics();
		g2d = (Graphics2D) graphic;
		g2d.rotate(Math.toRadians(angle), width/2, height/2);
		g2d.drawImage(((ImageIcon)this.labelMax.getIcon()).getImage(), 0, 0, width, height, 0, 0, width, height, null);
		g2d.dispose();
		graphic.dispose();
		this.labelMax.setIcon(new ImageIcon(bImage));
		this.labelMax.add(this.rotateMax);
		this.labelMax.add(this.close);
		this.labelMax.updateUI();
		
        this.pieceModel.rotateByNinetyToLeftNTimes(n);
	}	
	
	public void switchSmallBig() {
		if(this.isAncestorOf(this.label)) {
			this.remove(this.label);
			this.add(this.labelMax);
			this.setBounds(5, 5, 330, 338);
		} else {
			this.remove(this.labelMax);
			this.add(this.label);
			this.setBounds(0, 0, 90, 90);
		}
	}

	public JLabel getLabelMax() {
		return labelMax;
	}

	public void setLabelMax(JLabel labelMax) {
		this.labelMax = labelMax;
	}
}
