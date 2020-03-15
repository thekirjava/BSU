package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class DrawingPanel extends JPanel {
    DrawingPanel(int w, int h) {
        setPreferredSize(new Dimension(w, h));
        repaint();
        buffer = new BufferedImage(w + 10, h + 10, BufferedImage.TYPE_INT_ARGB);
        movingPicture = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(buffer, 0, 0, null);
    }

    public BufferedImage getBuffer() {
        return buffer;
    }

    private BufferedImage compress(BufferedImage buf) {
        double max;
        int size;
        int deltaWidth = 50 - buf.getWidth();
        int deltaHeight = 50 - buf.getHeight();
        if ((deltaWidth < 0) || (deltaHeight < 0)) {
            max = 50;
            if (deltaWidth < deltaHeight) {
                size = buf.getWidth();
            } else {
                size = buf.getHeight();
            }
            double trans = 1.0 / (size / max);
            AffineTransform affineTransform = new AffineTransform();
            affineTransform.scale(trans, trans);
            AffineTransformOp transformOp = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_BILINEAR);
            double w = buf.getWidth() * trans;
            double h = buf.getHeight() * trans;
            BufferedImage ans = new BufferedImage((int) w, (int) h, buf.getType());
            transformOp.filter(buf, ans);
            return ans;
        } else {
            return buf;
        }
    }

    public void loadImage(BufferedImage buf) {
        buf = compress(buf);
        movingPicture.createGraphics().setColor(Color.WHITE);
        movingPicture.createGraphics().fillRect(0, 0, 50, 50);
        movingPicture.createGraphics().drawImage(buf, 0, 0, null);
    }

    public BufferedImage getMovingPicture() {
        return movingPicture;
    }

    private BufferedImage buffer;
    private BufferedImage movingPicture;
}