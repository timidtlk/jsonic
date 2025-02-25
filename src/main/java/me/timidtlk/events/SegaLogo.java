package me.timidtlk.events;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import me.timidtlk.core.GamePanel;
import me.timidtlk.core.Sound;
import me.timidtlk.core.animation.Animation;
import me.timidtlk.core.animation.Sprite;

public class SegaLogo extends Event {

    Sprite segaLogo;
    Animation animation;
    GamePanel gp;
    Sound sound;
    boolean audioPlayed = false;

    public SegaLogo(GamePanel gp) {
        super();

        this.gp = gp;
        this.sound = new Sound();
        segaLogo = new Sprite("misc/sega.png", gp.getSCREEN_WIDTH(), gp.getSCREEN_HEIGHT());

        BufferedImage[] segaLogoFrames = new BufferedImage[25];
        try {
            segaLogoFrames[0] = segaLogo.getSprite(0, 0);
            segaLogoFrames[1] = segaLogo.getSprite(1, 0);
            segaLogoFrames[2] = segaLogo.getSprite(2, 0);
            segaLogoFrames[3] = segaLogo.getSprite(3, 0);
            segaLogoFrames[4] = segaLogo.getSprite(0, 1);
            segaLogoFrames[5] = segaLogo.getSprite(1, 1);
            segaLogoFrames[6] = segaLogo.getSprite(2, 1);
            segaLogoFrames[7] = segaLogo.getSprite(3, 1);
            segaLogoFrames[8] = segaLogo.getSprite(0, 2);
            segaLogoFrames[9] = segaLogo.getSprite(1, 2);
            segaLogoFrames[10] = segaLogo.getSprite(2, 2);
            segaLogoFrames[11] = segaLogo.getSprite(3, 2);
            segaLogoFrames[12] = segaLogo.getSprite(0, 3);
            segaLogoFrames[13] = segaLogo.getSprite(1, 3);
            segaLogoFrames[14] = segaLogo.getSprite(2, 3);
            segaLogoFrames[15] = segaLogo.getSprite(3, 3);
            segaLogoFrames[16] = segaLogo.getSprite(0, 4);
            segaLogoFrames[17] = segaLogo.getSprite(1, 4);
            segaLogoFrames[18] = segaLogo.getSprite(2, 4);
            segaLogoFrames[19] = segaLogo.getSprite(3, 4);
            segaLogoFrames[20] = segaLogo.getSprite(0, 5);
            segaLogoFrames[21] = segaLogo.getSprite(1, 5);
            segaLogoFrames[22] = segaLogo.getSprite(2, 5);
            segaLogoFrames[23] = segaLogo.getSprite(3, 5);
            segaLogoFrames[24] = segaLogo.getSprite(0, 6);

            animation = new Animation(segaLogoFrames, 2);
            animation.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        animation.update();

        if (animation.getCurrentFrame() == 24 && !audioPlayed) {
            audioPlayed = true;
            animation.stop();
            sound.setFile(3);
            sound.play();
        } else if (audioPlayed) {
            if (sound.isFinished()) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                end = true;
            }
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(animation.getSprite(), 0, 0, null);
    }
    
}
