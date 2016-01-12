/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qait.automation.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;

import com.qait.automation.coach.behavedemo.getstory.Constants;
import com.qait.automation.coach.behavedemo.getstory.JiraStoryDownloader;



/**
 *
 * @author prashantshukla
 */
public class FileHandler {


    public FileHandler() {
    }

   

    public static File[] getAllFiles(String directory) {
        File files = getFile(directory);
        return files.listFiles();
    }

    public static int getFilesCount(String directory) {
        File files = getFile(directory);
        return files.listFiles().length;
    }

    public static void readFileContent() {

    }

    public static List<String> getFileNames(String filepathname) {
        return getFileNames(filepathname, "");
    }

    public static List<String> getFileNames(String filedirpath, final String filenameendswith) {

        List<String> filenames = new ArrayList<>();

        File[] files = getFile(filedirpath).listFiles();

        for (File file : files) {
            String filename = file.getName();
            if (file.isFile() && filename.toLowerCase().endsWith(filenameendswith.toLowerCase())) {
                filenames.add(filename);
            }
        }

        if (filenames.isEmpty()) {
            throw new NullPointerException("There are no files matching the criteria in directoy: " + filedirpath.toUpperCase());
        }

        return filenames;
    }

    public static void cleanStoryLocation() {
        File storiesDir = getFile(Constants.STORY_LOC);
        if (storiesDir.exists()) {
            try {
                FileUtils.deleteDirectory(storiesDir);

            } catch (IOException ex) {
                Logger.getLogger(JiraStoryDownloader.class
                        .getName()).log(Level.SEVERE, "UNABLE TO DELETE OLD STORIES", ex);
                ex.printStackTrace();
            }
        }
        storiesDir.mkdir();
    }

    public static File getFile(String filepathname) {
        return new File(filepathname);
    }

}
