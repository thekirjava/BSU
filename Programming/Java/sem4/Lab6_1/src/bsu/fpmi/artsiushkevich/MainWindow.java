package bsu.fpmi.artsiushkevich;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class MainWindow extends JFrame {
    BufferedImage image = null;
    boolean win = false;
    int[] field = new int[16];
    DefaultTableModel gameModel;
    Random random = new Random(System.currentTimeMillis());

    private void gameOver() throws InterruptedException {
        PixelGrabber pixelGrabber;
        int[] pix = new int[10000];
        pixelGrabber = new PixelGrabber(image, 300, 300, 100, 100, pix, 0, 100);
        pixelGrabber.grabPixels();
        Image part = createImage(new MemoryImageSource(100, 100, pix, 0, 100));
        gameModel.setValueAt(new ImageIcon(part), 3, 3);
        JOptionPane.showMessageDialog(MainWindow.this, "You are won");
    }

    private void shuffle() {
        for (int i = 0; i < 15; i++) {
            field[i] = i;
        }
        for (int i = 0; i < 10000; i++) {
            int a = random.nextInt(14);
            int b = random.nextInt(14);
            int buf = field[a];
            field[a] = field[b];
            field[b] = buf;
        }
        field[15] = -1;
        int check = 0;
        for (int i = 0; i < 15; i++) {
            for (int j = 1; j < 15; j++) {
                if (field[j] < field[i]) {
                    check++;
                }
            }
        }
        if (check % 2 == 1) {
            for (int i = 0; i < 15; i++) {
                if ((field[i] == 0) || (field[i] == 14)) {
                    int buf = field[i + 1];
                    field[i + 1] = field[i];
                    field[i] = buf;
                    break;
                }
            }
        }
    }


    private void slice(BufferedImage image) throws InterruptedException {
        PixelGrabber pixelGrabber;
        int[] pix = new int[10000];
        for (int i = 0; i < 15; i++) {
            int row = field[i] / 4;
            int col = field[i] % 4;
            pixelGrabber = new PixelGrabber(image, col * 100, row * 100, 100, 100, pix, 0, 100);
            pixelGrabber.grabPixels();
            Image part = createImage(new MemoryImageSource(100, 100, pix, 0, 100));
            gameModel.setValueAt(new ImageIcon(part), i / 4, i % 4);
        }
        gameModel.setValueAt(null, 3, 3);
    }

    private BufferedImage normalize(BufferedImage buf) {
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.scale(400.0 / buf.getWidth(), 400.0 / buf.getHeight());
        AffineTransformOp transformOp = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_BILINEAR);
        double w = 400;
        double h = 400;
        BufferedImage ans = new BufferedImage((int) w, (int) h, buf.getType());
        transformOp.filter(buf, ans);
        return ans;
    }

    MainWindow() {
        this.setSize(900, 500);
        this.setTitle("Lab6_1");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = this.getContentPane();
        JMenuBar menuBar = new JMenuBar();
        JMenuItem open = new JMenuItem("Open picture");
        JMenuItem restart = new JMenuItem("Restart");
        JMenuItem show = new JMenuItem("Show picture");
        menuBar.add(open);
        menuBar.add(restart);
        menuBar.add(show);
        restart.setEnabled(false);
        show.setEnabled(false);

        gameModel = new DefaultTableModel(new Object[4][4], new String[4]) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return ImageIcon.class;
            }
        };

        JTable game = new JTable(gameModel);
        game.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        game.setRowHeight(105);
        game.getColumnModel().getColumn(0).setPreferredWidth(105);
        game.getColumnModel().getColumn(1).setPreferredWidth(105);
        game.getColumnModel().getColumn(2).setPreferredWidth(105);
        game.getColumnModel().getColumn(3).setPreferredWidth(105);
        open.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
            fileChooser.setFileFilter(new FileNameExtensionFilter("Image", "png", "jpg", "jpeg", "bmp"));
            if (fileChooser.showDialog(MainWindow.this, "Open") == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    image = ImageIO.read(file);
                    image = normalize(image);
                    shuffle();
                    slice(image);

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(MainWindow.this, "File doesn't exist");
                } catch (InterruptedException ex) {
                    JOptionPane.showMessageDialog(MainWindow.this, ex.getMessage());
                }
                restart.setEnabled(true);
                show.setEnabled(true);
            }
        });
        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    shuffle();
                    slice(image);
                } catch (InterruptedException ex) {
                    JOptionPane.showMessageDialog(MainWindow.this, ex.getMessage());
                }
            }
        });
        show.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(MainWindow.this, new ImageIcon(image));
            }
        });
        game.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if ((image != null) && (!win)) {
                    int[] deltaRow = {-1, 0, 0, 1};
                    int[] deltaCol = {0, 1, -1, 0};
                    for (int i = 0; i < 4; i++) {
                        int row = game.getSelectedRow() + deltaRow[i];
                        int col = game.getSelectedColumn() + deltaCol[i];
                        if ((row >= 0) && (row <= 3) && (col >= 0) && (col <= 3)) {
                            if (field[row * 4 + col] == -1) {
                                field[row * 4 + col] = field[game.getSelectedRow() * 4 + game.getSelectedColumn()];
                                field[game.getSelectedRow() * 4 + game.getSelectedColumn()] = -1;
                                gameModel.setValueAt(gameModel.getValueAt(game.getSelectedRow(), game.getSelectedColumn()), row, col);
                                gameModel.setValueAt(null, game.getSelectedRow(), game.getSelectedColumn());
                                break;
                            }
                        }
                    }
                    boolean flag = true;
                    for (int i = 0; i < 15; i++) {
                        if (i != field[i]) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        try {
                            gameOver();
                        }
                        catch (InterruptedException ex) {
                            JOptionPane.showMessageDialog(MainWindow.this, ex.getMessage());
                        }
                    }
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(game);
        container.setLayout(new BorderLayout());
        container.add(scrollPane, BorderLayout.CENTER);
        this.setJMenuBar(menuBar);
    }
}
