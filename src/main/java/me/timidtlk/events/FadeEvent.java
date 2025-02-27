package me.timidtlk.events;

import java.awt.Color;
import java.awt.Graphics2D;

import lombok.Getter;
import me.timidtlk.core.GamePanel;

@Getter
public class FadeEvent extends Event {
    private float alpha;
    private int delay;
    private int targetAlpha;
    private Color color;
    private GamePanel gp;

    public FadeEvent(GamePanel gp, int delay, int targetAlpha, Color color) {
        super();
        this.delay = delay;
        this.targetAlpha = targetAlpha;
        this.gp = gp;
        alpha = (targetAlpha == 255) ? 0f : 255f;
        this.color = new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.round(alpha));
    }

    @Override
    public void update() {
        alpha = alpha + (targetAlpha - alpha) * .1f;
        color = new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) Math.ceil(alpha));
        if (alpha == targetAlpha) {
            end = true;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        color = new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) Math.ceil(alpha));
        if (gp.getKeyH().isDebug()) {
            g2.setColor(Color.RED);
            g2.drawString("Alpha: " + alpha, 10, 40);
        }        
        g2.setColor(color);
        g2.fillRect(0, 0, gp.getSCREEN_WIDTH(), gp.getSCREEN_HEIGHT());
    }
}
