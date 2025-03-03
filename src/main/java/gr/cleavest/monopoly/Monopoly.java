package gr.cleavest.monopoly;

import gr.cleavest.monopoly.display.Display;

import javax.swing.*;

/**
 * @author Cleavest on 1/3/2025
 */
public class Monopoly {

    public static void main(String[] args) {
        JFrame window = new JFrame("Monopoly");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setContentPane(new Display());
        window.setResizable(false);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
