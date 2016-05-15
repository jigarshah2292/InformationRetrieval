   /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Jigar
 */

//Inverted Index class which uses Hashmap within a Hashmap to store the key-->docID-->position
public class InvertedIndex {
    private HashMap<String, HashMap<String,ArrayList<Integer>>> hm;
    
    public InvertedIndex(){
      hm=new HashMap<>();
    }
    
    public HashMap<String, HashMap<String,ArrayList<Integer>>> getHm() {
        return hm;
    }
    
    public void createIndex(String fileContent, String docId){    
            String key;
            String[] data = fileContent.split("\\W+");
            for(int i=0;i<data.length;i++){
                
                key=data[i];
                if(hm.containsKey(key)){
                    if(!hm.get(key).containsKey(docId))
                        hm.get(key).put(docId, new ArrayList<Integer>());
                    hm.get(key).get(docId).add(i);
                }
                else{
                    hm.put(key, new HashMap<String,ArrayList<Integer>>());
                    hm.get(key).put(docId, new ArrayList<Integer>());
                    hm.get(key).get(docId).add(i);
                }
            }  
      }
    
    public void displayIndex(){
        System.out.println(hm);
    }

    
        
}
