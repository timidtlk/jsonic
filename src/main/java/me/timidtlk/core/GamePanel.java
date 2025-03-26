package me.timidtlk.core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JPanel;
import javax.swing.Timer;

import lombok.Getter;
import me.timidtlk.entities.Player;
import me.timidtlk.scenes.Scene;
import me.timidtlk.scenes.IntroScene;
import me.timidtlk.scenes.TestLevel;

@Getter
public class GamePanel extends JPanel implements Runnable {
    
    private final int SCREEN_WIDTH = 320;
    private final int SCREEN_HEIGHT = 224;
    private final int MAX_FPS = 60;
    private double SCALE = 2;

    private Thread gameThread;
    private Controller controller;
    private KeyHandler keyH;
    private int actualFPS = 0;
    private Scene actualScene;
    private Sound sound;

    public GamePanel() {
        controller = new Controller();
        keyH = new KeyHandler(controller);
        actualScene = new IntroScene(this);
        sound = new Sound();

        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(SCREEN_WIDTH * (int) Math.round(SCALE), SCREEN_HEIGHT * (int) Math.round(SCALE)));
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
        SCALE = Math.min(getHeight() / SCREEN_HEIGHT, getWidth() / SCREEN_WIDTH);
    }

    private void renderGame(Graphics2D g2) {
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Get the window dimensions
        int windowWidth = getWidth();
        int windowHeight = getHeight();

        // Sega Genesis default resolution (4:3 aspect ratio)
        int baseWidth = 320;
        int baseHeight = 224;

        // Calculate the scale factor to maintain 4:3 aspect ratio
        double scaleX = (double) windowWidth / baseWidth;
        double scaleY = (double) windowHeight / baseHeight;
        double scale = Math.min(scaleX, scaleY); // Use the smaller scale to fit within the window

        // Calculate the scaled resolution
        int scaledWidth = (int) (baseWidth * scale);
        int scaledHeight = (int) (baseHeight * scale);

        // Calculate offsets for centering the game content
        int offsetX = (windowWidth - scaledWidth) / 2;
        int offsetY = (windowHeight - scaledHeight) / 2;

        // Fill the black bars
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, windowWidth, offsetY); // Top bar
        g2.fillRect(0, offsetY + scaledHeight, windowWidth, windowHeight - offsetY - scaledHeight); // Bottom bar
        g2.fillRect(0, offsetY, offsetX, scaledHeight); // Left bar
        g2.fillRect(offsetX + scaledWidth, offsetY, windowWidth - offsetX - scaledWidth, scaledHeight); // Right bar

        // Scale and translate the game content
        g2.translate(offsetX, offsetY);
        g2.scale(scale, scale);

        // Render the game content
        renderGame(g2);

        // Reset transformations
        g2.dispose();
    }
}
