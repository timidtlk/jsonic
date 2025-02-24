package me.timidtlk.entities;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Entity {
    protected boolean isCollidable;
    protected Rectangle hitbox;
    protected double x, y;

    public abstract void update();
    public abstract void draw(Graphics2D g2);
}
