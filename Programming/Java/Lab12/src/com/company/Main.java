package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


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
            DefaultListModel<String> elements1 = new DefaultListModel<>();
            DefaultListModel<String> elements2 = new DefaultListModel<>();
            elements1.add(elements1.getSize(), "aaa");
            elements1.add(elements1.getSize(), "bbb");
            elements1.add(elements1.getSize(), "ccc");
            elements1.add(elements1.getSize(), "ddd");
            elements1.add(elements1.getSize(), "eee");
            elements2.add(elements2.getSize(), "fff");
            elements2.add(elements2.getSize(), "ggg");
            elements2.add(elements2.getSize(), "iii");
            elements2.add(elements2.getSize(), "hhh");
            elements2.add(elements2.getSize(), "jjj");
            JList<String> list1 = new JList<>(elements1);
            task1.add(list1, BorderLayout.WEST);
            JList<String> list2 = new JList<>(elements2);
            list1.setPrototypeCellValue("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            list2.setPrototypeCellValue("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            task1.add(list2, BorderLayout.EAST);
            JPanel buttonPane = new JPanel(new BorderLayout());
            JButton toLeft = new JButton("<");
            JButton toRight = new JButton(">");
            toLeft.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    while (list2.getSelectedIndex() != -1) {
                        elements1.add(elements1.getSize(), list2.getSelectedValue());
                        elements2.remove(list2.getSelectedIndex());
                    }
                }
            });
            toRight.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    while (list1.getSelectedIndex() != -1) {
                        elements2.add(elements2.getSize(), list1.getSelectedValue());
                        elements1.remove(list1.getSelectedIndex());
                    }
                }
            });
            toLeft.setPreferredSize(new Dimension(50, 30));
            toRight.setPreferredSize(new Dimension(50, 30));
            buttonPane.setPreferredSize(new Dimension(100, this.getHeight()));
            buttonPane.add(toRight, BorderLayout.NORTH);
            buttonPane.add(toLeft, BorderLayout.SOUTH);
            task1.add(buttonPane, BorderLayout.CENTER);


            JPanel task2 = new JPanel();
            task2.setLayout(new GridLayout(6, 6));
            MouseListener mouseListener = new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    JButton cur = (JButton) e.getComponent();
                    if (MouseEvent.BUTTON1 == e.getButton()) {
                        buttonNumber = Integer.parseInt(cur.getText());
                        cur.setText("Clicked!");

                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    JButton cur = (JButton) e.getComponent();
                    if (MouseEvent.BUTTON1 == e.getButton()) {
                        cur.setText(Integer.toString(buttonNumber));
                        buttonNumber = 0;

                    }
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    JButton cur = (JButton) e.getComponent();
                    cur.setBackground(Color.MAGENTA);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    JButton cur = (JButton) e.getComponent();
                    cur.setBackground(Color.CYAN);
                }
            };
            for (int i = 0; i < 36; i++) {
                JButton button = new JButton(Integer.toString(i + 1));
                button.setBackground(Color.CYAN);
                button.addMouseListener(mouseListener);
                task2.add(button);
            }


            JPanel task3 = new JPanel();
            task3.setLayout(new GridLayout(5, 1));
            JRadioButton monday = new JRadioButton("Monday");
            JRadioButton tuesday = new JRadioButton("Tuesday");
            JRadioButton wednesday = new JRadioButton("Wednesday");
            JRadioButton thursday = new JRadioButton("Thursday");
            JRadioButton friday = new JRadioButton("Friday");
            JRadioButton[] buttonArray = {monday, tuesday, wednesday, thursday, friday};
            ImageIcon check = new ImageIcon(Toolkit.getDefaultToolkit().getImage("check.jpg"));
            ImageIcon cross = new ImageIcon(Toolkit.getDefaultToolkit().getImage("cross.jpg"));
            ImageIcon question = new ImageIcon(Toolkit.getDefaultToolkit().getImage("question.png"));
            ImageIcon hourglass = new ImageIcon(Toolkit.getDefaultToolkit().getImage("hourglass.png"));
            ButtonGroup week = new ButtonGroup();
            for (JRadioButton jRadioButton : buttonArray) {
                week.add(jRadioButton);
                jRadioButton.setSelectedIcon(check);
                jRadioButton.setDisabledIcon(cross);
                jRadioButton.setRolloverSelectedIcon(check);
                jRadioButton.setRolloverIcon(question);
                jRadioButton.setPressedIcon(hourglass);
                jRadioButton.setRolloverEnabled(true);
                jRadioButton.setDisabledSelectedIcon(check);
                jRadioButton.setIcon(cross);
                task3.add(jRadioButton);
            }
            buttonArray[0].setSelected(true);
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
