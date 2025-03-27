package me.timidtlk.events;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import me.timidtlk.core.GamePanel;
import me.timidtlk.core.animation.Sprite;
import me.timidtlk.objects.BackgroundIntro;

public class TitleScreen extends Event {
    private BackgroundIntro[] backgroundIntros;
    private GamePanel gp;
    float alpha;
    float targetAlpha;

    public TitleScreen(GamePanel gp) {
        super();
        this.gp = gp;
        this.end = false;
        this.alpha = 0f;
        this.targetAlpha = 1f;

        BufferedImage[] bgFrames0 = null;
        BufferedImage[] bgFrames1 = null;
        try {
            bgFrames0 = new BufferedImage[]{ 
                new Sprite("misc/bg_intro_0.png", 1024, 112).getSprite(0, 0) 
            };
            bgFrames1 = new BufferedImage[]{ 
                new Sprite("misc/bg_intro_1.png", 1024, 144).getSprite(0, 0), 
                new Sprite("misc/bg_intro_1.png", 1024, 144).getSprite(0, 1), 
                new Sprite("misc/bg_intro_1.png", 1024, 144).getSprite(0, 2), 
                new Sprite("misc/bg_intro_1.png", 1024, 144).getSprite(0, 3) 
            };
        } catch (Exception e) {
            e.printStackTrace();
        }

        int delay = 40;
        BackgroundIntro bg0 = new BackgroundIntro(0, 0, delay, bgFrames0, 3, 1);
        BackgroundIntro bgTwin0 = new BackgroundIntro(1024, 0, delay, bgFrames0, 3, 1);
        BackgroundIntro bg1 = new BackgroundIntro(0, 112, delay, bgFrames1, 3, 2);
        BackgroundIntro bgTwin1 = new BackgroundIntro(1024, 112, delay, bgFrames1, 3, 2);

        backgroundIntros = new BackgroundIntro[]{bg0, bgTwin0, bg1, bgTwin1};
    }

    @Override
    public void update() {
        alpha = alpha + (targetAlpha - alpha) * .1f;
        for (BackgroundIntro backgroundIntro : backgroundIntros) {
            backgroundIntro.update();
            if (backgroundIntro.getX() < -1024) {
                backgroundIntro.setX(320);
            }
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        g2.setComposite(ac);
        for(BackgroundIntro backgroundIntro : backgroundIntros) {
            backgroundIntro.draw(g2);
        }
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
}
