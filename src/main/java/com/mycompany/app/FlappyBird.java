/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.app;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList.*;
import java.util.Random;
import javax.swing.*;
/**
 *
 * @author kmraj
 */
public class FlappyBird extends JPanel implements ActionListener{
      int boardHeight=640;
      int boardWidth=360;
      
      //images used
      Image backgroundImg;
      Image birdImg;
      Image topPipeImg;
      Image bottomPipeImg;
      
      //Bird how it moves through in the window
      int birdX=boardWidth/8;
      int birdY=boardHeight/2;
      int birdWidth=34;
      int birdHeight=24;
      
      class Bird{
          int x=birdX;
          int y=birdY;
          int width=birdWidth;
          int height=birdHeight;
          Image img;
          
          Bird(Image img){
              this.img=img;
          }
      }
      //bird logic
      Bird bird;//global variable
      int velocityY=-6;//to make the bird move-->it moves in -ve pixels in y direction
      
      Timer gameLoop;
      
      FlappyBird(){
          setPreferredSize(new Dimension(boardWidth,boardHeight));
          setBackground(Color.green);
          
          //load images
          backgroundImg=new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
          birdImg =new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
          topPipeImg=new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
          bottomPipeImg=new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();
          //System.out.println(getClass().getResource("."));
          
          bird=new Bird(birdImg);
          
          //game timer
          gameLoop= new Timer(1000/60,this);//1000/60=16.6
          gameLoop.start();
      }
      public void paintComponent(Graphics g){
          super.paintComponent(g);
          draw(g);
      }
      
      public void draw(Graphics g){
          System.out.println("draw");
          //background
          g.drawImage(backgroundImg, 0, 0, boardWidth, boardHeight, null);
          
          //bird image
          g.drawImage(bird.img,bird.x,bird.y,bird.width,bird.height,null);
      }
      
      public void move(){
          //bird movement is updated every sec
          bird.y += velocityY;
          //to make the bird move within the frame
          bird.y=Math.max(bird.y, 0);
      }
      //override
      public void actionPerformed(ActionEvent e){
          move();
          repaint();
      }
}
