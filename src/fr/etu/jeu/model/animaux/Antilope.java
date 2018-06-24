package fr.etu.jeu.model.animaux;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Antilope extends Animal {
	
	@Override
	public int getValue() {
		return 0;
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
	
	public Icon getImageMini() {
		InputStream in = getClass().getResourceAsStream(this.getClass().getSimpleName()+"_mini.png"); 
		Image img = null;
		try {
			img = ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ImageIcon(img);
	}
}
