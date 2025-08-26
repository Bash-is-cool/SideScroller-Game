import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Menu extends JPanel{
    private static final long serialVersionUID = 1L;

    public static final int WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    public static final int HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    public int iWIDTH = WIDTH, iHEIGHT = HEIGHT, buttonWIDTH = 273, buttonHEIGHT = 108;
    private boolean isSplash, isTimerRunning;
    private JPanel screen = new JPanel();

    final BufferedImage bg = ImageIO.read (new File("resources/Menu/BG.jpg"));
    final BufferedImage yogi = ImageIO.read (new File("resources/Menu/alienBack2.png"));
    final BufferedImage logo = ImageIO.read (new File("resources/Menu/logo.png"));
    
    final BufferedImage startButton = ImageIO.read (new File("resources/Menu/startButton.png"));
    public final BufferedImage exit = ImageIO.read (new File("resources/Menu/exit.png"));
    public final BufferedImage pause = ImageIO.read (new File("resources/Menu/pause.png"));
    
    int buttonWidth = 273;
    int buttonHeight = 108;
    int spacing = 50; 
        
    public int totalWidth = (3 * buttonWidth) + (2 * spacing);

    public int startX = (WIDTH / 2) - (buttonWidth / 2);

    int leftExitX = startX - buttonWidth - spacing;

    int rightExitX = startX + buttonWidth + spacing;
    
    public int buttonY = HEIGHT / 2 - buttonHeight + 30;

    public Menu (boolean isSplash) throws IOException {
        this.isSplash = isSplash;
        isTimerRunning = false;

        if (isSplash == true) {
            screen.repaint();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //draw BG
        g.drawImage(bg, 0, 0, iWIDTH, iHEIGHT, null);

        if (iWIDTH != (WIDTH + 170)) {
            iWIDTH = iWIDTH + 1;
            iHEIGHT = iHEIGHT + 1;
        }

        //draw Yogi (our character lol)
        int yogiWIDTH = 233, yogiHEIGHT = 227;
        g.drawImage(yogi, WIDTH / 2 - (yogiWIDTH / 2), HEIGHT - yogiHEIGHT - 70, yogiWIDTH, yogiHEIGHT, null);

        //draw logo
        int logoWIDTH = 263, logoHEIGHT = 176;
        g.drawImage(logo, WIDTH / 2 - (logoWIDTH / 2), 30, logoWIDTH, logoHEIGHT, null);

        //draw start button
        g.drawImage(startButton, startX, buttonY, buttonWidth, buttonHeight, null);
        
        g.drawImage(exit, leftExitX, buttonY, buttonWidth, buttonHeight, null);
        
        g.drawImage(exit, rightExitX, buttonY, buttonWidth, buttonHeight, null);

        //drawing the sun
        g.setColor(new Color(255, 231, 149));
        g.fillOval(1600 - 50 - 140, 25, 140, 140);

        repaint();
    }
}