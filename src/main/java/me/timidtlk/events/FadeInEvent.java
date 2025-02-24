package me.timidtlk.events;

import java.awt.Color;
import java.awt.Graphics2D;

import me.timidtlk.core.GamePanel;
import me.timidtlk.utils.Fader;

public class FadeInEvent extends Event {

    Fader fader;

    public FadeInEvent(float fadeSpeed, Color color, GamePanel gp) {
        super();
        this.fader = new Fader(fadeSpeed, color, gp);
        fader.fadeIn();
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
