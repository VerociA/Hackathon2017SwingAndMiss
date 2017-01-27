package com.greenfox.memorycardgame.memory;




import org.junit.Assert;
import org.junit.Test;

import java.io.File;

/**
 * Created by ${rudolfps} on 2017.01.26..
 */
public class ImageFileProviderTest {


    @Test
    public void testGetImageCount() throws Exception {
        ImageFileProvider provider = new ImageFileProvider("");

        Assert.assertTrue(0 < provider.getImageCount());
    }

    @Test
    public void testGetImageFiles() throws Exception {
        ImageFileProvider provider = new ImageFileProvider("");

        File[] imageFiles = provider.getImageFiles();
        Assert.assertNotNull(imageFiles);
        Assert.assertTrue(0 < imageFiles.length);
    }
}