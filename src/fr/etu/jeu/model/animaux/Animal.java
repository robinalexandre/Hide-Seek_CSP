package fr.etu.jeu.model.animaux;

import java.io.IOException;

import javax.swing.Icon;

public abstract class Animal {
		
	public Animal() {
		//vide
	}
	
	public abstract int getValue();
	public abstract Icon getImage() throws IOException;

	public abstract Icon getImageMini();

}
