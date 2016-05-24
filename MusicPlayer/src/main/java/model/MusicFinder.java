package model;

import sun.awt.image.ImageWatched;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Stefan Smiljkovic on 24.05.2016.
 */
public class MusicFinder implements Enumeration<File> {

    public static String directoryName = ".\\";
    private List<File> fileList;
    private int cnt;

    public MusicFinder(){
        cnt = 0;
        fileList = searchForMusic();
    }

    public List<File> getFileList() {
        return fileList;
    }

    private List<File> searchForMusic()
    {
        List<File> newListFiles = new LinkedList<File>();
        File dir = new File(directoryName);

        File[] files = dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".mp3");
            }
        });

        for (File f:files)
        {
            newListFiles.add(f);
        }
        return newListFiles;
    }


    public boolean hasMoreElements() {
        return cnt < fileList.size();
    }

    public File nextElement() {
        if(hasMoreElements()) {
            ++cnt;
            return fileList.get(cnt - 1);
        }

        cnt = 0;
        return null;
    }
}
