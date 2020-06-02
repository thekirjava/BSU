package bsu.fpmi.artsiushkevich.characters;

import bsu.fpmi.artsiushkevich.observer.Observer;

import java.awt.*;

public abstract class Character implements Observer {
    public abstract void move();

    void move(Direction d) {
        switch (d) {
            case LEFT:
                if (isValidDest(x - STEP, y)) {
                    x -= STEP;
                }
                break;
            case RIGHT:
                if (isValidDest(x + GRID_SIZE, y)) {
                    x += STEP;
                }
                break;
            case UP:
                if (isValidDest(x, y - STEP)) {
                    y -= STEP;
                }
                break;
            case DOWN:
                if (isValidDest(x, y + GRID_SIZE)) {
                    y += STEP;
                }
                break;
        }
    }

    @Override
    public void handleEvent(String message) {
        if (message.equals("Move")) {
            move();
            updateDot();
        }
    }

    @Override
    public void handleEvent(String message, Graphics graphics) {
        if (message.equals("CopyArea")) {
            graphics.copyArea(this.getX() - 20, this.getY() - 20, 80, 80, 0, 0);
        }
        if (message.equals("Move")) {
            graphics.setColor(Color.BLACK);
            graphics.fillRect(this.getLastX(), this.getLastY(), 20, 20);
        }
    }

    public void setState(boolean[][] state) {
        this.state = new boolean[state.length][state[0].length];
        for (int i = 0; i < state.length; i++) {
            System.arraycopy(state[i], 0, this.state[i], 0, state[i].length);
        }
    }


    public void updateDot() {
        if ((x % GRID_SIZE == 0) && (y % GRID_SIZE == 0)) {
            dotX = x / GRID_SIZE - 1;
            dotY = y / GRID_SIZE - 1;
        }
    }

    public void drawCharacterFrame(Image character, Graphics graphics) {
        graphics.drawImage(character, this.getX(), this.getY(), Color.BLACK, null);
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getLastX() {
        return lastX;
    }

    public int getLastY() {
        return lastY;
    }

    public int getDotX() {
        return dotX;
    }

    public int getDotY() {
        return dotY;
    }

    public int getLastDotX() {
        return lastDotX;
    }

    public int getLastDotY() {
        return lastDotY;
    }

    public boolean isValidDest(int x, int y) {
        return (((x % 20 == 0) || (y % 20 == 0)) && (20 <= x) && (x < 400) && (20 <= y) && (y < 400) && (state[x / 20 - 1][y / 20 - 1]));
    }

    public boolean isTeleport() {
        return teleport;
    }

    public void setTeleport(boolean teleport) {
        this.teleport = teleport;
    }

    protected Direction direction;
    protected boolean[][] state;
    public int frameCount;
    protected int x;
    protected int y;
    protected int lastX;
    protected int lastY;
    protected int dotX;
    protected int dotY;
    protected int lastDotX;
    protected int lastDotY;
    protected final int STEP = 4;
    protected final int MAX_SIZE = 400;

    protected final int GRID_SIZE = 20;
    protected boolean teleport;
}
