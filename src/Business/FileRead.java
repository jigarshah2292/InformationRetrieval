/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Jigar
 */
public class FileRead {
    
    File folder;
    File[] listOfFiles;
    InvertedIndex iIndex;
    
    public FileRead(){
        
    }
    
    public FileRead(String path){
        folder = new File(path);
        listOfFiles = folder.listFiles();
        iIndex=new InvertedIndex();
    }
    
    public HashMap<String, HashMap<String,ArrayList<Integer>>> getHm() {
        return iIndex.getHm();
    }
    
    
    //Read List of Files to create Inverted Index
    public void readInput() throws IOException{
        for (int i = 0; i < listOfFiles.length; i++) {
            //System.out.println("length"+listOfFiles.length);
            //String name=listOfFiles[i].getName();
            File file = listOfFiles[i];
            if (file.isFile() && file.getName().endsWith(".txt")) {
                String content = FileUtils.readFileToString(file);
                /* do somthing with content */
                //System.out.println("content"+content);
                iIndex.createIndex(content,listOfFiles[i].getName());
            } 
        }
        iIndex.displayIndex();
    }
    
}
