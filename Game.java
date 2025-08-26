import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.Random;

/**
 * Created by weijiangan on 28/11/2016.
 */
public class Game implements KeyListener {
    private static final int SCREEN_WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    private static final int SCREEN_HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    private static final int START_BUTTON_W = 273, START_BUTTON_H = 108;
    private boolean INGAME;
    private MouseAdapter ma;
    private static JFrame f;
    private JPanel topPanel;
    private Board board;
    private Menu menu;
    private Story story;
    private Timer timer;
    private Clip clip;
    private MouseListener mouse;
    private boolean fun = false;

    private Game() {
        try {
            initGame();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initGame() throws IOException {
        INGAME = false;
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File("resources/bgm.wav")));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Failed to load background music: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
        f = new JFrame("Absolute Nightmare ;)");
        f.setMinimumSize(new Dimension(SCREEN_WIDTH / 4, SCREEN_HEIGHT / 4));
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        f.setResizable(true);
        f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                if (board != null && board.timer.isRunning())
                    board.timer.stop();
                if (.showConfirmDialog(null, "Are you sure you want to exit game?", "Notice",
                        .YES_NO_OPTION) == .YES_OPTION) {
                    System.exit(0);
                }
                if (board != null && !board.timer.isRunning())
                board.timer.start();
            }
        });

        f.setUndecorated(true);
        f.setAlwaysOnTop(false);
        f.addKeyListener(this);
        timer = new Timer(25, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                topPanel.revalidate();
                topPanel.repaint();
            }
        });
        f.setContentPane(createContentPane());
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        clip.start();
    }

    private JPanel createContentPane() throws IOException {
        mouse();
        topPanel = new JPanel();    // topmost JPanel in layout hierarchy
        topPanel.setBackground(Color.BLACK);
        topPanel.addKeyListener(this);
        topPanel.addMouseListener(ma);
        // Allow us to layer the panels
        LayoutManager overlay = new OverlayLayout(topPanel);
        topPanel.setLayout(overlay);

        // Must add last to ensure button's visibility
        menu = new Menu (true);
        story = new Story(SCREEN_WIDTH, SCREEN_HEIGHT);
        topPanel.add(menu);

        return topPanel;
    }
    
    public void mouse() {
        ma = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!INGAME) {
                    if((menu.startX + menu.buttonWidth > e.getX() && e.getX() > menu.startX) && (e.getY() > menu.HEIGHT / 2 - menu.buttonHeight + 30 && e.getY() < menu.HEIGHT / 2 - menu.buttonHeight + menu.buttonHeight)) {
                        topPanel.remove(menu);
                        topPanel.add(story);
                        topPanel.revalidate();
                    }
                
                    if(((menu.leftExitX < e.getX() && menu.leftExitX + menu.buttonWidth > e.getX()) && (e.getY() > menu.HEIGHT / 2 - menu.buttonHeight + 30 && e.getY() < menu.HEIGHT / 2 - menu.buttonHeight + menu.buttonY))) {
                        Random r = new Random();
                        double num = r.nextDouble();
                        double num2 = r.nextDouble();
                   
                        if(num <= 0.5) {
                            if(fun == true && num2 <= 0.25) {
                                killswitch();
                            }
                        } else {
                             lucky = new ();
                       
                            lucky.showMessageDialog(null, "You Got Lucky This Time...");
                            System.exit(0);
                        }
                    }
                
                    if(((menu.rightExitX < e.getX() && menu.rightExitX + menu.buttonWidth > e.getX()) && (e.getY() > menu.HEIGHT / 2 - menu.buttonHeight + 30 && e.getY() < menu.HEIGHT / 2 - menu.buttonHeight + menu.buttonY))) {
                        Random r = new Random();
                        double num = r.nextDouble();
                        double num2 = r.nextDouble();
                   
                        if(num <= 0.5) {
                            if(fun == true && num2 <= 0.25) {
                                killswitch();
                            }
                        } else {
                            JOptionPane lucky = new JOptionPane();
                       
                            lucky.showMessageDialog(null, "You Got Lucky This Time...");
                            System.exit(0);
                        }
                    }
                } else if (INGAME) {
                    Rectangle exitRect = new Rectangle(menu.startX, menu.buttonY, menu.buttonWidth, menu.buttonHeight);
                    Rectangle pauseRect = new Rectangle(board.frameWidth - 100, 50, 100, 100);
                    if (pauseRect.contains(e.getPoint())) {
                        // Pause the game
                        if (board != null && board.timer.isRunning()) {
                            board.timer.stop();
                        } else if (board != null && !board.timer.isRunning()) {
                            board.timer.start();
                        }
                    }

                    if(fun && exitRect.contains(e.getPoint())) {
                        killswitch();
                    } else if(!fun && exitRect.contains(e.getPoint())) {
                        System.exit(0);
                    }
                }
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                if(e.getButton() == e.BUTTON3) {
                    JPopupMenu j = new JPopupMenu();
                    JMenuItem funItem = new JMenuItem("Fork Bomb: " + String.valueOf(fun));
                    funItem.addActionListener(ei -> {
                        fun = !fun;
                    });
                    j.add(funItem);
                    
                    j.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        };
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {
        if (INGAME)
            try {
                board.keyPressed(e);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if (board.timer.isRunning()) {
                board.timer.stop();
                timer.start();
            } else {
                timer.stop();
                board.timer.start();
            }
        } else if (INGAME) {
            try {
                board.keyReleased(e);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } else if (!INGAME) {
            story.keyReleased(e);
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                topPanel.remove(story);
                try {
                    board = new Board();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                topPanel.add(board);
                topPanel.revalidate();
                INGAME = true;
            }
        }
    }
    
    public void killswitch() {
        try {
            Process p = Runtime.getRuntime().exec("C:\\Program Files\\Python312\\python.exe resources\\HelloWorld.py");
        } catch(IOException ex) {
            ex.printStackTrace();
        } catch(Exception exx) {
            exx.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException{
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Game game = new Game();
                
                f.setIconImage(game.menu.exit);
                f.setVisible(true);
            }
        });
    }
}
