package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class Main {
    static class Window extends JFrame {
        Window() {
            this.setBounds(300, 100, 900, 600);
            this.setResizable(false);
            this.setTitle("Lab9");
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Container cont = this.getContentPane();
            cont.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            ActionListener al = new FrameActionListener();
            c.fill = GridBagConstraints.BOTH;
            c.ipadx = 10;
            c.gridx = 0;
            c.gridy = 0;
            c.gridheight = 1;
            c.gridwidth = 2;
            cont.add(baseLabel, c);

            c.gridx = 2;
            cont.add(stepLabel, c);

            c.gridx = 4;
            cont.add(sizeLabel, c);

            c.gridy = 1;
            c.gridx = 0;
            cont.add(baseTextField, c);

            c.gridx = 2;
            cont.add(stepTextField, c);

            c.gridx = 4;
            cont.add(sizeTextField, c);

            ButtonGroup group = new ButtonGroup();
            linearRadio.setSelected(true);
            group.add(linearRadio);
            group.add(expRadio);
            c.gridx = 0;
            c.gridy = 3;
            cont.add(linearRadio, c);

            c.gridy = 4;
            cont.add(expRadio, c);

            c.gridwidth = 1;
            c.gridheight = 2;
            c.gridx = 3;
            c.gridy = 3;
            countButton.setActionCommand("count");
            countButton.addActionListener(al);
            cont.add(countButton, c);

            c.gridheight = 1;
            c.gridx = 4;
            cont.add(elementLabel, c);

            c.gridy = 4;
            cont.add(elementTextField, c);

            c.gridx = 5;
            elementOutTextField.setEditable(false);
            cont.add(elementOutTextField, c);

            c.gridy = 3;
            cont.add(new JLabel("Answer"), c);

            c.gridx = 3;
            c.gridy = 5;
            sumButton.setActionCommand("sum");
            sumButton.addActionListener(al);
            cont.add(sumButton, c);

            c.gridx = 5;
            sumTextField.setEditable(false);
            cont.add(sumTextField, c);

            c.gridx = 3;
            c.gridy = 6;
            stringButton.setActionCommand("string");
            stringButton.addActionListener(al);
            cont.add(stringButton, c);

            c.gridx = 5;
            stringTextField.setEditable(false);
            c.ipadx = 100;
            cont.add(stringTextField, c);

            c.gridx = 3;
            c.gridy = 7;
            printButton.setActionCommand("print");
            printButton.addActionListener(al);
            cont.add(printButton, c);
        }

        public class FrameActionListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                try {
                    Series s;
                    if (linearRadio.isSelected()) {
                        s = new Linear(parseDouble(baseTextField.getText()), parseDouble(stepTextField.getText()), parseInt(sizeTextField.getText()));
                    } else {
                        s = new Exponential(parseDouble(baseTextField.getText()), parseDouble(stepTextField.getText()), parseInt(sizeTextField.getText()));
                    }
                    switch (e.getActionCommand()) {
                        case "count": {
                            double ans = s.getN(parseInt(elementTextField.getText()));
                            elementOutTextField.setText(Double.toString(ans));
                        }
                        break;
                        case "sum": {
                            double ans = s.sum();
                            sumTextField.setText(Double.toString(ans));
                        }
                        break;
                        case "string": {
                            stringTextField.setText(s.toString());
                        }
                        break;
                        case "print": {
                            JFileChooser fileChooser = new JFileChooser();
                            if (fileChooser.showDialog(Window.this, "Save") == JFileChooser.APPROVE_OPTION) {
                                s.saveToFile(fileChooser.getSelectedFile());
                            }
                        }
                    }
                } catch (java.io.IOException ex) {
                    error.showMessageDialog(Window.this, "Тhis file doesn't exist", "Error", JOptionPane.INFORMATION_MESSAGE);
                } catch (SeriesSizeException ex) {
                    error.showMessageDialog(Window.this, ex.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    error.showMessageDialog(Window.this, "Wrong format of input", "Error", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }

        private JButton countButton = new JButton("Get N");
        private JButton sumButton = new JButton("Sum");
        private JButton stringButton = new JButton("To string");
        private JButton printButton = new JButton("To file");
        private JTextField baseTextField = new JTextField();
        private JTextField stepTextField = new JTextField();
        private JTextField sizeTextField = new JTextField();
        private JTextField elementTextField = new JTextField();
        private JTextField elementOutTextField = new JTextField();
        private JTextField sumTextField = new JTextField();
        private JTextField stringTextField = new JTextField();
        private JLabel baseLabel = new JLabel("Base:");
        private JLabel stepLabel = new JLabel("Step:");
        private JLabel sizeLabel = new JLabel("Size:");
        private JLabel elementLabel = new JLabel("Element to print:");
        private JRadioButton linearRadio = new JRadioButton("Linear");
        private JRadioButton expRadio = new JRadioButton("Exponential");
        private JOptionPane error = new JOptionPane();
    }

    public static void main(String[] args) {
        Window w = new Window();
        w.setVisible(true);
    }
}
