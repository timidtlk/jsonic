package me.timidtlk.entities;

import java.awt.Rectangle;

import lombok.Getter;
import lombok.Setter;
import me.timidtlk.core.IGameLogic;

@Getter
@Setter
public abstract class Entity implements IGameLogic {
    protected boolean isCollidable;
    protected Rectangle hitbox;
    protected double x, y;
}
