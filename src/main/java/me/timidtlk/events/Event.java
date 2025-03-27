package me.timidtlk.events;

import java.awt.Graphics2D;

import lombok.Getter;
import me.timidtlk.core.IGameLogic;

@Getter
public abstract class Event implements IGameLogic {
    protected boolean end;

    protected Event() {
        end = false;
    }
}
