package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Main {

    public static class Window extends JFrame {
        Window() {
            this.setBounds(10, 10, 1000, 700);
            this.setTitle("Lab12");
            this.setResizable(false);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JTabbedPane tabbedPane = new JTabbedPane();

            JPanel task1 = new JPanel();
            task1.setLayout(new BorderLayout());
            String[] elements = new String[]{"aaa", "bbbb", "ccccc", "dddddd", "eeeee"};
            JList<String> list1 = new JList<>(elements);
            task1.add(list1, BorderLayout.WEST);
            elements = new String[]{"fff", "ggggggggg", "hhhhhhhh", "iiiiiiii", "jjjjjjj"};
            JList<String> list2 = new JList<>(elements);
            task1.add(list2, BorderLayout.EAST);
            JPanel buttonPane = new JPanel(new BorderLayout());
            JButton toLeft = new JButton("<");
            JButton toRight = new JButton(">");
            buttonPane.add(toRight, BorderLayout.NORTH);
            buttonPane.add(toLeft, BorderLayout.SOUTH);
            task1.add(buttonPane, BorderLayout.CENTER);

            JPanel task2 = new JPanel();
            task2.setLayout(new GridLayout(6, 6));

            for (int i = 0; i < 36; i++) {
                JButton button = new JButton(Integer.toString(i + 1));
                button.setBackground(Color.CYAN);
                button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        buttonNumber = Integer.parseInt(button.getText());
                        button.setText("Clicked!");
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        button.setText(Integer.toString(buttonNumber));
                        buttonNumber = 0;
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        button.setBackground(Color.MAGENTA);
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        button.setBackground(Color.CYAN);
                    }
                });
                task2.add(button);
            }
            JPanel task3 = new JPanel();
            tabbedPane.add(task1, "Task1");
            tabbedPane.add(task2, "Task2");
            tabbedPane.add(task3, "Task3");
            Container container = this.getContentPane();
            container.add(tabbedPane);
        }

        private int buttonNumber;
    }

    public static void main(String[] args) {
        Window w = new Window();
        w.setVisible(true);
    }
}
