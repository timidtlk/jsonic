package me.timidtlk.events;

import java.awt.Graphics2D;

public class ChangeToScene extends Event {
    private final String sceneName;

    public ChangeToScene(String sceneName) {
        super();
        this.sceneName = sceneName;
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void draw(Graphics2D g2) {
        throw new UnsupportedOperationException("Unimplemented method 'draw'");
    }
}
