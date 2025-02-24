package me.timidtlk.events;

import java.awt.Color;
import java.awt.Graphics2D;

import me.timidtlk.core.GamePanel;
import me.timidtlk.utils.Fader;

public class FadeInEvent extends Event {

    Fader fader;

    public FadeInEvent(float fadeSpeed, Color color, GamePanel gp) {
        super();
        this.fader = new Fader(fadeSpeed, color, gp, 255, 0);
    }
    @Override
    public void update() {
        fader.update();
        if (fader.getAlpha() == 0) {
            end = true;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        fader.draw(g2);
    }
}
