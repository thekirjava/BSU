package bsu.fpmi.artsiushkevich.characters;

import bsu.fpmi.artsiushkevich.utility.Pair;

public class Inky extends Ghost {
    public Inky(int x, int y, PacMan pacManReference, Blinky blinkyReference) {
        super(x, y, pacManReference);
        this.blinkyReference = blinkyReference;
    }

    @Override
    protected Pair<Integer, Integer> getGoal() {
        Pair<Integer, Integer> middle = new Pair<>(blinkyReference.getX(), blinkyReference.getY());
        switch (pacManReference.getDirection()) {
            case UP:
                middle = new Pair<>(pacManReference.getX(), pacManReference.getY() - 2 * STEP);
                break;
            case DOWN:
                middle = new Pair<>(pacManReference.getX(), pacManReference.getY() + 2 * STEP);
                break;
            case RIGHT:
                middle = new Pair<>(pacManReference.getX() - 2 * STEP, pacManReference.getY());
                break;
            case LEFT:
                middle = new Pair<>(pacManReference.getX() + 2 * STEP, pacManReference.getY());
                break;
        }
        return new Pair<>(blinkyReference.getX() + 2 * (middle.first - blinkyReference.getX()), blinkyReference.getY() + 2 * (middle.second - blinkyReference.getY()));
    }

    Blinky blinkyReference;
}
