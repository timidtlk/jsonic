package me.timidtlk.utils;

import java.awt.Color;
import java.awt.Graphics2D;

import me.timidtlk.core.GamePanel;

public class Fader {
    private float alpha;
    private float targetAlpha;
    private float fadeSpeed;
    private Color color;
    private GamePanel gp;

    public Fader(float fadeSpeed, Color color, GamePanel gp) {
        this.gp = gp;
        this.color = color;
        this.fadeSpeed = fadeSpeed;
        alpha = 0;
        targetAlpha = 0;
    }

    public void fadeIn() {
        targetAlpha = 255;
    }

    public void fadeOut() {
        targetAlpha = 0;
    }

    public void update() {
        alpha = alpha + (targetAlpha - alpha) * fadeSpeed;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) alpha));
        g2.fillRect(0, 0, gp.getSCREEN_WIDTH(), gp.getSCREEN_HEIGHT());
    }

    public float getAlpha() {
        return alpha;
    }
}