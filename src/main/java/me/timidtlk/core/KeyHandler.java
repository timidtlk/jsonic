package me.timidtlk.core;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import lombok.Getter;

@Getter
public class KeyHandler extends KeyAdapter {

    private boolean debug;
    Controller controller;

    public KeyHandler(Controller controller) {
        this.controller = controller;
        debug = false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        switch (code) {
            case KeyEvent.VK_UP -> {
                controller.setUp(true);
                break;
            } case KeyEvent.VK_LEFT -> {
                controller.setLeft(true);
                break;
            } case KeyEvent.VK_RIGHT -> {
                controller.setRight(true);
                break;
            } case KeyEvent.VK_DOWN -> {
                controller.setDown(true);
                break;
            } case KeyEvent.VK_ENTER -> {
                controller.setStart(true);
                break;
            } case KeyEvent.VK_D -> {
                controller.setJump(true);
                break;
            }
        }

        if (code == KeyEvent.VK_F3) debug = !debug;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
     
        switch (code) {
            case KeyEvent.VK_UP -> {
                controller.setUp(false);
                break;
            } case KeyEvent.VK_LEFT -> {
                controller.setLeft(false);
                break;
            } case KeyEvent.VK_RIGHT -> {
                controller.setRight(false);
                break;
            } case KeyEvent.VK_DOWN -> {
                controller.setDown(false);
                break;
            } case KeyEvent.VK_ENTER -> {
                controller.setStart(false);
                break;
            } case KeyEvent.VK_D -> {
                controller.setJump(false);
                break;
            }
        }
    }
}
