/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package traffics;

import map.Map;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.time.Clock;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 *
 * @author juliu
 */
public class Element  implements Serializable,Cloneable{
    public int imagesize;
    public final int x, y;
    private static BufferedImage image;
    public int type;
    public int occupied=0;
    public static int size;
           static int count=0;

    public Element(int size,int x,int y) {
        this.imagesize=100;
        this.size = size;
        this.x = x*size;
        this.y = y*size;
        this.occupied=0;
        this.type=Map.elem[x][y];


        count++;

        random(x,y);
        }    
    public Element(String file){
    x=0;
    y=0;
    try {
  
            image = ImageIO.read(getClass().getResource(file));
        } catch (IOException ex) {
                System.out.println("Image not found");
        }
    }


    private void random(int x,int y) {

             int n=Map.elem[y][x];
        
            if(n==55){//grass
                this.type=10;
            }else if(n==67){
            this.type=11;//sidewalk
            }else if(n==50){//building
            this.type=12;
            }else if(n==2){//down
            this.type=2;
            }else if(n==8){//up
            this.type=8;
            }else if(n==4){//left
            this.type=4;
            }else if(n==6){//right
            this.type=6;
            }else if(n==7){//left&up
            this.type=7;
            }else if(n==9){//right&up
            this.type=9;
            }else if(n==1){//left&down
            this.type=1;
            }else if(n==3){//right&down
            this.type=3;
            }
 

      
    }
    public void renderTile(Graphics g){
        if (type == 11) {
            
            g.drawImage(image, x, y, x + size , y + size,0, 0, imagesize -1 , imagesize, null);
        }else if(type ==10){
            g.drawImage(image, x, y, x + size , y + size,imagesize, 0, imagesize*2 - 1, imagesize, null);
        }else if(type ==12){//building
          g.drawImage(image, x, y, x + size , y + size,imagesize*10, 0, imagesize*11 - 1, imagesize, null);
        }else if(type ==8){//up
            g.drawImage(image, x, y, x + size , y + size,imagesize*2, 0, imagesize*3 - 1, imagesize, null);
        }else if(type ==2){//down
            g.drawImage(image, x, y, x + size , y + size,imagesize*3, 0, imagesize*4 - 1, imagesize, null);
        }else if(type ==6){//right
            g.drawImage(image, x, y, x + size , y + size,imagesize*4, 0, imagesize*5 - 1, imagesize, null);
        }else if(type ==4){//left
           g.drawImage(image, x, y, x + size , y + size,imagesize*5, 0, imagesize*6 - 1, imagesize, null);
        }else if(type ==7){//up left
            g.drawImage(image, x, y, x + size , y + size,imagesize*9, 0, imagesize*10 - 1, imagesize, null);
            
        }else if(type ==9){//up right
            g.drawImage(image, x, y, x + size , y + size,imagesize*7, 0, imagesize*8 - 1, imagesize, null);
        }else if(type ==1){//down left
            g.drawImage(image, x, y, x + size , y + size,imagesize*8, 0, imagesize*9 - 1, imagesize, null);
        }else if(type ==3){//down right
            g.drawImage(image, x, y, x + size , y + size,imagesize*6, 0, imagesize*7 - 1, imagesize, null);
        }
    }
        
  

}
