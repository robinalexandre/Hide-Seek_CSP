package fr.etu.jeu.vue.cases;

import javax.swing.JLabel;

import fr.etu.jeu.model.Plateau;
import fr.etu.jeu.model.animaux.Antilope;
import fr.etu.jeu.model.animaux.Aucun;
import fr.etu.jeu.model.animaux.Elephant;
import fr.etu.jeu.model.animaux.Lion;
import fr.etu.jeu.model.animaux.Rhinoceros;
import fr.etu.jeu.model.animaux.Zebre;

public class CaseBasGauche extends Case {
	private static final long serialVersionUID = -1799779631093116255L;

	private CaseBasGauche() {
		super();
		for(int i = 0; i < 3; ++i) {
			for(int j = 0; j < 3; ++j) {
				JLabel label;
				if(Plateau.getInstance().getBasGauche().getMatriceAnimaux()[i][j].getClass() == new Elephant().getClass()) {
					label = new JLabel(new Elephant().getImage());
				} else if(Plateau.getInstance().getBasGauche().getMatriceAnimaux()[i][j].getClass() == new Lion().getClass()) {
					label = new JLabel(new Lion().getImage());
				} else if(Plateau.getInstance().getBasGauche().getMatriceAnimaux()[i][j].getClass() == new Antilope().getClass()) {
					label = new JLabel(new Antilope().getImage());
				} else if(Plateau.getInstance().getBasGauche().getMatriceAnimaux()[i][j].getClass() == new Rhinoceros().getClass()) {
					label = new JLabel(new Rhinoceros().getImage());
				} else if(Plateau.getInstance().getBasGauche().getMatriceAnimaux()[i][j].getClass() == new Zebre().getClass()) {
					label = new JLabel(new Zebre().getImage());
				} else {
					label = new JLabel(new Aucun().getImage());
				}
				label.setPreferredSize(this.casesPanels[i][j].getSize());
				this.casesPanels[i][j].add(label);
			}	
		}
	}
	
	private static class CaseHolder {		
		private final static CaseBasGauche c = new CaseBasGauche();
	}
 
	public static CaseBasGauche getInstance() {
		return CaseHolder.c;
	}
}
