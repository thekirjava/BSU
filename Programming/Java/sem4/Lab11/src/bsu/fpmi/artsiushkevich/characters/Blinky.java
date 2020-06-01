package bsu.fpmi.artsiushkevich.characters;

import bsu.fpmi.artsiushkevich.utility.Pair;

public class Blinky extends Ghost {
    public Blinky(PacMan pacManReference) {
        super(pacManReference);
    }

    @Override
    protected Pair<Integer, Integer> getGoal() {
        return new Pair<>(pacManReference.getX(), pacManReference.getY());
    }

    @Override
    public void handleEvent(String message) {

    }


}
