/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package traffics;

import exceptions.FileException;
import exceptions.WrongElementException;
import exceptions.WrongFileSizeException;
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import map.GridSize;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import static map.Grid.elements;
import static traffics.MyPanel.auto;
import static traffics.MyPanel.character;

/**
 *
 * @author juliu
 */
public class TrafficS implements KeyListener, MouseListener, MouseMotionListener {

    Key key;
    private boolean running;

    /**
     */
    public TrafficS() throws IOException, WrongElementException, WrongFileSizeException {
        
        // TODO code application logic here
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("");
        frame.setUndecorated(true);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setSize(1000, 1000);
        Rectangle window = frame.getBounds();
        new GridSize(window.width, window.height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MyPanel panel;
        panel = new MyPanel();
        frame.add(panel);
        frame.validate();

        frame.addKeyListener(this);
        frame.addMouseListener(this);
        frame.addMouseMotionListener(this);

    }

    public static void main(String[] args) throws IOException {
        try {
            new TrafficS();
        } catch (WrongElementException ex) {
            System.out.println(ex);
        } catch (WrongFileSizeException ex) {
            System.out.println(ex);
            System.out.println("Current size " + ex.currentSize + " should be " + ex.calculatedSize);
        }
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        Key.state = 1;
        if (ke.getKeyCode() == KeyEvent.VK_UP) {
            Key.direction = 8;
        } else if (ke.getKeyCode() == KeyEvent.VK_DOWN) {
            Key.direction = 2;
        }
        if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
            Key.direction = 4;
        } else if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {
            Key.direction = 6;
        }else if(ke.getKeyCode()==KeyEvent.VK_DELETE){
        Key.delete=1;
        }else if(ke.getKeyCode()==KeyEvent.VK_CONTROL){
        Key.generateRoad=1;
        }else if(ke.getKeyCode()==KeyEvent.VK_SHIFT){
        Key.save=1;
        }else if(ke.getKeyCode()==KeyEvent.VK_ALT){
        Key.load=1;
        }
        if (ke.getKeyCode() == KeyEvent.VK_SPACE) {
            Key.direction = 7;
            
            Key.copy=1;
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        Key.generateRoad=0;
        Key.delete=0;
        Key.state = 0;
        Key.direction = 5;
        key.load=0;
        key.save=0;
        Key.copy=0;
    }

    @Override
    public void mouseClicked(MouseEvent me) {

    }

    @Override
    public void mousePressed(MouseEvent me) {
        int tempCheck=0;
        for(int i=0;i<auto.size();i++){
            Character tempChar=auto.get(i);

            
        if (me.getX() > tempChar.pos.x && me.getX() < tempChar.pos.x + GridSize.size) {
            if ((me.getY() - 30) > tempChar.pos.y && (me.getY() - 30) < tempChar.pos.y + GridSize.size) {
                                tempCheck=1;
                if (Key.drag == 0) {
                    Key.drag = 1;
                    character=tempChar;
                } else if (Key.drag == 1) {
             
                    
        if (me.getX() > character.pos.x && me.getX() < character.pos.x + GridSize.size) {
            if ((me.getY() - 30) > character.pos.y && (me.getY() - 30) < character.pos.y + GridSize.size) {
                character.destination=1;
                elements[character.pos.y/GridSize.size][character.pos.x/GridSize.size].occupied=0;
              Key.drag=2;  
                
                
            }
            }
                } else {
                   
                    
                    
                    Key.drag = 0;
                    
                }
                            break;
            }
        }
        }
        if(tempCheck==0){
               int tempX=me.getX()/GridSize.size;
                    int tempY=me.getY()/GridSize.size;
                    if(elements[tempY][tempX].occupied==0&&Key.drag==2){
                    character.road=new LinkedList<>();
                    elements[tempY][tempX].occupied=1;

                    }
        Key.drag=0;

        }
    }

    @Override
    public void mouseReleased(MouseEvent me) {

    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        Key.dragx = me.getX();
        Key.dragy = me.getY();
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        Key.dragx = me.getX();
        Key.dragy = me.getY();
    }

}
