package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;

import Controller.GestionVilleController;
import Main.GestionVille;

public class DebugViewPanel extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static private Color BG_COLOR  = Color.white;
	private JButton nextStepButton;
	private JSlider speedMoveSlider;
	private Thread actionThread;
	
	
	public GestionVilleController gestionVilleController;
	/**
	 * Constructeur par défaut
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
		
		/** Slider **/
	    this.speedMoveSlider = new JSlider();
	    this.speedMoveSlider.setBackground(Color.red);
	    this.speedMoveSlider.setBorder(BorderFactory.createTitledBorder("Vitesse de défilement"));
	    this.speedMoveSlider.setMajorTickSpacing(20);
	    this.speedMoveSlider.setMinorTickSpacing(5);
	    this.speedMoveSlider.setPaintTicks(true);
	    this.speedMoveSlider.setPaintLabels(true);
	    add(this.speedMoveSlider, BorderLayout.SOUTH);
		
		
		/** Pour que le panneaux puisse √©couter les √©v√©nements */
		//addKeyListener(this);
		
		/** Autre */
		setFocusable(true);
		requestFocus();
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		
		
		Object source = event.getSource();
		if (source == this.nextStepButton) {
			
			
			/*
			for (int i=0; i<100; i++) {
				GestionVille.getVille().oneStep();
				this.gestionVilleController.mapViewPanel.repaint();
			}
			*/

			//Timer timer = new Timer(0, event);
			
			if (actionThread != null) 
				return;
			
			
			
				//return;
			
			actionThread = new Thread(){
				 public void run() {
					 
				while(true) {
					  System.out.println("slaut");
					  
					  GestionVille.getVille().oneStep();
					  gestionVilleController.mapViewPanel.repaint();
					  
					  
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				  
				  }
				 }
				};
				
				actionThread.start();
				
			
		}
	}
}
