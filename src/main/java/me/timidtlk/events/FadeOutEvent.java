package me.timidtlk.events;

import java.awt.Color;
import java.awt.Graphics2D;

import lombok.Getter;
import me.timidtlk.core.GamePanel;

@Getter
public class FadeOutEvent extends Event {

    float fadeSpeed;
    Color color;
    float alpha;
    float targetAlpha;
    GamePanel gp;

    public FadeOutEvent(float fadeSpeed, Color color, GamePanel gp) {
        super();
        this.fadeSpeed = fadeSpeed;
        this.color = color;
        this.alpha = 1f;
        this.targetAlpha = 0f;
        this.gp = gp;
    }
    @Override
    public void update() {
        if (targetAlpha > alpha) {
            alpha += fadeSpeed;
        } else {
            alpha -= fadeSpeed;
        }
        if (getAlpha() == getTargetAlpha()) {
            end = true;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(new Color(0f, 0f, 0f, alpha));
        g2.fillRect(0, 0, gp.getSCREEN_WIDTH(), gp.getSCREEN_HEIGHT());
    }
}
