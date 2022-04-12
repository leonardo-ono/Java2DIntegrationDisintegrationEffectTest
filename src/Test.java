

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Integration and Disintegration Effect
 * 
 * resources:
 * bg.jpg - https://wallpaperscraft.com/download/background_spot_light_81400/800x600
 * 
 * @author Leo
 */
public class Test extends JPanel {
    
    private BufferedImage bg;
    private BufferedImage mask;
    private BufferedImage off;
    
    public Test() {
        try {
            bg = ImageIO.read(getClass().getResourceAsStream("bg.jpg"));
            mask = ImageIO.read(getClass().getResourceAsStream("mask4.png"));
            off = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
    
    private void generateOff(int v) {
        off.getGraphics().clearRect(0, 0, 800, 600);
        for (int y = 0; y < mask.getHeight(); y++) {
            for (int x = 0; x < mask.getWidth(); x++) {
                int mc = mask.getRGB(x, y) & 255;
                if (Math.abs(mc - v) < 5) {
                    off.setRGB(x, y, Color.WHITE.getRGB());
                }
                else if (Math.abs(mc - v) < 8) {
                    off.setRGB(x, y, Color.CYAN.getRGB());
                }
                else if (Math.abs(mc - v) < 11) {
                    off.setRGB(x, y, Color.BLUE.getRGB());
                }
                else if (mc < v) {
                    off.setRGB(x, y, (bg.getRGB(x, y)));
                }
            }
        }
    }
    
    double offv = -100;
    double offds = 2;
    double offdv = offds;
    
    private void update() {
        offv += offdv;
        
        if (offv > 300) {
            offdv = -offds;
        }
        else if (offv < -50) {
            offdv = +offds;
        }
        
        generateOff((int) offv);        
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        update();
        
        g.drawImage(off, 0, 0, null);
        
        try {
            Thread.sleep(1000 / 60);
        } 
        catch (InterruptedException ex) { }
        
        repaint();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Test test = new Test();
            test.setPreferredSize(new Dimension(800, 600));
            JFrame frame = new JFrame();
            frame.setTitle("Java 2D Integration and Disintegration Effect Test");
            frame.add(test);
            frame.pack();
            frame.setResizable(false);
            //frame.setLocationRelativeTo(null);
            frame.setLocation(50, 50);
            frame.setVisible(true);
            test.requestFocus();
        });
    }

    
}
