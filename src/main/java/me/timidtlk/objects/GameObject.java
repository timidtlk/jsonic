package me.timidtlk.objects;

import java.awt.Rectangle;

import lombok.Getter;
import me.timidtlk.core.IGameLogic;

@Getter
public abstract class GameObject implements IGameLogic {
    protected boolean isCollidable;
    protected Rectangle hitbox;
    protected int x, y;
}
