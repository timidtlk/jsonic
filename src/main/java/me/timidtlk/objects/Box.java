package me.timidtlk.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import lombok.Getter;

@Getter
public class Box extends GameObject {
    public Box(int x, int y, int width, int height) {
        isCollidable = true;

        hitbox = new Rectangle();
        hitbox.x = x;
        hitbox.y = y;
        hitbox.setSize(width, height);
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(hitbox.x, hitbox.y, (int) hitbox.getWidth(), (int) hitbox.getHeight());
    }
}
