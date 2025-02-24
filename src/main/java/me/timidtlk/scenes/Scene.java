package me.timidtlk.scenes;

import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Stack;

import lombok.Getter;
import me.timidtlk.core.Camera;
import me.timidtlk.core.GamePanel;
import me.timidtlk.entities.Entity;
import me.timidtlk.events.Event;
import me.timidtlk.objects.GameObject;

@Getter
public abstract class Scene {
    protected HashMap<String, GameObject> objects;
    protected HashMap<String, Entity> entities;
    protected Stack<Event> events;
    protected Camera camera;
    
    private GamePanel gp;
    public Scene(GamePanel gp) {
        this.gp = gp;
        objects = new HashMap<>();
        entities = new HashMap<>();
        events = new Stack<>();
    }

    public boolean hasCamera() {
        return camera != null;
    }

    public void update() {
        if (!events.empty()) {
            events.getFirst().update();
            if (events.getFirst().isEnd()) {
                events.pop();
            }
        }
        for (GameObject object : objects.values()) {
            object.update();
        }
        for (Entity entity : entities.values()) {
            entity.update();
        }
        if (hasCamera()) {
            camera.update();
        }
    }

    public void draw(Graphics2D g2) {
        for (GameObject object : objects.values()) {
            object.draw(g2);
        }
        for (Entity entity : entities.values()) {
            entity.draw(g2);
        }
        if (!events.empty()) {
            for (Event event : events) {
                event.draw(g2);
            }
        }
    }
}
