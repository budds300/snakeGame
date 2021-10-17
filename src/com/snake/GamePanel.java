package com.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;


public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH =600;
    static final int SCREEN_HEIGHT =600;
    static final int UNIT_SIZE =25;
    static final int GAME_UNITS =(SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    // The higher the number of delay the slower the game is
    static final int DELAY=75;
    final int x[]= new int[GAME_UNITS];
    final int y[]= new int[GAME_UNITS];
    int bodyParts = 6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction ='R';
    boolean running = false;
    Timer timer;
    Random random;
    GamePanel(){
       random = new Random();
       this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
       this.setBackground(Color.black);
       this.setFocusable(true);
       this.addKeyListener(new MyKeyAdapter());
       startGame();
    }
    public void startGame(){
        newApple();
        running = true;
        timer = new Timer(DELAY,this);
        timer.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public  void draw(Graphics g){
        //if the game is running the following takes place
        if (running) {
            //this is to display grid lines that are continuous in the x and y-axis
         /*   for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }*/
            // This is to set the color, shape and size of the apple
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
            // This is to set the head and body of the snake
            for (int i = 0; i < bodyParts; i++) {
                // color of the head of the snake
                if (i == 0) {
                    g.setColor(Color.green);

                }
                // color of the body of the snake
                else {
                    g.setColor(new Color(45, 180, 0));
                /*    //random colored snake
                    g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));*/

                }
                //body of the snake
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }
            // Score text
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free",Font.BOLD,40));
            //Font metrics is used here to align text to the center of the screen
            FontMetrics metrics = getFontMetrics(g.getFont());
            //getFont().getSize is to put the score at the top of the screen.
            g.drawString("Score "+ applesEaten,  (SCREEN_WIDTH- metrics.stringWidth("Score "+ applesEaten))/2,g.getFont().getSize());
        }
        // if the game doesn't run, the game over method is called
        else{
            gameOver(g);
        }

    }

    public void newApple(){
        //This is for the apple to be placed evenly around the grid
    appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
    appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }
    public void move(){
        //        for loop is to iterate the body parts of the snake
        for (int i=bodyParts;i>0;i--){
            // Shifting coordinates by one to move the snake
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        // controlling the movement of the snake
        switch (direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case'D':
                y[0]=y[0]+UNIT_SIZE;
                break;
            case'L':
                x[0]=x[0]-UNIT_SIZE;
                break;
            case'R':
                x[0]=x[0]+UNIT_SIZE;
                break;
        }
    }
    public void checkApples() {
        // Grabbing of the apples by examining the coordinates of the snake and the coordinates of the apple
        if ((x[0] == appleX) && (y[0] == appleY)) {
            // if the snake gets to the apple it's body size will increase by 1
            bodyParts++;
            // This is to sum up the score of the number of apples eaten by the snake
            applesEaten++;
            // this is to generate new apples after being eaten by the snake
            newApple();
        }
    }
    public void checkCollisions(){
        //This checks if head touches body/ collides with body
        for (int i = bodyParts;i>0;i--){
            if ((x[0]==x[i])&&(y[0]==y[i])){
                running = false;
            }
            //checks if head touches left border
            if (x[0]<0){
                running = false;
            }
            //checks if head touches right border
            if (x[0]>SCREEN_WIDTH){
                running = false;
            }
//            checks if head touches top border
            if (y[0]<0){
                running = false;
            }
            //checks if head touches bottom border
            if (y[0]>SCREEN_HEIGHT){
                running= false;
            }
            if(!running){
                timer.stop();
            }
        }
    }

    public void gameOver(Graphics g){

        // Score text on the game over screen
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,40));
        //Font metrics is used here to align text to the center of the screen
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        //getFont().getSize is to put the score at the top of the screen.
        g.drawString("Score "+ applesEaten,  (SCREEN_WIDTH- metrics1.stringWidth("Score "+ applesEaten))/2,g.getFont().getSize());
        //Game over text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,75));
        //Font metrics is used here to align text to the center of the screen
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over",  (SCREEN_WIDTH- metrics.stringWidth("Game Over"))/2,SCREEN_HEIGHT/2);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
    if (running){
        move();
        checkApples();
        checkCollisions();
    }
    repaint();
    }
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            // This is to enable us to control the snake
            switch (e.getKeyCode()){
                //Limit the user to 90 degree turns
                case KeyEvent.VK_LEFT:
                    if (direction != 'R'){
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D'){
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U'){
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}
