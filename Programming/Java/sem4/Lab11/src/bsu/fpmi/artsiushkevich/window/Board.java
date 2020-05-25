package bsu.fpmi.artsiushkevich.window;

import bsu.fpmi.artsiushkevich.characters.*;

import javax.swing.*;
import java.awt.*;

public class Board extends JPanel {
    /* Initialize the images*/
    /* For JAR File*/
  /*
  Image pacmanImage = Toolkit.getDefaultToolkit().getImage(Pacman.class.getResource("img/pacman.jpg"));
  Image pacmanUpImage = Toolkit.getDefaultToolkit().getImage(Pacman.class.getResource("img/pacmanup.jpg"));
  Image pacmanDownImage = Toolkit.getDefaultToolkit().getImage(Pacman.class.getResource("img/pacmandown.jpg"));
  Image pacmanLeftImage = Toolkit.getDefaultToolkit().getImage(Pacman.class.getResource("img/pacmanleft.jpg"));
  Image pacmanRightImage = Toolkit.getDefaultToolkit().getImage(Pacman.class.getResource("img/pacmanright.jpg"));
  Image ghost10 = Toolkit.getDefaultToolkit().getImage(Pacman.class.getResource("img/ghost10.jpg"));
  Image ghost20 = Toolkit.getDefaultToolkit().getImage(Pacman.class.getResource("img/ghost20.jpg"));
  Image ghost30 = Toolkit.getDefaultToolkit().getImage(Pacman.class.getResource("img/ghost30.jpg"));
  Image ghost40 = Toolkit.getDefaultToolkit().getImage(Pacman.class.getResource("img/ghost40.jpg"));
  Image ghost11 = Toolkit.getDefaultToolkit().getImage(Pacman.class.getResource("img/ghost11.jpg"));
  Image ghost21 = Toolkit.getDefaultToolkit().getImage(Pacman.class.getResource("img/ghost21.jpg"));
  Image ghost31 = Toolkit.getDefaultToolkit().getImage(Pacman.class.getResource("img/ghost31.jpg"));
  Image ghost41 = Toolkit.getDefaultToolkit().getImage(Pacman.class.getResource("img/ghost41.jpg"));
  Image titleScreenImage = Toolkit.getDefaultToolkit().getImage(Pacman.class.getResource("img/titleScreen.jpg"));
  Image gameOverImage = Toolkit.getDefaultToolkit().getImage(Pacman.class.getResource("img/gameOver.jpg"));
  Image winScreenImage = Toolkit.getDefaultToolkit().getImage(Pacman.class.getResource("img/winScreen.jpg"));
  */
    /* For NOT JAR file*/
    Image pacmanImage = Toolkit.getDefaultToolkit().getImage("img/pacman.jpg");
    Image pacmanUpImage = Toolkit.getDefaultToolkit().getImage("img/pacmanup.jpg");
    Image pacmanDownImage = Toolkit.getDefaultToolkit().getImage("img/pacmandown.jpg");
    Image pacmanLeftImage = Toolkit.getDefaultToolkit().getImage("img/pacmanleft.jpg");
    Image pacmanRightImage = Toolkit.getDefaultToolkit().getImage("img/pacmanright.jpg");
    Image ghost10 = Toolkit.getDefaultToolkit().getImage("img/ghost10.jpg");
    Image ghost20 = Toolkit.getDefaultToolkit().getImage("img/ghost20.jpg");
    Image ghost30 = Toolkit.getDefaultToolkit().getImage("img/ghost30.jpg");
    Image ghost40 = Toolkit.getDefaultToolkit().getImage("img/ghost40.jpg");
    Image ghost11 = Toolkit.getDefaultToolkit().getImage("img/ghost11.jpg");
    Image ghost21 = Toolkit.getDefaultToolkit().getImage("img/ghost21.jpg");
    Image ghost31 = Toolkit.getDefaultToolkit().getImage("img/ghost31.jpg");
    Image ghost41 = Toolkit.getDefaultToolkit().getImage("img/ghost41.jpg");
    Image titleScreenImage = Toolkit.getDefaultToolkit().getImage("img/titleScreen.jpg");
    Image gameOverImage = Toolkit.getDefaultToolkit().getImage("img/gameOver.jpg");
    Image winScreenImage = Toolkit.getDefaultToolkit().getImage("img/winScreen.jpg");

