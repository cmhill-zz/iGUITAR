package edu.umd.cs.guitar.ripper.testcase;

import java.awt.*;
import javax.swing.*;

/**
 * Simple example illustrating the use of JButton, especially the new
 * constructors that permit you to add an image. 1998-99 Marty Hall,
 * http://www.apl.jhu.edu/~hall/java/
 */

public class TextBox extends JFrame {
    public static void main(String[] args) {
        new TextBox();
    }

    public TextBox() {
        this("TextBox testcase");
    }
    
    public TextBox(String title ) {
        super(title);
        Container content = getContentPane();
        content.setBackground(Color.white);
        content.setLayout(new FlowLayout());
        JButton button1 = new JButton("1st Button");
        content.add(button1);
//        ImageIcon cup = new ImageIcon("resource-testcase/images/cup.gif");
//        JButton button2 = new JButton("2nd Button", cup);
        JTextField textField;
        textField = new JTextField(20);

        content.add(textField);
        pack();
        setVisible(true);
    }
}