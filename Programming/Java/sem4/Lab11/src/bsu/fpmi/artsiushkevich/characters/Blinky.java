package bsu.fpmi.artsiushkevich.characters;

import bsu.fpmi.artsiushkevich.utility.Pair;

public class Blinky extends Ghost {
    public Blinky(int x, int y, PacMan pacManReference) {
        super(x, y, pacManReference);
    }

    @Override
    protected Pair<Integer, Integer> getGoal() {
        return new Pair<>(pacManReference.getX(), pacManReference.getY());
    }
}