    /* Initialize the player and ghosts */
    PacMan player = new PacMan();
    Blinky blinky = new Blinky();
    Inky inky = new Inky();
    Pinky pinky = new Pinky();
    Clyde clyde = new Clyde();

    /* Timer is used for playing sound effects and animations */
    long timer = System.currentTimeMillis();

    /* Dying is used to count frames in the dying animation.  If it's non-zero,
       pacman is in the process of dying */
    int dying = 0;

    /* Score information */
    int currScore;
    int highScore;

    /* if the high scores have been cleared, we have to update the top of the screen to reflect that */
    boolean clearHighScores = false;

    int numLives = 2;

    /*Contains the game map, passed to player and ghosts */
    boolean[][] state;

    /* Contains the state of all dots*/
    boolean[][] dots;

    /* Game dimensions */
    int gridSize;
    int max;

    /* State flags*/
    boolean stopped;
    boolean titleScreen;
    boolean winScreen = false;
    boolean overScreen = false;
    boolean demo = false;
    int New;

    /* Used to call sound effects */
    Sound sounds;

    int lastDotEatenX = 0;
    int lastDotEatenY = 0;

    /* This is the font used for the menus */
    Font font = new Font("Monospaced", Font.BOLD, 12);

    /* Constructor initializes state flags etc.*/
    public Board() {
        sounds = new Sound();
        currScore = 0;
        stopped = false;
        max = 400;
        gridSize = 20;
        New = 0;
        titleScreen = true;
    }


