package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    static StudentMap<String, Student> studentCollection = new StudentMap<>();

    public static class Window extends JFrame {
        Window() {
            this.setBounds(30, 30, 1000, 800);
            this.setTitle("Lab13");
            this.setDefaultCloseOperation(EXIT_ON_CLOSE);
            this.setResizable(false);
            JMenuBar menuBar = new JMenuBar();
            JMenu file = new JMenu("Файл");
            JMenu data = new JMenu("Данные");
            JMenuItem open = new JMenuItem("Открыть");
            JMenuItem add = new JMenuItem("Добавить");
            JMenuItem search = new JMenuItem("Искать");
            Container container = this.getContentPane();
            DefaultListModel<String> listModel = new DefaultListModel<>();

            file.add(open);
            data.add(add);
            data.add(search);
            menuBar.add(file);
            menuBar.add(data);
            open.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File("."));
                    if (fileChooser.showDialog(Window.this, "Open") == JFileChooser.APPROVE_OPTION) {
                        try {
                            Scanner s = new Scanner(fileChooser.getSelectedFile());
                            studentCollection.clear();
                            listModel.clear();
                            while (s.hasNext()) {
                                String str = s.nextLine();
                                studentCollection.add(str);
                                listModel.add(listModel.getSize(), str);
                                validate();
                            }
                        } catch (FileNotFoundException ex) {
                            optionPane.showMessageDialog(Window.this, "File doesn't exist", "Error", JOptionPane.ERROR_MESSAGE);
                        } catch (NoSuchElementException ex) {
                            optionPane.showMessageDialog(Window.this, "Wrong input format", "Error", JOptionPane.ERROR_MESSAGE);
                        } catch (NumberFormatException ex) {
                            optionPane.showMessageDialog(Window.this, "Grade and semester must be integer", "Error", JOptionPane.ERROR_MESSAGE);
                        } catch (WrongIdException ex) {
                            optionPane.showMessageDialog(Window.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            });

            add.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        String s = optionPane.showInputDialog(Window.this, "Input data as: \"id student_name exam_semester exam_name grade\"", "");
                        if (s != null) {
                            studentCollection.add(s);
                            listModel.add(listModel.getSize(), s);
                            validate();
                        }
                    } catch (NumberFormatException ex) {
                        optionPane.showMessageDialog(Window.this, "Grade and semester must be integer", "Error", JOptionPane.ERROR_MESSAGE);
                    } catch (NoSuchElementException ex) {
                        optionPane.showMessageDialog(Window.this, "Wrong input format", "Error", JOptionPane.ERROR_MESSAGE);
                    } catch (WrongIdException ex) {
                        optionPane.showMessageDialog(Window.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            search.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String s = optionPane.showInputDialog(Window.this, "Input data as: \"semester exam1 exam 2 ...\"", "");
                    try {
                        ArrayList ans;
                        if (studentCollection.size() == 0) {
                            throw new EmptyCollectionException();
                        }
                        ans = studentCollection.notPassed(s);
                        Iterator iterator = ans.iterator();
                        StringBuffer out = new StringBuffer();
                        while (iterator.hasNext()) {
                            out.append(iterator.next());
                            out.append('\n');
                        }
                        optionPane.showMessageDialog(Window.this, out, "Answer", JOptionPane.INFORMATION_MESSAGE);
                    } catch (NoSuchElementException ex) {
                        optionPane.showMessageDialog(Window.this, "Text field is empty or input is incorrect", "Error", JOptionPane.ERROR_MESSAGE);
                    } catch (EmptyCollectionException ex) {
                        optionPane.showMessageDialog(Window.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

                    }
                }
            });
            this.setJMenuBar(menuBar);
            container.setLayout(new FlowLayout());
            JList<String> studentJList = new JList<>(listModel);
            container.add(studentJList);

        }
        JOptionPane optionPane = new JOptionPane();
    }

    public static void main(String[] args) {
        Window window = new Window();
        window.setVisible(true);
    }
}
