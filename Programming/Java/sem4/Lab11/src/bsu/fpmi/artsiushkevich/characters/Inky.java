package bsu.fpmi.artsiushkevich.characters;

import bsu.fpmi.artsiushkevich.utility.Pair;

public class Inky extends Ghost {
    public Inky(PacMan pacManReference, Blinky blinkyReference) {
        super(pacManReference);
        this.blinkyReference = blinkyReference;
    }

    @Override
    public void handleEvent(String message) {

    }


    @Override
    protected Pair<Integer, Integer> getGoal() {
        Pair<Integer, Integer> middle = new Pair<>(blinkyReference.getX(), blinkyReference.getY());
        switch (pacManReference.getDirection()) {
            case UP:
                middle = new Pair<>(pacManReference.getX(), pacManReference.getY() - 2 * STEP);
            case DOWN:
                middle = new Pair<>(pacManReference.getX(), pacManReference.getY() + 2 * STEP);
            case RIGHT:
                middle = new Pair<>(pacManReference.getX() - 2 * STEP, pacManReference.getY());
            case LEFT:
                middle = new Pair<>(pacManReference.getX() + 2 * STEP, pacManReference.getY());
        }
        return new Pair<>(blinkyReference.getX() + 2 * (middle.first - blinkyReference.getX()), blinkyReference.getY() + 2 * (middle.second - blinkyReference.getY()));
    }

    Blinky blinkyReference;
}
