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
            toLeft.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    while (list2.getSelectedIndex() != -1) {
                        elements1.add(elements1.getSize(), list2.getSelectedValue());
                        elements2.remove(list2.getSelectedIndex());
                    }
                }
            });
            toRight.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
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
            for (int i = 0; i < 36; i++) {
                JButton button = new JButton(Integer.toString(i + 1));

                button.setBackground(Color.CYAN);
                button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        buttonNumber = Integer.parseInt(button.getText());
                        button.setText("Clicked!");
                        releaseButton = button;
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        releaseButton.setText(Integer.toString(buttonNumber));
                        buttonNumber = 0;
                        releaseButton = null;
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        button.setBackground(Color.MAGENTA);
                        if (buttonNumber != 0) {
                            buttonNumber = Integer.parseInt(button.getText());
                            button.setText("Clicked!");
                            releaseButton = button;
                        }
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        button.setBackground(Color.CYAN);
                        if (buttonNumber != 0) {
                            button.setText(Integer.toString(buttonNumber));
                        }
                    }
                });
                task2.add(button);
            }


            JPanel task3 = new JPanel();
            task3.setLayout(new GridLayout(5, 1));
            JRadioButton monday = new JRadioButton("Monday");
            JRadioButton tuesday = new JRadioButton("Tuesday");
            JRadioButton wednesday = new JRadioButton("Wednesday");
            JRadioButton thursday = new JRadioButton("Thursday");
            JRadioButton friday = new JRadioButton("Friday");
            ButtonGroup week = new ButtonGroup();
            week.add(monday);
            week.add(tuesday);
            week.add(wednesday);
            week.add(thursday);
            week.add(friday);
            monday.setSelected(true);
            prev = monday;
            monday.setIcon(check);
            tuesday.setIcon(cross);
            wednesday.setIcon(cross);
            thursday.setIcon(cross);
            friday.setIcon(cross);
            MouseListener radioListener = new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JRadioButton cur = (JRadioButton) e.getComponent();
                    prev.setIcon(cross);
                    cur.setIcon(check);
                    prev = cur;
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    JRadioButton cur = (JRadioButton) e.getComponent();
                    cur.setIcon(hourglass);
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    this.mouseClicked(e);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    JRadioButton cur = (JRadioButton) e.getComponent();
                    if (!cur.isSelected()) {
                        cur.setIcon(question);
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    JRadioButton cur = (JRadioButton) e.getComponent();
                    if (!cur.isSelected()) {
                        cur.setIcon(cross);
                    }
                }
            };
            monday.addMouseListener(radioListener);
            tuesday.addMouseListener(radioListener);
            wednesday.addMouseListener(radioListener);
            thursday.addMouseListener(radioListener);
            friday.addMouseListener(radioListener);
            task3.add(monday);
            task3.add(tuesday);
            task3.add(wednesday);
            task3.add(thursday);
            task3.add(friday);

            tabbedPane.add(task1, "Task1");
            tabbedPane.add(task2, "Task2");
            tabbedPane.add(task3, "Task3");
            Container container = this.getContentPane();
            container.add(tabbedPane);
        }

        private int buttonNumber;
        private JButton releaseButton;
        private JRadioButton prev = null;
        private ImageIcon check = new ImageIcon(Toolkit.getDefaultToolkit().getImage("check.jpg"));
        private ImageIcon cross = new ImageIcon(Toolkit.getDefaultToolkit().getImage("cross.jpg"));
        private ImageIcon hourglass = new ImageIcon(Toolkit.getDefaultToolkit().getImage("hourglass.png"));
        private ImageIcon question = new ImageIcon(Toolkit.getDefaultToolkit().getImage("question.png"));

    }

    public static void main(String[] args) {
        Window w = new Window();
        w.setVisible(true);
    }
}
