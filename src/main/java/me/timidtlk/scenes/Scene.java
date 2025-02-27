package me.timidtlk.scenes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Stack;

import lombok.Getter;
import me.timidtlk.core.Camera;
import me.timidtlk.core.GamePanel;
import me.timidtlk.entities.Entity;
import me.timidtlk.events.Event;
import me.timidtlk.objects.GameObject;
import me.timidtlk.utils.Drawable;

@Getter
public abstract class Scene implements Drawable {
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

    @Override
    public void update() {
        if (!events.empty()) {
            events.getFirst().update();
            if (events.getFirst().isEnd()) {
                events.push(events.getFirst());
                events.remove(events.getFirst());
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

    @Override
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

        g2.setColor(Color.RED);
        if (gp.getKeyH().isDebug()) {
            for (int index = 0; index < events.size(); index++) {
                g2.drawString(events.get(index).getClass().getSimpleName(), 10, 20 + 10 * index);
            }
        }
    }
}
