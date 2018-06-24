package fr.etu.jeu.model.pieces;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Piece2 extends Piece {
	
	public Piece2() {
		super();
		super.setMatricePiece(0, 0, 1);
		super.setMatricePiece(1, 0, 1);
		super.setMatricePiece(2, 0, 1);
		super.setMatricePiece(0, 1, 1);
		super.setMatricePiece(2, 1, 1);
		super.setMatricePiece(0, 2, 1);
		super.setMatricePiece(2, 2, 1);
	}

	public ImageIcon getImage() {
		InputStream in = getClass().getResourceAsStream(this.getClass().getSimpleName()+".png"); 
		Image img = null;
		try {
			img = ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ImageIcon(img);
	}
	
	public ImageIcon getImageMaxi() {
		InputStream in = getClass().getResourceAsStream(this.getClass().getSimpleName()+"_maxi.png"); 
		Image img = null;
		try {
			img = ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ImageIcon(img);
	}
}
