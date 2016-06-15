package model;

import javafx.scene.control.Alert;
import sun.awt.image.ImageWatched;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Stefan Smiljkovic on 24.05.2016.
 */
public class MusicFinder implements Enumeration<File> {

    public String directoryName = "";
    private List<File> fileList;
    private int cnt;

    public MusicFinder(){
        cnt = 0;
    }

    public String getDirectoryName()
    {
        return directoryName;
    }

    public void setDirectoryName(String name)
    {
        directoryName = name;
    }

    public List<File> getFileList() {
        return fileList;
    }

    public int getPosition()
    {
        return cnt;
    }

    public void setPosition(int i)
    {
        cnt = i;
    }

    /**
     * Searches for music
     * @return
     */
    public boolean searchForMusic()
    {
        fileList = searchMusic();//Saving music-files to list
        return (fileList.size() != 0);//Are files in list
    }

    /**
     * Searchung for music
     * @return
     */
    private List<File> searchMusic()
    {
        List<File> newListFiles = new LinkedList<>();
        File dir = new File(directoryName);

        //List all files
        File[] files = dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".mp3");//Save only mp3 - files
            }
        });

        /*Saving array into list*/
        for (File f:files)
        {
            newListFiles.add(f);
        }
        cnt = 0;

        return newListFiles;
    }

    /**
     * Returns true, if elements are available
     * @return
     */
    public boolean hasMoreElements() {
        return cnt < fileList.size();//Is end of list reached?
    }

    /**
     * Returns next File
     * @return
     */
    public File nextElement() {
        if(hasMoreElements()) {//Not last element
            ++cnt;
            return fileList.get(cnt - 1);//Returns next element
        }

        //cnt = 0;//Setting count to first element
        return null;
    }

    /**
     * Get previous element
     * @return
     */
    public File prevElement()
    {
        if (cnt > 1)//Not first element
        {
            --cnt;
            System.out.println(cnt - 1);
            return fileList.get(cnt - 1);
        }

        //cnt = fileList.size() - 1;//Seting count to last element
        System.out.println(cnt);
        return null;
    }
}
