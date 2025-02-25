package me.timidtlk.events;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import lombok.Getter;
import me.timidtlk.core.GamePanel;

@Getter
public class FadeInEvent extends Event {

    float fadeSpeed;
    Color color;
    float alpha;
    float targetAlpha;
    GamePanel gp;

    public FadeInEvent(float fadeSpeed, Color color, GamePanel gp) {
        super();
        this.fadeSpeed = fadeSpeed;
        this.color = color;
        this.alpha = 0f;
        this.gp = gp;
        end = false;
    }
    @Override
    public void update() {
        System.out.println("FadeInEvent update");
        if (alpha < 255f) {
            alpha += fadeSpeed;
            color = new Color(0,0,0,alpha);
        }
        if (getAlpha() == 255f) {
            end = true;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(color);
        g2.fillRect(0, 0, gp.getSCREEN_WIDTH(), gp.getSCREEN_HEIGHT());
    }
}
