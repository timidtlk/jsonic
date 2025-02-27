package me.timidtlk.events;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.imageio.ImageIO;

import me.timidtlk.core.GamePanel;

public class SonicTeamLogo extends Event {
    Image sonicTeamLogo;
    float alpha;
    float targetAlpha;
    int count;
    GamePanel gp;

    public SonicTeamLogo(GamePanel gp) {
        super();
        this.gp = gp;

        try {
            sonicTeamLogo = ImageIO.read(getClass().getClassLoader().getResourceAsStream("sprites/misc/sonicteam.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        alpha = 0.0f;
        targetAlpha = 1.0f;
        count = 0;
    }

    @Override
    public void update() {
        alpha = alpha + (targetAlpha - alpha) * .1f;
        if (Math.round(alpha * 100) == Math.round(targetAlpha * 100) && count == 0) {
            try {
                Thread.sleep(100);
                targetAlpha = 0.0f;
                count++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (Math.round(alpha * 100) == Math.round(targetAlpha * 100) && count == 1) {
            end = true;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        g2.setComposite(ac);
        g2.drawImage(sonicTeamLogo, 0, 0, gp.getSCREEN_WIDTH(), gp.getSCREEN_HEIGHT(), null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        if (gp.getKeyH().isDebug()) {
            g2.setColor(Color.GREEN);
            g2.drawString("Sonic Team Alpha: " + alpha, 10, 50);
        }
    }
}
