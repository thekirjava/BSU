package bsu.fpmi.artsiushkevich;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    MainWindow() {
        this.setTitle("Lab9");
        this.setSize(900, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTable table = new JTable(WindowController.createModel());
        JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        Container container = this.getContentPane();
        //container.setLayout(new FlowLayout());
        container.add(scrollPane);
    }
}
