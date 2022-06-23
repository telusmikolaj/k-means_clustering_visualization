package com.company;

import javax.swing.*;

public class MyFrame extends JFrame {
    public MyFrame() {
        super("Rysowanie");
        JPanel panel = new MyPanel();

        add(panel);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

}