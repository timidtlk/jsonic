package me.timidtlk.core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import lombok.Getter;
import me.timidtlk.entities.Player;
import me.timidtlk.scenes.Scene;
import me.timidtlk.scenes.SegaScene;
import me.timidtlk.scenes.TestLevel;

@Getter
public class GamePanel extends JPanel implements Runnable {
    
    private final int SCREEN_WIDTH = 320;
    private final int SCREEN_HEIGHT = 224;
    private final int MAX_FPS = 60;

    private Thread gameThread;
    private Controller controller;
    private KeyHandler keyH;
    private int actualFPS = 0;
    private Scene actualScene;
    private Sound sound;

    public GamePanel() {
        controller = new Controller();
        keyH = new KeyHandler(controller);
        actualScene = new SegaScene(this);
        sound = new Sound();

        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setDoubleBuffered(true);
        addKeyListener(keyH);
        setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / MAX_FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;    
                drawCount++;
            }

            if (timer >= 1000000000) {
                actualFPS = drawCount;
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        actualScene.update();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (actualScene.hasCamera()) {
            Camera camera = actualScene.getCamera();
            g2.translate(-camera.getX(), -camera.getY());
        }

        actualScene.draw(g2);

        if (actualScene.hasCamera()) {
            Camera camera = actualScene.getCamera();
            g2.translate(camera.getX(), camera.getY());
        }

        if (keyH.isDebug()) {
            g2.setColor(Color.WHITE);
            g2.drawString(String.format("FPS: %d", actualFPS), 0, 10);
            if (actualScene.hasCamera()) {
                Camera camera = actualScene.getCamera();
                camera.drawDebug(g2);
            }
        }
    }
}
