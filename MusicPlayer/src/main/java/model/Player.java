package model;

import com.sun.xml.internal.ws.api.addressing.WSEndpointReference;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.io.IOException;

/**
 * Created by Stefan Smiljkovic on 24.05.2016.
 */
public class Player {
    private File currentFile;
    private MediaPlayer mediaPlayer;
    private Duration duration;
    private SongInfo songInfo;

    public Player() {

    }

    public boolean start(File currentFile){
        this.currentFile = currentFile;

        try{
            Media hit = new Media(currentFile.toURI().toASCIIString());
            mediaPlayer = new MediaPlayer(hit);

            songInfo = new SongInfo(currentFile);


            return true;
        }
        catch(Exception ex)
        {
            System.out.println(ex);
            return false;
        }
    }

    public void jump(double p){
        mediaPlayer.seek(new Duration(mediaPlayer.getTotalDuration().toMillis() * p));
    }

    public void pause() {
        mediaPlayer.pause();
    }

    public void play()
    {
        if(mediaPlayer != null) {
            mediaPlayer.play();
        }
        else
            System.out.println("MediaPlayer is null");
    }

    public boolean isPlaying()
    {
        return mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING);
    }

    public Duration getElapsedTime()
    {
        duration = mediaPlayer.getTotalDuration();
        return duration;
    }

    public SongInfo getSongInfo()
    {
        return songInfo;
    }
}
