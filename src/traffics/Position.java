/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package traffics;

import java.io.Serializable;

/**
 *
 * @author ToxicFire
 */
public class Position  implements Serializable{
    public int x;
    public int y;
    Position(int x,int y){
    this.x=x;
    this.y=y;
    }
    
}
