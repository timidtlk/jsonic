package me.timidtlk.scenes;

import java.awt.Color;

import me.timidtlk.core.GamePanel;
import me.timidtlk.events.FadeInEvent;
import me.timidtlk.events.FadeOutEvent;
import me.timidtlk.events.SegaLogo;

public class SegaScene extends Scene {
    public SegaScene(GamePanel gp) {
        super(gp);
        // events.push(new FadeOutEvent(1f, Color.BLACK, gp));
        events.push(new SegaLogo(gp));
        // events.push(new FadeInEvent(1f, Color.BLACK, gp));
    }
}
