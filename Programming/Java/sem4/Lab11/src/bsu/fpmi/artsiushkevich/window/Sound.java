package bsu.fpmi.artsiushkevich.window;

import bsu.fpmi.artsiushkevich.characters.State;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Sound {
    Clip nomNom;
    Clip newGame;
    Clip death;
    /* Keeps track of whether or not the eating sound is playing*/
    boolean stopped;


    /* Initialize audio files */
    public Sound(){
        stopped=true;
        AudioInputStream audioIn;

        try{
            // Pacman eating sound
            audioIn = AudioSystem.getAudioInputStream(new File("sounds/nomnom.wav"));
            nomNom = AudioSystem.getClip();
            nomNom.open(audioIn);

            // newGame
            audioIn = AudioSystem.getAudioInputStream(new File("sounds/newGame.wav"));
            newGame = AudioSystem.getClip();
            newGame.open(audioIn);

            // death
            audioIn = AudioSystem.getAudioInputStream(new File("sounds/death.wav"));
            death = AudioSystem.getClip();
            death.open(audioIn);

        }catch(Exception e){}
    }

    /* Play pacman eating sound */
    public void nomNom(){
        /* If it's already playing, don't start it playing again!*/
        if (!stopped)
            return;

        stopped=false;
        nomNom.stop();
        nomNom.setFramePosition(0);
        nomNom.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /* Stop pacman eating sound */
    public void nomNomStop(){
        stopped=true;
        nomNom.stop();
        nomNom.setFramePosition(0);
    }

    /* Play new game sound */
    public void newGame(){
        newGame.stop();
        newGame.setFramePosition(0);
        newGame.start();
    }

    /* Play pacman death sound */
    public void death(){
        death.stop();
        death.setFramePosition(0);
        death.start();
    }
}
