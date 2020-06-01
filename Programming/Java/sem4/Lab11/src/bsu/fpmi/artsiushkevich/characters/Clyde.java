package bsu.fpmi.artsiushkevich.characters;

import bsu.fpmi.artsiushkevich.utility.Pair;

public class Clyde extends Ghost {
    public Clyde(int x, int y, PacMan pacManReference) {
        super(x, y, pacManReference);
    }

    @Override
    protected Pair<Integer, Integer> getGoal() {
        if (Math.abs(this.getX() - pacManReference.getX()) + Math.abs(this.getY() - pacManReference.getY()) <= 8 * STEP) {
            return new Pair<>(0, 500);
        }
        return new Pair<>(pacManReference.getX(), pacManReference.getY());
    }
}
