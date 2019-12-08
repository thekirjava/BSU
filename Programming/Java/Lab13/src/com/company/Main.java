package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    static StudentCollection<Student> studentCollection = new StudentCollection<>();

    public static class Window extends JFrame {
        Window() {
            this.setBounds(30, 30, 1000, 800);
            this.setTitle("Lab13");
            this.setDefaultCloseOperation(EXIT_ON_CLOSE);
            this.setResizable(false);
            JMenuBar menuBar = new JMenuBar();
            JMenu menu = new JMenu("Файл");
            JMenuItem open = new JMenuItem("Открыть");
            menu.add(open);
            menuBar.add(menu);
            open.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFileChooser fileChooser = new JFileChooser();
                    if (fileChooser.showDialog(Window.this, "Open") == JFileChooser.APPROVE_OPTION) {
                        try {
                            Scanner s = new Scanner(fileChooser.getSelectedFile());
                            while (s.hasNext()) {
                                StringTokenizer stringTokenizer = new StringTokenizer(s.nextLine());
                                String name = stringTokenizer.nextToken();
                                String id = stringTokenizer.nextToken();
                                ArrayList<Student.Exam> exams = new ArrayList<>();
                                while (stringTokenizer.hasMoreTokens()) {
                                    exams.add(new Student.Exam(stringTokenizer.nextToken(), Integer.parseInt(stringTokenizer.nextToken()), Integer.parseInt(stringTokenizer.nextToken())));
                                }
                                studentCollection.add(new Student(name, id, exams));
                            }
                        } catch (FileNotFoundException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });
            this.setJMenuBar(menuBar);
            Container container = this.getContentPane();
            container.setLayout(null);
            JTextField textField = new JTextField();
            JLabel label = new JLabel("Поле для ввода");
            JButton button = new JButton("Старт");
            textField.setBounds(400, 385, 200, 40);
            label.setBounds(400, 345, 200, 40);
            button.setBounds(450, 425, 100, 30);
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        ArrayList ans = new ArrayList<>();
                        if (studentCollection.size() == 0) {
                            throw new EmptyCollectionException();
                        }
                        ans = studentCollection.notPassed(textField.getText());
                        Iterator iterator = ans.iterator();
                        StringBuffer out = new StringBuffer();
                        while (iterator.hasNext()) {
                            out.append(iterator.next());
                            out.append('\n');
                        }
                        optionPane.showMessageDialog(Window.this, out, "Answer", JOptionPane.INFORMATION_MESSAGE);
                    } catch (NoSuchElementException ex) {
                        optionPane.showMessageDialog(Window.this, "Text field is empty or input is incorrect", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    catch (EmptyCollectionException ex) {
                        optionPane.showMessageDialog(Window.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

                    }
                }
            });
            container.add(label);
            container.add(textField);
            container.add(button);
        }

        JOptionPane optionPane = new JOptionPane();
    }

    public static void main(String[] args) {
        Window window = new Window();
        window.setVisible(true);
    }
}
