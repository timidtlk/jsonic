package me.timidtlk.events;

import java.awt.Color;
import java.awt.Graphics2D;

import me.timidtlk.core.GamePanel;
import me.timidtlk.utils.Fader;

public class FadeOutEvent extends Event {

    Fader fader;

    public FadeOutEvent(float fadeSpeed, Color color, GamePanel gp) {
        super();
        this.fader = new Fader(fadeSpeed, color, gp);
        fader.fadeOut();
    }
    @Override
    public void update() {
        fader.update();
    }

    @Override
    public void draw(Graphics2D g2) {
        fader.draw(g2);
    }
}
