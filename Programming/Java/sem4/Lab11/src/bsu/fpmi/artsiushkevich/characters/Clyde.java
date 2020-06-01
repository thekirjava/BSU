package bsu.fpmi.artsiushkevich.characters;

import bsu.fpmi.artsiushkevich.utility.Pair;

public class Clyde extends Ghost {
    public Clyde(PacMan pacManReference) {
        super(pacManReference);
    }

    @Override
    public void handleEvent(String message) {

    }

    @Override
    protected Pair<Integer, Integer> getGoal() {
        if (Math.abs(this.getX() - pacManReference.getX()) + Math.abs(this.getY() - pacManReference.getY()) <= 8 * STEP) {
            return new Pair<>(0, 500);
        }
        return new Pair<>(pacManReference.getX(), pacManReference.getY());
    }

}
