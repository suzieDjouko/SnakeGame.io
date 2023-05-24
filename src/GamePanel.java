import javax.swing.*;
import java.awt.*;
import  java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH
            = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 30;
    static final int GAME_UNITS= (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 75;
    final int X[]= new int [GAME_UNITS];
    final int Y[]= new int [GAME_UNITS];
    int bodyParts =5; // 5 body parts of the snake
    int applesEaten;
    int applesX;
    int applesY;
    char directions = 'R'; // the snake beginn on the right 'U' up , 'D' down , 'L' left
    boolean running = false;
    Timer timer;
    Random random;
    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();

    }
    public  void startGame(){
        newApple();
        running=true;
        timer = new Timer(DELAY, this);
        timer.start();
        // we start the Game and the timer 

    }
    public  void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);

    }
    public  void draw(Graphics g){

        if (running){
            for (int i=0; i < SCREEN_HEIGHT/ UNIT_SIZE; i++){
                g.drawLine(i*UNIT_SIZE, 0 , i*UNIT_SIZE , SCREEN_HEIGHT);
                g.drawLine(0, i*UNIT_SIZE , SCREEN_WIDTH , i*UNIT_SIZE);
            }
            g.setColor(Color.red);
            g.fillOval(applesX, applesY, UNIT_SIZE,UNIT_SIZE);
            for (int i=0; i< bodyParts; i++){
                if ( i==0){
                    g.setColor(Color.green);
                    g.fillRect(X[i], Y[i],UNIT_SIZE, UNIT_SIZE);
                }
                else if (bodyParts >= 6 && bodyParts < 10)  {
                        g.setColor(new Color(10, 108, 111));
                        g.fillRect(X[i], Y[i],UNIT_SIZE, UNIT_SIZE);
                    }
                    else if (bodyParts >=10 && bodyParts<=15) {
                    g.setColor(new Color(86, 10, 111));
                    g.fillRect(X[i], Y[i], UNIT_SIZE, UNIT_SIZE);
                }
                   else if (bodyParts >15){
                       g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
                       g.fillRect(X[i], Y[i],UNIT_SIZE, UNIT_SIZE);
                    }
                   else {
                    g.setColor(new Color(36, 111, 10));
                    g.fillRect(X[i], Y[i],UNIT_SIZE, UNIT_SIZE);

                   // g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
                }

            }
            g.setColor(Color.blue);
            g.setFont(new Font("Ink Free",Font.BOLD,40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " +applesEaten, (SCREEN_WIDTH -metrics.stringWidth("Score: " +applesEaten)) /2 , g.getFont().getSize());

        }
        else {
            gameOver(g);
        }

    }
    public void newApple(){
        applesX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        applesY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;

    }
    public  void move(){

        for (int i = bodyParts; i>0; i--){
            X[i] = X[i-1];
            Y[i] = Y[i-1];
        }
        switch (directions){
            case 'U':
                Y[0]= Y[0] -UNIT_SIZE;
                break;
            case 'D':
                Y[0]= Y[0] + UNIT_SIZE;
                break;
            case 'L':
                X[0]= X[0] -UNIT_SIZE;
                break;
            case 'R':
                X[0]= X[0] +UNIT_SIZE;
                break;

        }

    }
    public void checkApple(){
        if((X[0] == applesX) && (Y[0] == applesY)){
            bodyParts++;
            applesEaten ++;
            newApple();
        }

    }
    public void checkCollisions(){
        // to check if head collides with Body
        for (int i = bodyParts; i >0 ; i--){
            if((X[0] == X[i]) && (Y[0] == Y[i])){
                running = false;
            }
        }
        // to check if head touches left border
        if (X[0] < 0){
            running = false;
        }
        // to check if head touches right border
        if (X[0] > SCREEN_WIDTH){
            running = false;
        }
        // to check if head touches top border
        if (Y[0] < 0){
            running = false;
        }
        // to check if head touches bottom border
        if (Y[0] > SCREEN_HEIGHT){
            running = false;
        }
        if (!running){
            timer.stop();
        }


    }
    public void gameOver(Graphics g){
        //score
        g.setColor(Color.blue);
        g.setFont(new Font("Ink Free",Font.BOLD,40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: " +applesEaten, (SCREEN_WIDTH -metrics1.stringWidth("Score: " +applesEaten)) /2 , g.getFont().getSize());

        // Game Over text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH -metrics2.stringWidth("Game Over")) /2 , SCREEN_HEIGHT /2);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (running){
            move();
            checkApple();
            checkCollisions();
        }
        repaint();

    }
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT :
                    if (directions != 'R'){
                        directions = 'L';
                    }
                    break;
                case  KeyEvent.VK_RIGHT:
                if (directions != 'L'){
                    directions = 'R';
                }
                break;
                case  KeyEvent.VK_UP:
                    if (directions != 'D'){
                        directions = 'U';
                    }
                    break;
                case  KeyEvent.VK_DOWN:
                    if (directions != 'U'){
                        directions = 'D';
                    }
                    break;
            }

        }
    }
}
