package bsu.fpmi.artsiushkevich.characters;

import bsu.fpmi.artsiushkevich.observer.Observer;
import bsu.fpmi.artsiushkevich.utility.Pair;

public abstract class Ghost extends Character implements Observer {

    public Ghost(PacMan pacManReference) {
        this.pacManReference = pacManReference;
    }

    protected Direction newDirection() {
        Pair<Integer, Integer> goal = getGoal();
        switch (direction) {
            case LEFT:
            case RIGHT:
                if ((isValidDest(x, y - STEP)) && (isValidDest(x, y + GRID_SIZE))) {
                    if (goal.second < y) {
                        return Direction.UP;
                    } else {
                        return Direction.DOWN;
                    }
                } else if (isValidDest(x, y - STEP)) {
                    return Direction.UP;
                } else {
                    return Direction.DOWN;
                }
            case UP:
            case DOWN:
                if ((isValidDest(x - STEP, y)) && (isValidDest(x + GRID_SIZE, y))) {
                    if (goal.first < x) {
                        return Direction.LEFT;
                    } else {
                        return Direction.RIGHT;
                    }
                } else if (isValidDest(x - STEP, y)) {
                    return Direction.LEFT;
                } else {
                    return Direction.RIGHT;
                }
        }
        return null;
    }

    protected abstract Pair<Integer, Integer> getGoal();

    public void move() {
        lastX = x;
        lastY = y;

        /* If we can make a decision, pick a new direction randomly */
        if (isChoiceDest()) {
            direction = newDirection();
        }

        /* If that direction is valid, move that way */
        move(direction);
    }


    private boolean isChoiceDest() {
        switch (direction) {
            case UP:
                return ((isValidDest(x - STEP, y)) || (isValidDest(x + GRID_SIZE, y))) && (!isValidDest(x, y - STEP));
            case DOWN:
                return ((isValidDest(x - STEP, y)) || (isValidDest(x + GRID_SIZE, y))) && (!isValidDest(x, y + GRID_SIZE));
            case LEFT:
                return ((isValidDest(x, y - STEP)) || (isValidDest(x, y + GRID_SIZE))) && (!isValidDest(x - STEP, y));
            case RIGHT:
                return ((isValidDest(x, y - STEP)) || (isValidDest(x, y + GRID_SIZE))) && (!isValidDest(x + GRID_SIZE, y));
        }
        return false;
    }
    PacMan pacManReference;
}
