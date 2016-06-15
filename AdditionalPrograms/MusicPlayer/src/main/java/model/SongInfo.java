package model;

import com.sun.org.apache.xerces.internal.impl.dv.xs.DateTimeDV;
import com.sun.xml.internal.ws.api.addressing.WSEndpointReference;
import javafx.collections.ObservableMap;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;

import java.io.File;
import java.io.IOException;
import java.util.Observable;

/**
 * Created by Stefan Smiljkovic on 25.05.2016.
 */
public class SongInfo {
    private AudioFile audioFile;
    private AudioHeader audioHeader;

    public SongInfo(File musicFile) throws Exception
    {
        try {
            /*Load audiofiles*/
            audioFile = AudioFileIO.read(musicFile);
            audioHeader = audioFile.getAudioHeader();
        }
        catch(CannotReadException | IOException | org.jaudiotagger.tag.TagException | ReadOnlyFileException | InvalidAudioFrameException ex)
        {
            System.out.println(ex);
        }
    }

    public String getAlbum() {
        return audioFile.getTag().getFirst(FieldKey.ALBUM);
    }

    public String getTitle() {
        return audioFile.getTag().getFirst(FieldKey.TITLE);
    }

    public String getArtist() {
        return audioFile.getTag().getFirst(FieldKey.ARTIST);
    }

    public String getYear() {
        return audioFile.getTag().getFirst(FieldKey.YEAR);
    }

    public Image getImage(){
        try {
            if(!audioFile.getTag().getArtworkList().isEmpty())//Does exits image?
                return SwingFXUtils.toFXImage(audioFile.getTag().getArtworkList().get(0).getImage(), null);//Load awtImage and convert to fximage
            return null;//No image
        }
        catch (Exception ex)
        {
            System.out.println(ex);
            return null;
        }
    }
}
