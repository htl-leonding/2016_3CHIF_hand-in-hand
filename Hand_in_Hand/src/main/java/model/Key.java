 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.event.KeyEvent;

/**
 *
 * @author Andrej Sakal, Stefan Smiljkovic
 */
public class Key {
   
    String keyString = "";
    int outputKey = -1;
    
    
    public String getKeyString() 
    {
        return keyString;
    }

    public Integer getOutputKey() 
    {
        return outputKey;
    }
    
    public Key(String s, int k) 
    {
        this.keyString = s;
        this.outputKey = k;
    }
    
    @Override
    public String toString()
    {
        return keyString + " " + outputKey;
    }
    
}
