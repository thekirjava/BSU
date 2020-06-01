package bsu.fpmi.artsiushkevich.characters;

import bsu.fpmi.artsiushkevich.utility.Pair;

public class Pinky extends Ghost {
    public Pinky(int x, int y, PacMan pacManReference) {
        super(x, y, pacManReference);
    }

    @Override
    protected Pair<Integer, Integer> getGoal() {
        switch (pacManReference.getDirection()) {
            case UP:
                return new Pair<>(pacManReference.getX(), pacManReference.getY() - 4 * STEP);
            case DOWN:
                return new Pair<>(pacManReference.getX(), pacManReference.getY() + 4 * STEP);
            case RIGHT:
                return new Pair<>(pacManReference.getX() - 4 * STEP, pacManReference.getY());
            case LEFT:
                return new Pair<>(pacManReference.getX() + 4 * STEP, pacManReference.getY());
        }
        return null;
    }
}
