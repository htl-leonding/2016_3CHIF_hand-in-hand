package model;

import com.sun.org.apache.xerces.internal.impl.dv.xs.DateTimeDV;
import com.sun.xml.internal.ws.api.addressing.WSEndpointReference;
import javafx.collections.ObservableMap;

import java.util.Observable;

/**
 * Created by Stefan Smiljkovic on 25.05.2016.
 */
public class SongInfo {
    private String title;
    private String album;
    private String artist;
    private String date;

    private ObservableMap metadata;

    public SongInfo(ObservableMap metadata)
    {
        this.metadata = metadata;
    }
}
