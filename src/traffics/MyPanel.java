
package traffics;

import exceptions.FileException;
import exceptions.WrongElementException;
import exceptions.WrongFileSizeException;
import map.Grid;
import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import static map.Grid.elements;
import map.GridSize;

/**
 *
 * @author juliu
 *
 */
public class MyPanel extends JPanel implements Runnable {

    int o = 0;
    int timer = 0;
    Grid grid;
    Color color;
    public static Character character;
    public Character testCar;
    public boolean running;
    LoadSave loadsave;
    Thread thread;
    public static LinkedList<Element> carR = new LinkedList<Element>();
    public static LinkedList<Character> auto = new LinkedList<Character>();

    public MyPanel() throws IOException, WrongElementException, WrongFileSizeException {


        //while(true){
        grid = new Grid();
        sideRoads();
        testCar = new Character(carR.get(Character.random(carR.size())));

        //  pedestrian=new Pedestrian();
        //  car=new Car();
        //  System.out.println(car.toString());
        //  System.out.println(pedestrian.toString());
        // pedestrian.stop(1);
        // car.stop(2);
        //System.out.println(car.toString());
        // System.out.println(pedestrian.toString());
        character = new Character();
        character.loadImage();
        auto.add(testCar);
        
        
        loadsave = new LoadSave();
        thread=new Thread(loadsave);
        thread.start();
        start();
        //}
    }

    public void sideRoads() {
        for (int i = 0; i < GridSize.number; i++) {
            if (Grid.elements[0][i].type == 2) {
                carR.add(Grid.elements[0][i]);
            }
            if (Grid.elements[GridSize.number - 1][i].type == 8) {
                carR.add(Grid.elements[GridSize.number - 1][i]);
            }
            if (Grid.elements[i][0].type == 6) {
                carR.add(Grid.elements[i][0]);
            }
            if (Grid.elements[i][GridSize.number - 1].type == 4) {
                carR.add(Grid.elements[i][GridSize.number - 1]);
            }

        }

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        grid.render(g);
        Character temp;
        for (int i = 0; i < auto.size(); i++) {
            temp = auto.get(i);
            auto.get(i).renderCar(g);
        }

        if (Key.drag == 1) {
            character.renderLine(g);
            g.setColor(Color.red);
            g.drawRect(character.pos.x, character.pos.y, character.size, character.size);
        } else if (Key.drag == 2) {
            g.setColor(Color.green);
            g.drawRect(character.pos.x, character.pos.y, character.size, character.size);
        }

    }

    public void start() {
        running = true;
        new Thread(this, "Main-Thread").start();
        repaint();

    }
    double interpolation = 0;
    final int TICKS_PER_SECOND = 50;
    final int SKIP_TICKS = 1000 / TICKS_PER_SECOND;
    final int MAX_FRAMESKIP = 5;

    public void run() {
        double next_game_tick = System.currentTimeMillis();
        int loops;

        while (true) {
            loops = 0;
            while (System.currentTimeMillis() > next_game_tick
                    && loops < MAX_FRAMESKIP) {
//---------------------------------------

                if (Key.copy == 1 && Key.drag == 1) {

                    Character temp = (Character) character.copy();
                    auto.add(temp);
                    character = temp;
                    character.direction = 1;
                    character.destination = 1;
                    Key.copy = 0;
                    Key.drag = 2;
                }

                if (Key.drag == 0) {
                    int x1 = character.pos.x / GridSize.size;
                    int y1 = character.pos.y / GridSize.size;
                    if (Grid.elements[y1][x1].type == 10 || Grid.elements[y1][x1].type == 11 || Grid.elements[y1][x1].type == 12||Grid.elements[y1][x1].occupied==0) {
                        Key.drag = 2;
                        System.out.println("Can't be placed here");
                    }
                }

                if (Key.drag == 1 && Key.delete == 1) {
                    Key.drag = 0;
                    elements[character.pos.y / GridSize.size][character.pos.x / GridSize.size].occupied = 0;
                    auto.remove(character);
                    
                }
                if (Key.drag == 1) {
                    if (Key.generateRoad == 1) {
                        Key.generateRoad = 0;
                        int tempX = character.pos.x / character.size;
                        int tempY = character.pos.y / character.size;
                        character.generateCarRoad(elements[tempY][tempX]);

                    }

                } else if (Key.drag == 2) {
                    int posX = Key.dragx / GridSize.size;
                    int posY = Key.dragy / GridSize.size;
                    character.pos.x = posX * GridSize.size;
                    character.pos.y = posY * GridSize.size;
                }

                if (o == 3) {
                    o = 0;
                    if (auto.size() < 1000) {
                        testCar = new Character(carR.get(Character.random(carR.size())));
                        auto.add(testCar);
                    }
                    for (int i = 0; i < auto.size(); i++) {
                        auto.get(i).operate();
                        if (auto.get(i).finished == 1) {
                            auto.remove(i);
                        }
                    }
                }
                o++;

                super.repaint();
                //      System.out.println(o);

//---------------------------------------
                next_game_tick += SKIP_TICKS;
                loops++;

            }
            interpolation = (System.currentTimeMillis() + SKIP_TICKS - next_game_tick
                    / (double) SKIP_TICKS);

        }
    }

}
