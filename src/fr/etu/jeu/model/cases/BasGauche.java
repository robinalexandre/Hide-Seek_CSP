package fr.etu.jeu.model.cases;

import fr.etu.jeu.model.animaux.Antilope;
import fr.etu.jeu.model.animaux.Elephant;
import fr.etu.jeu.model.animaux.Lion;
import fr.etu.jeu.model.animaux.Rhinoceros;
import fr.etu.jeu.model.animaux.Zebre;

public class BasGauche extends Case {
	
	public BasGauche() {
		super();
		super.setMatriceAnimaux(0, 0, new Rhinoceros());
		super.setMatriceAnimaux(1, 0, new Antilope());
		super.setMatriceAnimaux(2, 0, new Lion());
		super.setMatriceAnimaux(0, 1, new Elephant());
		super.setMatriceAnimaux(0, 2, new Zebre());
		super.setMatriceAnimaux(1, 2, new Antilope());
		super.setMatriceAnimaux(2, 2, new Rhinoceros());
	}

}
