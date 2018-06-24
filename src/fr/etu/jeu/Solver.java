package fr.etu.jeu;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.variables.IntVar;

import fr.etu.jeu.model.Defi;
import fr.etu.jeu.model.PlateauPieces;
import fr.etu.jeu.model.animaux.Antilope;
import fr.etu.jeu.model.animaux.Elephant;
import fr.etu.jeu.model.animaux.Lion;
import fr.etu.jeu.model.animaux.Rhinoceros;
import fr.etu.jeu.model.animaux.Zebre;
import fr.etu.jeu.model.cases.CasePiece;
import fr.etu.jeu.model.pieces.Piece1;
import fr.etu.jeu.model.pieces.Piece2;
import fr.etu.jeu.model.pieces.Piece3;
import fr.etu.jeu.model.pieces.Piece4;
import fr.etu.jeu.vue.MainFrame;

public class Solver extends Model{
	
	private PlateauPieces plateauPieces;
	private List<Solution> solutions = new ArrayList<>();
	private Defi defi;
	IntVar nbRhinoceros;
	IntVar nbLion;
	IntVar nbAntilope;
	IntVar nbElephant;
	IntVar nbZebre;
	
	IntVar[] pieceHautGauche;
	IntVar[] pieceHautDroite;
	IntVar[] pieceBasGauche;
	IntVar[] pieceBasDroite;
	
	private static int MINI = 0;
	public static int FACILE = 3;
	public static int MOYEN = 6;
	public static int DIFFICILE = 7;
	public static int EXPERT = 9;

	public Solver() {
		super();
	}
	
	public List<Solution> respecteDefi(Defi defi) {
		this.defi = defi;
		this.createModel();
		
		this.and(this.allEqual(new IntVar[] {this.nbRhinoceros, this.intVar(defi.getRhinoceros())}), this.allEqual(new IntVar[] {this.nbElephant, this.intVar(defi.getElephant())}),
				this.allEqual(new IntVar[] {this.nbLion, this.intVar(defi.getLion())}), this.allEqual(new IntVar[] {this.nbAntilope, this.intVar(defi.getAntilope())}),
				this.allEqual(new IntVar[] {this.nbZebre, this.intVar(defi.getZebre())})).post();
		
		//Solve
		org.chocosolver.solver.Solver solver = this.getSolver();	
		
		List<Solution> s = solver.findAllSolutions();
		System.out.println("Nombre de solutions: " + s.size());

		return s;
	}
	
	public void creerDefi(int niveau) {
		this.createModel();
		
		this.sum(new IntVar[] {this.nbAntilope, this.nbElephant, this.nbLion, this.nbRhinoceros, this.nbZebre}, "<=", niveau).post();
		if(niveau ==  Solver.FACILE)
			this.sum(new IntVar[] {this.nbAntilope, this.nbElephant, this.nbLion, this.nbRhinoceros, this.nbZebre}, ">", Solver.MINI).post();
		else if(niveau ==  Solver.MOYEN)
			this.sum(new IntVar[] {this.nbAntilope, this.nbElephant, this.nbLion, this.nbRhinoceros, this.nbZebre}, ">", Solver.FACILE).post();
		else if(niveau ==  Solver.DIFFICILE)
			this.sum(new IntVar[] {this.nbAntilope, this.nbElephant, this.nbLion, this.nbRhinoceros, this.nbZebre}, ">", Solver.MOYEN).post();
		else if(niveau ==  Solver.EXPERT)
			this.sum(new IntVar[] {this.nbAntilope, this.nbElephant, this.nbLion, this.nbRhinoceros, this.nbZebre}, ">", Solver.DIFFICILE).post();
		
		
		long timeLimit = 7*1000;

		//Solve
		org.chocosolver.solver.Solver solver = this.getSolver();
		solver.limitTime(timeLimit);
		List<Solution> solutions = solver.findAllSolutions();
		
//		int min = 26;
//		int max = 0;
//		for(Solution s : solutions) {
//			int sum =  s.getIntVal(nbLion) + s.getIntVal(nbElephant) + s.getIntVal(nbAntilope) + s.getIntVal(nbZebre) + s.getIntVal(nbRhinoceros);
//			if(sum > max)
//				max = sum;
//			if(sum < min)
//				min = sum;
////		}
//		System.out.println("Min: " + min);
//		System.out.println("Max: " + max);
		System.out.println("Nombre de défis créés en " + timeLimit/1000 + " secondes : " + solutions.size());
		
		int index = new Random().nextInt(solutions.size());
		Solution s = solutions.get(index);
		
		this.defi = new Defi(s.getIntVal(nbLion), s.getIntVal(nbElephant), s.getIntVal(nbAntilope), s.getIntVal(nbZebre), s.getIntVal(nbRhinoceros));
			
		MainFrame.getInstance().setSolver(new Solver());
		
		MainFrame.getInstance().getSolver().solutions = MainFrame.getInstance().getSolver().respecteDefi(this.defi);
		
		for(Solution sol : MainFrame.getInstance().getSolver().solutions) {
			this.creerPlateauPieces(sol);
			this.showResult(sol == null);
		}
	}
	
