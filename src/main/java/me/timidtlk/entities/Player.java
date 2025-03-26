package me.timidtlk.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.HashMap;

import lombok.Getter;
import me.timidtlk.core.Controller;
import me.timidtlk.core.GamePanel;
import me.timidtlk.core.animation.Animation;
import me.timidtlk.core.animation.Sprite;
import me.timidtlk.objects.GameObject;

@Getter
public class Player extends Entity {

    private final double AIR_ACCELERATION_SPEED = 0.09375;
    private final double ACCELERATION_SPEED = 0.046875;
    private final double DECELERATION_SPEED = 0.5;
    private final double FRICTION_SPEED = 0.046875;
    private final double GRAVITY_FORCE = 0.21875;
    private final double TOP_SPEED = 6.0;
    private final double JUMP_FORCE = 6.5;
    private final double ROLL_FRICTION_SPEED = 0.0234375;
    private final double ROLL_DECELERATION_SPEED = 0.125;
    private final double ROLL_TOP_SPEED = 16.0;

    Sprite sprite;
    GamePanel gp;
    HashMap<String, Animation> animations;
    Controller controller;

    private Rectangle hitbox;
    private Rectangle nextHitbox;
    private double speedX, speedY;
    private String direction;
    private boolean colliding;
    private boolean onGround;
    private boolean jumping;
    private boolean onBorder;
    private boolean skidding;
    private boolean lastTimeSkid;
    private boolean rolling;
    private boolean lastTimeRoll;

    public Animation actualAnimation;

