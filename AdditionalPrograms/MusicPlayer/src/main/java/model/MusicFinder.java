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

    private int maximum;

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
    public boolean searchForMusic(int levels, int max)
    {
        fileList = searchMusic(directoryName, levels, max);//Saving music-files to list
        return (fileList.size() != 0);//Are files in list
    }

    /**
     * Searches for music
     * @return
     */
    private List<File> searchMusic(String directory, int levels, int max)
    {
        maximum = max;

        List<File> newListFiles = new LinkedList<>();
        File dir = new File(directory);
        int temp;

        //List all files
        File[] files = dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                if(name.endsWith(".mp3") && maximum > 0)//Save only mp3 - files
                {
                    --maximum;
                    return true;
                }
                return false;
            }
        });

        if(files != null) {
        /*Saving array into list*/
            for (File f : files) {
                newListFiles.add(f);
            }
        }


        if(maximum > 0 && levels > 0) {//"Going" deeper
            File direct[] = dir.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return dir.isDirectory();//Filter all directories
                }
            });

            if(direct != null) {
                for (File f : direct) {
                    if (maximum > 0) {
                        newListFiles.addAll(searchMusic(f.getAbsolutePath(), levels - 1, maximum));//Search in next level
                    }
                }
            }
        }

        cnt = 0;

        return newListFiles;
    }

    /**
     * Convert File-Arrays to List<File>
     * @param files
     * @return
     */
    private List<File> arrayToList(File[] files)
    {
        List<File> newList = new LinkedList<>();
        for(File f: files)
        {
            newList.add(f);
        }
        return newList;
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
