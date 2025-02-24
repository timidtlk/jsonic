package me.timidtlk.scenes;

import java.awt.Graphics2D;
import java.util.HashMap;

import lombok.Getter;
import me.timidtlk.core.GamePanel;
import me.timidtlk.entities.Entity;
import me.timidtlk.objects.GameObject;

@Getter
public abstract class Scene {
    protected HashMap<String, GameObject> objects;
    protected HashMap<String, Entity> entities;
    
    private GamePanel gp;
    public Scene(GamePanel gp) {
        this.gp = gp;
        objects = new HashMap<>();
        entities = new HashMap<>();
    }

    public void update() {
        for (GameObject object : objects.values()) {
            object.update();
        }
        for (Entity entity : entities.values()) {
            entity.update();
        }
    }

    public void draw(Graphics2D g2) {
        for (GameObject object : objects.values()) {
            object.draw(g2);
        }
        for (Entity entity : entities.values()) {
            entity.draw(g2);
        }
    }
}
