/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.app;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
/**
 *
 * @author kmraj
 */
public class FlappyBird extends JPanel implements ActionListener,KeyListener{
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
      
      //pipes
      int pipeX=boardWidth;
      int pipeY=0;
      int pipeWidth=64; //scales by 1/6
      int pipeHeight= 512;
      
      class Pipe{
          int x=pipeX;
          int y=pipeY;
          int width=pipeWidth;
          int height=pipeHeight;
          Image img;
          boolean passed=false;
          
          Pipe(Image img){
              this.img=img;
          }
      }
      //bird game logic
      Bird bird;//global variable
      int velocityX=-4;//moves the pipe towrds -x so that bird moves ryt
      int velocityY=0;//to make the bird move-->it moves in -ve pixels in y direction
      int gravity =1;
      
      ArrayList<Pipe> pipes;
      Random random= new Random();
      
      Timer gameLoop;
      Timer placePipesTimer;
      
      boolean gameOver=false;
      double score=0;
      
      FlappyBird(){
          setPreferredSize(new Dimension(boardWidth,boardHeight));
          //setBackground(Color.green);
          setFocusable(true);
          addKeyListener(this);
          
          //load images
          backgroundImg=new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
          birdImg =new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
          topPipeImg=new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
          bottomPipeImg=new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();
          //System.out.println(getClass().getResource("."));
          
          bird=new Bird(birdImg);
          pipes= new ArrayList<Pipe>();
          
          
          //place pipes timer
          placePipesTimer = new Timer(1500,new ActionListener(){
              public void actionPerformed(ActionEvent e){
                  placePipes();
              }
          });
          placePipesTimer.start();
          
          
          
          //game timer
          gameLoop= new Timer(1000/60,this);//1000/60=16.6
          gameLoop.start();
      }
      
      
       public void placePipes(){
           //(0-1)*pipeHeight/2->(0-256)
           //512/4=128
           //0-128-(0-256)-->pipeHeight/4->3/4 pipeHeight
           int randomPipeY= (int)(pipeY-pipeHeight/4-Math.random()*(pipeHeight/2));
           int openingSpace=boardHeight/4;
          Pipe topPipe=new Pipe(topPipeImg);
          topPipe.y=randomPipeY;
          pipes.add(topPipe);
          
          Pipe bottomPipe=new Pipe(bottomPipeImg);
          bottomPipe.y=topPipe.y+pipeHeight+openingSpace;
          pipes.add(bottomPipe);
      }
       
       
      public void paintComponent(Graphics g){
          super.paintComponent(g);
          draw(g);
      }
      
     
      
      public void draw(Graphics g){
          //System.out.println("draw");
          
          //background
          g.drawImage(backgroundImg, 0, 0, boardWidth, boardHeight, null);
          
          //bird image
          g.drawImage(bird.img,bird.x,bird.y,bird.width,bird.height,null);
          
          //pipes
          for(int i=0;i<pipes.size();i++){
              Pipe pipe=pipes.get(i);
              g.drawImage(pipe.img,pipe.x,pipe.y,pipe.width,pipe.height,null);
          }
          
          //scores
          g.setColor(Color.black);
          g.setFont(new Font("Arial",Font.ITALIC,32));
          if(gameOver){
              g.drawString("GAME OVER: "+String.valueOf((int)score), 10, 35);
          }
          else{
              g.drawString(String.valueOf((int)score), 10, 35);
          }
      }
      
      public void move(){
          velocityY += gravity;
          //bird movement is updated every sec
          bird.y += velocityY;
          //to make the bird move within the frame
          bird.y=Math.max(bird.y, 0);
          
          //pipes
          for(int i=0;i<pipes.size();i++){
              Pipe pipe=pipes.get(i);
              pipe.x+=velocityX;
              
              if(!pipe.passed && bird.x>pipe.x+pipe.width){
                  pipe.passed=true;
                  score+=0.5;//there are two pipes so score is split by half for each pipe->1 for each set of pipe
              }
              
              
              if(collision(bird,pipe)){
                  gameOver=true;
              }
          }
          
          if(bird.y>boardHeight){
              gameOver=true;
          }
          
          
          
      }
      
      public boolean collision(Bird a,Pipe b){
          return a.x<b.x+b.width&&  //a's top left corner doesn't reach b's top right corner
                  a.x+a.width>b.x&&  //a's top ryt corner passes b's top left corner
                  a.y<b.y+b.height&&  //a's top left corner doesn't reach b's bottom left corner
                  a.y+a.height>b.y;   //a's bottom left corner passes b's top left corner
      }
      //override
      public void actionPerformed(ActionEvent e){
          move();
          repaint();
          if(gameOver){
              placePipesTimer.stop();//this stops adding more pipes to the ArrayList
              gameLoop.stop();
          }
      }
      
      
      //override
      public void keyPressed(KeyEvent e){
          if(e.getKeyCode() == KeyEvent.VK_SPACE){
              velocityY = -9;
              if(gameOver){
                  //restart the game by resetting the condition
                  bird.y=birdY;
                  velocityY=0;
                  pipes.clear();
                  score=0;
                  gameOver=false;
                  gameLoop.start();
                  placePipesTimer.start();
              }
          }
      }
     public void keyTyped(KeyEvent e){}
     
     //override
     public void keyReleased(KeyEvent e){}
}
