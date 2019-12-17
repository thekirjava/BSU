package com.company;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
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
            JMenu file = new JMenu("File");
            JMenu data = new JMenu("Data");
            JMenuItem open = new JMenuItem("Open");
            JMenuItem add = new JMenuItem("Add");
            JMenuItem search = new JMenuItem("Search");
            JMenuItem openXML = new JMenuItem("Open XML file");
            JMenuItem saveXML = new JMenuItem("Save to XML file");
            Container container = this.getContentPane();
            DefaultListModel<String> listModel = new DefaultListModel<>();

            file.add(open);
            file.add(openXML);
            file.add(saveXML);
            data.add(add);
            data.add(search);
            menuBar.add(file);
            menuBar.add(data);
            open.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File("."));
                    fileChooser.setFileFilter(new FileNameExtensionFilter("TXT files", "txt"));
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
                            JOptionPane.showMessageDialog(Window.this, "File doesn't exist", "Error", JOptionPane.ERROR_MESSAGE);
                        } catch (NoSuchElementException ex) {
                            JOptionPane.showMessageDialog(Window.this, "Wrong input format", "Error", JOptionPane.ERROR_MESSAGE);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(Window.this, "Grade and semester must be integer", "Error", JOptionPane.ERROR_MESSAGE);
                        } catch (WrongIdException ex) {
                            JOptionPane.showMessageDialog(Window.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            });
            openXML.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File("."));
                    fileChooser.setFileFilter(new FileNameExtensionFilter("XML files", "xml"));
                    if (fileChooser.showDialog(Window.this, "Open") == JFileChooser.APPROVE_OPTION) {
                        try {
                            studentCollection.clear();
                            listModel.clear();
                            Scanner s = new Scanner(fileChooser.getSelectedFile());
                            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
                            DocumentBuilder builder = builderFactory.newDocumentBuilder();
                            Document document = builder.parse(fileChooser.getSelectedFile());
                            NodeList nodeList = document.getDocumentElement().getElementsByTagName("student");
                            for (int i = 0; i < nodeList.getLength(); i++) {
                                Node student = nodeList.item(i);
                                NamedNodeMap attributes = student.getAttributes();
                                StringBuilder stringBuilder = new StringBuilder(attributes.getNamedItem("id").getNodeValue());
                                stringBuilder.append(" ").append(attributes.getNamedItem("name").getNodeValue());
                                stringBuilder.append(" ").append(attributes.getNamedItem("semester").getNodeValue());
                                stringBuilder.append(" ").append(attributes.getNamedItem("examName").getNodeValue());
                                stringBuilder.append(" ").append(attributes.getNamedItem("grade").getNodeValue());
                                studentCollection.add(stringBuilder.toString());
                                listModel.add(listModel.getSize(), stringBuilder.toString());
                            }
                        } catch (FileNotFoundException e) {
                            JOptionPane.showMessageDialog(Window.this, "File doesn't exist", "Error", JOptionPane.ERROR_MESSAGE);
                        } catch (ParserConfigurationException e) {
                            JOptionPane.showMessageDialog(Window.this, "Parser is poorly configured", "Error", JOptionPane.ERROR_MESSAGE);
                        } catch (SAXException e) {
                            JOptionPane.showMessageDialog(Window.this, "SAX isn't working properly", "Error", JOptionPane.ERROR_MESSAGE);
                        } catch (IOException e) {
                            JOptionPane.showMessageDialog(Window.this, "Input file is wrong", "Error", JOptionPane.ERROR_MESSAGE);
                        } catch (WrongIdException e) {
                            JOptionPane.showMessageDialog(Window.this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            });
            saveXML.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    try {
                        JFileChooser jFileChooser = new JFileChooser();
                        jFileChooser.setCurrentDirectory(new File("."));
                        jFileChooser.setFileFilter(new FileNameExtensionFilter("XML files", "xml"));
                        if (jFileChooser.showDialog(Window.this, "Save") == JFileChooser.APPROVE_OPTION) {
                            PrintStream printStream = new PrintStream(jFileChooser.getSelectedFile());
                            Iterator<Map.Entry<String, Student>> entryIterator = studentCollection.entrySet().iterator();
                            printStream.println("<?xml version=\"1.0\"  encoding=\"UTF-8\" standalone=\"yes\"?>");
                            printStream.println("<students>");
                            while (entryIterator.hasNext()) {
                                Map.Entry<String, Student> entry = entryIterator.next();
                                for (Student.Exam exam : entry.getValue().getExams()) {
                                    printStream.print("<student ");
                                    printStream.print("id=\"" + entry.getValue().getId() + "\" ");
                                    printStream.print("name=\"" + entry.getValue().getName() + "\" ");
                                    printStream.print("semester=\"" + exam.getSem() + "\" ");
                                    printStream.print("examName=\"" + exam.getName() + "\" ");
                                    printStream.print("grade=\"" + exam.getGrade() + "\" ");
                                    printStream.println("/>");
                                }
                            }
                            printStream.println("</students>");
                        }
                    } catch (FileNotFoundException e) {
                        JOptionPane.showMessageDialog(Window.this, "Wrong file", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            add.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        String s = JOptionPane.showInputDialog(Window.this, "Input data as: \"id student_name exam_semester exam_name grade\"", "");
                        if (s != null) {
                            studentCollection.add(s);
                            listModel.add(listModel.getSize(), s);
                            validate();
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(Window.this, "Grade and semester must be integer", "Error", JOptionPane.ERROR_MESSAGE);
                    } catch (NoSuchElementException ex) {
                        JOptionPane.showMessageDialog(Window.this, "Wrong input format", "Error", JOptionPane.ERROR_MESSAGE);
                    } catch (WrongIdException ex) {
                        JOptionPane.showMessageDialog(Window.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            search.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String s = JOptionPane.showInputDialog(Window.this, "Input data as: \"semester exam1 exam 2 ...\"", "");
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
                        JOptionPane.showMessageDialog(Window.this, out, "Answer", JOptionPane.INFORMATION_MESSAGE);
                    } catch (NoSuchElementException ex) {
                        JOptionPane.showMessageDialog(Window.this, "Text field is empty or input is incorrect", "Error", JOptionPane.ERROR_MESSAGE);
                    } catch (EmptyCollectionException ex) {
                        JOptionPane.showMessageDialog(Window.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

                    }
                }
            });
            this.setJMenuBar(menuBar);
            container.setLayout(new FlowLayout());
            JList<String> studentJList = new JList<>(listModel);
            container.add(studentJList);

        }
    }

    public static void main(String[] args) {
        Window window = new Window();
        window.setVisible(true);
    }
}
