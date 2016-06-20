package model;

import java.io.File;
import java.util.LinkedList;
import java.util.List;


public class ImageGallery {

    //The FilesList contains the pictures from a specified directory
    private List<File> filesList = new LinkedList<File>();
    //Instance of model
    private static ImageGallery model = null;
    //The Folder which has to be scanned for Images
    private File rootFolder = new File("");
    //Max size of fileList
    private int maxSize = 50;


    //Singleton Pattern
    private ImageGallery(){}

    public static ImageGallery getInstance() {
        if (model == null)
        {
            model = new ImageGallery();
        }
        return model;
    }

    /**
     * Returns FileList with the contained pictures
     * @return
     */
    public List<File> getFilesList() {
        return filesList;
    }

    /**
     * Searches all Pictures in a folder
     */
    public boolean searchPicturesInDirectory() {
        filesList = getPicturesInDirectory(getFolder(), 0);
        if (maxSize != 50)
            return true;
        else
            return false;
    }

    private List<File> getPicturesInDirectory(File dir, int actLevel)
    {
        File[] filesArray = dir.listFiles();
        List <File> temp = new LinkedList<File>();
        if(actLevel < 3 && maxSize > 0) {
            for (File file : filesArray) {
                if (file.isDirectory()) {
                    temp.addAll(getPicturesInDirectory(file, actLevel + 1));
                } else {
                    String path = String.valueOf(file.getPath());
                    if (((path.toUpperCase().contains(".JPG")) || (path.toUpperCase().contains(".PNG")) || (path.toUpperCase().contains(".JPEG")) && !temp.contains(new File(path)))) {
                        temp.add(file);
                        maxSize--;
                    }
                }
                if(maxSize <= 0)
                    return temp;
            }
        }
        return temp;
    }

    public File getFolder()
    {
        return rootFolder;
    }

    public void setFolder(File folder) {
        this.rootFolder = folder;
        maxSize = 50;
        filesList = null;
    }
}