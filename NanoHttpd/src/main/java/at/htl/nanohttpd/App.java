package at.htl.nanohttpd;

import fi.iki.elonen.NanoHTTPD;

import java.awt.*;
import java.io.IOException;
import java.util.Map;

public class App extends NanoHTTPD {

    public App() throws IOException {
        super(8080);
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        System.out.println("\nRunning! Point your browers to http://localhost:8080/ \n");
    }

    public static void main(String[] args) {
        try {
            new App();
        } catch (IOException ioe) {
            System.err.println("Couldn't start server:\n" + ioe);
        }
    }



    @Override
    public Response serve(IHTTPSession session) {

        return newFixedLengthResponse("<html><body>" +
                "<h1><center> Hand in Hand <h1> " +
                "<button name='btLeft' onclick=''> Left</button>\n" +
                "<button name='btMiddle' onclick=''> Middle</button>\n" +
                "<button name='btRight' onclick=''> Right</button>\n" +
                "</body></html>");
    }
}
