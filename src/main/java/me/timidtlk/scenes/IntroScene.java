package me.timidtlk.scenes;

import me.timidtlk.core.GamePanel;
import me.timidtlk.events.SegaLogo;
import me.timidtlk.events.SonicTeamLogo;
public class IntroScene extends Scene {
    public IntroScene(GamePanel gp) {
        super(gp);
        events.push(new SegaLogo(gp));
        events.push(new SonicTeamLogo(gp));
    }
}
