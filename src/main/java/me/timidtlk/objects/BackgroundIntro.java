package me.timidtlk.objects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import lombok.Getter;
import lombok.Setter;
import me.timidtlk.core.IGameLogic;
import me.timidtlk.core.animation.Animation;

@Getter
@Setter
public class BackgroundIntro implements IGameLogic {

    private Animation backgroundAnimation;
    private int x, y;
    private int delay;
    private int timer;
    private int speed;

    public BackgroundIntro(int x, int y, int delay, BufferedImage[] backgroundFrames, int frameDelay, int speed) {
        this.delay = delay;
        this.x = x;
        this.y = y;
        this.timer = 0;
        this.speed = speed;
        backgroundAnimation = new Animation(backgroundFrames, frameDelay);

        backgroundAnimation.start();
    }

    @Override
    public void update() {
        backgroundAnimation.update();
        timer++;
        if (timer > delay) {
            x -= speed;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(backgroundAnimation.getSprite(), x, y, null);
    }
}
