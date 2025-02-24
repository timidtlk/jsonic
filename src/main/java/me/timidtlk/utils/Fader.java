package me.timidtlk.utils;

import java.awt.Color;
import java.awt.Graphics2D;

import me.timidtlk.core.GamePanel;

public class Fader {
    private int alpha;
    private int targetAlpha;
    private float fadeSpeed;
    private Color color;
    private GamePanel gp;

    public Fader(float fadeSpeed, Color color, GamePanel gp, int alpha, int targetAlpha) {
        this.gp = gp;
        this.color = color;
        this.fadeSpeed = fadeSpeed;
        this.targetAlpha = targetAlpha;
        this.alpha = alpha;
    }

    public void update() {
        alpha = (int)Math.round(alpha + (targetAlpha - alpha) * fadeSpeed);
    }

    public void draw(Graphics2D g2) {
        g2.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) alpha));
        g2.fillRect(0, 0, gp.getSCREEN_WIDTH(), gp.getSCREEN_HEIGHT());
    }

    public float getAlpha() {
        return alpha;
    }
}