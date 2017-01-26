package MemoryGame;

import javax.swing.*;

/**
 * Created by zoloe on 2016. 11. 25..
 */
public class MemoryGameFrame {
    public MemoryGameFrame() {
        //Create and set up the window.
        JFrame frame = new JFrame("Memory Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add content to the window.
        JPanel MemoryPanel = new MemoryPanel();
        frame.add(MemoryPanel);

//        frame.setSize(400,400);

        //Display the window.
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }
}
