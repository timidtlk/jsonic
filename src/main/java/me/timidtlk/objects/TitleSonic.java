package me.timidtlk.objects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import me.timidtlk.core.IGameLogic;
import me.timidtlk.core.animation.Animation;
import me.timidtlk.core.animation.Sprite;

public class TitleSonic implements IGameLogic {

    private BufferedImage[] startSequence;
    private BufferedImage[] loopSequence;

    public Animation actualAnimation;

    public TitleSonic() {
        startSequence = new BufferedImage[16];
        loopSequence = new BufferedImage[2];

        try {
            var sprite = new Sprite("misc/title.png", 320, 224);

            startSequence[0] = sprite.getSprite(0, 0);
            startSequence[1] = sprite.getSprite(1, 0);
            startSequence[2] = sprite.getSprite(2, 0);
            startSequence[3] = sprite.getSprite(0, 1);
            startSequence[4] = sprite.getSprite(1, 1);
            startSequence[5] = sprite.getSprite(2, 1);
            startSequence[6] = sprite.getSprite(0, 2);
            startSequence[7] = sprite.getSprite(1, 2);
            startSequence[8] = sprite.getSprite(2, 2);
            startSequence[9] = sprite.getSprite(0, 3);
            startSequence[10] = sprite.getSprite(1, 3);
            startSequence[11] = sprite.getSprite(2, 3);
            startSequence[12] = sprite.getSprite(0, 4);
            startSequence[13] = sprite.getSprite(1, 4);
            startSequence[14] = sprite.getSprite(2, 4);
            startSequence[15] = sprite.getSprite(0, 5);

            loopSequence[0] = sprite.getSprite(1, 5);
            loopSequence[1] = sprite.getSprite(2, 5);
        } catch (Exception e) {
            e.printStackTrace();
        }

        actualAnimation = new Animation(startSequence, 3);
    }

    @Override
    public void update() {
        actualAnimation.update();
        if (actualAnimation.getCurrentFrame() == 15) {
            actualAnimation = new Animation(loopSequence, 6);
            actualAnimation.start();
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(actualAnimation.getSprite(), 0, 0, null);
    }

}
