package model;

import java.io.File;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;


public class ImageGallery {

    //The FilesList contains the pictures from a specified directory
    private List<File> filesList = new LinkedList<File>();
    private static ImageGallery model = null;
    final File folder = new File("C:\\Users\\Andrej\\Desktop");

    //Singleton Pattern
    private ImageGallery(){};

    public static ImageGallery getInstance() {
        if (model == null)
        {
            model = new ImageGallery();
        }
        return model;
    }

    /*
        Gets the fileList with the Pictures
     */
    public List<File> getFilesList() {
        return filesList;
    }

    /*
        Searches all Pictures in a folder
     */
    public void searchPicturesInDirectory() {
        File[] filesArray = folder.listFiles();
        List<File> temp = new LinkedList<File>();
        for (File file : filesArray) {
            if (!file.isDirectory()) {
                String path = String.valueOf(file.getPath());

                if (((path.toUpperCase().contains(".JPG")) || (path.toUpperCase().contains(".PNG")) || (path.toUpperCase().contains(".JPEG")) && !temp.contains(new File(path)))) {
                    temp.add(file);
                } else {
                    continue;
                }
            }
        }
        filesList = temp;
    }
}