    public Player(GamePanel gp) {
        

        sprite = new Sprite("player/sonic.png", 66, 66);
        animations = new HashMap<>();
        BufferedImage[] idleSequence = new BufferedImage[1];
        BufferedImage[] lookUpSequence = new BufferedImage[1];
        BufferedImage[] lookDownSequence = new BufferedImage[1];
        BufferedImage[] jogSequence = new BufferedImage[6];
        BufferedImage[] runSequence = new BufferedImage[4];
        BufferedImage[] jumpSequence = new BufferedImage[5];
        BufferedImage[] pushSequence = new BufferedImage[4];
        BufferedImage[] balanceSequence = new BufferedImage[2];
        BufferedImage[] skiddingSequence = new BufferedImage[2];

        try {
            idleSequence[0] = sprite.getSprite(0, 0);
            lookUpSequence[0] = sprite.getSprite(5, 0);
            lookDownSequence[0] = sprite.getSprite(6, 0);

            jogSequence[0] = sprite.getSprite(0, 1);
            jogSequence[1] = sprite.getSprite(1, 1);
            jogSequence[2] = sprite.getSprite(2, 1);
            jogSequence[3] = sprite.getSprite(3, 1);
            jogSequence[4] = sprite.getSprite(4, 1);
            jogSequence[5] = sprite.getSprite(5, 1);

            runSequence[0] = sprite.getSprite(0, 3);
            runSequence[1] = sprite.getSprite(1, 3);
            runSequence[2] = sprite.getSprite(2, 3);
            runSequence[3] = sprite.getSprite(3, 3);

            jumpSequence[0] = sprite.getSprite(0, 4);
            jumpSequence[1] = sprite.getSprite(1, 4);
            jumpSequence[2] = sprite.getSprite(2, 4);
            jumpSequence[3] = sprite.getSprite(3, 4);
            jumpSequence[4] = sprite.getSprite(4, 4);

            pushSequence[0] = sprite.getSprite(5, 4);
            pushSequence[1] = sprite.getSprite(6, 4);
            pushSequence[2] = sprite.getSprite(7, 4);
            pushSequence[3] = sprite.getSprite(0, 5);

            balanceSequence[0] = sprite.getSprite(6, 2);
            balanceSequence[1] = sprite.getSprite(7, 2);

            skiddingSequence[0] = sprite.getSprite(6, 1);
            skiddingSequence[1] = sprite.getSprite(7, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        animations.put("idle", new Animation(idleSequence, 1));
        animations.put("lookUp", new Animation(lookUpSequence, 1));
        animations.put("lookDown", new Animation(lookDownSequence, 1));
        animations.put("jog", new Animation(jogSequence, 4));
        animations.put("run", new Animation(runSequence, 4));
        animations.put("jump", new Animation(jumpSequence, 4));
        animations.put("push", new Animation(pushSequence, 6));
        animations.put("balance", new Animation(balanceSequence, 32));
        animations.put("skidding", new Animation(skiddingSequence, 6));

        int centerX = (int)x + 33;
        int centerY = (int)y + 33;

        hitbox = new Rectangle(centerX - 9, centerY - 19, 19, 41);
        actualAnimation = animations.get("idle");
        this.gp = gp;
        direction = "right";
        controller = gp.getController();

        onGround = false;
        jumping = false;
    }

    @Override
    public void update() {

        actualAnimation.update();

        if (isRolling() && !lastTimeRoll) {
            gp.getSound().setFile(2);
            gp.getSound().play();
        }
        lastTimeRoll = rolling;
        
        if (isSkidding() && !lastTimeSkid && isOnGround() && !isJumping() && Math.abs(speedX) > 1) {
            gp.getSound().setFile(1);
            gp.getSound().play();
        }
        lastTimeSkid = skidding;

        handleAnimations();

        handleSpeed();

        handleJump();

        handleCollision();

        x += speedX;
        y += speedY;
        hitbox.x = (int)Math.ceil(x + 33 - 9);
        hitbox.y = (int)Math.ceil(y + 33 - 19);
    }

    private void handleAnimations() {
        if (isOnGround()) {
            if (speedX == 0) {
                rolling = false;
                if (controller.isUp()) {
                    actualAnimation = animations.get("lookUp");
                    actualAnimation.reset();
                    actualAnimation.start();
                } else if (controller.isDown()) {
                    actualAnimation = animations.get("lookDown");
                    actualAnimation.reset();
                    actualAnimation.start();
                } else if (onBorder) {
                    actualAnimation = animations.get("balance");
                    actualAnimation.start();
                } else {
                    actualAnimation = animations.get("idle");
                    actualAnimation.reset();
                    actualAnimation.start();
                }
            } else if (controller.isDown() && Math.abs(speedX) > 1 && !rolling) {
                actualAnimation.reset();
                rolling = true;
            } else if (isRolling()) {
                actualAnimation = animations.get("jump");
                actualAnimation.setFrameDelay((int)Math.floor(Math.max(0, 4-Math.abs(speedX))));
                actualAnimation.start(); 
            } else if (isSkidding()) {
                actualAnimation = animations.get("skidding");
                actualAnimation.start();
            } else if (Math.abs(speedX) < 6 && !isRolling()) {
                actualAnimation = animations.get("jog");
                actualAnimation.setFrameDelay((int)Math.floor(Math.max(0, 8-Math.abs(speedX))));
                actualAnimation.start();
            } else {
                actualAnimation = animations.get("run");
                actualAnimation.setFrameDelay((int)Math.floor(Math.max(0, 8-Math.abs(speedX))));
                actualAnimation.start();
            }
        } else {
            if (isJumping()) {
                actualAnimation = animations.get("jump");
                actualAnimation.setFrameDelay((int)Math.floor(Math.max(0, 4-Math.abs(speedX))));
                actualAnimation.start();
            }
        }
    }

    private void handleSpeed() {
        if(!(controller.isLeft() && controller.isRight())) { // dealing with press left and right at the same time
            if (isRolling()) {
                if (controller.isLeft()) {
                    if (speedX > 0) {
                        speedX -= ROLL_DECELERATION_SPEED + ROLL_FRICTION_SPEED;
                        if (speedX <= 0) {
                            rolling = false;
                        }
                    }
                } else if (controller.isRight()) {
                    if (speedX < 0) {
                        speedX += ROLL_DECELERATION_SPEED + ROLL_FRICTION_SPEED;
                        if (speedX >= 0) {
                            rolling = false;
                        }
                    }
                } else {
                    speedX -= Math.min(Math.abs(speedX), ROLL_FRICTION_SPEED) * Math.signum(speedX);
                }

                if (Math.abs(speedX) > ROLL_TOP_SPEED) {
                    speedX = ROLL_TOP_SPEED * Math.signum(speedX);
                }
            } else {
                if (controller.isLeft()) {
                    direction = "left";
        
                    if (speedX > 0) {
                        skidding = true;
                        speedX -= (isOnGround()) ? DECELERATION_SPEED : AIR_ACCELERATION_SPEED;
                        if (speedX <= 0)
                            speedX = -0.5;
                    } else if (speedX > -TOP_SPEED) {
                        skidding = false;
                        speedX -= (isOnGround()) ? ACCELERATION_SPEED : AIR_ACCELERATION_SPEED;
                        if (speedX <= -TOP_SPEED)
                            speedX = -TOP_SPEED;
                    }
                }
        
                if (controller.isRight()) {
                    direction = "right";
        
                    if (speedX < 0) {
                        skidding = true;
                        speedX += (isOnGround()) ? DECELERATION_SPEED : AIR_ACCELERATION_SPEED;
                        if (speedX >= 0)
                            speedX = 0.5;
                    } else if (speedX < TOP_SPEED) {
                        skidding = false;
                        speedX += (isOnGround()) ? ACCELERATION_SPEED : AIR_ACCELERATION_SPEED;
                        if (speedX >= TOP_SPEED)
                            speedX = TOP_SPEED;
                    }
                }
                if ((!controller.isLeft() && !controller.isRight()) || (controller.isLeft() && controller.isRight()))
                    speedX -= Math.min(Math.abs(speedX), FRICTION_SPEED) * Math.signum(speedX);

                if (!onGround && speedY < 0 && speedY > -4) {
                    speedX -= (speedX / 0.125) / 256;
                }
            }
        }
    }

    private void handleJump() {
        // deal with jump
        if (controller.isJump() && onGround) {
            gp.getSound().setFile(0);
            gp.getSound().play();
            jumping = true;
            speedY = -JUMP_FORCE;
            onGround = false;
        }
    }

    private void handleCollision() {

        // collision aabb basic implementation
        speedY += GRAVITY_FORCE;
        
        nextHitbox = hitbox;
        nextHitbox.x += speedX;
        nextHitbox.y += speedY;
        
        Collection<GameObject> objects = gp.getActualScene().getObjects().values();
        for (GameObject object : objects) {
            if (object.isCollidable()) {
                Rectangle objectHitbox = object.getHitbox();
                if (nextHitbox.intersects(objectHitbox)) {
                    Rectangle intersection = nextHitbox.intersection(objectHitbox);
                    if (intersection.width < intersection.height) {
                        if (hitbox.x < objectHitbox.x) {
                            nextHitbox.x -= intersection.width;
                        } else {
                            nextHitbox.x += intersection.width;
                        }
                        speedX = 0;
                    } else {
                        if (hitbox.y < objectHitbox.y) {
                            nextHitbox.y -= intersection.height;
                            onGround = true;
                            jumping = false;
                        } else {
                            nextHitbox.y += intersection.height;
                        }
                        speedY = 0;
                    }

                    if (intersection.height <= 4 && intersection.width <= 10) {
                        onBorder = true;
                    } else {
                        onBorder = false;
                    }
                }
            }
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(actualAnimation.getSprite(), (int)Math.ceil(x + ((direction == "right") ? ((isSkidding()) ? 66 : 0) : ((isSkidding()) ? 0 : 66))), (int)Math.ceil(y), (direction == "right") ? ((isSkidding()) ? -66 : 66) : ((isSkidding()) ? 66 : -66), 66, null);
        if (gp.getKeyH().isDebug()) {
            g2.setColor(new Color(255, 0, 0, 50));
            g2.fillRect(hitbox.x, hitbox.y, (int) hitbox.getWidth(), (int) hitbox.getHeight());
            g2.setColor(Color.WHITE);
            g2.drawString(String.format("X: %.2f", speedX), (int)hitbox.x, (int)y);
            g2.drawString(String.format("Y: %.2f", speedY), (int)hitbox.x, (int)y + 10);
        }
    }
    
}
