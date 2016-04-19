 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Andrej Sakal, Stefan Smiljkovic, Gabriel Ionescu
 */
public class Key {
   
    private String keyString = null;
    private int outputKey = 0;
    
    public Key(int key, String keyString) 
    {
        this.keyString = keyString;
        this.outputKey = key;
    }

    public String getInputString() {
        return keyString;
    }

    public void setInputKey(String keyString) {
        this.keyString = keyString;
    }

    public int getOutputKey() {
        return outputKey;
    }

    public void setOutputKey(int outputKey) {
        this.outputKey = outputKey;
    }
    
    @Override
    public String toString()
    {
        return keyString + " " + outputKey;
    }
    
}
