/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Jigar
 */

public class Search {
    HashMap<String, HashMap<String,ArrayList<Integer>>> hm;
    ArrayList<ArrayList<Integer>> positionList=new ArrayList<>();
    ArrayList<Integer> position=new ArrayList<>();
    
    public Search(){
        
    }
    
    public Search(HashMap<String, HashMap<String,ArrayList<Integer>>> hm){
        this.hm=hm;
    }
    
//    public void performSearch(String searchString){
//        
//        String[] data = searchString.split(" ");
//        if(data.length==1)
//            singleWordSearch(data[0]);
//        else
//            phraseSearch(searchString);
//    }
    
    
    //Search within inverted index to perform detailed word search that includes position number
    public TreeMap<String, ArrayList<Integer>> singleWordSearch(String singleWord, HashMap<String, HashMap<String,ArrayList<Integer>>> hm){
        TreeMap<String, ArrayList<Integer>> tm=new TreeMap<>();
        if(hm.containsKey(singleWord)){
            Iterator it = hm.get(singleWord).entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                position=(ArrayList) pair.getValue();
//                int frequency=((ArrayList) pair.getValue()).size();
//                positionList.add((ArrayList) pair.getValue());
                //ArrayList<Integer> position= (ArrayList) pair.getValue();
                tm.put((String)pair.getKey(), position);
//                System.out.println("1"+pair.getValue());
//                System.out.println("2"+frequency);
//                System.out.println("3"+(String)pair.getKey());
            }
            System.out.println("tm"+tm);
        }
        return tm;
    }
    
    
    //Search within inverted index to perform quick word search
    public ArrayList singleWordQuickSearch(String singleWord, HashMap<String, HashMap<String,ArrayList<Integer>>> hm){
        ArrayList<String> docIDs=new ArrayList<>();
        if(hm.containsKey(singleWord)){
            Iterator it = hm.get(singleWord).entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                String docID=(String) pair.getKey();
                docIDs.add(docID);
            }
        }
        return docIDs;
    }
    
    public void phraseSearch(String phrase){
        String[] data = phrase.split("\\W+");
        String partWord;
        for(int i=0;i<data.length;i++){              
                partWord=data[i];
                exactMatch(partWord);
        }
    }
    
    public void exactMatch(String partWord){
        
        
    }
    
    public void partialMatch(){
        
    }
    
}
