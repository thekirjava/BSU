package bsu.fpmi.artsiushkevich.window;

import bsu.fpmi.artsiushkevich.characters.Direction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
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

    public MainWindow() throws IOException {
        this.setSize(720, 760);
        this.setResizable(false);
        this.setTitle("PacMan");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = this.getContentPane();
        container.setLayout(new BorderLayout());
        b = new Board();
        b.requestFocus();
        container.add(b, BorderLayout.CENTER);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (b.titleScreen || b.winScreen || b.overScreen) {
                    return;
                }
                int x = e.getX();
                int y = e.getY();
                if (400 <= y && y <= 460) {
                    if (100 <= x && x <= 150) {
                        b.New = 1;
                    } else if (350 <= x && x <= 420) {
                        System.exit(0);
                    }
                }
            }
        });
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (b.titleScreen) {
                    b.titleScreen = false;
                    return;
                } else if (b.winScreen || b.overScreen) {
                    b.titleScreen = true;
                    b.winScreen = false;
                    b.overScreen = false;
                    return;
                }
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        b.player.setDesiredDirection(Direction.LEFT);
                        break;
                    case KeyEvent.VK_RIGHT:
                        b.player.setDesiredDirection(Direction.RIGHT);
                        break;
                    case KeyEvent.VK_UP:
                        b.player.setDesiredDirection(Direction.UP);
                        break;
                    case KeyEvent.VK_DOWN:
                        b.player.setDesiredDirection(Direction.DOWN);
                        break;
                }
                repaint();
            }
        });
        b.newGame();
        stepFrame(true);
        frameTimer.schedule(update, 30, 30);
        b.requestFocus();
    }

    public void stepFrame(boolean New) {
        if (!b.titleScreen && !b.winScreen && !b.overScreen) {
            pseudoTimer = -1;
        }
        if (b.dying > 0) {
            b.repaint();
            return;
        }
        New = New || (b.New != 0);
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
        if (!New) {
            b.observable.notifyObservers("Move");
        }
        if (b.stopped || New) {
            frameTimer.cancel();
            while (b.dying > 0) {
                stepFrame(false);
            }
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
            b.repaint(0, 0, 600, 600);
            b.stopped = false;
            frameTimer = new Timer();
            frameTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    stepFrame(false);
                }
            }, 30, 30);
        } else {
            repaint();
        }
    }

    @Override
    public void repaint() {
        if (b.player.isTeleport()) {
            b.repaint(b.player.getLastX() - 20, b.player.getLastY() - 20, 80, 80);
            b.player.setTeleport(false);
        }
        b.repaint(0, 0, 600, 20);
        b.repaint(0, 420, 600, 40);
    }

    private long pseudoTimer = -1;

}
