package fr.etu.jeu.model;

public class Defi {
	
	private int lion;
	private int elephant;
	private int antilope;
	private int zebre;
	private int rhinoceros;
	
	public Defi(int lion, int elephant, int antilope, int zebre, int rhinoceros) {
		this.setLion(lion);
		this.setElephant(elephant);
		this.setAntilope(antilope);
		this.setZebre(zebre);
		this.setRhinoceros(rhinoceros);
	}

	public int getLion() {
		return this.lion;
	}

	public void setLion(int lion) {
		this.lion = lion;
	}

	public int getElephant() {
		return this.elephant;
	}

	public void setElephant(int elephant) {
		this.elephant = elephant;
	}

	public int getAntilope() {
		return this.antilope;
	}

	public void setAntilope(int antilope) {
		this.antilope = antilope;
	}

	public int getZebre() {
		return this.zebre;
	}

	public void setZebre(int zebre) {
		this.zebre = zebre;
	}

	public int getRhinoceros() {
		return this.rhinoceros;
	}

	public void setRhinoceros(int rhinoceros) {
		this.rhinoceros = rhinoceros;
	}

	public String toString() {
		return "Zebre: " + this.zebre + " ,Lion: " + this.lion + " ,Rhinoceros: " + this.rhinoceros 
		+ " ,Elephant: " + this.elephant + " ,Antilope: " + this.antilope + "\n";
	}
}
