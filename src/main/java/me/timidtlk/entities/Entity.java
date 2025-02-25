package me.timidtlk.entities;

import java.awt.Rectangle;

import lombok.Getter;
import lombok.Setter;
import me.timidtlk.utils.Drawable;

@Getter
@Setter
public abstract class Entity implements Drawable {
    protected boolean isCollidable;
    protected Rectangle hitbox;
    protected double x, y;
}
