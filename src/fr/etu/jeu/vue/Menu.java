package fr.etu.jeu.vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import fr.etu.jeu.Solver;
import fr.etu.jeu.model.Defi;
import fr.etu.jeu.model.animaux.Animal;
import fr.etu.jeu.model.animaux.Antilope;
import fr.etu.jeu.model.animaux.Elephant;
import fr.etu.jeu.model.animaux.Lion;
import fr.etu.jeu.model.animaux.Rhinoceros;
import fr.etu.jeu.model.animaux.Zebre;

public class Menu extends MenuBar {
	private static final long serialVersionUID = -3357895288688180006L;

	public Menu() {
		super();

		java.awt.Menu menu = new java.awt.Menu("Partie");
		MenuItem newGame = new MenuItem("Nouvelle partie", new MenuShortcut(KeyEvent.getExtendedKeyCodeForChar('N'), false));
		newGame.addActionListener(new NewGame());
		menu.add(newGame);

		MenuItem quit = new MenuItem("Quitter partie", new MenuShortcut(KeyEvent.getExtendedKeyCodeForChar('Q'), false));
		quit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainFrame.getInstance().remove(Outils.getInstance());
				MainFrame.getInstance().pack();
			}
		});
		menu.add(quit);

		MenuItem quitAll = new MenuItem("Quitter Safari - Cache Cache", new MenuShortcut(KeyEvent.getExtendedKeyCodeForChar('E'), true));
		quitAll.addActionListener(new Quitter());

		menu.add(quitAll);

		this.add(menu);
	}

	public class Quitter implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JOptionPane pane = new JOptionPane("Êtes-vous sûr de vouloir quitter le jeu?", 0);
			final JDialog dialog = new JDialog(MainFrame.getInstance(), "Quitter", true);
			JButton oui = new JButton("Oui");
			JButton non = new JButton("Non");
			oui.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
			non.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					dialog.dispose();
				}
			});
			pane.setOptions(new JButton[] {oui, non});
			dialog.add(pane);
			dialog.pack();
			dialog.setAlwaysOnTop(true);
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			dialog.setLocation(dim.width/2-dialog.getSize().width/2, dim.height/2-dialog.getSize().height/2);
			dialog.setVisible(true);
		}
	}


	public class NewGame implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			MainFrame.getInstance().setSolver(new Solver());

			JButton facile = new JButton("Facile");
			JButton moyen = new JButton("Moyen");
			JButton difficile = new JButton("Difficile");
			JButton expert = new JButton("Expert");
			final JDialog dialog = new JDialog(MainFrame.getInstance(), "Niveaux", true);
			facile.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					dialog.dispose();
					waiting(Solver.FACILE);
				}
			});
			moyen.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					dialog.dispose();
					waiting(Solver.MOYEN);
				}
			});
			difficile.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					dialog.dispose();
					waiting(Solver.DIFFICILE);
				}
			});
			expert.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					dialog.dispose();
					waiting(Solver.EXPERT);
				}
			});
			JOptionPane pane = new JOptionPane("Choisissez le niveau de difficulté voulue", JOptionPane.INFORMATION_MESSAGE, JOptionPane.NO_OPTION, null, new JButton[] {facile, moyen, difficile, expert}, null);
			dialog.add(pane, BorderLayout.NORTH);
			JPanel panel = new JPanel(new GridLayout(1, 4));
			panel.add(facile);
			panel.add(moyen);
			panel.add(difficile);
			panel.add(expert);
			dialog.add(panel, BorderLayout.CENTER);
			final JButton creerDefi = new JButton("Créez votre défi");
			creerDefi.setBackground(new Color(202, 209, 223));
			creerDefi.setOpaque(true);
			creerDefi.setBorderPainted(false);
			creerDefi.addActionListener(new Menu().new CreationDefiPerso(null, dialog));
			dialog.add(creerDefi, BorderLayout.SOUTH);
			dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
			dialog.pack();
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			dialog.setLocation(dim.width/2-dialog.getSize().width/2, dim.height/2-dialog.getSize().height/2);
			dialog.setVisible(true);
		}
	}

	public void initCustomDefi(final Defi d) {
		final JDialog dialog = new JDialog(MainFrame.getInstance(), "Chargement", true);
		dialog.setLayout(new GridLayout(2, 1));
		dialog.setPreferredSize(new Dimension(520, 500));

		Thread solving = new Thread(new Runnable() {
			@Override
			public void run() {
				MainFrame.getInstance().getSolver().setCustomDefi(d);

				MainFrame.getInstance().remove(Outils.getInstance());
				Outils.resetInstance();
				Outils outils = Outils.getInstance();

				// gestion des défis sans solution
				if (MainFrame.getInstance().getSolver().getSolutions().isEmpty() )
				{
					dialog.dispose();

					JDialog erreur = new JDialog();
					JOptionPane pane = new JOptionPane("<html> Il n'y a pas de solution pour ce défi </html>", JOptionPane.INFORMATION_MESSAGE, JOptionPane.NO_OPTION,null , new Object[] {}, null);

					JPanel panelErreur = new JPanel();

					erreur.add(panelErreur);
					erreur.add(pane);
					erreur.setTitle("Erreur");
					erreur.setModal(true); // mets du focus 
					erreur.setAlwaysOnTop(true);
					erreur.setSize(400, 120);
					Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
					erreur.setLocation(dim.width/2-erreur.getSize().width/2, dim.height/2-erreur.getSize().height/2);
					erreur.setVisible(true);

					erreur.pack();


					MainFrame.getInstance().init();
				} else {
					outils.setPanelDefi(MainFrame.getInstance().getSolver().getDefi());

					MainFrame.getInstance().add(outils, BorderLayout.EAST);
					MainFrame.getInstance().pack();
					MainFrame.getInstance().setEnabled(true);
				}
				outils.updateUI();
				dialog.dispose();
			}
		});
		solving.start();


		InputStream in = getClass().getResourceAsStream("loading.gif"); 
		BufferedInputStream bis = new BufferedInputStream(in);
		// a buffer large enough for our image
		//
		// can be
		byte[] byBuf = null;
		try {
			byBuf = new byte[in.available()];
			bis.read(byBuf);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Image wait = Toolkit.getDefaultToolkit().createImage(byBuf);
		JOptionPane pane = new JOptionPane("Merci de patentiez quelques instants durant la résolution de votre défi");
		pane.setOptions(new Object[0]);
		dialog.add(pane);
		ImageIcon waitIcon = new ImageIcon(wait);
		JLabel waiting = new JLabel(waitIcon);
		waiting.setSize(new Dimension(waitIcon.getIconWidth(), waitIcon.getIconHeight()));
		dialog.add(waiting);
		dialog.setAlwaysOnTop(true);
		MainFrame.getInstance().setEnabled(false);
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dialog.pack();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		dialog.setLocation(dim.width/2-dialog.getSize().width/2, dim.height/2-dialog.getSize().height/2);
		dialog.setVisible(true);
	}

	private void waiting(final int niv) {
		final JDialog dialog = new JDialog(MainFrame.getInstance(), "Chargement", true);
		dialog.setLayout(new GridLayout(2, 1));
		dialog.setPreferredSize(new Dimension(520, 500));

		Thread solving = new Thread(new Runnable() {
			@Override
			public void run() {
				MainFrame.getInstance().getSolver().creerDefi(niv);
				MainFrame.getInstance().remove(Outils.getInstance());
				Outils.resetInstance();
				Outils outils = Outils.getInstance();

				outils.setPanelDefi(MainFrame.getInstance().getSolver().getDefi());
				MainFrame.getInstance().add(outils, BorderLayout.EAST);
				MainFrame.getInstance().pack();
				outils.updateUI();
				dialog.dispose();
				MainFrame.getInstance().setEnabled(true);
			}
		});
		solving.start();


		InputStream in = getClass().getResourceAsStream("loading.gif"); 
		BufferedInputStream bis = new BufferedInputStream(in);
		// a buffer large enough for our image
		//
		// can be
		byte[] byBuf = null;
		try {
			byBuf = new byte[in.available()];
			bis.read(byBuf);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Image wait = Toolkit.getDefaultToolkit().createImage(byBuf);
		JOptionPane pane = new JOptionPane("Merci de patentiez quelques instants durant la création d'un nouveau défi");
		pane.setOptions(new Object[0]);
		dialog.add(pane);
		ImageIcon waitIcon = new ImageIcon(wait);
		JLabel waiting = new JLabel(waitIcon);
		waiting.setSize(new Dimension(waitIcon.getIconWidth(), waitIcon.getIconHeight()));
		dialog.add(waiting);
		dialog.setAlwaysOnTop(true);
		MainFrame.getInstance().setEnabled(false);
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dialog.pack();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		dialog.setLocation(dim.width/2-dialog.getSize().width/2, dim.height/2-dialog.getSize().height/2);
		dialog.setVisible(true);
	}

	public class CreationDefiPerso implements ActionListener {
		private final JDialog dialog1;
		private final JDialog dialog2;

		public CreationDefiPerso(final JDialog dialogUn, final JDialog dialogDeux) {
			dialog1 = dialogUn;
			dialog2 = dialogDeux;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			final JDialog dialogCreerDefi;
			if(dialog1 != null ) {
				dialog1.setAlwaysOnTop(false);	
				dialogCreerDefi = new JDialog(dialog1);
			} else 
				dialogCreerDefi = new JDialog(MainFrame.getInstance());
			if(dialog2 != null)
				dialog2.dispose();

			dialogCreerDefi.setPreferredSize(new Dimension(200,350));
			dialogCreerDefi.setTitle("Créez votre défi");

			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			dialogCreerDefi.setLocation(dim.width/2-dialogCreerDefi.getSize().width/2, dim.height/2-dialogCreerDefi.getSize().height/2);

			JPanel globalPanel = new JPanel();
			globalPanel.setLayout(new FlowLayout());



			JPanel nbAnimauxPanel = new JPanel();
			nbAnimauxPanel.setLayout(new GridLayout(6, 2));



			final Animal[] animaux = {new Lion(), new Elephant(), new Antilope(), new Zebre(),  new Rhinoceros()};
			@SuppressWarnings("unchecked")
			final JComboBox<String>[] fieldAnimaux = new JComboBox[animaux.length];

			for(int i = 0; i < animaux.length; ++i)
			{
				JLabel labelAnimal;
				labelAnimal = new JLabel(animaux[i].getImageMini());
				nbAnimauxPanel.add(labelAnimal);

				String nombresAutorises[] = {"0","1","2","3","4","5"};
				fieldAnimaux[i] = new JComboBox<String>(nombresAutorises);
				nbAnimauxPanel.add(fieldAnimaux[i]);

			}

			JPanel panelValider = new JPanel();
			JButton validerNombres = new JButton("Valider");
			panelValider.add(validerNombres);



			globalPanel.add(nbAnimauxPanel);
			globalPanel.add(panelValider);

			dialogCreerDefi.add(globalPanel);
			dialogCreerDefi.pack();
			dialogCreerDefi.setVisible(true);

			validerNombres.addActionListener(new ActionListener() {


				int[] choix = new int[animaux.length];

				public void actionPerformed(ActionEvent e) {

					for (int i = 0; i < animaux.length; ++i)
					{
						choix[i] = Integer.parseInt((String) fieldAnimaux[i].getSelectedItem());
						System.out.println(choix[i]);
					}

					if(dialog1 != null)
						dialog1.dispose();
					dialogCreerDefi.dispose();

					MainFrame.getInstance().setSolver(new Solver());
					Defi leDefi = new Defi(choix[0], choix[1], choix[2], choix[3], choix[4]);

					new Menu().initCustomDefi(leDefi);
				}
			});				
		}
	}
}
