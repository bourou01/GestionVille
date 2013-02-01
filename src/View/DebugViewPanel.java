package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
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
		    int count = 20;
		    @Override
		    public void actionPerformed(ActionEvent ae) {
		        if (count < 1000 ) {
					GestionVille.getVille().oneStep();
					gestionVilleController.mapViewPanel.repaint();
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
		nextStepButton = new JButton("GO GO");
		nextStepButton.setBackground(Color.yellow);
		nextStepButton.setForeground(Color.darkGray);
		nextStepButton.setOpaque(false);
		nextStepButton.addActionListener(this);
		add(nextStepButton);
		
		/** Slider **/
	    speedMoveSlider = new JSlider();
	    speedMoveSlider.setBackground(Color.red);
	    speedMoveSlider.setBorder(BorderFactory.createTitledBorder("Vitesse de défilement"));
	    speedMoveSlider.setMajorTickSpacing(20);
	    speedMoveSlider.setMinorTickSpacing(5);
	    speedMoveSlider.setPaintTicks(true);
	    speedMoveSlider.setPaintLabels(true);
	    speedMoveSlider.addChangeListener(new MyChangeListener());
	    add(speedMoveSlider, BorderLayout.SOUTH);
	
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
		if (source == this.nextStepButton) {

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
