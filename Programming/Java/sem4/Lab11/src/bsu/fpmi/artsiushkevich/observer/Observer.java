package bsu.fpmi.artsiushkevich.observer;

import java.awt.*;

public interface Observer {
    void handleEvent(String message);
    void handleEvent(String message, Graphics graphics);
}
