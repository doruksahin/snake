import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener{

    private final int B_WIDTH = 300;
    private final int B_HEIGHT = 300;
    private final int DOT_SIZE = 10;
    private final int ALL_DOTS = 900;
    private final int RAND_POS = 29;
    private final int DELAY = 140;

    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];

    private int dots;
    private int apple_x;
    private int apple_y;
    private int difficulty;
    private int delay;

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;

    private boolean inGame = true;
    private boolean timePassedFlag = true;
    private long startTime;
    private long endTime;

    private Timer timer;
    ControlHandler ch;

    public Board(String difficulty) {
        ch = new ControlHandler(this);
        ch.position();
        ch.addAll();
        //ch.getEndText().setVisible(true);

        initBoard(difficulty);
    }


    private void initBoard(String difficulty) {

        // Table setup
        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT + 30));
        timer = new Timer(DELAY, this);
        timer.start();
        startTime = System.nanoTime();

        // SnakeGame's initial length is 3.
        dots = 3;
        // init snake's location
        for (int z = 0; z < dots; z++) {
            x[z] = 50;
            y[z] = 50;
        }
        locateApple();

        // easy -> 0, medium -> 1, hard -> 2
        // Hard : Walls, speeds up
        // Medium : Walls
        // Easy : No restriction
        this.difficulty = mappingDifficulty(difficulty);
        initGame();
    }



    private void initGame(){
        move();
        ch.getCurrentSpeed().setText("Speed: " + 0);
    }





    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);

        //Kontrol panelinin oldugu kisim boyandi.
        g.setColor(new Color(120, 120, 120, 200));
        g.fillRect((B_HEIGHT/2), B_WIDTH + 4, 190, 25);
    }


    // Drawing apple and snake
    private void doDrawing(Graphics g) {

        if (inGame) {
            g.drawRect(apple_x, apple_y, 10, 10);

            for (int z = 0; z < dots; z++) {
                if (z == 0) {
                    g.drawRect(x[z], y[z], 10, 10);
                } else {
                    g.drawRect(x[z], y[z], 10, 10);
                }
            }
            Toolkit.getDefaultToolkit().sync(); // Bu nedir?
        } else {
            gameOver(g);
            endGame();
        }

        // if game isnt easy, draw the walls
        if(difficulty != 0) {
            g.setColor(Color.red);
            for (int i = 0; i < B_WIDTH; i++) {
                g.drawRect(0, i, 5, 5);
                g.drawRect(B_HEIGHT - 4, i, 5, 5);
            }

            for (int i = 0; i < B_HEIGHT; i++) {
                g.drawRect(i, 0, 5, 5);
                g.drawRect(i, B_HEIGHT - 4, 5, 5);
            }
        }


    }

    private void gameOver(Graphics g) {

        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);
    }

    // Yilanin kafasi elmaya geldiyse yilan buyudu.
    // TODO Duzgun bir hiz fonksiyonu yazilacak.
    private void checkApple() {
        if ((x[0] == apple_x) && (y[0] == apple_y)) {
            dots++;
            locateApple();
            if(difficulty == 2) {
                setSpeed();
                ch.getCurrentSpeed().setText("Speed: " + ((double)1/(double)delay)*100);
                System.out.println(((double)1/(double)delay)*100);
            }
            else
                ch.getCurrentSpeed().setText("Speed: 1");

        }
    }

    // Yilanin tum vucudu x[2] = x[1], x[1] = x[0] seklinde kaydirilir.
    // Daha sonra gidilen yone gore kafa koordinati degistirilir.
    // Tahtadan ciktiginda olmez, diger taraftan cikar.
    // Bu kisma bir switch eklenebilir.
    private void move() {

        for (int z = dots; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }

        if (leftDirection) {
            x[0] -= DOT_SIZE;
            if(x[0] < 0)
                // if its not hard, no wall restrictions
                if(difficulty != 2)
                    x[0] += B_WIDTH;

        }

        if (rightDirection) {
            x[0] += DOT_SIZE;
            if(difficulty != 2)
                x[0] = x[0] % B_WIDTH;
        }

        if (upDirection) {
            y[0] -= DOT_SIZE;
            if(y[0] < 0)
                if(difficulty != 2)
                    y[0] += B_HEIGHT;

        }

        if (downDirection) {
            y[0] += DOT_SIZE;
            if(difficulty != 2)
                y[0] = y[0] % B_HEIGHT;
        }
    }

    // Tahtadan cikildiginda oyunu bitirir.
    // Carpisma oldugunda oyunu bitirir.
    private void checkCollision() {

        for (int z = dots; z > 0; z--) {
            if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
                inGame = false;
            }
        }

        if (y[0] >= B_HEIGHT) {
            inGame = false;
        }
        if (y[0] < 0) {
            inGame = false;
        }
        if (x[0] >= B_WIDTH) {
            inGame = false;
        }
        if (x[0] < 0) {
            inGame = false;
        }

        if (!inGame) {
            timer.stop();
            endTime = System.nanoTime();

        }
    }


    //Elmanin yeri rastgele ayarlanir.
    private void locateApple() {
        //TODO: Burada X ve Y yilanla kesisemez.
        int r = (int) (Math.random() * RAND_POS);
        apple_x = ((r * DOT_SIZE));

        r = (int) (Math.random() * RAND_POS);
        apple_y = ((r * DOT_SIZE));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkApple();
            checkCollision();
            move();
        }
        repaint();
    }


    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_UP) && (!downDirection)) {
                upDirection = true;
                rightDirection = false;
                leftDirection = false;
            }

            if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
                downDirection = true;
                rightDirection = false;
                leftDirection = false;
            }
        }
    }

    private int mappingDifficulty(String difficulty){
        if(difficulty.equals("Easy"))
            return 0;
        else if(difficulty.equals("Medium"))
            return 1;
        else
            return 2;
    }

    // When snake eats apple, speed up the timer
    private void setSpeed(){
        //delay = (int)(DELAY - (Math.log(dots) / Math.log(1.5)));
        delay = (int)(DELAY - dots * 4);
        timer.setDelay(delay);
    }

    private void endGame(){
        ch.getTimePassed().setVisible(true);
        if(timePassedFlag){
            ch.getTimePassed().setText("Time: " + (endTime - startTime)/10000000);
            timePassedFlag = false;
        }
    }





}