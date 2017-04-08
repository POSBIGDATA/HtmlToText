package io.adrianosb.htmltotext;

import java.io.File;

/**
 * Html to Text
 * @author adriano
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        File path = new File(".");
        if(args.length > 0){
            path = new File(args[0]);
        }
        
        new HtmlToText().start(path);
    }
    
}
