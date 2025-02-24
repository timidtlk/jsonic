package me.timidtlk;

import javax.swing.JFrame;

import me.timidtlk.core.GamePanel;

public class Main extends JFrame {
    GamePanel panel;

    Main() {
        super("Sonic The Hedgehog");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        panel = new GamePanel();
        panel.startGameThread();

        add(panel);
        pack();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}