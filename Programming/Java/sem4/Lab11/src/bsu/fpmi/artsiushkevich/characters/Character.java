package bsu.fpmi.artsiushkevich.characters;

import bsu.fpmi.artsiushkevich.utility.Pair;

public class Character {
    public void moveTo() {
    }

    public void setState(boolean[][] state) {
        this.state = state;
    }

    protected Direction direction;
    protected Pair<Integer, Integer> position;
    protected boolean[][] state;

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

    public int frameCount;
    protected int x;
    protected int y;
    protected int lastX;
    protected int lastY;
    protected int dotX;
    protected int dotY;
    protected int lastDotX;
    protected int lastDotY;
}