    /* Reset occurs on a new game*/
    public void reset() {
        numLives = 2;
        state = new boolean[20][20];
        dots = new boolean[20][20];

        /* Clear state and dots arrays */
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                state[i][j] = true;
                dots[i][j] = true;
            }
        }

        /* Handle the weird spots with no dots*/
        for (int i = 5; i < 14; i++) {
            for (int j = 5; j < 12; j++) {
                dots[i][j] = false;
            }
        }
        dots[9][7] = false;
        dots[8][8] = false;
        dots[9][8] = false;
        dots[10][8] = false;

    }


    /* Function is called during drawing of the map.
       Whenever the a portion of the map is covered up with a barrier,
       the map and dots arrays are updated accordingly to note
       that those are invalid locations to travel or put dots
    */
    public void updateMap(int x, int y, int width, int height) {
        for (int i = x / gridSize; i < x / gridSize + width / gridSize; i++) {
            for (int j = y / gridSize; j < y / gridSize + height / gridSize; j++) {
                state[i - 1][j - 1] = false;
                dots[i - 1][j - 1] = false;
            }
        }
    }


    /* Draws the appropriate number of lives on the bottom left of the screen.
       Also draws the menu */
    public void drawLives(Graphics g) {
        g.setColor(Color.BLACK);

        /*Clear the bottom bar*/
        g.fillRect(0, max + 5, 600, gridSize);
        g.setColor(Color.YELLOW);
        for (int i = 0; i < numLives; i++) {
            /*Draw each life */
            g.fillOval(gridSize * (i + 1), max + 5, gridSize, gridSize);
        }
        /* Draw the menu items */
        g.setColor(Color.YELLOW);
        g.setFont(font);
        g.drawString("Reset", 100, max + 5 + gridSize);
        g.drawString("Clear High Scores", 180, max + 5 + gridSize);
        g.drawString("Exit", 350, max + 5 + gridSize);
    }


    /*  This function draws the board.  The pacman board is really complicated and can only feasibly be done
        manually.  Whenever I draw a wall, I call updateMap to invalidate those coordinates.  This way the pacman
        and ghosts know that they can't traverse this area */
    public void drawBoard(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 600, 600);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 420, 420);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 20, 600);
        g.fillRect(0, 0, 600, 20);
        g.setColor(Color.WHITE);
        g.drawRect(19, 19, 382, 382);
        g.setColor(Color.BLUE);

        g.fillRect(40, 40, 60, 20);
        updateMap(40, 40, 60, 20);
        g.fillRect(120, 40, 60, 20);
        updateMap(120, 40, 60, 20);
        g.fillRect(200, 20, 20, 40);
        updateMap(200, 20, 20, 40);
        g.fillRect(240, 40, 60, 20);
        updateMap(240, 40, 60, 20);
        g.fillRect(320, 40, 60, 20);
        updateMap(320, 40, 60, 20);
        g.fillRect(40, 80, 60, 20);
        updateMap(40, 80, 60, 20);
        g.fillRect(160, 80, 100, 20);
        updateMap(160, 80, 100, 20);
        g.fillRect(200, 80, 20, 60);
        updateMap(200, 80, 20, 60);
        g.fillRect(320, 80, 60, 20);
        updateMap(320, 80, 60, 20);

        g.fillRect(20, 120, 80, 60);
        updateMap(20, 120, 80, 60);
        g.fillRect(320, 120, 80, 60);
        updateMap(320, 120, 80, 60);
        g.fillRect(20, 200, 80, 60);
        updateMap(20, 200, 80, 60);
        g.fillRect(320, 200, 80, 60);
        updateMap(320, 200, 80, 60);

        g.fillRect(160, 160, 40, 20);
        updateMap(160, 160, 40, 20);
        g.fillRect(220, 160, 40, 20);
        updateMap(220, 160, 40, 20);
        g.fillRect(160, 180, 20, 20);
        updateMap(160, 180, 20, 20);
        g.fillRect(160, 200, 100, 20);
        updateMap(160, 200, 100, 20);
        g.fillRect(240, 180, 20, 20);
        updateMap(240, 180, 20, 20);
        g.setColor(Color.BLUE);


        g.fillRect(120, 120, 60, 20);
        updateMap(120, 120, 60, 20);
        g.fillRect(120, 80, 20, 100);
        updateMap(120, 80, 20, 100);
        g.fillRect(280, 80, 20, 100);
        updateMap(280, 80, 20, 100);
        g.fillRect(240, 120, 60, 20);
        updateMap(240, 120, 60, 20);

        g.fillRect(280, 200, 20, 60);
        updateMap(280, 200, 20, 60);
        g.fillRect(120, 200, 20, 60);
        updateMap(120, 200, 20, 60);
        g.fillRect(160, 240, 100, 20);
        updateMap(160, 240, 100, 20);
        g.fillRect(200, 260, 20, 40);
        updateMap(200, 260, 20, 40);

        g.fillRect(120, 280, 60, 20);
        updateMap(120, 280, 60, 20);
        g.fillRect(240, 280, 60, 20);
        updateMap(240, 280, 60, 20);

        g.fillRect(40, 280, 60, 20);
        updateMap(40, 280, 60, 20);
        g.fillRect(80, 280, 20, 60);
        updateMap(80, 280, 20, 60);
        g.fillRect(320, 280, 60, 20);
        updateMap(320, 280, 60, 20);
        g.fillRect(320, 280, 20, 60);
        updateMap(320, 280, 20, 60);

        g.fillRect(20, 320, 40, 20);
        updateMap(20, 320, 40, 20);
        g.fillRect(360, 320, 40, 20);
        updateMap(360, 320, 40, 20);
        g.fillRect(160, 320, 100, 20);
        updateMap(160, 320, 100, 20);
        g.fillRect(200, 320, 20, 60);
        updateMap(200, 320, 20, 60);

        g.fillRect(40, 360, 140, 20);
        updateMap(40, 360, 140, 20);
        g.fillRect(240, 360, 140, 20);
        updateMap(240, 360, 140, 20);
        g.fillRect(280, 320, 20, 40);
        updateMap(280, 320, 20, 60);
        g.fillRect(120, 320, 20, 60);
        updateMap(120, 320, 20, 60);
        drawLives(g);
    }


    /* Draws the dots on the screen */
    public void drawDots(Graphics g) {
        g.setColor(Color.YELLOW);
        for (int i = 1; i < 20; i++) {
            for (int j = 1; j < 20; j++) {
                if (dots[i - 1][j - 1])
                    g.fillOval(i * 20 + 8, j * 20 + 8, 4, 4);
            }
        }
    }

    /* Draws one individual dot.  Used to redraw dots that ghosts have run over */
    public void fillDot(int x, int y, Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillOval(x * 20 + 28, y * 20 + 28, 4, 4);
    }

    /* This is the main function that draws one entire frame of the game */
    public void paint(Graphics g) {
    /* If we're playing the dying animation, don't update the entire screen.
       Just kill the pacman*/
        if (dying > 0) {
            /* Stop any pacman eating sounds */
            sounds.nomNomStop();

            /* Draw the pacman */
            g.drawImage(pacmanImage, player.getX(), player.getY(), Color.BLACK, null);
            g.setColor(Color.BLACK);

            /* Kill the pacman */
            if (dying == 4)
                g.fillRect(player.getX(), player.getY(), 20, 7);
            else if (dying == 3)
                g.fillRect(player.getX(), player.getY(), 20, 14);
            else if (dying == 2)
                g.fillRect(player.getX(), player.getY(), 20, 20);
            else if (dying == 1) {
                g.fillRect(player.getX(), player.getY(), 20, 20);
            }

      /* Take .1 seconds on each frame of death, and then take 2 seconds
         for the final frame to allow for the sound effect to end */
            long currTime = System.currentTimeMillis();
            long temp;
            if (dying != 1)
                temp = 100;
            else
                temp = 2000;
            /* If it's time to draw a new death frame... */
            if (currTime - timer >= temp) {
                dying--;
                timer = currTime;
                /* If this was the last death frame...*/
                if (dying == 0) {
                    if (numLives == -1) {
                        /* Demo mode has infinite lives, just give it more lives*/
                        if (demo)
                            numLives = 2;
                        else {
                            /* Game over for player.  If relevant, update high score.  Set gameOver flag*/
                            overScreen = true;
                        }
                    }
                }
            }
            return;
        }

        /* If this is the title screen, draw the title screen and return */
        if (titleScreen) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, 600, 600);
            g.drawImage(titleScreenImage, 0, 0, Color.BLACK, null);

            /* Stop any pacman eating sounds */
            sounds.nomNomStop();
            New = 1;
            return;
        }

        /* If this is the win screen, draw the win screen and return */
        else if (winScreen) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, 600, 600);
            g.drawImage(winScreenImage, 0, 0, Color.BLACK, null);
            New = 1;
            /* Stop any pacman eating sounds */
            sounds.nomNomStop();
            return;
        }

        /* If this is the game over screen, draw the game over screen and return */
        else if (overScreen) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, 600, 600);
            g.drawImage(gameOverImage, 0, 0, Color.BLACK, null);
            New = 1;
            /* Stop any pacman eating sounds */
            sounds.nomNomStop();
            return;
        }

        /* If need to update the high scores, redraw the top menu bar */
        if (clearHighScores) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, 600, 18);
            g.setColor(Color.YELLOW);
            g.setFont(font);
            clearHighScores = false;
            if (demo)
                g.drawString("DEMO MODE PRESS ANY KEY TO START A GAME\t High Score: " + highScore, 20, 10);
            else
                g.drawString("Score: " + (currScore) + "\t High Score: " + highScore, 20, 10);
        }

        /* oops is set to true when pacman has lost a life */
        boolean oops = false;

        /* Game initialization */
        if (New == 1) {
            reset();
            player = new PacMan();
            pinky = new Pinky();
            inky = new Inky();
            blinky = new Blinky();
            clyde = new Clyde();
            drawBoard(g);
            drawDots(g);
            drawLives(g);
            /* Send the game map to player and all ghosts */
            player.setState(state);
            /* Don't let the player go in the ghost box*/
            blinky.setState(state);
            pinky.setState(state);
            inky.setState(state);
            clyde.setState(state);

            /* Draw the top menu bar*/
            g.setColor(Color.YELLOW);
            g.setFont(font);
            if (demo)
                g.drawString("DEMO MODE PRESS ANY KEY TO START A GAME\t High Score: " + highScore, 20, 10);
            else
                g.drawString("Score: " + (currScore) + "\t High Score: " + highScore, 20, 10);
            New++;
        }
        /* Second frame of new game */
        else if (New == 2) {
            New++;
        }
        /* Third frame of new game */
        else if (New == 3) {
            New++;
            /* Play the newGame sound effect */
            sounds.newGame();
            timer = System.currentTimeMillis();
            return;
        }
        /* Fourth frame of new game */
        else if (New == 4) {
            /* Stay in this state until the sound effect is over */
            long currTime = System.currentTimeMillis();
            if (currTime - timer >= 5000) {
                New = 0;
            } else
                return;
        }

        /* Drawing optimization */
        g.copyArea(player.getX() - 20, player.getY() - 20, 80, 80, 0, 0);
        g.copyArea(blinky.getX() - 20, blinky.getY() - 20, 80, 80, 0, 0);
        g.copyArea(pinky.getX() - 20, pinky.getY() - 20, 80, 80, 0, 0);
        g.copyArea(inky.getX() - 20, inky.getY() - 20, 80, 80, 0, 0);
        g.copyArea(clyde.getX() - 20, clyde.getY() - 20, 80, 80, 0, 0);



        /* Detect collisions */
        if (player.getX() == blinky.getX() && Math.abs(player.getY() - blinky.getY()) < 10)
            oops = true;
        else if (player.getX() == pinky.getX() && Math.abs(player.getY() - pinky.getY()) < 10)
            oops = true;
        else if (player.getX() == inky.getX() && Math.abs(player.getY() - inky.getY()) < 10)
            oops = true;
        else if (player.getX() == clyde.getX() && Math.abs(player.getY() - clyde.getY()) < 10)
            oops = true;
        else if (player.getY() == blinky.getY() && Math.abs(player.getX() - blinky.getX()) < 10)
            oops = true;
        else if (player.getY() == pinky.getY() && Math.abs(player.getX() - pinky.getX()) < 10)
            oops = true;
        else if (player.getY() == inky.getY() && Math.abs(player.getX() - inky.getX()) < 10)
            oops = true;
        else if (player.getY() == clyde.getY() && Math.abs(player.getX() - clyde.getX()) < 10)
            oops = true;

        /* Kill the pacman */
        if (oops && !stopped) {
            /* 4 frames of death*/
            dying = 4;

            /* Play death sound effect */
            sounds.death();
            /* Stop any pacman eating sounds */
            sounds.nomNomStop();

            /*Decrement lives, update screen to reflect that.  And set appropriate flags and timers */
            numLives--;
            stopped = true;
            drawLives(g);
            timer = System.currentTimeMillis();
        }

        /* Delete the players and ghosts */
        g.setColor(Color.BLACK);
        g.fillRect(player.getLastX(), player.getLastY(), 20, 20);
        g.fillRect(blinky.getLastX(), blinky.getLastY(), 20, 20);
        g.fillRect(pinky.getLastX(), pinky.getLastY(), 20, 20);
        g.fillRect(inky.getLastX(), inky.getLastY(), 20, 20);
        g.fillRect(clyde.getLastX(), clyde.getLastY(), 20, 20);

        /* Eat dots */
        if (dots[player.getDotX()][player.getDotY()] && New != 2 && New != 3) {
            lastDotEatenX = player.getDotX();
            lastDotEatenY = player.getDotY();

            /* Play eating sound */
            sounds.nomNom();

            /* Increment dots eaten value to track for end game */
            player.eat();

            /* Delete the dot*/
            dots[player.getDotX()][player.getDotY()] = false;

            /* Increment the score */
            currScore += 50;

            /* Update the screen to reflect the new score */
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, 600, 20);
            g.setColor(Color.YELLOW);
            g.setFont(font);
            if (demo)
                g.drawString("DEMO MODE PRESS ANY KEY TO START A GAME\t High Score: " + highScore, 20, 10);
            else
                g.drawString("Score: " + (currScore) + "\t High Score: " + highScore, 20, 10);

            /* If this was the last dot */
            if (player.getDotsEaten() == 173) {
                /*Demo mode can't get a high score */
                if (!demo) {
                    winScreen = true;
                } else {
                    titleScreen = true;
                }
                return;
            }
        }

        /* If we moved to a location without dots, stop the sounds */
        else if ((player.getDotX() != lastDotEatenX || player.getDotY() != lastDotEatenY) || player.isStopped()) {
            /* Stop any pacman eating sounds */
            sounds.nomNomStop();
        }


        /* Replace dots that have been run over by ghosts */
        if (dots[blinky.getLastDotX()][blinky.getLastDotY()])
            fillDot(blinky.getLastDotX(), blinky.getLastDotY(), g);
        if (dots[pinky.getLastDotX()][pinky.getLastDotY()])
            fillDot(pinky.getLastDotX(), pinky.getLastDotY(), g);
        if (dots[inky.getLastDotX()][inky.getLastDotY()])
            fillDot(inky.getLastDotX(), inky.getLastDotY(), g);
        if (dots[clyde.getLastDotX()][clyde.getLastDotY()])
            fillDot(clyde.getLastDotX(), clyde.getLastDotY(), g);


        /*Draw the ghosts */
        if (blinky.frameCount < 5) {
            /* Draw first frame of ghosts */
            g.drawImage(ghost10, blinky.getX(), blinky.getY(), Color.BLACK, null);
            g.drawImage(ghost20, pinky.getX(), pinky.getY(), Color.BLACK, null);
            g.drawImage(ghost30, inky.getX(), inky.getY(), Color.BLACK, null);
            g.drawImage(ghost40, clyde.getX(), clyde.getY(), Color.BLACK, null);
            blinky.frameCount++;
        } else {
            /* Draw second frame of ghosts */
            g.drawImage(ghost11, blinky.getX(), blinky.getY(), Color.BLACK, null);
            g.drawImage(ghost21, pinky.getX(), pinky.getY(), Color.BLACK, null);
            g.drawImage(ghost31, inky.getX(), inky.getY(), Color.BLACK, null);
            g.drawImage(ghost41, clyde.getX(), clyde.getY(), Color.BLACK, null);
            if (blinky.frameCount >= 10)
                blinky.frameCount = 0;
            else
                blinky.frameCount++;
        }

        /* Draw the pacman */
        if (player.frameCount < 5) {
            /* Draw mouth closed */
            g.drawImage(pacmanImage, player.getX(), player.getY(), Color.BLACK, null);
        } else {
            /* Draw mouth open in appropriate direction */
            if (player.frameCount >= 10)
                player.frameCount = 0;

            switch (player.getDirection()) {
                case WEST:
                    g.drawImage(pacmanLeftImage, player.getX(), player.getY(), Color.BLACK, null);
                    break;
                case EAST:
                    g.drawImage(pacmanRightImage, player.getX(), player.getY(), Color.BLACK, null);
                    break;
                case NORTH:
                    g.drawImage(pacmanUpImage, player.getX(), player.getY(), Color.BLACK, null);
                    break;
                case SOUTH:
                    g.drawImage(pacmanDownImage, player.getX(), player.getY(), Color.BLACK, null);
                    break;
            }
        }

        /* Draw the border around the game in case it was overwritten by ghost movement or something */
        g.setColor(Color.WHITE);
        g.drawRect(19, 19, 382, 382);

    }
}
