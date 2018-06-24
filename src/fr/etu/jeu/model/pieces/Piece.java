package fr.etu.jeu.model.pieces;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Piece {
	
private int[][] matricePiece = new int[3][3];
	
	public Piece() {
		for(int i = 0; i < 3; ++i)
			for(int j = 0; j < 3; ++j)
				matricePiece[i][j] = 0;
	}
	
	public void setMatricePiece(int i, int j, int valeur) {
		this.matricePiece[i][j] = valeur;
	}
	
	public int[][] getMatricePiece() {
		return this.matricePiece;
	}
	
	public boolean compare(Piece piece) {
		for(int i = 0; i < 3; ++i) {
			for(int j = 0; j < 3; ++j) {
				if(this.getMatricePiece()[i][j] != piece.getMatricePiece()[i][j])
					return false;
			}
		}
		return true;
	}
	
	private void swapRows() {
	    for (int  i = 0, k = this.matricePiece.length - 1; i < k; ++i, --k) {
	        int[] x = this.matricePiece[i];
	        this.matricePiece[i] = this.matricePiece[k];
	        this.matricePiece[k] = x;
	    }
	}

	private void transpose() {
        for (int i = 0; i < this.matricePiece.length; i++) {
            for (int j = i; j < this.matricePiece[0].length; j++) {
                int x = this.matricePiece[i][j];
                this.matricePiece[i][j] = this.matricePiece[j][i];
                this.matricePiece[j][i] = x;
            }
        }
    }
	
	public int[][] rotateByNinetyToLeftNTimes(int n) {
		for(int i = 0; i < n; i++) {
		    transpose();
		    swapRows();
		}
		return this.matricePiece;
	}
	
	public ImageIcon getImage() {
		return null;
	}
	

	public ImageIcon getImageMaxi() {
		return null;
	}

	public ImageIcon getImageRotate() {
		InputStream in = getClass().getResourceAsStream("rotate.png"); 
		Image img = null;
		try {
			img = ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ImageIcon(img);
	}
	
	public ImageIcon getImageRotateMax() {
		InputStream in = getClass().getResourceAsStream("rotate_max.png"); 
		Image img = null;
		try {
			img = ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ImageIcon(img);
	}

	public ImageIcon getImageClose() {
		InputStream in = getClass().getResourceAsStream("close.png"); 
		Image img = null;
		try {
			img = ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ImageIcon(img);
	}
	
	public String toString() {
		String sol = "\n-----------------------"+this.getClass()+"-----------------------\n";
	    for (int i = 0; i < 3*3; ++i) {
	    		if(i%3 == 0) {
			    sol += "\n";
	    		}
	    		sol += this.getMatricePiece()[i/3][i%3] + " ";
	    }
	    return sol;
	}
}
