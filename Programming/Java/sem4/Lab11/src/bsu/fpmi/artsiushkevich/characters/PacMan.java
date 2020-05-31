package bsu.fpmi.artsiushkevich.characters;

public class PacMan extends Character {
    public PacMan() {
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

    private boolean stopped;
    private Direction desiredDirection;
    private int dotsEaten;
}
