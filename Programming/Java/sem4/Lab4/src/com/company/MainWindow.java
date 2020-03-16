package com.company;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.Timer;

public class MainWindow extends JFrame {

    static class ParsedData {
        String name;
        double value;
    }

    MainWindow() {
        this.setBounds(100, 100, 1100, 700);
        this.setTitle("Lab4");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel task1 = new JPanel();
        JPanel task2 = new JPanel();
        JPanel task3 = new JPanel();
        task1.setLayout(new BorderLayout());
        task2.setLayout(new BorderLayout());
        task3.setLayout(new BorderLayout());
        DrawingPanel draw1 = new DrawingPanel(310, 310);
        DrawingPanel draw2 = new DrawingPanel(510, 510);
        task1.add(draw1, BorderLayout.CENTER);
        task2.add(draw2, BorderLayout.CENTER);

        JTabbedPane tabbedPane = new JTabbedPane();
        Timer timer = new Timer();
        TimerTask clock = new TimerTask() {
            private void draw(Graphics g, Graphics gbuf) {
                double x = 150 + 150 * Math.cos(angle1);
                double y = 150 + 150 * Math.sin(angle1);
                g.drawLine(150, 150, (int) x, (int) y);
                gbuf.drawLine(150, 150, (int) x, (int) y);
            }

            @Override
            public void run() {
                if (tabbedPane.getSelectedIndex() == 0) {
                    Graphics g = draw1.getGraphics();
                    Graphics gbuf = draw1.getBuffer().getGraphics();
                    penColor = Color.WHITE;
                    draw(g, gbuf);
                    g.setColor(penColor);
                    gbuf.setColor(penColor);
                    angle1 += Math.PI / 30;
                    if (angle1 >= 2 * Math.PI) {
                        angle1 = 0;
                    }
                    penColor = Color.BLACK;
                    g.setColor(penColor);
                    gbuf.setColor(penColor);
                    draw(g, gbuf);
                    draw1.repaint();
                }
            }
        };
        TimerTask init = new TimerTask() {
            @Override
            public void run() {
                Graphics g = draw1.getGraphics();
                Graphics gbuf = draw1.getBuffer().getGraphics();
                penColor = Color.WHITE;
                g.setColor(penColor);
                gbuf.setColor(penColor);
                g.fillRect(0, 0, 300, 300);
                gbuf.fillRect(0, 0, 300, 300);
                penColor = Color.BLACK;
                g.setColor(penColor);
                gbuf.setColor(penColor);
                g.drawOval(0, 0, 300, 300);
                gbuf.drawOval(0, 0, 300, 300);
            }
        };
        timer.schedule(init, 700);
        timer.schedule(clock, 750, 1000);


        JSlider speed = new JSlider(1, 20, 10);
        JRadioButton clockwise = new JRadioButton("Clockwise", true);
        JRadioButton counterClockwise = new JRadioButton("Counter clockwise");
        ButtonGroup directions = new ButtonGroup();
        directions.add(clockwise);
        directions.add(counterClockwise);
        JButton load = new JButton("Load picture");
        load.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser(".");
            fileChooser.setFileFilter(new FileNameExtensionFilter("Images", "jpg", "jpeg", "png", "bmp"));
            if (fileChooser.showDialog(MainWindow.this, "Load") == JFileChooser.APPROVE_OPTION) {
                try {
                    draw2.loadImage(ImageIO.read(fileChooser.getSelectedFile()));
                } catch (IOException exc) {
                    JOptionPane.showMessageDialog(MainWindow.this, "File doesn't exist", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        TimerTask runningPicture = new TimerTask() {
            @Override
            public void run() {
                if ((draw2.getMovingPicture() != null) && (tabbedPane.getSelectedIndex() == 1)) {
                    Graphics g = draw2.getGraphics();
                    Graphics gbuf = draw2.getBuffer().getGraphics();
                    penColor = Color.WHITE;
                    g.setColor(penColor);
                    gbuf.setColor(penColor);
                    g.fillRect(0, 0, 500, 500);
                    gbuf.fillRect(0, 0, 500, 500);
                    penColor = Color.BLACK;
                    g.setColor(penColor);
                    gbuf.setColor(penColor);
                    double x = 150 + 150 * Math.cos(angle2);
                    double y = 150 + 150 * Math.sin(angle2);

                    g.drawImage(draw2.getMovingPicture(), (int) x, (int) y, null);
                    gbuf.drawImage(draw2.getMovingPicture(), (int) x, (int) y, null);
                    double delta = Math.PI / 180 * speed.getValue();
                    if (counterClockwise.isSelected()) {
                        delta *= -1;
                    }
                    angle2 += delta;
                    draw2.repaint();
                }
            }
        };
        timer.schedule(runningPicture, 750, 1000);
        task2.add(speed, BorderLayout.SOUTH);
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new GridLayout(3, 1));
        buttonPane.add(clockwise);
        buttonPane.add(counterClockwise);
        buttonPane.add(load);
        task2.add(buttonPane, BorderLayout.EAST);
        JButton jsonOpen = new JButton("Open");
        DefaultPieDataset pieDataset = new DefaultPieDataset();
        jsonOpen.addActionListener(e -> {
            try {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File("."));
                fileChooser.setFileFilter(new FileNameExtensionFilter("JSON files", "json"));
                if (fileChooser.showDialog(MainWindow.this, "Open") == JFileChooser.APPROVE_OPTION) {

                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();
                    JsonReader reader = new JsonReader(new FileReader(fileChooser.getSelectedFile()));
                    ArrayList<ParsedData> jsonParsed;
                    jsonParsed = gson.fromJson(reader, new TypeToken<List<ParsedData>>() {
                    }.getType());
                    pieDataset.clear();
                    for (ParsedData parsedData : jsonParsed) {
                        pieDataset.setValue(parsedData.name, parsedData.value);
                    }
                }
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(MainWindow.this, "File doesn't exist", "Error", JOptionPane.ERROR_MESSAGE);
            }
            catch (JsonSyntaxException ex) {
                JOptionPane.showMessageDialog(MainWindow.this, "Incorrect JSON file", "Error", JOptionPane.ERROR_MESSAGE);

            }
        });
        JFreeChart chart = ChartFactory.createPieChart("Chart", pieDataset, true, true, false);
        ChartPanel pane = new ChartPanel(chart);
        task3.add(pane, BorderLayout.CENTER);
        task3.add(jsonOpen, BorderLayout.SOUTH);
        tabbedPane.add("Task 1", task1);
        tabbedPane.add("Task 2", task2);
        tabbedPane.add("Task 3", task3);
        Container container = this.getContentPane();
        container.setLayout(new BorderLayout());
        container.add(tabbedPane, BorderLayout.CENTER);
    }

    Color penColor = Color.BLACK;
    double angle1 = 3 * Math.PI / 2;
    double angle2 = 3 * Math.PI / 2;
    double angle3 = 3 * Math.PI / 2;
}