package me.timidtlk;

import javax.swing.JFrame;

import me.timidtlk.core.GamePanel;

public class Main extends JFrame {
    GamePanel panel;

    Main() {
        super("Sonic The Hedgehog");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);

        panel = new GamePanel();
        
        add(panel);
        pack();
        
        setLocationRelativeTo(null);
        setVisible(true);
        panel.startGameThread();
    }

    public static void main(String[] args) {
        new Main();
    }
}