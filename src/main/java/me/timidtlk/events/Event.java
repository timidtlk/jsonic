package me.timidtlk.events;

import java.awt.Graphics2D;

import lombok.Getter;
import me.timidtlk.utils.Drawable;

@Getter
public abstract class Event implements Drawable {
    protected boolean end;

    protected Event() {
        end = false;
    }
}
