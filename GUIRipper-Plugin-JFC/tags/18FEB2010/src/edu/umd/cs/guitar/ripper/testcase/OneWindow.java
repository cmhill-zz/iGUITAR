package edu.umd.cs.guitar.ripper.testcase;

import java.awt.*;
import javax.swing.*;

/**
 * Simple example illustrating the use of JButton, especially the new
 * constructors that permit you to add an image. 1998-99 Marty Hall,
 * http://www.apl.jhu.edu/~hall/java/
 */

public class OneWindow extends JFrame {
    public static void main(String[] args) {
        new OneWindow();
    }

    public OneWindow() {
        this("OneWindow testcase");
    }
    
    public OneWindow(String title ) {
        super(title);
        Container content = getContentPane();
        content.setBackground(Color.white);
        content.setLayout(new FlowLayout());
        JButton button1 = new JButton("1st Button");
        content.add(button1);
//        ImageIcon cup = new ImageIcon("resource-testcase/images/cup.gif");
//        JButton button2 = new JButton("2nd Button", cup);
        JButton button2 = new JButton("2nd Button");
        button2.setHorizontalTextPosition(SwingConstants.LEFT);
        content.add(button2);
        pack();
        setVisible(true);
    }
}