package bsu.fpmi.artsiushkevich.view;

import bsu.fpmi.artsiushkevich.controller.WindowController;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;

import static bsu.fpmi.artsiushkevich.controller.WindowController.setMouseListener;

public class MainWindow extends JFrame {
    public MainWindow() {
        this.setTitle("Lab9");
        this.setSize(900, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTable table = new JTable(WindowController.createModel());
        table.setRowHeight(15);
        TableColumnModel tableColumnModel = table.getColumnModel();
        tableColumnModel.getColumn(0).setPreferredWidth(30);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        System.out.println(tableColumnModel.getColumnCount());
        for (int i = 1; i < tableColumnModel.getColumnCount(); i++) {
            tableColumnModel.getColumn(i).setPreferredWidth(80);
        }
        setMouseListener(table);
        JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(850, 350));
        Container container = this.getContentPane();
        container.add(scrollPane);
    }
}
