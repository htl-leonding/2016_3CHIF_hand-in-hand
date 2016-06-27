package model;

/**
 *
 * @author Andrej Sakal
 */
public class Key {

    private String keyString = null;
    private int outputKey = 0;


    public Key(int key, String keyString)
    {
        String[] line = keyString.split(":");
        this.keyString = line[0];
        this.outputKey = key;
    }

    public String getInputString() {
        return keyString;
    }

    public int getOutputKey() {
        return outputKey;
    }
}
