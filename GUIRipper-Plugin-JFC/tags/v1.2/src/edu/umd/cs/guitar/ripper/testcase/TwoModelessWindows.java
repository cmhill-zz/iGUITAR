package edu.umd.cs.guitar.ripper.testcase;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * Simple example illustrating the use of JButton, especially the new
 * constructors that permit you to add an image. 1998-99 Marty Hall,
 * http://www.apl.jhu.edu/~hall/java/
 */

public class TwoModelessWindows extends JFrame implements ActionListener {
    public static void main(String[] args) {
        new TwoModelessWindows();
    }

    public TwoModelessWindows() {
        super("Two modaless widows test case");
        Container content = getContentPane();
        content.setBackground(Color.white);
        content.setLayout(new FlowLayout());
        JButton button1 = new JButton("Open new window Menu");
        button1.addActionListener(this);
        content.add(button1);
        JButton button2 = new JButton("2nd Button");
        
        
        button2.setHorizontalTextPosition(SwingConstants.LEFT);
        content.add(button2);
        pack();
        setVisible(true);
    }

    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        OneWindow wNewWin = new OneWindow("2nd window");
//        wNewWin.setVisible(true);
        
    }
}