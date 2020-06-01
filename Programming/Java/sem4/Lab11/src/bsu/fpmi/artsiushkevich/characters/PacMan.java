package bsu.fpmi.artsiushkevich.characters;

public class PacMan extends Character {
    public PacMan(int x, int y) {
        teleport = false;
        dotsEaten = 0;
        dotX = x / GRID_SIZE - 1;
        dotY = y / GRID_SIZE - 1;
        this.lastX = x;
        this.lastY = y;
        this.x = x;
        this.y = y;
        direction = Direction.LEFT;
        desiredDirection = Direction.LEFT;
    }


    public void eat() {
        dotsEaten++;
    }

    @Override
    public void setState(boolean[][] state) {
        super.setState(state);
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
        if (((x % 20 == 0) && (y % 20 == 0)) ||
                (desiredDirection == Direction.LEFT && direction == Direction.RIGHT) ||
                (desiredDirection == Direction.RIGHT && direction == Direction.LEFT) ||
                (desiredDirection == Direction.UP && direction == Direction.DOWN) ||
                (desiredDirection == Direction.DOWN && direction == Direction.UP)
        ) {
            move(desiredDirection);
        }
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
