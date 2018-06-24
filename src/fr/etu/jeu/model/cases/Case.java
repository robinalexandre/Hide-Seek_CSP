package fr.etu.jeu.model.cases;

import fr.etu.jeu.model.animaux.Animal;
import fr.etu.jeu.model.animaux.Aucun;

public abstract class Case {
	
	private Animal[][] matriceAnimaux = new Animal[3][3];
	
	protected Case() {
		for(int i = 0; i < 3; ++i)
			for(int j = 0; j < 3; ++j)
				matriceAnimaux[i][j] = new Aucun();
	}
	
	protected void setMatriceAnimaux(int i, int j, Animal animal) {
		this.matriceAnimaux[i][j] = animal;
	}
	
	public Animal[][] getMatriceAnimaux() {
		return this.matriceAnimaux;
	}

}
