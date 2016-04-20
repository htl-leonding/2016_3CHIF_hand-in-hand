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
    private String actionOfButton = null;
    private int outputKey = 0;
    private static int nuOfObj = 0;
    private int id = 0;

    public Key(int key, String keyString, String actionOfButton)
    {
        this.keyString = keyString;
        this.outputKey = key;
        this.actionOfButton = actionOfButton;
        nuOfObj++;
        id = nuOfObj;
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
        return String.format("%d                                                            %s", id, actionOfButton);
    }
    
}
