package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Main {

    static class Window extends JFrame {
        Window() {
            setBounds(30, 30, 800, 500);
            setTitle("Lab10_2");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setResizable(false);
            questionField = new JLabel("Do you find this task original?");
            questionField.setFont(new Font("Georgia", Font.PLAIN, 20));
            stillButton = new JButton("No");
            runningButton = new JButton("Yes");
            out = new JOptionPane();
            Container container = getContentPane();
            container.setLayout(null);
            questionField.setBounds(250, 150, 300, 30);
            stillButton.setBounds(420, 200, 120, 40);
            runningButton.setBounds(250, 200, 120, 40);
            stillButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    out.showMessageDialog(Window.this, "And it is pretty obvious", "Lab10_2", JOptionPane.INFORMATION_MESSAGE);
                }
            });
            runningButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    int deltaX, deltaY;
                    if (e.getX() >= runningButton.getWidth() / 2) {
                        deltaX = e.getX() - runningButton.getWidth();
                        deltaX -= 5;
                    } else {
                        deltaX = e.getX() + 5;
                    }
                    if (e.getY() >= runningButton.getHeight() / 20) {
                        deltaY = e.getY() - runningButton.getHeight();
                        deltaY -= 5;
                    } else {
                        deltaY = e.getY() + 5;
                    }
                    int x = runningButton.getX() + deltaX;
                    int y = runningButton.getY() + deltaY;
                    if (x < 0) {
                        x = (runningButton.getX() + e.getX()) + 10;
                    }
                    if (x + runningButton.getWidth() >= Window.this.getWidth()) {
                        x = (runningButton.getX() + e.getX()) - runningButton.getWidth() - 10;
                    }
                    if (y < 0) {
                        y = (runningButton.getY() + e.getY()) + 10;
                    }
                    if (y + runningButton.getHeight() >= Window.this.getHeight()) {
                        y = (runningButton.getY() + e.getY()) - runningButton.getHeight() - 10;
                    }
                    runningButton.setLocation(x, y);
                }
            });
            container.add(questionField);
            container.add(stillButton);
            container.add(runningButton);
        }

        private JLabel questionField;
        private JButton stillButton;
        private JButton runningButton;
        private JOptionPane out;
    }

    public static void main(String[] args) {
        Window w = new Window();
        w.setVisible(true);
    }
}
