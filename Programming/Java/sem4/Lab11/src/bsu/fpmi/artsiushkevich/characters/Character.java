package bsu.fpmi.artsiushkevich.characters;

import bsu.fpmi.artsiushkevich.utility.Pair;

public abstract class Character {
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

    public void setState(boolean[][] state) {
        this.state = state;
    }


    public void updateDot() {
        if (x % GRID_SIZE == 0 && y % GRID_SIZE == 0) {
            dotX = x / GRID_SIZE - 1;
            dotY = y / GRID_SIZE - 1;
        }
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

    public void setLastX(int lastX) {
        this.lastX = lastX;
    }

    public int getLastY() {
        return lastY;
    }

    public void setLastY(int lastY) {
        this.lastY = lastY;
    }

    public int getDotX() {
        return dotX;
    }

    public void setDotX(int dotX) {
        this.dotX = dotX;
    }

    public int getDotY() {
        return dotY;
    }

    public void setDotY(int dotY) {
        this.dotY = dotY;
    }

    public int getLastDotX() {
        return lastDotX;
    }

    public void setLastDotX(int lastDotX) {
        this.lastDotX = lastDotX;
    }

    public int getLastDotY() {
        return lastDotY;
    }

    public void setLastDotY(int lastDotY) {
        this.lastDotY = lastDotY;
    }

    public boolean isValidDest(int x, int y) {
        return ((x % 20 == 0) || (y % 20 == 0) && (20 <= x) && (x < 400) && (20 <= y) && (y < 400) && state[x / 20 - 1][y / 20 - 1]);
    }

    protected Direction direction;
    protected Pair<Integer, Integer> position;
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
