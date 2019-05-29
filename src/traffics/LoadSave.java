/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package traffics;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static map.Grid.elements;
import static traffics.MyPanel.auto;

/**
 *
 * @author ToxicFire
 */
public class LoadSave extends Thread {

    public int save = 0;
    public int load = 0;

    public synchronized void changeValue() {
        if (Key.save != 0 || Key.load != 0) {
            this.save = Key.save;
            this.load = Key.load;
            Key.save = 0;
            Key.load = 0;
        }
    }

    public void run() {

        while (true) {
            changeValue();
            if (this.save == 1) {
                try {
                    FileOutputStream fileOut= new FileOutputStream(new File("file.txt"));
                    ObjectOutputStream out = new ObjectOutputStream(fileOut);
                    out.writeObject(elements);
                    out.writeObject(auto);
                    out.close();
                    fileOut.close();
                    System.out.printf("File is saved\n");
                } catch (IOException i) {
                    i.printStackTrace();
                }

                this.save = 0;
            } else if (this.load == 1) {

                try {
                    FileInputStream fileIn = new FileInputStream("file.txt");
                    ObjectInputStream in = new ObjectInputStream(fileIn);
                    elements = (Element[][]) in.readObject();
                    auto = (LinkedList<Character>) in.readObject();
                    in.close();
                    fileIn.close();
                    System.out.printf("Loaded\n");
                } catch (IOException i) {
                    i.printStackTrace();
                    return;
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(MyPanel.class.getName()).log(Level.SEVERE, null, ex);
                }

                this.load = 0;

            }

        }

    }
}
