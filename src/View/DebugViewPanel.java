package View;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Controller.GestionVilleController;
import Main.GestionVille;

public class DebugViewPanel extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static private Color BG_COLOR  = Color.white;
	private static JButton nextStepButton;
	private static JSlider speedMoveSlider;
	private static Timer actionTimer;
	
	private static JLabel pompiersLabel;
	private static JLabel medecinsLabel;
	private static JLabel citoyensLabel;
	
	
	public GestionVilleController gestionVilleController;
	/**
	 * Constructeur par défaut
	 *
	 */
	public DebugViewPanel() {
		super();
		this.setBackground(BG_COLOR);
		/** Dessine la vue principale */
		this.drawView();
		
		/** Initialise le thread*/
		this.initTimer();
		
	}
	private void initTimer() {
		int delay = 10* (100-speedMoveSlider.getValue());
		actionTimer = new Timer(delay, new AbstractAction() {
		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			int count = 20;
		    @Override
		    public void actionPerformed(ActionEvent ae) {
		        if (count < 10000 ) {
		        	
					GestionVille.getVille().oneStep();
					gestionVilleController.mapViewPanel.repaint();
					
					medecinsLabel.setText("<html><FONT COLOR=BLUE><B>Medecins : "+ GestionVille.getVille().getNbMedecin() +
							"</B></FONT></html>");

					pompiersLabel.setText("<html><FONT COLOR=RED><B>Pompiers : "+ GestionVille.getVille().getNbPompier() +
							"</B></FONT></html>");
					
					citoyensLabel.setText("<html><FONT COLOR=GRAY><B>Citoyens : "+ GestionVille.getVille().getNbCitoyen() +
							"</B></FONT></html>");
					
		            count++;
		        } else {//counter is at 1000 stop the timer
		            ((Timer) ae.getSource()).stop();
		        }
		    }
		});
	}

	/**
	 * Draw the view
	 * 
	 * 
	 */
	private void drawView() {
		/** Dessine le titre */		
		setLayout (new BoxLayout(this,BoxLayout.Y_AXIS));

	    /**Conteneur Labels*/
	    JPanel labelConteneur = new JPanel(new GridLayout(2,2));
	    
	    /**label 1*/
	    medecinsLabel = new JLabel(new ImageIcon("res/hero-attack.gif"));
	    medecinsLabel.setText("<html><FONT COLOR=BLUE><B>Medecins </B></FONT></html>");
	    medecinsLabel.setHorizontalTextPosition(JLabel.CENTER);
	    medecinsLabel.setVerticalTextPosition(JLabel.BOTTOM);
	    medecinsLabel.setBorder(BorderFactory.createTitledBorder("Medecins"));
	    labelConteneur.add(medecinsLabel);
	    
	    /**label 2*/
	    pompiersLabel = new JLabel(new ImageIcon("res/orc-walk.gif"));
	    pompiersLabel.setText("<html><FONT COLOR=RED><B>Pompiers </B></FONT></html>");
	    pompiersLabel.setHorizontalTextPosition(JLabel.CENTER);
	    pompiersLabel.setVerticalTextPosition(JLabel.BOTTOM);
	    pompiersLabel.setBorder(BorderFactory.createTitledBorder("Pompiers"));
	    labelConteneur.add(pompiersLabel);
	    
	    /**label 3*/
	    citoyensLabel = new JLabel(new ImageIcon("res/golem-walk.gif"));
	    citoyensLabel.setText("<html><FONT COLOR=GRAY><B>Citoyens </B></FONT></html>");
	    citoyensLabel.setHorizontalTextPosition(JLabel.CENTER);
	    citoyensLabel.setVerticalTextPosition(JLabel.BOTTOM);
	    citoyensLabel.setBorder(BorderFactory.createTitledBorder("Citoyens"));
	    labelConteneur.add(citoyensLabel);
	    
		/** Ajoute un boutons*/
		nextStepButton = new JButton("GO");
		nextStepButton.setBackground(Color.yellow);
		nextStepButton.setFont(new Font("Garamond", Font.ITALIC, 70));
		nextStepButton.setForeground(Color.darkGray);
		nextStepButton.setOpaque(false);
		nextStepButton.addActionListener(this);
		labelConteneur.add(nextStepButton);
	    add(labelConteneur);

		JPanel sliderConteneur = new JPanel(new GridLayout(1,1));
		/** Slider **/
	    speedMoveSlider = new JSlider(JSlider.CENTER);
	    speedMoveSlider.setBackground(Color.red);
	    speedMoveSlider.setBorder(BorderFactory.createTitledBorder("Vitesse de défilement"));
	    speedMoveSlider.setMajorTickSpacing(50);
	    speedMoveSlider.setMinorTickSpacing(20);
	    speedMoveSlider.setPaintTicks(true);
	    speedMoveSlider.setPaintLabels(true);
	    speedMoveSlider.addChangeListener(new MyChangeListener());
	    sliderConteneur.add(speedMoveSlider);
	    add(sliderConteneur);
	    
		/** Autre */
		setFocusable(true);
		requestFocus();
	}

	static class MyChangeListener implements ChangeListener {
		MyChangeListener() {
		    }
		public synchronized void stateChanged(ChangeEvent e) {
		    if (e.getSource() == speedMoveSlider) {
		    	int delay = 10* (100-speedMoveSlider.getValue()) ;
		    	actionTimer.setDelay(delay);
		    }
		}
	}
	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if (source == nextStepButton) {

			if (actionTimer.isRunning()) {
				nextStepButton.setText("Play");
				actionTimer.stop();
			} else {
				nextStepButton.setText("Pause");
				actionTimer.start();
			}
		}
	}
}
