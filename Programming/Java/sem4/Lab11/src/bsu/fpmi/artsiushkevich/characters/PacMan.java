package bsu.fpmi.artsiushkevich.characters;

public class PacMan extends Character {
    public PacMan() {
        teleport = false;
    }

    public void setDirection() {
    }

    public void eat() {
        dotsEaten++;
    }

    @Override
    public void setState(boolean[][] state) {
        this.state = state;
        this.state[9][7] = false;
    }

    public int getDotsEaten() {
        return dotsEaten;
    }

    public boolean isStopped() {
        return stopped;
    }

    public void setDesiredDirection(Direction desiredDirection) {
        this.desiredDirection = desiredDirection;
    }

    @Override
    public void move() {
        lastX = x;
        lastY = y;

        /* Try to turn in the direction input by the user */
        /*Can only turn if we're in center of a grid*/
        if (x % 20 == 0 && y % 20 == 0 ||
                /* Or if we're reversing*/
                (desiredDirection == Direction.LEFT && direction == Direction.RIGHT) ||
                (desiredDirection == Direction.RIGHT && direction == Direction.LEFT) ||
                (desiredDirection == Direction.UP && direction == Direction.DOWN) ||
                (desiredDirection == Direction.DOWN && direction == Direction.UP)
        ) {
            move(desiredDirection);
        }
        /* If we haven't moved, then move in the direction the pacman was headed anyway */
        if (lastX == x && lastY == y) {
            switch (direction) {
                case LEFT:
                    if (isValidDest(x - STEP, y)) {
                        x -= STEP;
                    } else if (y == 9 * GRID_SIZE && x < 2 * GRID_SIZE) {
                        x = MAX_SIZE - GRID_SIZE;
                        teleport = true;
                    }
                    break;
                case RIGHT:
                    if (isValidDest(x + GRID_SIZE, y)) {
                        x += STEP;
                    } else if (y == 9 * GRID_SIZE && x > MAX_SIZE - GRID_SIZE * 2) {
                        x = GRID_SIZE;
                        teleport = true;
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
        } else {
            direction = desiredDirection;
        }
        if ((lastX == x) && (lastY == y)) {
            stopped = true;
        } else {
            stopped = false;
            frameCount++;
        }
    }

    private boolean stopped;
    private Direction desiredDirection;
    private int dotsEaten;
}
