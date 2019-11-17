package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;;
import java.io.IOException;

public class Main {

    static class Window extends JFrame {
        Window() {
            this.setBounds(100, 100, 900, 500);
            this.setTitle("Lab11");
            this.setDefaultCloseOperation(EXIT_ON_CLOSE);
            Container cont = this.getContentPane();
            cont.setLayout(new BorderLayout());
            drawSpace = new DrawingPanel(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
            scrollPane = new JScrollPane(drawSpace);
            scrollPane.setPreferredSize(new Dimension(this.getWidth() - 100, this.getHeight() - 200));
            buttonPanel = new JPanel(new FlowLayout());
            redButton = new JButton("Chosen");
            greenButton = new JButton();
            blueButton = new JButton();
            saveButton = new JButton("Save");
            loadButton = new JButton("Load");
            error = new JOptionPane();
            redButton.setBackground(Color.RED);
            greenButton.setBackground(Color.GREEN);
            blueButton.setBackground(Color.BLUE);
            penColor = Color.RED;
            redButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    penColor = Color.RED;
                    redButton.setText("Chosen");
                    greenButton.setText("");
                    blueButton.setText("");
                }
            });
            greenButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    penColor = Color.GREEN;
                    redButton.setText("");
                    greenButton.setText("Chosen");
                    blueButton.setText("");
                }
            });
            blueButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    penColor = Color.BLUE;
                    redButton.setText("");
                    greenButton.setText("");
                    blueButton.setText("Chosen");
                }
            });
            saveButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    fileChooser = new JFileChooser(".");
                    if (fileChooser.showDialog(Window.this, "Save") == JFileChooser.APPROVE_OPTION) {
                        try {
                            ImageIO.write(drawSpace.getBuffer(), "png", fileChooser.getSelectedFile());
                        } catch (IOException exc) {
                            error.showMessageDialog(Window.this, "File doesn't exist", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            });
            loadButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    fileChooser = new JFileChooser(".");
                    if (fileChooser.showDialog(Window.this, "Load") == JFileChooser.APPROVE_OPTION) {
                        try {
                            drawSpace.loadImage(ImageIO.read(fileChooser.getSelectedFile()));
                        } catch (IOException exc) {
                            error.showMessageDialog(Window.this, "File doesn't exist", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            });
            buttonPanel.add(redButton);
            buttonPanel.add(greenButton);
            buttonPanel.add(blueButton);
            buttonPanel.add(saveButton);
            buttonPanel.add(loadButton);
            drawSpace.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent mouseEvent) {
                    Graphics g = drawSpace.getGraphics();
                    Graphics gbuf = drawSpace.getBuffer().getGraphics();
                    g.setColor(penColor);
                    gbuf.setColor(penColor);
                    g.fillRect(mouseEvent.getX(), mouseEvent.getY(), 1, 1);
                    gbuf.fillRect(mouseEvent.getX(), mouseEvent.getY(), 1, 1);
                }

                @Override
                public void mousePressed(MouseEvent mouseEvent) {
                    previousX = mouseEvent.getX();
                    previousY = mouseEvent.getY();
                }

                @Override
                public void mouseReleased(MouseEvent mouseEvent) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            });
            drawSpace.addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    Graphics g = drawSpace.getGraphics();
                    Graphics gbuf = drawSpace.getBuffer().getGraphics();
                    g.setColor(penColor);
                    gbuf.setColor(penColor);
                    g.drawLine(previousX, previousY, e.getX(), e.getY());
                    gbuf.drawLine(previousX, previousY, e.getX(), e.getY());
                    previousX = e.getX();
                    previousY = e.getY();
                }
            });
            cont.add(scrollPane, BorderLayout.CENTER);
            cont.add(buttonPanel, BorderLayout.SOUTH);
        }

        private DrawingPanel drawSpace;
        private JPanel buttonPanel;
        private JButton redButton;
        private JButton greenButton;
        private JButton blueButton;
        private JButton saveButton;
        private JButton loadButton;
        private Color penColor;
        private int previousX;
        private int previousY;
        private JScrollPane scrollPane;
        private JFileChooser fileChooser;
        private JOptionPane error;

    }

    public static void main(String[] args) {
        Window w = new Window();
        w.setVisible(true);
    }
}
