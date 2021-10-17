package com.snake;

import javax.swing.*;

public class GameFrame extends JFrame {
    GameFrame(){
        // instance of the GamePanel class called
        this.add(new GamePanel());
        //        title of project
        this.setTitle("Snake");
        //        close when you press close
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //        refusing it to resize from default
        this.setResizable(false);
        //  Takes JFrame and fits it around all components fitted to the frame
         this.pack();
        //        For it to appear
        this.setVisible(true);
        //        for the window to appear in the center
        this.setLocationRelativeTo(null);
    }
}
