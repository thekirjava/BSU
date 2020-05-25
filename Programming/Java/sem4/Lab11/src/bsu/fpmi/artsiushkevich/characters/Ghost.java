package bsu.fpmi.artsiushkevich.characters;

import bsu.fpmi.artsiushkevich.observer.Observer;

public abstract class Ghost extends Character implements Observer{

    Ghost() {

    }
    public abstract void findGoal();

    public void setState (State state) {

    }
    protected State state;
    protected int chaseTime;
    protected int spreadTime;
}
