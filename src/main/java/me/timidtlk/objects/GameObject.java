package me.timidtlk.objects;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import lombok.Getter;

@Getter
public abstract class GameObject {
    protected boolean isCollidable;
    protected Rectangle hitbox;
    protected int x, y;

    public abstract void update();
    public abstract void draw(Graphics2D g2);
}
