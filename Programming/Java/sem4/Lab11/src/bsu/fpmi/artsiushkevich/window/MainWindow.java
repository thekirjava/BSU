package bsu.fpmi.artsiushkevich.window;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    public MainWindow() {
        this.setSize(420, 460);
        this.setResizable(false);
        Container container = this.getContentPane();
        container.setLayout(new BorderLayout());
        Board b = new Board();
        container.add(b, BorderLayout.CENTER);
        b.newGame();
    }
}
