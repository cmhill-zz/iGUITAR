/*  Copyright (c) 2009-@year@. The GUITAR group at the University of Maryland. Names of owners of this group may
 *  be obtained by sending an e-mail to atif@cs.umd.edu
 * 
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 *  documentation files (the "Software"), to deal in the Software without restriction, including without 
 *  limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 *	the Software, and to permit persons to whom the Software is furnished to do so, subject to the following 
 *	conditions:
 * 
 *	The above copyright notice and this permission notice shall be included in all copies or substantial 
 *	portions of the Software.
 *
 *	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT 
 *	LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO 
 *	EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER 
 *	IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR 
 *	THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * @author Bao Nguyen
 * 
 */
public class RadioButtonDemo extends JFrame {

	// -------------------------
	// GUI widgets
	JButton w0;
	JRadioButton w1;
	JRadioButton w2;

	JButton w3;
	JPanel w4;
	JButton w5;

	JCheckBox w6;

	// -------------------------
	// Internal variables
	Boolean created = false;

	Boolean log = false;

	Shape currentShape = Shape.CIRCLE;

	// -------------------------
	// Constants
	enum Shape {
		CIRCLE, SQUARE
	}

	private static final String LOG_FILE_NAME = "log.txt";

	// -------------------------
	// Container panel
	JPanel contentPane;

	/**
	 * Constructor to setup the initial state
	 */
	public RadioButtonDemo() {

		super("Radio Button Demo");
		contentPane = new JPanel(new BorderLayout());

		w1 = new JRadioButton("Circle");
		w1.setSelected(true);
		w1.addActionListener(new W1Listener());

		w2 = new JRadioButton("Square");
		w2.addActionListener(new W2Listener());

		ButtonGroup selectShapeGroup = new ButtonGroup();
		selectShapeGroup.add(w1);
		selectShapeGroup.add(w2);

		Box selectShapePanel = new Box(BoxLayout.Y_AXIS);
		selectShapePanel.add(w1);
		selectShapePanel.add(w2);

		selectShapePanel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.GRAY), "Select"));

		draw(new EmptyPanel());

		w3 = new JButton("Create");
		w3.addActionListener(new W3Listener());

		w5 = new JButton("Reset");
		w5.setEnabled(false);
		w5.addActionListener(new W5Listener());

		w0 = new JButton("Exit");
		w0.addActionListener(new W0Listener());

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(w3);
		buttonPanel.add(w5);
		buttonPanel.add(w0);

		contentPane.add(selectShapePanel, BorderLayout.WEST);
		contentPane.add(w4, BorderLayout.CENTER);
		contentPane.add(buttonPanel, BorderLayout.SOUTH);

		w6 = new JCheckBox("Log exit time.");
		w6.addActionListener(new W6Listener());

		setContentPane(contentPane);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Draw a shape in the `Rendered Shape` area
	 * 
	 * @param shape
	 *            the shape to draw
	 */
	private void draw(JPanel shape) {
		if (w4 != null)
			contentPane.remove(w4);
		w4 = shape;
		w4.setBorder(BorderFactory.createTitledBorder(BorderFactory
				.createLineBorder(Color.GRAY), "Rendered Shape"));
		contentPane.add(w4);
		repaint();
		pack();
	}

	// -----------------------
	// Listeners for each widget
	// -----------------------
	class W1Listener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			currentShape = Shape.CIRCLE;
			if (created) {
				draw(new CirclePanel());
			}
		}
	}

	class W2Listener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			currentShape = Shape.SQUARE;
			if (created) {
				draw(new SquarePanel());
			}
		}
	}

	class W3Listener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			w5.setEnabled(true);
			created = true;
			JPanel shape;
			if (currentShape == Shape.CIRCLE)
				shape = new CirclePanel();
			else if (currentShape == Shape.SQUARE)
				shape = new SquarePanel();
			else
				shape = new EmptyPanel();
			draw(shape);
		}
	}

	class W5Listener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			w5.setEnabled(false);
			created = false;
			draw(new EmptyPanel());
		}
	}

	class W0Listener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			String message = "Are you sure you want to exit?";
			Object[] params = { message, w6 };

			int exit = JOptionPane.showConfirmDialog(null, params,
					"Exit Confirmation", JOptionPane.YES_NO_OPTION);
			if (exit == 0) {
				if (log) {
					writeTimeStamp();
				}
				System.exit(0);
			}
		}

		private void writeTimeStamp() {
			try {
				BufferedWriter out = new BufferedWriter(new FileWriter(
						LOG_FILE_NAME));

				Date date = new Date();
				out.write((int) date.getTime());
				out.close();
			} catch (IOException e) {
			}
		}
	}

	class W6Listener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			log = w6.isSelected();
		}

	}

	// -----------------------
	// Draw panels
	// -----------------------
	/**
	 * circle panel
	 */
	class CirclePanel extends JPanel {
		public CirclePanel() {
			super();
		}

		@Override
		public void paintComponent(Graphics g) {
			g.drawOval(40, 20, 45, 45);
		}
	}

	/**
	 * square panel
	 */
	class SquarePanel extends JPanel {
		public SquarePanel() {
			super();
		}

		@Override
		public void paintComponent(Graphics g) {
			g.drawRect(40, 20, 45, 45);
		}
	}

	/**
	 * empty panel
	 * 
	 */
	class EmptyPanel extends JPanel {
		public EmptyPanel() {
			super();
		}
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event-dispatching thread.
	 */
	private static void createAndShowGUI() {
		RadioButtonDemo frame = new RadioButtonDemo();
		frame.setVisible(true);
		frame.pack();

	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

}
