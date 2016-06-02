package com.colwiz.nutch.parse.pagefetcher;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Shoaib
 *         Singleton class used to validate and export content
 *         i.e html pages and its parsed content.
 *
 *         Custom implement the methods of ContentValidator
 *         abstract class as required.
 *
 *         Default implementation saves the html pages and
 *         text content in two different folders; raw-content
 *         and text-content.
 */
public class ContentExporter extends ContentValidator {
    private static ContentExporter instance = null;
    private String rawContentPath;
    private String textContentPath;

    private ContentExporter() {
        this.rawContentPath = PageFetcher.FolderPath.concat("raw-content/");
        this.textContentPath = PageFetcher.FolderPath.concat("text_content/");
    }

    public static ContentExporter getInstance() {
        if (instance == null) {
            instance = new ContentExporter();
        }
        return instance;
    }

    /**
     * Validate or modify the content here. Check if this is the content we want
     * to save; and then write to file.
     *
     * @param url         url of the page
     * @param rawContent  raw html content
     * @param textContent text content retrieve from html after parsing
     */
    @Override
    public void extract(String url, String rawContent, String textContent) {
        // retrieve file name from url
        String filename = FetchUtil.getFileNameFromUrl(url);

        if (validateUrl(url)) {
            if (validateHtml(rawContent)) {
                writeToFile(this.rawContentPath, filename, rawContent);
            }
            if (validateText(textContent)) {
                writeToFile(this.textContentPath, filename, textContent);
            }
        }
    }

    /**
     * Writes the content in the file. Path is retrieved from nutch-site.xml
     * "page.export.path" property
     *
     * @param path     path to write a file
     * @param fileName name of the file that will be written
     * @param content  content to write in file
     */
    private void writeToFile(String path, String fileName, String content) {
        File file = new File(path.concat(fileName));

        // Keep check of directories
        String dir = file.getParent();
        if (dir != null) {
            Path dirPath = Paths.get(dir);
            if (!Files.exists(dirPath)) {
                File parent = file.getParentFile();
                if (!parent.mkdirs()) {
                    IOException ex = new IOException();
                    ex.printStackTrace();
                    PageFetcher.LOG.error(ex.getStackTrace());
                }
            }
        } else {
            IOException ex = new IOException();
            ex.printStackTrace();
            PageFetcher.LOG.error(ex.getStackTrace());
        }

        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            PageFetcher.LOG.error(ex.getStackTrace());
        }
    }

    @Override
    protected Boolean validateHtml(String rawContent) {
        return true;
    }

    @Override
    protected Boolean validateText(String textContent) {
        return true;
    }

    @Override
    protected Boolean validateUrl(String url) {
        return super.validateUrl(url);
    }
}
