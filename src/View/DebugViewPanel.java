package View;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import Controller.GestionVilleController;
import Main.GestionVille;

public class DebugViewPanel extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static private Color BG_COLOR  = Color.red;
	private JButton nextStepButton;
	
	public GestionVilleController gestionVilleController;
	
	
	/**
	 * Constructeur par d�faut
	 * 
	 * 
	 */
	public DebugViewPanel() {
		super();
		
		this.setBackground(BG_COLOR);
		
		/** Dessine la vue principale */
		this.drawView();

	}
	
	/**
	 * Draw the view
	 * 
	 * 
	 */
	private void drawView() {
		
		/** Dessine le titre */
		setLayout (new BoxLayout(this,BoxLayout.Y_AXIS));
		JLabel label1 = new JLabel("DEBUG");
		label1.setBackground(Color.gray);
		label1.setForeground(Color.white);
		label1.setOpaque(true);
		add(label1);

		/** Dessine les radios */
		JRadioButton rb1 = new JRadioButton("Suivre tout le monde");
		JRadioButton rb2 = new JRadioButton("Suivre une personne");
		rb1.setSelected(true);
		JPanel radioPanel = new JPanel(new GridLayout(2,2));
		radioPanel.setBackground(Color.yellow);
		ButtonGroup bgroup = new ButtonGroup();
		bgroup.add(rb1); radioPanel.add(rb1);
		bgroup.add(rb2); radioPanel.add(rb2);
		add(radioPanel);

		/** Dessine la liste des ids */
		String[] valeurs = {"1","2","3","4","5","6","7","8", "9","10","11","12"};
		JComboBox jcb = new JComboBox(valeurs);
		jcb.setSelectedIndex(2);
		add(jcb);
		
		/** Ajoute un boutons*/
		this.nextStepButton = new JButton("Next Step >");
		this.nextStepButton.setBackground(Color.yellow);
		this.nextStepButton.setForeground(Color.darkGray);
		this.nextStepButton.setOpaque(false);
		this.nextStepButton.addActionListener(this);
		add(this.nextStepButton);
		
		
		
		/** Pour que le panneaux puisse écouter les événements */
		//addKeyListener(this);
		
		/** Autre */
		setFocusable(true);
		requestFocus();
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		
		Object source = event.getSource();
		
		if (source == this.nextStepButton) {
			
			GestionVille.getVille().oneStep();
			this.gestionVilleController.mapViewPanel.repaint();
		}
		
		
		
	}
	
	
	
	
	
	
	

	
}
