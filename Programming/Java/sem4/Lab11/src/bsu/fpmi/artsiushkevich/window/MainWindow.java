package bsu.fpmi.artsiushkevich.window;

import bsu.fpmi.artsiushkevich.characters.Direction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

public class MainWindow extends JFrame {
    Board b;
    Timer frameTimer = new Timer();
    TimerTask update = new TimerTask() {
        @Override
        public void run() {
            stepFrame(false);
        }
    };

    public MainWindow() {
        this.setSize(420, 460);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = this.getContentPane();
        container.setLayout(new BorderLayout());
        b = new Board();
        container.add(b, BorderLayout.CENTER);
        b.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
        b.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
            }
        });
        b.newGame();
        stepFrame(true);


        frameTimer.schedule(update, 5, 30);
    }

    public void stepFrame(boolean New) {
        /* If we aren't on a special screen than the timers can be set to -1 to disable them */
        if (!b.titleScreen && !b.winScreen && !b.overScreen) {
            pseudoTimer = -1;
        }

        /* If we are playing the dying animation, keep advancing frames until the animation is complete */
        if (b.dying > 0) {
            b.repaint();
            return;
        }

    /* New can either be specified by the New parameter in stepFrame function call or by the state
       of b.New.  Update New accordingly */
        New = New || (b.New != 0);



    /* If this is the win screen or game over screen, make sure to only stay on the screen for 5 seconds.
       If after 5 seconds the user hasn't pressed a key, go to title screen */
        if (b.winScreen || b.overScreen) {
            if (pseudoTimer == -1) {
                pseudoTimer = System.currentTimeMillis();
            }

            long currTime = System.currentTimeMillis();
            if (currTime - pseudoTimer >= 5000) {
                b.winScreen = false;
                b.overScreen = false;
                b.titleScreen = true;
                pseudoTimer = -1;
            }
            b.repaint();
            return;
        }


        /* If we have a normal game state, move all pieces and update pellet status */
        if (!New) {
      /* The pacman player has two functions, demoMove if we're in demo mode and move if we're in
         user playable mode.  Call the appropriate one here */

            b.player.move();

            /* Also move the ghosts, and update the pellet states */
            b.blinky.move();
            b.pinky.move();
            b.inky.move();
            b.clyde.move();
            b.player.updateDot();
            b.blinky.updateDot();
            b.pinky.updateDot();
            b.inky.updateDot();
            b.clyde.updateDot();
        }

        /* We either have a new game or the user has died, either way we have to reset the board */
        if (b.stopped || New) {
            /*Temporarily stop advancing frames */
            frameTimer.cancel();

            /* If user is dying ... */
            while (b.dying > 0) {
                /* Play dying animation. */
                stepFrame(false);
            }

            /* Move all game elements back to starting positions and orientations */
            b.player.setDirection(Direction.LEFT);
            b.player.setDesiredDirection(Direction.LEFT);
            b.player.setX(200);
            b.player.setY(300);
            b.blinky.setX(180);
            b.blinky.setY(180);
            b.pinky.setX(200);
            b.pinky.setY(180);
            b.inky.setX(220);
            b.inky.setY(180);
            b.clyde.setX(220);
            b.clyde.setY(180);

            /* Advance a frame to display main state*/
            b.repaint(0, 0, 600, 600);

            /*Start advancing frames once again*/
            b.stopped = false;
            frameTimer = new Timer();
            frameTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    stepFrame(false);
                }
            }, 5, 30);
        }
        /* Otherwise we're in a normal state, advance one frame*/
        else {
            repaint();
        }
    }

    private long pseudoTimer = -1;

}
