package bsu.fpmi.artsiushkevich.characters;

import bsu.fpmi.artsiushkevich.utility.Pair;

import java.util.Random;

public abstract class Ghost extends Character{

    public Ghost(int x, int y, PacMan pacManReference) {
        this.pacManReference = pacManReference;
        direction = Direction.LEFT;
        dotX = x / GRID_SIZE - 1;
        dotY = x / GRID_SIZE - 1;
        lastDotX = dotX;
        lastDotY = dotY;
        this.lastX = x;
        this.lastY = y;
        this.x = x;
        this.y = y;
    }

    protected Direction newDirection() {
        if ((isValidDest(x - STEP, y)) && (!isValidDest(x + GRID_SIZE, y)) && (!isValidDest(x, y - STEP)) &&
                (!isValidDest(x, y + GRID_SIZE))) {
            return Direction.LEFT;
        }
        if ((!isValidDest(x - STEP, y)) && (isValidDest(x + GRID_SIZE, y)) && (!isValidDest(x, y - STEP)) &&
                (!isValidDest(x, y + GRID_SIZE))) {
            return Direction.RIGHT;
        }
        if ((!isValidDest(x - STEP, y)) && (!isValidDest(x + GRID_SIZE, y)) && (isValidDest(x, y - STEP)) &&
                (!isValidDest(x, y + GRID_SIZE))) {
            return Direction.UP;
        }
        if ((!isValidDest(x - STEP, y)) && (!isValidDest(x + GRID_SIZE, y)) && (!isValidDest(x, y - STEP)) &&
                (isValidDest(x, y + GRID_SIZE))) {
            return Direction.DOWN;
        }
        Pair<Integer, Integer> goal = getGoal();
        switch (direction) {
            case LEFT:
                if (goal.second == y) {
                    if (isValidDest(x - STEP, y)) {
                        return Direction.LEFT;
                    }
                    if ((isValidDest(x, y - STEP)) && (isValidDest(x, y + GRID_SIZE))) {
                        Random random = new Random(System.currentTimeMillis());
                        if (random.nextInt() % 2 == 0) {
                            return Direction.UP;
                        } else {
                            return Direction.DOWN;
                        }
                    } else if (isValidDest(x, y - STEP)) {
                        return Direction.UP;
                    } else {
                        return Direction.DOWN;
                    }
                } else if (goal.second < y) {
                    if (isValidDest(x, y - STEP)) {
                        return Direction.UP;
                    }
                    if ((isValidDest(x - STEP, y)) && (isValidDest(x, y + GRID_SIZE))) {
                        Random random = new Random(System.currentTimeMillis());
                        if (random.nextInt() % 2 == 0) {
                            return Direction.LEFT;
                        } else {
                            return Direction.DOWN;
                        }
                    } else if (isValidDest(x - STEP, y)) {
                        return Direction.LEFT;
                    } else {
                        return Direction.DOWN;
                    }
                } else {
                    if (isValidDest(x, y + GRID_SIZE)) {
                        return Direction.DOWN;
                    }
                    if ((isValidDest(x - STEP, y)) && (isValidDest(x, y - STEP))) {
                        Random random = new Random(System.currentTimeMillis());
                        if (random.nextInt() % 2 == 0) {
                            return Direction.LEFT;
                        } else {
                            return Direction.UP;
                        }
                    } else if (isValidDest(x - STEP, y)) {
                        return Direction.LEFT;
                    } else {
                        return Direction.UP;
                    }
                }
            case RIGHT:
                if (goal.second == y) {
                    if (isValidDest(x + GRID_SIZE, y)) {
                        return Direction.RIGHT;
                    }
                    if ((isValidDest(x, y - STEP)) && (isValidDest(x, y + GRID_SIZE))) {
                        Random random = new Random(System.currentTimeMillis());
                        if (random.nextInt() % 2 == 0) {
                            return Direction.UP;
                        } else {
                            return Direction.DOWN;
                        }
                    } else if (isValidDest(x, y - STEP)) {
                        return Direction.UP;
                    } else {
                        return Direction.DOWN;
                    }
                } else if (goal.second < y) {
                    if (isValidDest(x, y - STEP)) {
                        return Direction.UP;
                    }
                    if ((isValidDest(x + GRID_SIZE, y)) && (isValidDest(x, y + GRID_SIZE))) {
                        Random random = new Random(System.currentTimeMillis());
                        if (random.nextInt() % 2 == 0) {
                            return Direction.RIGHT;
                        } else {
                            return Direction.DOWN;
                        }
                    } else if (isValidDest(x + GRID_SIZE, y)) {
                        return Direction.RIGHT;
                    } else {
                        return Direction.DOWN;
                    }
                } else {
                    if (isValidDest(x, y + GRID_SIZE)) {
                        return Direction.DOWN;
                    }
                    if ((isValidDest(x + GRID_SIZE, y)) && (isValidDest(x, y - STEP))) {
                        Random random = new Random(System.currentTimeMillis());
                        if (random.nextInt() % 2 == 0) {
                            return Direction.RIGHT;
                        } else {
                            return Direction.UP;
                        }
                    } else if (isValidDest(x + GRID_SIZE, y)) {
                        return Direction.RIGHT;
                    } else {
                        return Direction.UP;
                    }
                }
            case UP:
                if (goal.first == x) {
                    if (isValidDest(x, y - STEP)) {
                        return Direction.UP;
                    }
                    if ((isValidDest(x - STEP, y)) && (isValidDest(x + GRID_SIZE, y))) {
                        Random random = new Random(System.currentTimeMillis());
                        if (random.nextInt() % 2 == 0) {
                            return Direction.LEFT;
                        } else {
                            return Direction.RIGHT;
                        }
                    } else if (isValidDest(x - STEP, y)) {
                        return Direction.LEFT;
                    } else {
                        return Direction.RIGHT;
                    }
                } else if (goal.first < x) {
                    if (isValidDest(x - STEP, y)) {
                        return Direction.LEFT;
                    }
                    if ((isValidDest(x + GRID_SIZE, y)) && (isValidDest(x, y - STEP))) {
                        Random random = new Random(System.currentTimeMillis());
                        if (random.nextInt() % 2 == 0) {
                            return Direction.RIGHT;
                        } else {
                            return Direction.UP;
                        }
                    } else if (isValidDest(x + GRID_SIZE, y)) {
                        return Direction.RIGHT;
                    } else {
                        return Direction.UP;
                    }
                } else {
                    if (isValidDest(x + GRID_SIZE, y)) {
                        return Direction.RIGHT;
                    }
                    if ((isValidDest(x - STEP, y)) && (isValidDest(x, y - STEP))) {
                        Random random = new Random(System.currentTimeMillis());
                        if (random.nextInt() % 2 == 0) {
                            return Direction.LEFT;
                        } else {
                            return Direction.UP;
                        }
                    } else if (isValidDest(x - STEP, y)) {
                        return Direction.LEFT;
                    } else {
                        return Direction.UP;
                    }
                }
            case DOWN:
                if (goal.first == x) {
                    if (isValidDest(x, y + GRID_SIZE)) {
                        return Direction.DOWN;
                    }
                    if ((isValidDest(x - STEP, y)) && (isValidDest(x + GRID_SIZE, y))) {
                        Random random = new Random(System.currentTimeMillis());
                        if (random.nextInt() % 2 == 0) {
                            return Direction.LEFT;
                        } else {
                            return Direction.RIGHT;
                        }
                    } else if (isValidDest(x - STEP, y)) {
                        return Direction.LEFT;
                    } else {
                        return Direction.RIGHT;
                    }
                } else if (goal.first < x) {
                    if (isValidDest(x - STEP, y)) {
                        return Direction.LEFT;
                    }
                    if ((isValidDest(x + GRID_SIZE, y)) && (isValidDest(x, y + GRID_SIZE))) {
                        Random random = new Random(System.currentTimeMillis());
                        if (random.nextInt() % 2 == 0) {
                            return Direction.RIGHT;
                        } else {
                            return Direction.DOWN;
                        }
                    } else if (isValidDest(x + GRID_SIZE, y)) {
                        return Direction.RIGHT;
                    } else {
                        return Direction.DOWN;
                    }
                } else {
                    if (isValidDest(x + GRID_SIZE, y)) {
                        return Direction.RIGHT;
                    }
                    if ((isValidDest(x - STEP, y)) && (isValidDest(x, y + GRID_SIZE))) {
                        Random random = new Random(System.currentTimeMillis());
                        if (random.nextInt() % 2 == 0) {
                            return Direction.LEFT;
                        } else {
                            return Direction.DOWN;
                        }
                    } else if (isValidDest(x - STEP, y)) {
                        return Direction.LEFT;
                    } else {
                        return Direction.DOWN;
                    }
                }
        }
        return null;
    }

    protected abstract Pair<Integer, Integer> getGoal();

    @Override
    public void updateDot() {
        int tempX, tempY;
        tempX = x / GRID_SIZE - 1;
        tempY = y / GRID_SIZE - 1;
        if ((tempX != dotX) || (tempY != dotY)) {
            lastDotX = dotX;
            lastDotY = dotY;
            dotX = tempX;
            dotY = tempY;
        }
    }

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
       /* switch (direction) {
            case UP:
            case DOWN:
                return ((isValidDest(x - STEP, y)) || (isValidDest(x + GRID_SIZE, y)));
            case LEFT:
            case RIGHT:
                return ((isValidDest(x, y - STEP)) || (isValidDest(x, y + GRID_SIZE)));
        }
        return false;*/
        return (x % GRID_SIZE == 0) && (y % GRID_SIZE == 0);
    }

    PacMan pacManReference;
}
