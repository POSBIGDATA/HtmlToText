package io.adrianosb.htmltotext;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 *
 * @author adriano
 */
public class HtmlToText {

    public void start(final File file) {

        if (!file.exists()) {
            System.out.println("Path or file doesn't exist!");
            return;
        }

        if (file.isFile()) {
            processFile(file);
        }
        processPath(file);
    }

    private void processFile(final File path) {
        try {
            Document document = Jsoup.parse(path, "utf-8");
            Element element = document.getElementById("i_apologize_for_the_soup");
            if (element != null) {
                if (!element.getElementsByTag("audio").isEmpty()) {
                    element.getElementsByTag("audio").forEach(e -> e.remove());
                }

                File fileTxt = new File(path.getAbsolutePath() + ".txt");
                FileUtils.writeByteArrayToFile(fileTxt, element.text().getBytes());
                System.out.println("OK -> " + fileTxt.getAbsolutePath());
            }
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }

    private void processPath(final File path) {
        try {
            Files.find(path.toPath(),
                    Integer.MAX_VALUE,
                    (filePath, fileAttr) -> fileAttr.isRegularFile()
                    && (filePath.getFileName().toString().toLowerCase().endsWith(".html")
                    || filePath.getFileName().toString().toLowerCase().endsWith(".htm")))
                    .forEach(p -> processFile(p.toFile()));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
