package bsu.fpmi.artsiushkevich.window;

import bsu.fpmi.artsiushkevich.characters.*;
import bsu.fpmi.artsiushkevich.observer.Observable;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Board extends JPanel {
    Observable observable = new Observable();
    Image pacmanImage = ImageIO.read(new File("img/pacman.jpg"));
    Image pacmanUpImage = ImageIO.read(new File("img/pacmanup.jpg"));
    Image pacmanDownImage = ImageIO.read(new File("img/pacmandown.jpg"));
    Image pacmanLeftImage = ImageIO.read(new File("img/pacmanleft.jpg"));
    Image pacmanRightImage = ImageIO.read(new File("img/pacmanright.jpg"));
    Image ghost10 = ImageIO.read(new File("img/ghost10.jpg"));
    Image ghost20 = ImageIO.read(new File("img/ghost20.jpg"));
    Image ghost30 = ImageIO.read(new File("img/ghost30.jpg"));
    Image ghost40 = ImageIO.read(new File("img/ghost40.jpg"));
    Image ghost11 = ImageIO.read(new File("img/ghost11.jpg"));
    Image ghost21 = ImageIO.read(new File("img/ghost21.jpg"));
    Image ghost31 = ImageIO.read(new File("img/ghost31.jpg"));
    Image ghost41 = ImageIO.read(new File("img/ghost41.jpg"));
    Image titleScreenImage = ImageIO.read(new File("img/titleScreen.jpg"));
    Image gameOverImage = ImageIO.read(new File("img/gameOver.jpg"));
    Image winScreenImage = ImageIO.read(new File("img/winScreen.jpg"));
    PacMan player;
    Blinky blinky;
    Pinky pinky;
    Inky inky;
    Clyde clyde;
    long timer = System.currentTimeMillis();
    int dying = 0;
    int currScore;
    int highScore;
    boolean clearHighScores = false;
    int numLives = 2;
    boolean[][] state;
    boolean[][] dots;
    final int GRID_SIZE = 20;
    final int MAX = 400;
    boolean stopped;
    boolean titleScreen;
    boolean winScreen = false;
    boolean overScreen = false;
    int New;
    Sound sounds;
    int lastDotEatenX = 0;
    int lastDotEatenY = 0;
    Font font = new Font("Monospaced", Font.BOLD, 12);

    public Board() throws IOException {
        initHighScores();
        sounds = new Sound();
        currScore = 0;
        stopped = false;
        New = 0;
        titleScreen = true;
        player = new PacMan(200, 300);
        blinky = new Blinky(180, 180, player);
        pinky = new Pinky(200, 180, player);
        inky = new Inky(220, 180, player, blinky);
        clyde = new Clyde(220, 180, player);
        observable.addObserver(player);
        observable.addObserver(blinky);
        observable.addObserver(pinky);
        observable.addObserver(inky);
        observable.addObserver(clyde);
    }

    public void updateScore(int score) {
        try {
            PrintWriter out;
            out = new PrintWriter("highScores.txt");
            out.println(score);
            out.close();
            highScore = score;
            clearHighScores = true;
        } catch (FileNotFoundException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void initHighScores() {
        File file = new File("highScores.txt");
        Scanner sc;
        try {
            sc = new Scanner(file);
            highScore = sc.nextInt();
            sc.close();
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    public void newGame() {
        New = 1;
    }

    public void reset() {
        numLives = 2;
        state = new boolean[20][20];
        dots = new boolean[20][20];

        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                state[i][j] = true;
                dots[i][j] = true;
            }
        }
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

    public void updateMap(int x, int y, int width, int height) {
        for (int i = x / GRID_SIZE; i < x / GRID_SIZE + width / GRID_SIZE; i++) {
            for (int j = y / GRID_SIZE; j < y / GRID_SIZE + height / GRID_SIZE; j++) {
                state[i - 1][j - 1] = false;
                dots[i - 1][j - 1] = false;
            }
        }
    }


    public void drawLives(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, MAX + 5, 600, GRID_SIZE);
        g.setColor(Color.YELLOW);
        for (int i = 0; i < numLives; i++) {
            g.fillOval(GRID_SIZE * (i + 1), MAX + 5, GRID_SIZE, GRID_SIZE);
        }
        g.setColor(Color.YELLOW);
        g.setFont(font);
        g.drawString("Reset", 100, MAX + 5 + GRID_SIZE);
        g.drawString("Clear High Scores", 180, MAX + 5 + GRID_SIZE);
        g.drawString("Exit", 350, MAX + 5 + GRID_SIZE);
    }

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

    public void drawDots(Graphics g) {
        g.setColor(Color.YELLOW);
        for (int i = 1; i < 20; i++) {
            for (int j = 1; j < 20; j++) {
                if (dots[i - 1][j - 1])
                    g.fillOval(i * 20 + 8, j * 20 + 8, 4, 4);
            }
        }
    }

    public void fillDot(int x, int y, Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillOval(x * 20 + 28, y * 20 + 28, 4, 4);
    }

    public void paint(Graphics g) {
        if (dying > 0) {
            sounds.nomNomStop();
            g.drawImage(pacmanImage, player.getX(), player.getY(), Color.BLACK, null);
            g.setColor(Color.BLACK);
            if (dying == 4) {
                g.fillRect(player.getX(), player.getY(), 20, 7);
            } else if (dying == 3) {
                g.fillRect(player.getX(), player.getY(), 20, 14);
            } else if (dying == 2) {
                g.fillRect(player.getX(), player.getY(), 20, 20);
            } else if (dying == 1) {
                g.fillRect(player.getX(), player.getY(), 20, 20);
            }
            long currTime = System.currentTimeMillis();
            long temp;
            if (dying != 1) {
                temp = 100;
            } else {
                temp = 2000;
            }
            if (currTime - timer >= temp) {
                dying--;
                timer = currTime;
                if (dying == 0) {
                    if (numLives == -1) {
                        if (currScore > highScore) {
                            updateScore(currScore);
                        }
                        overScreen = true;
                    }
                }
            }
            return;
        }
        if (titleScreen) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, 600, 600);
            g.drawImage(titleScreenImage, 0, 0, Color.BLACK, null);
            sounds.nomNomStop();
            New = 1;
            return;
        } else if (winScreen) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, 600, 600);
            g.drawImage(winScreenImage, 0, 0, Color.BLACK, null);
            New = 1;
            sounds.nomNomStop();
            return;
        } else if (overScreen) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, 600, 600);
            g.drawImage(gameOverImage, 0, 0, Color.BLACK, null);
            New = 1;
            sounds.nomNomStop();
            return;
        }
        if (clearHighScores) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, 600, 18);
            g.setColor(Color.YELLOW);
            g.setFont(font);
            clearHighScores = false;
            g.drawString("Score: " + (currScore) + "\t High Score: " + highScore, 20, 10);
        }
        boolean oops = false;
        if (New == 1) {
            reset();
            player = new PacMan(200, 300);
            blinky = new Blinky(180, 180, player);
            pinky = new Pinky(200, 180, player);
            inky = new Inky(220, 180, player, blinky);
            clyde = new Clyde(220, 180, player);
            observable = new Observable();
            observable.addObserver(player);
            observable.addObserver(blinky);
            observable.addObserver(pinky);
            observable.addObserver(inky);
            observable.addObserver(clyde);
            currScore = 0;
            drawBoard(g);
            drawDots(g);
            drawLives(g);
            player.setState(state);
            blinky.setState(state);
            pinky.setState(state);
            inky.setState(state);
            clyde.setState(state);
            g.setColor(Color.YELLOW);
            g.setFont(font);
            g.drawString("Score: " + (currScore) + "\t High Score: " + highScore, 20, 10);
            New++;
        } else if (New == 2) {
            New++;
        } else if (New == 3) {
            New++;
            sounds.newGame();
            timer = System.currentTimeMillis();
            return;
        } else if (New == 4) {
            long currTime = System.currentTimeMillis();
            if (currTime - timer >= 5000) {
                New = 0;
            } else {
                return;
            }
        }
        g.copyArea(player.getX() - 20, player.getY() - 20, 80, 80, 0, 0);
        g.copyArea(blinky.getX() - 20, blinky.getY() - 20, 80, 80, 0, 0);
        g.copyArea(pinky.getX() - 20, pinky.getY() - 20, 80, 80, 0, 0);
        g.copyArea(inky.getX() - 20, inky.getY() - 20, 80, 80, 0, 0);
        g.copyArea(clyde.getX() - 20, clyde.getY() - 20, 80, 80, 0, 0);
        if ((player.getX() == blinky.getX()) && (Math.abs(player.getY() - blinky.getY()) < 10)) {
            oops = true;
        } else if ((player.getX() == pinky.getX()) && (Math.abs(player.getY() - pinky.getY()) < 10)) {
            oops = true;
        } else if ((player.getX() == inky.getX()) && (Math.abs(player.getY() - inky.getY()) < 10)) {
            oops = true;
        } else if ((player.getX() == clyde.getX()) && (Math.abs(player.getY() - clyde.getY()) < 10)) {
            oops = true;
        } else if (player.getY() == blinky.getY() && Math.abs(player.getX() - blinky.getX()) < 10) {
            oops = true;
        } else if (player.getY() == pinky.getY() && Math.abs(player.getX() - pinky.getX()) < 10) {
            oops = true;
        } else if (player.getY() == inky.getY() && Math.abs(player.getX() - inky.getX()) < 10) {
            oops = true;
        } else if (player.getY() == clyde.getY() && Math.abs(player.getX() - clyde.getX()) < 10) {
            oops = true;
        }
        if (oops && !stopped) {
            dying = 4;
            sounds.death();
            sounds.nomNomStop();
            numLives--;
            stopped = true;
            drawLives(g);
            timer = System.currentTimeMillis();
        }
        g.setColor(Color.BLACK);
        g.fillRect(player.getLastX(), player.getLastY(), 20, 20);
        g.fillRect(blinky.getLastX(), blinky.getLastY(), 20, 20);
        g.fillRect(pinky.getLastX(), pinky.getLastY(), 20, 20);
        g.fillRect(inky.getLastX(), inky.getLastY(), 20, 20);
        g.fillRect(clyde.getLastX(), clyde.getLastY(), 20, 20);
        if (dots[player.getDotX()][player.getDotY()] && (New != 2) && (New != 3)) {
            lastDotEatenX = player.getDotX();
            lastDotEatenY = player.getDotY();
            sounds.nomNom();
            player.eat();
            dots[player.getDotX()][player.getDotY()] = false;
            currScore += 50;
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, 600, 20);
            g.setColor(Color.YELLOW);
            g.setFont(font);
            g.drawString("Score: " + (currScore) + "\t High Score: " + highScore, 20, 10);
            if (player.getDotsEaten() == 173) {
                if (currScore > highScore) {
                    updateScore(currScore);
                }
                winScreen = true;
                return;
            }
        } else if (((player.getDotX() != lastDotEatenX) || (player.getDotY() != lastDotEatenY)) || player.isStopped()) {
            sounds.nomNomStop();
        }
        if (dots[blinky.getLastDotX()][blinky.getLastDotY()]) {
            fillDot(blinky.getLastDotX(), blinky.getLastDotY(), g);
        }
        if (dots[pinky.getLastDotX()][pinky.getLastDotY()]) {
            fillDot(pinky.getLastDotX(), pinky.getLastDotY(), g);
        }
        if (dots[inky.getLastDotX()][inky.getLastDotY()]) {
            fillDot(inky.getLastDotX(), inky.getLastDotY(), g);
        }
        if (dots[clyde.getLastDotX()][clyde.getLastDotY()]) {
            fillDot(clyde.getLastDotX(), clyde.getLastDotY(), g);
        }
        if (blinky.frameCount < 5) {
            ghostFrame(g, ghost10, ghost20, ghost30, ghost40);
            blinky.frameCount++;
        } else {
            ghostFrame(g, ghost11, ghost21, ghost31, ghost41);
            if (blinky.frameCount >= 10) {
                blinky.frameCount = 0;
            } else {
                blinky.frameCount++;
            }
        }
        if (player.frameCount < 5) {
            g.drawImage(pacmanImage, player.getX(), player.getY(), Color.BLACK, null);
        } else {
            if (player.frameCount >= 10) {
                player.frameCount = 0;
            }
            switch (player.getDirection()) {
                case LEFT:
                    g.drawImage(pacmanLeftImage, player.getX(), player.getY(), Color.BLACK, null);
                    break;
                case RIGHT:
                    g.drawImage(pacmanRightImage, player.getX(), player.getY(), Color.BLACK, null);
                    break;
                case UP:
                    g.drawImage(pacmanUpImage, player.getX(), player.getY(), Color.BLACK, null);
                    break;
                case DOWN:
                    g.drawImage(pacmanDownImage, player.getX(), player.getY(), Color.BLACK, null);
                    break;
            }
        }
        g.setColor(Color.WHITE);
        g.drawRect(19, 19, 382, 382);
    }

    private void ghostFrame(Graphics g, Image ghost10, Image ghost20, Image ghost30, Image ghost40) {
        g.drawImage(ghost10, blinky.getX(), blinky.getY(), Color.BLACK, null);
        g.drawImage(ghost20, pinky.getX(), pinky.getY(), Color.BLACK, null);
        g.drawImage(ghost30, inky.getX(), inky.getY(), Color.BLACK, null);
        g.drawImage(ghost40, clyde.getX(), clyde.getY(), Color.BLACK, null);
    }
}
