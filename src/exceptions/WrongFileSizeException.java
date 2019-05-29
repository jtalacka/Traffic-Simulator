/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author juliu
 */
public class WrongFileSizeException extends FileException{
    public int currentSize;
    public int calculatedSize;
    public WrongFileSizeException(String error,int currentSize,int calculatedSize) {
            super(error);
        this.currentSize=currentSize;
    this.calculatedSize=calculatedSize;
    }

}
