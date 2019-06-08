package me.vzhilin.fps;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.WritableRaster;

class SwingFpsFrame extends JFrame {
    private BufferedImage image;
    private int dataBufferSize;

    /** FPS counter */
    private FpsCounter counter = new FpsCounter();

    public SwingFpsFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(400, 300);
        addComponentListener(new ComponentAdapter() {


            @Override
            public void componentResized(ComponentEvent e) {
                counter.reset();
                image = buildImage();
            }

            @Override
            public void componentShown(ComponentEvent e) {
                counter.reset();
                image = buildImage();
            }
        });

        image = buildImage();
        counter.reset();
    }

    private BufferedImage buildImage() {
        BufferedImage img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D gc = img.createGraphics();
        gc.setColor(Color.WHITE);
        gc.fillRect(0, 0, img.getWidth(), img.getHeight());
        gc.dispose();

        WritableRaster raster = img.getRaster();
        DataBuffer dataBuffer = raster.getDataBuffer();
        dataBufferSize = dataBuffer.getSize();
        return img;
    }

    @Override
    public void paint(Graphics g) {
        // small change to prevent caching
        image.setRGB(0, 0, Integer.MAX_VALUE);

        g.drawImage(image, 0, 0, null);
        touchFpsCounter();
        repaint();
    }

    private void touchFpsCounter() {
        if (counter.tick()) {
            long ticks = counter.ticks();
            float fps = counter.fps();
            int sz = getImageSize();     // picture data size
            float throughput = 1e-6f * ticks * sz; // throughput in megabytes per second

            String title = String.format("[%dx%d] fps: %.2f throughput: %.2f megabytes/sec",
                    getWidth(), getHeight(), fps, throughput);
            setTitle(title);
        }
    }

    public int getImageSize() {
        return dataBufferSize;
    }
}
