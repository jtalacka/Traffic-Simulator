/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package traffics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import javax.imageio.ImageIO;
import static map.Grid.elements;
import map.GridSize;

/**
 *
 * @author ToxicFire
 */
public class Character implements Cloneable, Serializable {

    public Position pos;
    public int size = GridSize.size;
    public static BufferedImage image;
    public int direction;
    public int current;
    public int finished;
    public int clicked;
    public int destination;
    LinkedList<Element> road;

    public Character() {
        clicked = 0;
        direction = 0;
        current = 0;
        finished = 0;
        destination = 0;
        pos = new Position(137, 0);
        System.out.println("traffics.Character.<init>()");
    }

    public Character(Element elem) {
        this();
        this.road = new LinkedList<>();
        generateCarRoad(elem);
    }

    public void loadImage() {
        System.out.println(size);
        try {

            image = ImageIO.read(getClass().getResource("/car.png"));
        } catch (IOException ex) {
            System.out.println("Image not found");
        }
    }

    public void renderCar(Graphics g) {
        AffineTransform at = AffineTransform.getTranslateInstance(pos.x, pos.y);
        at.scale(0.4, 0.4);
        if (destination == 0) {
            if (road.get(current).type == 2) {
                at.rotate(Math.toRadians(180), image.getWidth() / 2, image.getHeight() / 2);
            } else if (road.get(current).type == 8) {
                at.rotate(Math.toRadians(0), image.getWidth() / 2, image.getHeight() / 2);
            } else if (road.get(current).type == 4) {
                at.rotate(Math.toRadians(270), image.getWidth() / 2, image.getHeight() / 2);
            } else if (road.get(current).type == 6) {
                at.rotate(Math.toRadians(90), image.getWidth() / 2, image.getHeight() / 2);
            } else if (road.get(current).type == 7) {
                if (road.get(current + 1).type == 8) {

                } else if (road.get(current + 1).type == 1) {
                    at.rotate(Math.toRadians(270), image.getWidth() / 2, image.getHeight() / 2);
                }
            } else if (road.get(current).type == 9) {
                if (road.get(current + 1).type == 7) {

                } else if (road.get(current + 1).type == 6) {
                    at.rotate(Math.toRadians(90), image.getWidth() / 2, image.getHeight() / 2);
                }
            } else if (road.get(current).type == 1) {
                if (road.get(current + 1).type == 3) {
                    at.rotate(Math.toRadians(180), image.getWidth() / 2, image.getHeight() / 2);

                } else if (road.get(current + 1).type == 4) {
                    at.rotate(Math.toRadians(270), image.getWidth() / 2, image.getHeight() / 2);
                }
            } else if (road.get(current).type == 3) {
                if (road.get(current + 1).type == 2) {
                    at.rotate(Math.toRadians(180), image.getWidth() / 2, image.getHeight() / 2);

                } else if (road.get(current + 1).type == 9) {
                    at.rotate(Math.toRadians(90), image.getWidth() / 2, image.getHeight() / 2);
                }
            }

        }
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(image, at, null);

    }

    public void clearList() {
        road.clear();
    }

    public void operate() {
        if (destination == 0) {

            if (clicked != 2) {

                pos.x = road.get(current).x;
                pos.y = road.get(current).y;
                if (current < road.size() - 1) {

                    if (road.get(current + 1).occupied == 0) {
                        road.get(current).occupied = 0;
                        current++;
                        road.get(current).occupied = 1;

                    }

                } else {
                    road.get(current).occupied = 0;
                    finished = 1;
                }

            }
        }
    }

    public void renderLine(Graphics g) {
        if (destination == 0) {
            int tempCurrent = current;
            for (int i = tempCurrent; i < road.size(); i++) {

                if (road.get(i).type == 2) {
                    g.setColor(Color.red);
                    g.fillRect(road.get(i).x + GridSize.size / 2, road.get(i).y + GridSize.size, 2, GridSize.size);

                } else if (road.get(i).type == 8) {
                    g.setColor(Color.red);
                    g.fillRect(road.get(i).x + GridSize.size / 2, road.get(i).y + GridSize.size, 2, GridSize.size);

                } else if (road.get(i).type == 4) {
                    g.setColor(Color.red);
                    g.fillRect(road.get(i).x + GridSize.size, road.get(i).y + GridSize.size / 2, GridSize.size, 2);

                } else if (road.get(i).type == 6) {
                    g.setColor(Color.red);
                    g.fillRect(road.get(i).x + GridSize.size, road.get(i).y + GridSize.size / 2, GridSize.size, 2);

                }
            }
        }
    }

    public void generateCarRoad(Element elem) {
        road=new LinkedList<Element>();
        destination = 0;
        int temp;
        int x = elem.x / GridSize.size;
        int y = elem.y / GridSize.size;
        road.add(elements[y][x]);

        try {
            while (x < GridSize.number && x >= 0 && y < GridSize.number && y >= 0) {
                if (elements[y][x].type == 2) {
                    y++;
                    road.add(elements[y][x]);
                } else if (elements[y][x].type == 8) {
                    y--;
                    road.add(elements[y][x]);
                } else if (elements[y][x].type == 6) {
                    x++;
                    road.add(elements[y][x]);
                } else if (elements[y][x].type == 4) {
                    x--;
                    road.add(elements[y][x]);
                } else if (elements[y][x].type == 7) {
                    temp = random(10);
                    if (temp > 5) {
                        y--;
                    } else {
                        x--;
                    }
                    road.add(elements[y][x]);
                } else if (elements[y][x].type == 9) {
                    temp = random(10);
                    if (temp > 5) {
                        y--;
                    } else {
                        x++;
                    }
                    road.add(elements[y][x]);
                } else if (elements[y][x].type == 1) {
                    temp = random(10);
                    if (temp > 5) {
                        y++;
                    } else {
                        x--;
                    }
                    road.add(elements[y][x]);
                } else if (elements[y][x].type == 3) {
                    temp = random(10);
                    if (temp > 5) {
                        y++;
                    } else {
                        x++;
                    }
                    road.add(elements[y][x]);
                }

            }
        } catch (ArrayIndexOutOfBoundsException ex) {
        }

    }

    public static int random(int value) {
        Random rand = new Random();
        return (ThreadLocalRandom.current().nextInt(0, value));
    }

    protected Object clone() throws CloneNotSupportedException {

        Character temp;

        temp = (Character) super.clone();     

        temp.pos = new Position(this.pos.x, this.pos.y);

        return temp;
    }
    
     private Character(int clicked, int direction, int current, int finished, Position pos){
    this.clicked=clicked;
    this.direction=direction;
    this.current=current;
    this.finished=finished;
    this.pos=new Position(pos.x,pos.y);
    }
    
    public Object copy(){
    
    return  new Character(clicked,direction,current,finished,pos);
    }


}
