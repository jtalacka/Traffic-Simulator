/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map;

import exceptions.FileException;
import exceptions.WrongElementException;
import exceptions.WrongFileSizeException;
import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.io.Serializable;
import traffics.Element;

/**
 *
 * @author juliu
 */
public class Grid implements Serializable{

    public static Element elements[][] = new Element[GridSize.number][GridSize.number];
    int type;

    public Grid() throws IOException,WrongFileSizeException,WrongElementException{
                Map map=new Map();
        generateGrid();
    }

    private void generateGrid() {
        Element elem=new Element("/Tile.png");
        
        for (int y = 0; y < GridSize.number; y++) {
            for (int x = 0; x < GridSize.number; x++) {
                elements[y][x] = new Element(GridSize.size,x,y);
            }
        }
    }

    public int returnX(int x, int y) {

        return elements[y][x].x;
    }

    public int returnY(int x, int y) {

        return elements[y][x].y;
    }

    public int returnSize(int x, int y) {
        return elements[y][x].size;
    }

    public int returnColor(int x, int y) {
        return elements[y][x].type;
    }

    public void render(Graphics g) {

        for (int y = 0; y < GridSize.number; y++) {
            for (int x = 0; x < GridSize.number; x++) {
                elements[y][x].renderTile(g);
                

            }
        }

    }

}
