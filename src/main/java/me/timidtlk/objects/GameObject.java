package me.timidtlk.objects;

import java.awt.Rectangle;

import lombok.Getter;
import me.timidtlk.utils.Drawable;

@Getter
public abstract class GameObject implements Drawable {
    protected boolean isCollidable;
    protected Rectangle hitbox;
    protected int x, y;
}
