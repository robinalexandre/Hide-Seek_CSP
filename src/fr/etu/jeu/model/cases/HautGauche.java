package fr.etu.jeu.model.cases;

import fr.etu.jeu.model.animaux.Antilope;
import fr.etu.jeu.model.animaux.Elephant;
import fr.etu.jeu.model.animaux.Lion;
import fr.etu.jeu.model.animaux.Rhinoceros;
import fr.etu.jeu.model.animaux.Zebre;

public class HautGauche extends Case {
	
	public HautGauche() {
		super();
		super.setMatriceAnimaux(0, 0, new Elephant());
		super.setMatriceAnimaux(1, 0, new Zebre());
		super.setMatriceAnimaux(2, 0, new Rhinoceros());
		super.setMatriceAnimaux(1, 1, new Antilope());
		super.setMatriceAnimaux(2, 1, new Lion());
		super.setMatriceAnimaux(0, 2, new Lion());
		super.setMatriceAnimaux(1, 2, new Zebre());
		super.setMatriceAnimaux(2, 2, new Elephant());
	}

}
