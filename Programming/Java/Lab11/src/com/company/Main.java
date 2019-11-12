package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Main {

    static class Window extends JFrame {
        Window() {
            this.setBounds(100, 100, 900, 500);
            this.setTitle("Lab11");
            this.setDefaultCloseOperation(EXIT_ON_CLOSE);
            Container cont = this.getContentPane();
            cont.setLayout(new BorderLayout());
            drawSpace = new DrawingPanel(this.getWidth(), this.getHeight());
            colorPanel = new JPanel(new FlowLayout());
            redButton = new JButton();
            greenButton = new JButton();
            blueButton = new JButton();
            penColor = Color.RED;
            this.repaint();
            redButton.setBackground(Color.RED);

            greenButton.setBackground(Color.GREEN);

            blueButton.setBackground(Color.BLUE);
            redButton.setPreferredSize(new Dimension(this.getWidth()/4, 30));
            greenButton.setPreferredSize(new Dimension(this.getWidth()/4, 30));
            blueButton.setPreferredSize(new Dimension(this.getWidth()/4, 30));
            colorPanel.add(redButton);
            colorPanel.add(greenButton);
            colorPanel.add(blueButton);
            drawSpace.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }

                @Override
                public void mouseDragged(MouseEvent e) {
                    Graphics g = drawSpace.getGraphics();
                    Graphics gbuf = drawSpace.getBuffer().getGraphics();
                    g.setColor(penColor);
                    gbuf.setColor(penColor);
                    g.fillRect(e.getX(), e.getY(), 1, 1);
                    gbuf.fillRect(e.getX(), e.getY(), 1, 1);
                }
            });
            cont.add(drawSpace, BorderLayout.CENTER);
            cont.add(colorPanel, BorderLayout.SOUTH);
        }
        private DrawingPanel drawSpace;
        private JPanel colorPanel;
        private JButton redButton;
        private JButton greenButton;
        private JButton blueButton;
        private Color penColor;
    }

    public static void main(String[] args) {
        Window w = new Window();
        w.setVisible(true);
    }
}
