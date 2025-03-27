package me.timidtlk.core;

import java.awt.Color;
import java.awt.Graphics2D;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.timidtlk.entities.Player;

@Getter
@Setter
@AllArgsConstructor
public class Camera {
    private double x, y;
    @NonNull private GamePanel gp;
    @NonNull private Player player;

    private double HORIZONTAL_FOCAL_POINT;
    private double VERTICAL_FOCAL_POINT;
    private double LEFT_BORDER;
    private double RIGHT_BORDER;
    private double TOP_BORDER;
    private double BOTTOM_BORDER;

    private final double HORIZONTAL_SPEED_CAP = 16;
    private final double VERTICAL_SPEED_CAP = 16;
    private final double VERTICAL_SPEED_CAP_FAST = 24;
    private final double VERTICAL_SPEED_CAP_SLOW = 6;
    private final double VERTICAL_SPEED_CAP_SHIFTED = 2;

    public Camera(@NonNull GamePanel gp, @NonNull Player player) {
        this.gp = gp;
        this.player = player;

        HORIZONTAL_FOCAL_POINT = gp.getSCREEN_WIDTH()/2;
        VERTICAL_FOCAL_POINT   = gp.getSCREEN_HEIGHT()/2;
        LEFT_BORDER            = 144;
        RIGHT_BORDER           = 160;
        TOP_BORDER             = VERTICAL_FOCAL_POINT - 32;
        BOTTOM_BORDER          = VERTICAL_FOCAL_POINT + 32;
    }

    public void update() {
        double playerCenterX = player.getHitbox().getCenterX();
        double playerCenterY = player.getHitbox().getCenterY();
        double screenWidth = gp.getSCREEN_WIDTH();
        double screenHeight = gp.getSCREEN_HEIGHT();
        HORIZONTAL_FOCAL_POINT   = gp.getSCREEN_WIDTH()/2;
        VERTICAL_FOCAL_POINT     = gp.getSCREEN_HEIGHT()/2;

        // Horizontal camera movement
        double targetX = playerCenterX - screenWidth / 2;
        double deltaX = targetX - x;
        if (playerCenterX < x + LEFT_BORDER) {
            x = playerCenterX - LEFT_BORDER;
        } else if (playerCenterX > x + RIGHT_BORDER) {
            x = playerCenterX - RIGHT_BORDER;
        } else if (Math.abs(deltaX) > HORIZONTAL_FOCAL_POINT) {
            x += Math.signum(deltaX) * Math.min(Math.abs(deltaX), HORIZONTAL_SPEED_CAP);
        }

        // Vertical camera movement
        double targetY = playerCenterY - screenHeight / 2;
        double deltaY = targetY - y;
        double speedCap = VERTICAL_SPEED_CAP;
        if (player.isOnGround()) {
            // Center the camera on the player's y-axis when on the ground
            y = y + (playerCenterY - VERTICAL_FOCAL_POINT - y) * .2;
            if (Math.abs(player.getSpeedX()) >= 8) {
                speedCap = VERTICAL_SPEED_CAP_FAST;
            } else {
                speedCap = VERTICAL_SPEED_CAP_SLOW;
            }
        } else {
            speedCap = VERTICAL_SPEED_CAP;
            if (playerCenterY < y + TOP_BORDER) {
                y = playerCenterY - TOP_BORDER;
            } else if (playerCenterY > y + BOTTOM_BORDER) {
                y = playerCenterY - BOTTOM_BORDER;
            } else if (Math.abs(deltaY) > VERTICAL_FOCAL_POINT) {
                y += Math.signum(deltaY) * Math.min(Math.abs(deltaY), speedCap);
            }
        }
    }

    public void drawDebug(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.drawRect((int) LEFT_BORDER, (int) TOP_BORDER, (int) (RIGHT_BORDER - LEFT_BORDER),
                (int) (BOTTOM_BORDER - TOP_BORDER));
        g2.setColor(Color.GREEN);
        g2.drawLine((int)LEFT_BORDER, (int) VERTICAL_FOCAL_POINT, (int)RIGHT_BORDER, (int) VERTICAL_FOCAL_POINT);
    }
}