package bsu.fpmi.artsiushkevich;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
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
    final int FIELD_SIZE = 2;
    final int SHUFFLE_ITERATIONS = 10000;
    BufferedImage image = null;
    boolean win = false;
    int[] field = new int[FIELD_SIZE * FIELD_SIZE];
    DefaultTableModel gameModel;
    Random random = new Random(System.currentTimeMillis());
    int partSize = 400 / FIELD_SIZE;

    private void gameOver() throws InterruptedException {
        PixelGrabber pixelGrabber;
        int[] pix = new int[partSize * partSize];
        pixelGrabber = new PixelGrabber(image, (FIELD_SIZE - 1) * partSize, (FIELD_SIZE - 1) * partSize, partSize, partSize, pix, 0, partSize);
        pixelGrabber.grabPixels();
        Image part = createImage(new MemoryImageSource(partSize, partSize, pix, 0, partSize));
        gameModel.setValueAt(new ImageIcon(part), FIELD_SIZE - 1, FIELD_SIZE - 1);
        JOptionPane.showMessageDialog(MainWindow.this, "You are won");
    }

    private void shuffle() {
        for (int i = 0; i < FIELD_SIZE * FIELD_SIZE - 1; i++) {
            field[i] = i;
        }
        for (int i = 0; i < SHUFFLE_ITERATIONS; i++) {
            int a = random.nextInt(FIELD_SIZE * FIELD_SIZE - 2);
            int b = random.nextInt(FIELD_SIZE * FIELD_SIZE - 2);
            int buf = field[a];
            field[a] = field[b];
            field[b] = buf;
        }
        field[FIELD_SIZE * FIELD_SIZE - 1] = -1;
        int check = 0;
        for (int i = 0; i < FIELD_SIZE * FIELD_SIZE - 1; i++) {
            for (int j = i + 1; j < FIELD_SIZE * FIELD_SIZE - 1; j++) {
                if (field[j] < field[i]) {
                    check++;
                }
            }
        }
        if (check % 2 == 1) {
            for (int i = 0; i < FIELD_SIZE * FIELD_SIZE - 1; i++) {
                if ((field[i] == 0) || (field[i] == FIELD_SIZE * FIELD_SIZE - 2)) {
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
        int[] pix = new int[partSize * partSize];
        for (int i = 0; i < FIELD_SIZE * FIELD_SIZE - 1; i++) {
            int row = field[i] / FIELD_SIZE;
            int col = field[i] % FIELD_SIZE;
            pixelGrabber = new PixelGrabber(image, col * partSize, row * partSize, partSize, partSize, pix, 0, partSize);
            pixelGrabber.grabPixels();
            Image part = createImage(new MemoryImageSource(partSize, partSize, pix, 0, partSize));
            gameModel.setValueAt(new ImageIcon(part), i / FIELD_SIZE, i % FIELD_SIZE);
        }
        gameModel.setValueAt(null, FIELD_SIZE - 1, FIELD_SIZE - 1);
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
        this.setSize(FIELD_SIZE * (partSize + 5) + 30, FIELD_SIZE * (partSize + 5) + 100);
        this.setTitle("Lab6_1");
        this.setResizable(false);
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

        gameModel = new DefaultTableModel(new Object[FIELD_SIZE][FIELD_SIZE], new String[FIELD_SIZE]) {
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
        game.setRowHeight(partSize + 5);
        for (int i = 0; i < FIELD_SIZE; i++) {
            game.getColumnModel().getColumn(i).setPreferredWidth(partSize + 5);
        }
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
        restart.addActionListener(e -> {
            try {
                shuffle();
                slice(image);
            } catch (InterruptedException ex) {
                JOptionPane.showMessageDialog(MainWindow.this, ex.getMessage());
            }
        });
        show.addActionListener(e -> JOptionPane.showMessageDialog(MainWindow.this, new ImageIcon(image)));
        game.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if ((image != null) && (!win)) {
                    int[] deltaRow = {-1, 0, 0, 1};
                    int[] deltaCol = {0, 1, -1, 0};
                    for (int i = 0; i < 4; i++) {
                        int row = game.getSelectedRow() + deltaRow[i];
                        int col = game.getSelectedColumn() + deltaCol[i];
                        if ((row >= 0) && (row < FIELD_SIZE) && (col >= 0) && (col < FIELD_SIZE)) {
                            if (field[row * FIELD_SIZE + col] == -1) {
                                field[row * FIELD_SIZE + col] = field[game.getSelectedRow() * FIELD_SIZE + game.getSelectedColumn()];
                                field[game.getSelectedRow() * FIELD_SIZE + game.getSelectedColumn()] = -1;
                                gameModel.setValueAt(gameModel.getValueAt(game.getSelectedRow(), game.getSelectedColumn()), row, col);
                                gameModel.setValueAt(null, game.getSelectedRow(), game.getSelectedColumn());
                                break;
                            }
                        }
                    }
                    boolean flag = true;
                    for (int i = 0; i < FIELD_SIZE * FIELD_SIZE - 1; i++) {
                        if (i != field[i]) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        try {
                            gameOver();
                        } catch (InterruptedException ex) {
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