	public void setCustomDefi(Defi d) {					
		MainFrame.getInstance().getSolver().solutions = MainFrame.getInstance().getSolver().respecteDefi(d);
				
		for(Solution sol : MainFrame.getInstance().getSolver().solutions) {
			this.creerPlateauPieces(sol);
			this.showResult(sol == null);
		}
	}

	public void creerPlateauPieces(Solution s) {
		//Créer le plateau avec les pièces dessus
		CasePiece hg = new CasePiece();
		CasePiece hd = new CasePiece();
		CasePiece bg = new CasePiece();
		CasePiece bd = new CasePiece();
		if(s == null) {
			for(int i = 0; i < 9; ++i) {
				hg.setMatricePiece(i/3, i%3, pieceHautGauche[i].getValue());
				hd.setMatricePiece(i/3, i%3, pieceHautDroite[i].getValue());
				bg.setMatricePiece(i/3, i%3, pieceBasGauche[i].getValue());
				bd.setMatricePiece(i/3, i%3, pieceBasDroite[i].getValue());
			}
		} else {
			for(int i = 0; i < 9; ++i) {
				hg.setMatricePiece(i/3, i%3, s.getIntVal(pieceHautGauche[i]));
				hd.setMatricePiece(i/3, i%3, s.getIntVal(pieceHautDroite[i]));
				bg.setMatricePiece(i/3, i%3, s.getIntVal(pieceBasGauche[i]));
				bd.setMatricePiece(i/3, i%3, s.getIntVal(pieceBasDroite[i]));
			}
		}
		this.plateauPieces = new PlateauPieces(bd, bg, hd, hg);
	}
	
