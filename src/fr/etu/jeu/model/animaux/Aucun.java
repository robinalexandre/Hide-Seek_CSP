package fr.etu.jeu.model.animaux;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Aucun extends Animal {

	@Override
	public int getValue() {
		return -1;
	}

	@Override
	public Icon getImage() {
		InputStream in = getClass().getResourceAsStream(this.getClass().getSimpleName()+".png"); 
		Image img = null;
		try {
			img = ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ImageIcon(img);
	}

	@Override
	public Icon getImageMini() {
		// TODO Auto-generated method stub
		return null;
	}
}
