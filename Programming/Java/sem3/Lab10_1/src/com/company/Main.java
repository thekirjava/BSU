package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main {

    static class Window extends JFrame {
        Window() {
            setBounds(30, 30, 800, 500);
            setTitle("Lab10_1");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //setResizable(false);
            Container cont = this.getContentPane();
            cont.setLayout(new BorderLayout());
            but = new JButton();
            position = new JTextField();
            KeyListener buttonUpdater = new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent keyEvent) {
                    StringBuffer sb = new StringBuffer(but.getText());
                    if (keyEvent.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
                        if (sb.length() != 0) {
                            sb.deleteCharAt(sb.length() - 1);
                            but.setBounds(but.getX() + 4, but.getY(), but.getWidth() - 8, but.getHeight());
                        }
                    } else {
                        sb.append(keyEvent.getKeyChar());
                        but.setBounds(but.getX() - 4, but.getY(), but.getWidth() + 8, but.getHeight());
                    }
                    but.setText(sb.toString());
                }
            };
            cont.addKeyListener(buttonUpdater);
            but.addKeyListener(buttonUpdater);
            position.addKeyListener(buttonUpdater);
            cont.addMouseMotionListener(new MouseMotionListener() {
                @Override
                public void mouseDragged(MouseEvent mouseEvent) {
                    position.setText(mouseEvent.getX() + " " + mouseEvent.getY());
                }

                @Override
                public void mouseMoved(MouseEvent mouseEvent) {
                    position.setText(mouseEvent.getX() + " " + mouseEvent.getY());
                }
            });
            cont.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent mouseEvent) {
                    but.setLocation(mouseEvent.getX(), mouseEvent.getY());
                }
            });
            JPanel field = new JPanel(null);
            but.setBounds(380, 235, 40, 30);
            but.addMouseMotionListener(new MouseMotionListener() {
                @Override
                public void mouseDragged(MouseEvent mouseEvent) {
                    if (mouseEvent.isControlDown()) {
                        but.setLocation(mouseEvent.getX() + Window.this.but.getX(), mouseEvent.getY() + Window.this.but.getY());
                    }
                    position.setText((mouseEvent.getX() + Window.this.but.getX()) + " " + (mouseEvent.getY() + Window.this.but.getY()));
                }

                @Override
                public void mouseMoved(MouseEvent mouseEvent) {
                    position.setText((mouseEvent.getX() + Window.this.but.getX()) + " " + (mouseEvent.getY() + Window.this.but.getY()));
                }
            });
            field.add(but);
            position.setEditable(false);
            position.addMouseMotionListener(new MouseMotionListener() {
                @Override
                public void mouseDragged(MouseEvent mouseEvent) {
                    position.setText((mouseEvent.getX() + Window.this.position.getX()) + " " + (mouseEvent.getY() + Window.this.position.getY()));
                }

                @Override
                public void mouseMoved(MouseEvent mouseEvent) {
                    position.setText((mouseEvent.getX() + Window.this.position.getX()) + " " + (mouseEvent.getY() + Window.this.position.getY()));
                }
            });
            cont.add(field, BorderLayout.CENTER);
            cont.add(position, BorderLayout.SOUTH);
        }

        private JButton but;
        private JTextField position;
    }

    public static void main(String[] args) {
        Window w = new Window();
        w.setVisible(true);
    }
}
