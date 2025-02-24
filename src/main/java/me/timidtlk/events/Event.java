package me.timidtlk.events;

import java.awt.Graphics2D;

import lombok.Getter;

@Getter
public abstract class Event {
    protected boolean end;

    protected Event() {
        end = false;
    }

    public abstract void update();
    public abstract void draw(Graphics2D g2);
}
