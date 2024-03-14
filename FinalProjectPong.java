/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class FinalProjectPong extends JPanel {
        int x  = 300;
        int y  = 220;     
        int border = 20;
        boolean Down = true;
        boolean Right = true;
                
                
        private void moveBall() {        
            if (Down == true){
                y++ ;
            }
            if (Down == false){
                y--;
            }
            if(Right == true){
                x++ ;
            }
            if(Right == false){
                x--;
            }
            if (y == getHeight()- border){   
                Down = false; //ball makes the y integer flip 
            }
            if (y==0){
                Down = true;
            }
            if (x == getWidth()- border){
                Right = false;   
            }
        }

        private Timer timer;        // Timer that drives the animation.
        private int width, height;  // The size of the panel -- the values are set
                                    //    the first time the paintComponent() method
                                    //    is called.  This class is not designed to
                                    //    handle changes in size; once the width and
                                    //    height have been set, they are not changed.
                                    //    Note that width and height cannot be set
                                    //    in the constructor because the width and
                                    //    height of the panel have not been set at
                                    //    the time that the constructor is called.
        private Padel padel;
        public FinalProjectPong() {
            
            setBackground( new Color (0,0,0) ); 
            ActionListener action = new ActionListener() {
                // Defines the action taken each time the timer fires.
                public void actionPerformed(ActionEvent evt) {
                    if (padel != null) {
                        padel.updateForNewFrame();
                    }
                    repaint();
                }
            };
            timer = new Timer( 5, action );  // padel every 5 milliseconds.
            addMouseListener( new MouseAdapter() {
                // The mouse listener simply requests focus when the user
                // clicks the panel.
                public void mousePressed(MouseEvent evt) {
                requestFocus();
                }
            } );
            addFocusListener( new FocusListener() {
                // The focus listener starts the timer when the panel gains
                // the input focus and stops the timer when the panel loses
                // the focus.  It also calls repaint() when these events occur.
                public void focusGained(FocusEvent evt) {
                    timer.start();
                    repaint();
                }
                public void focusLost(FocusEvent evt) {
                    timer.stop();
                    repaint();
                }
            } );
            addKeyListener( new KeyAdapter() {
                // The key listener responds to keyPressed events on the panel. Only
                // the up- and down- keys have any effect.  The up- and
                // down-arrow keys move the boat while.
                public void keyPressed(KeyEvent evt) {
                    int code = evt.getKeyCode();  // Which key was pressed?
                    if (code == KeyEvent.VK_DOWN) {
                        // Move the padel down. 
                        padel.centerY+= 15;
                    }
                    else if (code == KeyEvent.VK_UP) {  
                        // Move the padel up.      
                        padel.centerY-= 15;
                    }
                }
            } );  
        }
        public void paintComponent(Graphics g) {

        super.paintComponent(g);  // Fill panel with background color, black.
        
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (padel == null) {
                // The first time that paintComponent is called, it assigns
                // values to the instance variables.
            width = getWidth();
            height = getHeight();
            padel = new Padel();
        }

        if (hasFocus())
            g.setColor(Color.red);
        else {
            g.setColor(Color.white);
            g.drawString("CLICK TO ACTIVATE", 20, 30);
            
        }
        g.drawRect(0,0,width-1,height-1);  // Draw a 3-pixel border.
        g.drawRect(1,1,width-3,height-3);
        g.drawRect(2,2,width-5,height-5);

        padel.draw(g);

    } // end paintComponent()
        
        private class Padel {
        int centerX, centerY;  // Current position of the center of the padel.
        Padel() { // Constructor centers the boat horizontally, 80 pixels from top.
            centerX = 70;
            centerY = 200;
        }
        void updateForNewFrame() { // Makes sure padel has not moved off screen.
            if (centerY > height)
                centerY = 0;
            else if (centerY < 0)
                centerY = height;          
        }
        void draw(Graphics g) {  // Draws the padel at its current location.
            g.setColor(Color.white);
            g.fillRect(centerX, centerY, 7, 70);
        }
    }
        
        
        public void paint(Graphics g){
            super.paint(g);
            g.setColor(Color.white);           
            g.fillOval(x, y, 15, 15);  
        }
            
   
    public static void main(String[] args) throws InterruptedException {
        JFrame newFrame = new JFrame("Pong: Racquetball version"); //creating a window
        FinalProjectPong drawingArea = new FinalProjectPong();
        drawingArea.setBackground(Color.black);     
        newFrame.setSize(700, 500);
        newFrame.setVisible(true);
        newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newFrame.setResizable(false);

        
        
        
        FinalProjectPong game = new FinalProjectPong();
        newFrame.add(game);
        
        
        while (true){
            game.moveBall();
            game.repaint();
            Thread.sleep(10);     
        } 
    }  
}
