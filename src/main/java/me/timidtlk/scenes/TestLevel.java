package me.timidtlk.scenes;

import me.timidtlk.core.Camera;
import me.timidtlk.core.GamePanel;
import me.timidtlk.entities.Player;
import me.timidtlk.objects.Box;

public class TestLevel extends Scene {
    public TestLevel(GamePanel gp) {
        super(gp);
        
        Player player = new Player(gp);
        player.setX(10);
        player.setY(10);

        objects.put("ground", new Box(0, 150, 1000, 50));
        entities.put("player", player);

        camera = new Camera(gp, player);
    }
}
