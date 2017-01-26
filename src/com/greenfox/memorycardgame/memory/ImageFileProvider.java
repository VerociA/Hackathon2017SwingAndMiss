package com.greenfox.memorycardgame.memory;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by ${rudolfps} on 2017.01.26.
 */

/**
 * Provider class to return all supported image files in the given path
 */
public final class ImageFileProvider {

    private File dirRoot;

    /**
     *
     * @param path The path of the directory containing the image files
     */
    public ImageFileProvider(String path) {
        setImageRoot(path);
    }

    /**
     *
     * @param path The path of the directory containing the image files
     */
    public void setImageRoot(String path) {
        dirRoot = new File(path);
    }

    public void setImageRoot(File rootDirectory) {
        dirRoot = rootDirectory;
    }

    public String getImageRootPath() {
        if (dirRoot == null) return "";
        return dirRoot.getPath();
    }

    /**
     *
     * @return the number of image files found in the given directory
     */
    public int getImageCount() {
        return getImageFiles().length;
    }

    /**
     *
     * @return an array of PNG or JPG image files in the directory specified by path
     */
    public File[] getImageFiles() {
        if ((! dirRoot.exists()) || (! dirRoot.isDirectory()) || (!dirRoot.canRead())) {
            return new File[]{};
        }
        // enumerate all images in directory
        final File[] files = dirRoot.listFiles((dir, name) -> {
            if (name.toLowerCase().endsWith(".png") || name.toLowerCase().endsWith(".jpg")) return true;
            return false;
        });

        Collections.shuffle(Arrays.asList(files));
        return files;
    }
}
