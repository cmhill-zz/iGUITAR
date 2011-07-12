package edu.umd.cs.guitar.ripper.testcase;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.*;

/**
 * Simple example illustrating the use of JButton, especially the new
 * constructors that permit you to add an image. 1998-99 Marty Hall,
 * http://www.apl.jhu.edu/~hall/java/
 */

public class DropDownMenu extends JFrame implements ActionListener {
    public static void main(String[] args) {
        new DropDownMenu();
    }

    public DropDownMenu() {
        super("DropDownMenu test case");
        Container content = getContentPane();
        content.setBackground(Color.white);
        content.setLayout(new FlowLayout());

        // Create the menu bar
        JMenuBar menuBar = new JMenuBar();
        
        // Create a menu
        JMenu menu = new JMenu("Top level menu");
        menuBar.add(menu);
        
        // Create a menu item
        JMenuItem item = new JMenuItem("Open New Window");
        item.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                OneWindow newWin = new OneWindow("Pop up Menu");
             }
          });
        menu.add(item);
        
        // Add another menu item
        JMenuItem item2 = new JMenuItem("2nd level menu 2");
        menu.add(item2);

        // Install the menu bar in the frame
        content.add(menuBar);
        pack();
        setVisible(true);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        OneWindow wNewWin = new OneWindow("2nd window");
        // wNewWin.setVisible(true);

    }
}