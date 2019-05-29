/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map;

import java.io.Serializable;

/**
 *
 * @author ToxicFire
 */
public class GridSize  implements Serializable{
    
    public static int number=30;
    public static int size;
    
    public GridSize(int WindowX,int WindowY){
        if(WindowX>WindowY){
        this.size=WindowY/number;
        }else{
        this.size=WindowX/number;
        
        }
    }
        
    
}
