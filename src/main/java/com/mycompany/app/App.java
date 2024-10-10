/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.app;
import javax.swing.*;
/**
 *
 * @author kmraj
 */
public class App {

    public static void main(String[] args) {
       int boardHeight=640;
       int boardWidth=360;
       JFrame frame=new JFrame("Flappy Bird");
       frame.setSize(boardWidth,boardHeight);
       frame.setLocationRelativeTo(null);
       frame.setResizable(false);
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setVisible(true);
    }
}
