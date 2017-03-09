package model;

/**
 *
 * @author Andrej Sakal
 */
public class Key {

    private String keyString = null;
    private int outputKey = 0;
    private boolean pressed;

    public Key(int key, String keyString)
    {
        String[] line = keyString.split(":");
        this.keyString = line[0];
        this.outputKey = key;
        pressed = false;
    }

    public String getInputString() {
        return keyString;
    }

    public int getOutputKey() {
        return outputKey;
    }

    public boolean isPressed() {
        return pressed;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }
}