	private void createModel() {
		//Variables
		pieceHautGauche = this.intVarArray(9, new int[]{0,1});
		pieceHautDroite = this.intVarArray(9, new int[]{0,1});
		pieceBasGauche = this.intVarArray(9, new int[]{0,1});
		pieceBasDroite = this.intVarArray(9, new int[]{0,1});
		
		List<int[][]> pieces1L = new ArrayList<>(2);
		List<int[][]> pieces2L = new ArrayList<>(4);
		List<int[][]> pieces3L= new ArrayList<>(4);
		List<int[][]> pieces4L = new ArrayList<>(4);
		for(int i = 0; i < 4; ++i) {
			pieces2L.add(new Piece2().rotateByNinetyToLeftNTimes(i));
			pieces3L.add(new Piece3().rotateByNinetyToLeftNTimes(i));
			pieces4L.add(new Piece4().rotateByNinetyToLeftNTimes(i));
		}
		pieces1L.add(new Piece1().rotateByNinetyToLeftNTimes(0));
		pieces1L.add(new Piece1().rotateByNinetyToLeftNTimes(1));
		
		IntVar[][] pieces1 = new IntVar[2][9];
		IntVar[][] pieces2 = new IntVar[4][9];
		IntVar[][] pieces3 = new IntVar[4][9];
		IntVar[][] pieces4 = new IntVar[4][9];
		for(int i = 0; i < 4; ++i) {
			for(int j = 0; j < 9; ++j) {
				pieces2[i][j] = this.intVar(pieces2L.get(i)[j/3][j%3]);
				pieces3[i][j] = this.intVar(pieces3L.get(i)[j/3][j%3]);
				pieces4[i][j] = this.intVar(pieces4L.get(i)[j/3][j%3]);
			}
		}
		for(int j = 0; j < 9; ++j) {
			pieces1[0][j] = this.intVar(pieces1L.get(0)[j/3][j%3]);
			pieces1[1][j] = this.intVar(pieces1L.get(1)[j/3][j%3]);
		}
		
		//Constraints

		//Chaque case doit correspondre à une pièce existante
		Constraint[] constraintPiecesHautGauche = new Constraint[14];
		Constraint[] constraintPiecesHautDroite = new Constraint[14];
		Constraint[] constraintPiecesBasGauche = new Constraint[14];
		Constraint[] constraintPiecesBasDroite = new Constraint[14];
			
		for(int i = 0; i < 4; ++i) {
			constraintPiecesHautGauche[i] = this.and(this.lexLessEq(pieceHautGauche, pieces2[i]), this.lexLessEq(pieces2[i], pieceHautGauche));
			constraintPiecesHautGauche[i+4] = this.and(this.lexLessEq(pieceHautGauche, pieces3[i]), this.lexLessEq(pieces3[i], pieceHautGauche));
			constraintPiecesHautGauche[i+8] = this.and(this.lexLessEq(pieceHautGauche, pieces4[i]), this.lexLessEq(pieces4[i], pieceHautGauche));
					
			constraintPiecesHautDroite[i] = this.and(this.lexLessEq(pieceHautDroite, pieces2[i]), this.lexLessEq(pieces2[i], pieceHautDroite));
			constraintPiecesHautDroite[i+4] = this.and(this.lexLessEq(pieceHautDroite, pieces3[i]), this.lexLessEq(pieces3[i], pieceHautDroite));
			constraintPiecesHautDroite[i+8] = this.and(this.lexLessEq(pieceHautDroite, pieces4[i]), this.lexLessEq(pieces4[i], pieceHautDroite));
			
			constraintPiecesBasGauche[i] = this.and(this.lexLessEq(pieceBasGauche, pieces2[i]).reify(), this.lexLessEq(pieces2[i], pieceBasGauche).reify());
			constraintPiecesBasGauche[i+4] = this.and(this.lexLessEq(pieceBasGauche, pieces3[i]).reify(), this.lexLessEq(pieces3[i], pieceBasGauche).reify());
			constraintPiecesBasGauche[i+8] = this.and(this.lexLessEq(pieceBasGauche, pieces4[i]).reify(), this.lexLessEq(pieces4[i], pieceBasGauche).reify());
			
			constraintPiecesBasDroite[i] = this.and(this.lexLessEq(pieceBasDroite, pieces2[i]), this.lexLessEq(pieces2[i], pieceBasDroite));
			constraintPiecesBasDroite[i+4] = this.and(this.lexLessEq(pieceBasDroite, pieces3[i]), this.lexLessEq(pieces3[i], pieceBasDroite));
			constraintPiecesBasDroite[i+8] = this.and(this.lexLessEq(pieceBasDroite, pieces4[i]), this.lexLessEq(pieces4[i], pieceBasDroite));
		}
		
		constraintPiecesHautGauche[12] = this.and(this.lexLessEq(pieceHautGauche, pieces1[0]), this.lexLessEq(pieces1[0], pieceHautGauche));
		constraintPiecesHautDroite[12] = this.and(this.lexLessEq(pieceHautDroite, pieces1[0]), this.lexLessEq(pieces1[0], pieceHautDroite));
		constraintPiecesBasGauche[12] = this.and(this.lexLessEq(pieceBasGauche, pieces1[0]), this.lexLessEq(pieces1[0], pieceBasGauche));
		constraintPiecesBasDroite[12] = this.and(this.lexLessEq(pieceBasDroite, pieces1[0]), this.lexLessEq(pieces1[0], pieceBasDroite));

		constraintPiecesHautGauche[13] = this.and(this.lexLessEq(pieceHautGauche, pieces1[1]), this.lexLessEq(pieces1[1], pieceHautGauche));
		constraintPiecesHautDroite[13] = this.and(this.lexLessEq(pieceHautDroite, pieces1[1]), this.lexLessEq(pieces1[1], pieceHautDroite));
		constraintPiecesBasGauche[13] = this.and(this.lexLessEq(pieceBasGauche, pieces1[1]), this.lexLessEq(pieces1[1], pieceBasGauche));
		constraintPiecesBasDroite[13] = this.and(this.lexLessEq(pieceBasDroite, pieces1[1]), this.lexLessEq(pieces1[1], pieceBasDroite));
		
		this.or(constraintPiecesHautGauche).post();
		this.or(constraintPiecesHautDroite).post();
		this.or(constraintPiecesBasGauche).post();
		this.or(constraintPiecesBasDroite).post();
		
		
		//Chaque case doit avoir une pièce différente d'une autre case
		Constraint[] p1HG = new Constraint[2];
		Constraint[] p1HD = new Constraint[2];
		Constraint[] p1BG = new Constraint[2];
		Constraint[] p1BD = new Constraint[2];

		Constraint[] cons1HG = new Constraint[2];
		Constraint[] cons1HD = new Constraint[2];
		Constraint[] cons1BG = new Constraint[2];
		Constraint[] cons1BD = new Constraint[2];

		Constraint[] p2HG = new Constraint[4];
		Constraint[] p2HD = new Constraint[4];
		Constraint[] p2BG = new Constraint[4];
		Constraint[] p2BD = new Constraint[4];

		Constraint[] cons2HG = new Constraint[4];
		Constraint[] cons2HD = new Constraint[4];
		Constraint[] cons2BG = new Constraint[4];
		Constraint[] cons2BD = new Constraint[4];
		
		Constraint[] p3HG = new Constraint[4];
		Constraint[] p3HD = new Constraint[4];
		Constraint[] p3BG = new Constraint[4];
		Constraint[] p3BD = new Constraint[4];

		Constraint[] cons3HG = new Constraint[4];
		Constraint[] cons3HD = new Constraint[4];
		Constraint[] cons3BG = new Constraint[4];
		Constraint[] cons3BD = new Constraint[4];
		
		Constraint[] p4HG = new Constraint[4];
		Constraint[] p4HD = new Constraint[4];
		Constraint[] p4BG = new Constraint[4];
		Constraint[] p4BD = new Constraint[4];

		Constraint[] cons4HG = new Constraint[4];
		Constraint[] cons4HD = new Constraint[4];
		Constraint[] cons4BG = new Constraint[4];
		Constraint[] cons4BD = new Constraint[4];

		
		for(int i = 0; i < 4; ++i) {
			p1HG[i%2] = this.and(this.lexLessEq(pieceHautGauche, pieces1[i%2]), this.lexLessEq(pieces1[i%2], pieceHautGauche));
			p1HD[i%2] = this.and(this.lexLessEq(pieceHautDroite, pieces1[i%2]), this.lexLessEq(pieces1[i%2], pieceHautDroite));
			p1BG[i%2] = this.and(this.lexLessEq(pieceBasGauche, pieces1[i%2]), this.lexLessEq(pieces1[i%2], pieceBasGauche));
			p1BD[i%2] = this.and(this.lexLessEq(pieceBasDroite, pieces1[i%2]), this.lexLessEq(pieces1[i%2], pieceBasDroite));

			cons1HG[i%2] = this.and(this.or(this.lexLess(pieceBasDroite, pieces1[i%2]), this.lexLess(pieces1[i%2], pieceBasDroite)), this.or(this.lexLess(pieceBasGauche, pieces1[i%2]), this.lexLess(pieces1[i%2], pieceBasGauche)), this.or(this.lexLess(pieceHautDroite, pieces1[i%2]), this.lexLess(pieces1[i%2], pieceHautDroite)));
			cons1HD[i%2] = this.and(this.or(this.lexLess(pieceBasDroite, pieces1[i%2]), this.lexLess(pieces1[i%2], pieceBasDroite)), this.or(this.lexLess(pieceBasGauche, pieces1[i%2]), this.lexLess(pieces1[i%2], pieceBasGauche)), this.or(this.lexLess(pieceHautGauche, pieces1[i%2]), this.lexLess(pieces1[i%2], pieceHautGauche)));
			cons1BG[i%2] = this.and(this.or(this.lexLess(pieceBasDroite, pieces1[i%2]), this.lexLess(pieces1[i%2], pieceBasDroite)), this.or(this.lexLess(pieceHautGauche, pieces1[i%2]), this.lexLess(pieces1[i%2], pieceHautGauche)), this.or(this.lexLess(pieceHautDroite, pieces1[i%2]), this.lexLess(pieces1[i%2], pieceHautDroite)));
			cons1BD[i%2] = this.and(this.or(this.lexLess(pieceHautGauche, pieces1[i%2]), this.lexLess(pieces1[i%2], pieceHautGauche)), this.or(this.lexLess(pieceBasGauche, pieces1[i%2]), this.lexLess(pieces1[i%2], pieceBasGauche)), this.or(this.lexLess(pieceHautDroite, pieces1[i%2]), this.lexLess(pieces1[i%2], pieceHautDroite)));
	
			p2HG[i] = this.and(this.lexLessEq(pieceHautGauche, pieces2[i]), this.lexLessEq(pieces2[i], pieceHautGauche));
			p2HD[i] = this.and(this.lexLessEq(pieceHautDroite, pieces2[i]), this.lexLessEq(pieces2[i], pieceHautDroite));
			p2BG[i] = this.and(this.lexLessEq(pieceBasGauche, pieces2[i]), this.lexLessEq(pieces2[i], pieceBasGauche));
			p2BD[i] = this.and(this.lexLessEq(pieceBasDroite, pieces2[i]), this.lexLessEq(pieces2[i], pieceBasDroite));

			cons2HG[i] = this.and(this.or(this.lexLess(pieceBasDroite, pieces2[i]), this.lexLess(pieces2[i], pieceBasDroite)), this.or(this.lexLess(pieceBasGauche, pieces2[i]), this.lexLess(pieces2[i], pieceBasGauche)), this.or(this.lexLess(pieceHautDroite, pieces2[i]), this.lexLess(pieces2[i], pieceHautDroite)));
			cons2HD[i] = this.and(this.or(this.lexLess(pieceBasDroite, pieces2[i]), this.lexLess(pieces2[i], pieceBasDroite)), this.or(this.lexLess(pieceBasGauche, pieces2[i]), this.lexLess(pieces2[i], pieceBasGauche)), this.or(this.lexLess(pieceHautGauche, pieces2[i]), this.lexLess(pieces2[i], pieceHautGauche)));
			cons2BG[i] = this.and(this.or(this.lexLess(pieceBasDroite, pieces2[i]), this.lexLess(pieces2[i], pieceBasDroite)), this.or(this.lexLess(pieceHautGauche, pieces2[i]), this.lexLess(pieces2[i], pieceHautGauche)), this.or(this.lexLess(pieceHautDroite, pieces2[i]), this.lexLess(pieces2[i], pieceHautDroite)));
			cons2BD[i] = this.and(this.or(this.lexLess(pieceHautGauche, pieces2[i]), this.lexLess(pieces2[i], pieceHautGauche)), this.or(this.lexLess(pieceBasGauche, pieces2[i]), this.lexLess(pieces2[i], pieceBasGauche)), this.or(this.lexLess(pieceHautDroite, pieces2[i]), this.lexLess(pieces2[i], pieceHautDroite)));
			
			p3HG[i] = this.and(this.lexLessEq(pieceHautGauche, pieces3[i]), this.lexLessEq(pieces3[i], pieceHautGauche));
			p3HD[i] = this.and(this.lexLessEq(pieceHautDroite, pieces3[i]), this.lexLessEq(pieces3[i], pieceHautDroite));
			p3BG[i] = this.and(this.lexLessEq(pieceBasGauche, pieces3[i]), this.lexLessEq(pieces3[i], pieceBasGauche));
			p3BD[i] = this.and(this.lexLessEq(pieceBasDroite, pieces3[i]), this.lexLessEq(pieces3[i], pieceBasDroite));

			cons3HG[i] = this.and(this.or(this.lexLess(pieceBasDroite, pieces3[i]), this.lexLess(pieces3[i], pieceBasDroite)), this.or(this.lexLess(pieceBasGauche, pieces3[i]), this.lexLess(pieces3[i], pieceBasGauche)), this.or(this.lexLess(pieceHautDroite, pieces3[i]), this.lexLess(pieces3[i], pieceHautDroite)));
			cons3HD[i] = this.and(this.or(this.lexLess(pieceBasDroite, pieces3[i]), this.lexLess(pieces3[i], pieceBasDroite)), this.or(this.lexLess(pieceBasGauche, pieces3[i]), this.lexLess(pieces3[i], pieceBasGauche)), this.or(this.lexLess(pieceHautGauche, pieces3[i]), this.lexLess(pieces3[i], pieceHautGauche)));
			cons3BG[i] = this.and(this.or(this.lexLess(pieceBasDroite, pieces3[i]), this.lexLess(pieces3[i], pieceBasDroite)), this.or(this.lexLess(pieceHautGauche, pieces3[i]), this.lexLess(pieces3[i], pieceHautGauche)), this.or(this.lexLess(pieceHautDroite, pieces3[i]), this.lexLess(pieces3[i], pieceHautDroite)));
			cons3BD[i] = this.and(this.or(this.lexLess(pieceHautGauche, pieces3[i]), this.lexLess(pieces3[i], pieceHautGauche)), this.or(this.lexLess(pieceBasGauche, pieces3[i]), this.lexLess(pieces3[i], pieceBasGauche)), this.or(this.lexLess(pieceHautDroite, pieces3[i]), this.lexLess(pieces3[i], pieceHautDroite)));
			
			p4HG[i] = this.and(this.lexLessEq(pieceHautGauche, pieces4[i]), this.lexLessEq(pieces4[i], pieceHautGauche));
			p4HD[i] = this.and(this.lexLessEq(pieceHautDroite, pieces4[i]), this.lexLessEq(pieces4[i], pieceHautDroite));
			p4BG[i] = this.and(this.lexLessEq(pieceBasGauche, pieces4[i]), this.lexLessEq(pieces4[i], pieceBasGauche));
			p4BD[i] = this.and(this.lexLessEq(pieceBasDroite, pieces4[i]), this.lexLessEq(pieces4[i], pieceBasDroite));

			cons4HG[i] = this.and(this.or(this.lexLess(pieceBasDroite, pieces4[i]), this.lexLess(pieces4[i], pieceBasDroite)), this.or(this.lexLess(pieceBasGauche, pieces4[i]), this.lexLess(pieces4[i], pieceBasGauche)), this.or(this.lexLess(pieceHautDroite, pieces4[i]), this.lexLess(pieces4[i], pieceHautDroite)));
			cons4HD[i] = this.and(this.or(this.lexLess(pieceBasDroite, pieces4[i]), this.lexLess(pieces4[i], pieceBasDroite)), this.or(this.lexLess(pieceBasGauche, pieces4[i]), this.lexLess(pieces4[i], pieceBasGauche)), this.or(this.lexLess(pieceHautGauche, pieces4[i]), this.lexLess(pieces4[i], pieceHautGauche)));
			cons4BG[i] = this.and(this.or(this.lexLess(pieceBasDroite, pieces4[i]), this.lexLess(pieces4[i], pieceBasDroite)), this.or(this.lexLess(pieceHautGauche, pieces4[i]), this.lexLess(pieces4[i], pieceHautGauche)), this.or(this.lexLess(pieceHautDroite, pieces4[i]), this.lexLess(pieces4[i], pieceHautDroite)));
			cons4BD[i] = this.and(this.or(this.lexLess(pieceHautGauche, pieces4[i]), this.lexLess(pieces4[i], pieceHautGauche)), this.or(this.lexLess(pieceBasGauche, pieces4[i]), this.lexLess(pieces4[i], pieceBasGauche)), this.or(this.lexLess(pieceHautDroite, pieces4[i]), this.lexLess(pieces4[i], pieceHautDroite)));
		}

		this.ifThen(this.or(p1HG), this.and(cons1HG));
		this.ifThen(this.or(p1HD), this.and(cons1HD));
		this.ifThen(this.or(p1BG), this.and(cons1BG));
		this.ifThen(this.or(p1BD), this.and(cons1BD));
						
		this.ifThen(this.or(p2HG), this.and(cons2HG));
		this.ifThen(this.or(p2HD), this.and(cons2HD));
		this.ifThen(this.or(p2BG), this.and(cons2BG));
		this.ifThen(this.or(p2BD), this.and(cons2BD));
		
		this.ifThen(this.or(p3HG), this.and(cons3HG));
		this.ifThen(this.or(p3HD), this.and(cons3HD));
		this.ifThen(this.or(p3BG), this.and(cons3BG));
		this.ifThen(this.or(p3BD), this.and(cons3BD));
		
		this.ifThen(this.or(p4HG), this.and(cons4HG));
		this.ifThen(this.or(p4HD), this.and(cons4HD));
		this.ifThen(this.or(p4BG), this.and(cons4BG));
		this.ifThen(this.or(p4BD), this.and(cons4BD));
		
		//Respecter le défi
		fr.etu.jeu.model.Plateau plateau = fr.etu.jeu.model.Plateau.getInstance();
		this.nbRhinoceros = this.intVar(new int[]{0,1,2,3,4,5});
		this.nbLion = this.intVar(new int[]{0,1,2,3,4,5,6});
		this.nbAntilope = this.intVar(new int[]{0,1,2,3,4,5});
		this.nbElephant = this.intVar(new int[]{0,1,2,3,4,5});
		this.nbZebre = this.intVar(new int[]{0,1,2,3,4,5});
		
		IntVar[] rhinoceros = new IntVar[36];
		IntVar[] lion = new IntVar[36];
		IntVar[] antilope = new IntVar[36];
		IntVar[] elephant = new IntVar[36];
		IntVar[] zebre = new IntVar[36];
		
		for(int i = 0; i < 36; ++i) {
			rhinoceros[i] = this.intVar(new int[]{0,1});
			lion[i] = this.intVar(new int[]{0,1});
			antilope[i] = this.intVar(new int[]{0,1});
			elephant[i] = this.intVar(new int[]{0,1});
			zebre[i] = this.intVar(new int[]{0,1});
		}
		
		for(int i = 0; i < 9; ++i) {
			this.ifThenElse(this.and(this.allEqual(new IntVar[] {pieceHautGauche[i], this.intVar(0)}), this.allEqual(this.intVar(plateau.getHautGauche().getMatriceAnimaux()[i/3][i%3].getValue()), this.intVar(new Elephant().getValue()))), this.allEqual(elephant[i], this.intVar(1)), this.allEqual(elephant[i], this.intVar(0)));
			this.ifThenElse(this.and(this.allEqual(new IntVar[] {pieceHautGauche[i], this.intVar(0)}), this.allEqual(this.intVar(plateau.getHautGauche().getMatriceAnimaux()[i/3][i%3].getValue()), this.intVar(new Lion().getValue()))), this.allEqual(lion[i], this.intVar(1)), this.allEqual(lion[i], this.intVar(0)));
			this.ifThenElse(this.and(this.allEqual(new IntVar[] {pieceHautGauche[i], this.intVar(0)}), this.allEqual(this.intVar(plateau.getHautGauche().getMatriceAnimaux()[i/3][i%3].getValue()), this.intVar(new Antilope().getValue()))), this.allEqual(antilope[i], this.intVar(1)), this.allEqual(antilope[i], this.intVar(0)));
			this.ifThenElse(this.and(this.allEqual(new IntVar[] {pieceHautGauche[i], this.intVar(0)}), this.allEqual(this.intVar(plateau.getHautGauche().getMatriceAnimaux()[i/3][i%3].getValue()), this.intVar(new Rhinoceros().getValue()))), this.allEqual(rhinoceros[i], this.intVar(1)), this.allEqual(rhinoceros[i], this.intVar(0)));
			this.ifThenElse(this.and(this.allEqual(new IntVar[] {pieceHautGauche[i], this.intVar(0)}), this.allEqual(this.intVar(plateau.getHautGauche().getMatriceAnimaux()[i/3][i%3].getValue()), this.intVar(new Zebre().getValue()))), this.allEqual(zebre[i], this.intVar(1)), this.allEqual(zebre[i], this.intVar(0)));

			this.ifThenElse(this.and(this.allEqual(new IntVar[] {pieceHautDroite[i], this.intVar(0)}), this.allEqual(this.intVar(plateau.getHautDroite().getMatriceAnimaux()[i/3][i%3].getValue()), this.intVar(new Elephant().getValue()))), this.allEqual(elephant[i+9], this.intVar(1)), this.allEqual(elephant[i+9], this.intVar(0)));
			this.ifThenElse(this.and(this.allEqual(new IntVar[] {pieceHautDroite[i], this.intVar(0)}), this.allEqual(this.intVar(plateau.getHautDroite().getMatriceAnimaux()[i/3][i%3].getValue()), this.intVar(new Lion().getValue()))), this.allEqual(lion[i+9], this.intVar(1)), this.allEqual(lion[i+9], this.intVar(0)));
			this.ifThenElse(this.and(this.allEqual(new IntVar[] {pieceHautDroite[i], this.intVar(0)}), this.allEqual(this.intVar(plateau.getHautDroite().getMatriceAnimaux()[i/3][i%3].getValue()), this.intVar(new Antilope().getValue()))), this.allEqual(antilope[i+9], this.intVar(1)), this.allEqual(antilope[i+9], this.intVar(0)));
			this.ifThenElse(this.and(this.allEqual(new IntVar[] {pieceHautDroite[i], this.intVar(0)}), this.allEqual(this.intVar(plateau.getHautDroite().getMatriceAnimaux()[i/3][i%3].getValue()), this.intVar(new Rhinoceros().getValue()))), this.allEqual(rhinoceros[i+9], this.intVar(1)), this.allEqual(rhinoceros[i+9], this.intVar(0)));
			this.ifThenElse(this.and(this.allEqual(new IntVar[] {pieceHautDroite[i], this.intVar(0)}), this.allEqual(this.intVar(plateau.getHautDroite().getMatriceAnimaux()[i/3][i%3].getValue()), this.intVar(new Zebre().getValue()))), this.allEqual(zebre[i+9], this.intVar(1)), this.allEqual(zebre[i+9], this.intVar(0)));

			this.ifThenElse(this.and(this.allEqual(new IntVar[] {pieceBasGauche[i], this.intVar(0)}), this.allEqual(this.intVar(plateau.getBasGauche().getMatriceAnimaux()[i/3][i%3].getValue()), this.intVar(new Elephant().getValue()))), this.allEqual(elephant[i+18], this.intVar(1)), this.allEqual(elephant[i+18], this.intVar(0)));
			this.ifThenElse(this.and(this.allEqual(new IntVar[] {pieceBasGauche[i], this.intVar(0)}), this.allEqual(this.intVar(plateau.getBasGauche().getMatriceAnimaux()[i/3][i%3].getValue()), this.intVar(new Lion().getValue()))), this.allEqual(lion[i+18], this.intVar(1)), this.allEqual(lion[i+18], this.intVar(0)));
			this.ifThenElse(this.and(this.allEqual(new IntVar[] {pieceBasGauche[i], this.intVar(0)}), this.allEqual(this.intVar(plateau.getBasGauche().getMatriceAnimaux()[i/3][i%3].getValue()), this.intVar(new Antilope().getValue()))), this.allEqual(antilope[i+18], this.intVar(1)), this.allEqual(antilope[i+18], this.intVar(0)));
			this.ifThenElse(this.and(this.allEqual(new IntVar[] {pieceBasGauche[i], this.intVar(0)}), this.allEqual(this.intVar(plateau.getBasGauche().getMatriceAnimaux()[i/3][i%3].getValue()), this.intVar(new Rhinoceros().getValue()))), this.allEqual(rhinoceros[i+18], this.intVar(1)), this.allEqual(rhinoceros[i+18], this.intVar(0)));
			this.ifThenElse(this.and(this.allEqual(new IntVar[] {pieceBasGauche[i], this.intVar(0)}), this.allEqual(this.intVar(plateau.getBasGauche().getMatriceAnimaux()[i/3][i%3].getValue()), this.intVar(new Zebre().getValue()))), this.allEqual(zebre[i+18], this.intVar(1)), this.allEqual(zebre[i+18], this.intVar(0)));

			this.ifThenElse(this.and(this.allEqual(new IntVar[] {pieceBasDroite[i], this.intVar(0)}), this.allEqual(this.intVar(plateau.getBasDroite().getMatriceAnimaux()[i/3][i%3].getValue()), this.intVar(new Elephant().getValue()))), this.allEqual(elephant[i+27], this.intVar(1)), this.allEqual(elephant[i+27], this.intVar(0)));
			this.ifThenElse(this.and(this.allEqual(new IntVar[] {pieceBasDroite[i], this.intVar(0)}), this.allEqual(this.intVar(plateau.getBasDroite().getMatriceAnimaux()[i/3][i%3].getValue()), this.intVar(new Lion().getValue()))), this.allEqual(lion[i+27], this.intVar(1)), this.allEqual(lion[i+27], this.intVar(0)));
			this.ifThenElse(this.and(this.allEqual(new IntVar[] {pieceBasDroite[i], this.intVar(0)}), this.allEqual(this.intVar(plateau.getBasDroite().getMatriceAnimaux()[i/3][i%3].getValue()), this.intVar(new Antilope().getValue()))), this.allEqual(antilope[i+27], this.intVar(1)), this.allEqual(antilope[i+27], this.intVar(0)));
			this.ifThenElse(this.and(this.allEqual(new IntVar[] {pieceBasDroite[i], this.intVar(0)}), this.allEqual(this.intVar(plateau.getBasDroite().getMatriceAnimaux()[i/3][i%3].getValue()), this.intVar(new Rhinoceros().getValue()))), this.allEqual(rhinoceros[i+27], this.intVar(1)), this.allEqual(rhinoceros[i+27], this.intVar(0)));
			this.ifThenElse(this.and(this.allEqual(new IntVar[] {pieceBasDroite[i], this.intVar(0)}), this.allEqual(this.intVar(plateau.getBasDroite().getMatriceAnimaux()[i/3][i%3].getValue()), this.intVar(new Zebre().getValue()))), this.allEqual(zebre[i+27], this.intVar(1)), this.allEqual(zebre[i+27], this.intVar(0)));
		}
		this.sum(rhinoceros, "+", nbRhinoceros).post();
		this.sum(lion, "+", nbLion).post();
		this.sum(antilope, "+", nbAntilope).post();
		this.sum(elephant, "+", nbElephant).post();
		this.sum(zebre, "+", nbZebre).post();
	}
	
	private void showResult(boolean s) {
		//Display
		String sol = "";
		if(s)
			 sol = "Unfeasible";
		else {
			sol = this.defi.toString();
			sol += this.plateauPieces.toString();
		}
		System.out.println(sol);
	}

	public PlateauPieces getPlateauPieces() {
		return plateauPieces;
	}
	
	public void setPlateauPieces(PlateauPieces plateauPieces) {
		this.plateauPieces = plateauPieces;
	}
	
	public Defi getDefi() {
		return defi;
	}
	
	public void setDefi(Defi defi) {
		this.defi = defi;
	}

	public List<Solution> getSolutions() {
		return solutions;
	}

	public void setSolutions(List<Solution> solutions) {
		this.solutions = solutions;
	}
}
