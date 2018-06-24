package fr.etu.jeu.vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import fr.etu.jeu.Solver;
import fr.etu.jeu.vue.pieces.Piece;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 2829783440648426823L;
	
	private Piece pieceSelected = new Piece();
	private Solver solver;

	private MainFrame() {
		super();		
		
		Plateau plateau = Plateau.getInstance();
		this.add(plateau, BorderLayout.WEST);
		final Menu menu;
		this.setMenuBar(menu = new Menu());
		
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				 menu.new Quitter().actionPerformed(null);
			}
		});

		this.pack();
		this.setResizable(false);
		
		this.setVisible(true);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		init();
	}
	
	public void init() {
		InputStream in = this.getClass().getResourceAsStream("safari.png"); 
		Image img = null;
		try {
			img = ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		final ImageIcon safari = new ImageIcon(img);
		JOptionPane pane = new JOptionPane("<html>Bienvenue dans Safari - Cache Cache!<br>Voulez-vous lire les règles du jeu, commencer une nouvelle partie ou créer votre propre défi?</html>", JOptionPane.INFORMATION_MESSAGE, JOptionPane.NO_OPTION, safari, new Object[] {}, null);
		final JDialog dialog1 = new JDialog(this);
		dialog1.setTitle("Bienvenue");
		pane.setLayout(new GridLayout(2, 1));
		JButton jouer = new JButton("Jouer");
		jouer.setBackground(new Color(111, 153, 89));
		jouer.setOpaque(true);
		jouer.setBorderPainted(false);
		
		final JButton creerDefi = new JButton("Créez votre défi");
		creerDefi.setBackground(new Color(202, 209, 223));
		creerDefi.setOpaque(true);
		creerDefi.setBorderPainted(false);
		final JDialog dialog2 = new JDialog(dialog1, "Règles du jeu", true);
		creerDefi.addActionListener(new Menu().new CreationDefiPerso(dialog1, dialog2));
		
		
		
		JButton regles = new JButton("Lire les règles");
		regles.setOpaque(true);
		jouer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog1.setAlwaysOnTop(false);
				dialog1.dispose();
				new Menu().new NewGame().actionPerformed(null);
			}
		});
		
		regles.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog1.setAlwaysOnTop(false);
				dialog1.dispose();
				try {
					BufferedReader buf = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("regles.txt")));
					        
					String line = buf.readLine();
					StringBuilder sb = new StringBuilder();
					        
					while(line != null){
					   sb.append(line).append("\n");
					   line = buf.readLine();
					}
					String fileAsString = sb.toString();

					JOptionPane pane2 = new JOptionPane(fileAsString, JOptionPane.INFORMATION_MESSAGE, JOptionPane.NO_OPTION, safari, new Object[] {}, null);
					
					JPanel buttons = new JPanel();
					JButton go = new JButton("Jouer");
					go.setBackground(new Color(111, 153, 89));
					go.setOpaque(true);
					go.setBorderPainted(false);
					go.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							dialog2.setAlwaysOnTop(false);
							dialog2.dispose();
							new Menu().new NewGame().actionPerformed(null);
						}
					});
					
					buttons.add(go);
					buttons.add(creerDefi);
					dialog2.add(pane2);
					dialog2.add(buttons, BorderLayout.SOUTH);
					
					dialog2.pack();
					dialog2.setAlwaysOnTop(true);
					Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
					dialog2.setLocation(dim.width/2-dialog2.getSize().width/2, dim.height/2-dialog2.getSize().height/2);
					dialog2.setVisible(true);
				} catch (HeadlessException | IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		pane.setOptions(new JButton[] {regles, creerDefi, jouer});
		dialog1.add(pane);
		dialog1.pack();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		dialog1.setLocation(dim.width/2-dialog1.getSize().width/2, dim.height/2-dialog1.getSize().height/2);
		dialog1.setVisible(true);	
		this.repaint();
	}
	
	private static class FrameHolder {
		private static MainFrame frame = new MainFrame();
	}
	
	public static MainFrame getInstance() {
		return FrameHolder.frame;
	}
	
	public void setPieceSelected(Piece pieceSelected) {
		this.pieceSelected = pieceSelected;
	}
	
	public Piece getPieceSelected() {
		return this.pieceSelected;
	}

	public Solver getSolver() {
		return solver;
	}

	public void setSolver(Solver solver) {
		this.solver = solver;
	}
}
