package bsu.fpmi.artsiushkevich.window;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {
    Clip nomNom;
    Clip newGame;
    Clip death;
    boolean stopped;

    public Sound() {
        stopped = true;
        AudioInputStream audioIn;
        try {
            audioIn = AudioSystem.getAudioInputStream(new File("sounds/nomnom.wav"));
            nomNom = AudioSystem.getClip();
            nomNom.open(audioIn);
            audioIn = AudioSystem.getAudioInputStream(new File("sounds/newGame.wav"));
            newGame = AudioSystem.getClip();
            newGame.open(audioIn);
            audioIn = AudioSystem.getAudioInputStream(new File("sounds/death.wav"));
            death = AudioSystem.getClip();
            death.open(audioIn);

        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    public void nomNom() {
        if (!stopped) {
            return;
        }
        stopped = false;
        nomNom.stop();
        nomNom.setFramePosition(0);
        nomNom.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void nomNomStop() {
        stopped = true;
        nomNom.stop();
        nomNom.setFramePosition(0);
    }
    public void newGame() {
        newGame.stop();
        newGame.setFramePosition(0);
        newGame.start();
    }
    public void death() {
        death.stop();
        death.setFramePosition(0);
        death.start();
    }
}
