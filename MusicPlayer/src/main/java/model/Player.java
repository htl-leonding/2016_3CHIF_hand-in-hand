package model;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

/**
 * Created by Stefan Smiljkovic on 24.05.2016.
 */
public class Player {
    File currentFile;
    Clip clip;

    public Player() {

    }

    public void start(File currentFile){
        try{
            AudioInputStream audioInputStream =
                    AudioSystem.getAudioInputStream(
                            this.getClass().getResource(currentFile.getAbsolutePath()));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        }
        catch(Exception ex)
        {
        }
    }

    public void jump(double p){
        clip.setFramePosition((int) (clip.getFrameLength() * p));
    }

    public void stop()
    {
        clip.stop();
    }

    public void play()
    {
        clip.start();
    }
}
