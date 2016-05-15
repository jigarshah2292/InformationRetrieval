/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import java.io.IOException;

/**
 *
 * @author Jigar
 */
public class InformationRetrieval {

    /**
     * @param args the command line arguments
     */
    
    //Just a test file
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        
        //Read all text files from document
        //Long startTime1 = System.nanoTime();
        //System.out.println("startTime1"+startTime1);
        FileRead fr=new FileRead("C:\\IR_TestFiles");
        fr.readInput();    
        //System.out.println((System.nanoTime()-startTime1)/1000000000);
        
        //Search
        String searchString1="and";
        String searchString2="this is line number";
        Search search=new Search(fr.getHm());
//        search.performSearch(searchString1);
//        search.performSearch(searchString2);
    }
    
}
