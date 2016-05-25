package model;

import com.sun.org.apache.xerces.internal.impl.dv.xs.DateTimeDV;
import com.sun.xml.internal.ws.api.addressing.WSEndpointReference;
import javafx.collections.ObservableMap;
import javafx.scene.image.Image;

import java.util.Observable;

/**
 * Created by Stefan Smiljkovic on 25.05.2016.
 */
public class SongInfo {
    private ObservableMap metadata;

    public SongInfo(ObservableMap metadata)
    {
        this.metadata = metadata;
        try {
            wait(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getAlbum() {
        if(metadata.containsKey("album"))
            System.out.println("JA");
        System.out.println("Nö");
        return (String) metadata.get("album");
    }

    public String getTitle() {
        if(metadata.containsKey("title"))
            System.out.println("JA");
        System.out.println("Nö");
        String temp = (String) metadata.get("title");
        System.out.println(temp);
        return temp;
    }

    public String getArtist() {
        if(metadata.containsKey("artist"))
            System.out.println("JA");
        System.out.println("Nö");

        if(metadata.isEmpty())
            System.out.println("Aufgelutscht");
        return (String) metadata.get("artist");
    }

    public String getDate() {
        if(metadata.containsKey("year"))
            System.out.println("JA");
        System.out.println("Nö");
        return (String) metadata.get("year");
    }

    public Image getImage(){
        if(metadata.containsKey("image"))
            System.out.println("JA");
        System.out.println("Nö");
        return (Image) metadata.get("image");
    }
}
