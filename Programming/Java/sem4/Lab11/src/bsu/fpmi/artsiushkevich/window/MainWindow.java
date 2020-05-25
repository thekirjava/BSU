package bsu.fpmi.artsiushkevich.window;

import javax.swing.*;

public class MainWindow extends JFrame {
    public MainWindow() {
        this.setSize(420, 460);
        this.setResizable(false);
        Board b = new Board();
    }
}
