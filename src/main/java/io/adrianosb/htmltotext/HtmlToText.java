package io.adrianosb.htmltotext;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 *
 * @author adriano
 */
public class HtmlToText {

    public void start(final File path) {

        if (!path.exists()) {
            System.out.println("Path or file doesn't exist!");
            return;
        }

        if (path.isFile()) {
            getHtmlAndSaveNewFileTxt(path);
        }
        
        try {
            findAllHtmls(path).forEach(p -> getHtmlAndSaveNewFileTxt(p.toFile()));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Get HTML and save new file txt 
     * @param path 
     */
    private void getHtmlAndSaveNewFileTxt(final File path) {
        try {
            // file HTML to object Document
            Document document = Jsoup.parse(path, "utf-8");
            //get contents of the book
            Element element = document.getElementById("i_apologize_for_the_soup");
            if (element != null) {
                if (!element.getElementsByTag("audio").isEmpty()) {
                    //remove audio
                    element.getElementsByTag("audio").forEach(e -> e.remove());
                }

                //create new file txt
                File fileTxt = new File(path.getAbsolutePath() + ".txt");
                //save clean content in txt
                FileUtils.writeByteArrayToFile(fileTxt, element.text().getBytes());
                
                System.out.println("OK -> " + fileTxt.getAbsolutePath());
            }
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }

    /**
     * Find all HTMLs
     * @param path
     * @return
     */
    private Stream<Path> findAllHtmls(final File path) throws IOException {
        return Files.find(path.toPath(),
                Integer.MAX_VALUE,
                (filePath, fileAttr) -> fileAttr.isRegularFile()
                && (filePath.getFileName().toString().toLowerCase().endsWith(".html")
                || filePath.getFileName().toString().toLowerCase().endsWith(".htm")));
    }

}
