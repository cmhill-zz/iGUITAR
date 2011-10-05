/*
 * CMSC 737. Oct 26 2009
 * By: Sonia Ng
 */

//package components;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class checkboxesDemo extends JPanel
						implements ItemListener{
	JCheckBox buttonA;
	JCheckBox buttonB;
	JCheckBox buttonC;
	JCheckBox buttonD;
	StringBuffer userChoices;
	JTextArea textArea;

	public checkboxesDemo() {
		super(new BorderLayout());

		//instantiating
		buttonA = new JCheckBox("A");
		buttonA.setSelected(true);

		buttonB = new JCheckBox("B");
		buttonB.setSelected(true);

		buttonC = new JCheckBox("C");
		buttonC.setSelected(true);

		buttonD = new JCheckBox("D");
		buttonD.setSelected(true);

		buttonA.addItemListener(this);
		buttonB.addItemListener(this);
		buttonC.addItemListener(this);
		buttonD.addItemListener(this);

		//string to store the choices made
		userChoices = new StringBuffer("ABCD");

		//text area showing chosen choice
		textArea = new JTextArea(1,5);
		textArea.setEditable(false);
		textArea.insert(userChoices.toString(), 0);


		//where the stuff is
		JPanel mainPanel = new JPanel(new GridLayout(0, 1));
		mainPanel.add(buttonA);
		mainPanel.add(buttonB);
		mainPanel.add(buttonC);
		mainPanel.add(buttonD);

		add(mainPanel, BorderLayout.LINE_START);
		add(textArea, BorderLayout.CENTER);
		setBorder (BorderFactory.createEmptyBorder(20,20,20,20));
	}

	public void itemStateChanged(ItemEvent item){
		int StrIndex = 0;
		char itemChar = '_';
		Object selected = item.getItemSelectable();

		if (selected == buttonA){
			StrIndex = 0;
			itemChar = 'A';
		}
		else if(selected == buttonB){
			StrIndex = 1;
			itemChar = 'B';
		}
		else if(selected == buttonC){
			StrIndex = 2;
			itemChar = 'C';
		}
		else if(selected == buttonD){
			StrIndex = 3;
			itemChar = 'D';
		}

		//the checkbox/button could have been selected or deselected
		//find out:

		if(item.getStateChange() == ItemEvent.DESELECTED){
			itemChar = '_';
		}

		//update choices
		userChoices.setCharAt(StrIndex, itemChar);
		textArea.setText(userChoices.toString());
		//System.out.println(userChoices.toString());


	}

	/**
	* GUI
	*
	*/

	private static void createAndShowGUI() {
		//Create and set up
		JFrame f = new JFrame("checkboxes");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Content pane
		JComponent newContentPane = new checkboxesDemo();
		newContentPane.setOpaque(true);
		f.setContentPane(newContentPane);

		//Display
		f.pack();
		f.setVisible(true);
	}

	public static void main(String[] args){
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run(){
				createAndShowGUI();
			}
		});
	}

}