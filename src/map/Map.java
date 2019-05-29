/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map;

import exceptions.FileException;
import exceptions.WrongElementException;
import exceptions.WrongFileSizeException;
import java.io.*;

/**
 *
 * @author ToxicFire
 */
public class Map  implements Serializable{

    public static int[][] elem = new int[GridSize.number][GridSize.number];

    Map() throws IOException, WrongFileSizeException,WrongElementException {
        FileInputStream in = null;
        int x = 0;
        int y = 0;

        try {
            in = new FileInputStream("map.txt");
            if (in.available() != (GridSize.number * GridSize.number + 2 * GridSize.number - 2)) {
                throw new WrongFileSizeException("Map file size is incorrect ",in.available(),(GridSize.number * GridSize.number + 2 * GridSize.number - 2));
            }

            int c;
            while ((c = in.read()) != -1) {
                if (c == 10 || c == 13)// new line symbols
                {
                } else {
                    elem[y][x] = c - 48;
                    if (elem[y][x] != 55 && elem[y][x] != 67 && elem[y][x] != 50 && elem[y][x] != 2 && elem[y][x] != 8 && elem[y][x] != 4 && elem[y][x] != 6 && elem[y][x] != 7 && elem[y][x] != 9 && elem[y][x] != 1 && elem[y][x] != 3) {
                        throw new WrongElementException("Wrong element in Map file "+Integer.toString(elem[y][x]));
                    }
                    x++;
                    if (x == GridSize.number) {
                        x = 0;
                        y++;
                    }

                }
                if (x == GridSize.number) {
                    x = 0;
                }
                if (y == GridSize.number) {
                    break;
                }

            }
        } finally {
            if (in != null) {
                in.close();

            }
        }

    }
}
