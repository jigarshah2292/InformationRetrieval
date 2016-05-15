/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosine.similarity;

import Business.InvertedIndex;
import Business.PorterAlgo;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import Business.Stopwords;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
/**
 * Class to read documents
 *
 * @author Mubin Shrestha
 */
public class DocumentParser {

    //This variable will hold all terms of each document in an array.
    private List<String[]> termsDocsArray = new ArrayList<String[]>();
    private List<String> allTerms = new ArrayList<String>(); //to hold all terms
    private List<double[]> tfidfDocsVector = new ArrayList<double[]>();
    private List<double[]> querytfidfDocsVector = new ArrayList<double[]>();
    private ArrayList<ArrayList<String>> likeListCollection=new ArrayList<>();
    private HashMap<String,HashMap<String, Double>> nextList = new HashMap<>(); //to hold all next terms
    File[] listOfFiles;
    Stopwords sw=new Stopwords();
    PorterAlgo pa = new PorterAlgo();
    
    
    static HashMap<String, Double> sortByComparator(TreeMap<String, Double> treeMap, final boolean order)
    {

        List<Entry<String, Double>> list = new LinkedList<Entry<String, Double>>(treeMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Entry<String, Double>>()
        {
            public int compare(Entry<String, Double> o1,
                    Entry<String, Double> o2)
            {
                if (order)
                {
                    return o1.getValue().compareTo(o2.getValue());
                }
                else
                {
                    return o2.getValue().compareTo(o1.getValue());

                }
            }
        });
        
        
        Map<String, Double> sortedMap = new LinkedHashMap<String, Double>();
        for (Entry<String, Double> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return (HashMap<String, Double>) sortedMap;
        
    }
    
    /**
     * Method to read files and store in array.
     * @param filePath : source file path
     * @throws FileNotFoundException
     * @throws IOException
     */
    
    public File[] fileRead(String path){
        File folder = new File(path);
        listOfFiles = folder.listFiles();
        return listOfFiles;
    }
    
    
    public String readInput(File file) throws IOException{
        //for (int i = 0; i < listOfFiles.length; i++) {
            //System.out.println("length"+listOfFiles.length);
            //File file = listOfFiles[i];
            //if (file.isFile() && file.getName().endsWith(".txt")) {
                String content = FileUtils.readFileToString(file);
                return content;
            //} 
        //}
        
    }
    
    //allfiles: array of files. 3 files from the folder path
    //sb: entire document contents in one string builder
    //tokenizedTerms: array of string containing all terms of a document at indvidual index
        //so perform tokenization, normalizatio, lemmatization and stemming after above and pass the 
        //tokenized term to respected function calls
    //allTerms: stores all the the words from the 3 files in this 
    //termsDocsArray: Stores 3 array of String files, one for each document
    public List<String[]> parseFiles(String filePath) throws FileNotFoundException, IOException {
        File[] allfiles = new File(filePath).listFiles();
        
        BufferedReader in = null;
        for (File f : allfiles) {
            if (f.isFile() && f.getName().endsWith(".txt")) {
//                in = new BufferedReader(new FileReader(f));
//                StringBuilder sb = new StringBuilder();
//                String s = null;
//                while ((s = in.readLine()) != null) {
//                    sb.append(s);
//                }
                String content = FileUtils.readFileToString(f);
                
                //to clean the data and split by space or special character
                String[] tokenizedTerms = content.replaceAll("[\\W&&[^\\s]]", "").split("\\W+");
                
                boolean flag=true;
//                String[] tokenizedTerms = sb.toString().split("\\W+");   //to get individual terms
                for (String term : tokenizedTerms) {
                    flag=sw.is(term);
                    if(flag==false){
//                        String s1 = pa.step1(term);
//                        String s2 = pa.step2(s1);
//                        String s3= pa.step3(s2);
//                        String s4= pa.step4(s3);
//                        String s5= pa.step5(s4);
                        if (!allTerms.contains(term)) {  //avoid duplicate entry
                            allTerms.add(term);
                        }
                    }
                }
                termsDocsArray.add(tokenizedTerms);
            }
        }   
        for(String s:allTerms){
        System.out.println("TERM-->"+s);
        }
        System.out.println("success1");
        return termsDocsArray;
    }

    /**
     * Method to create termVector according to its tfidf score.
     */
    public void tfIdfCalculator() {
        double tf; //term frequency
        double idf; //inverse document frequency
        double tfidf; //term requency inverse document frequency 
       
        for (String[] docTermsArray : termsDocsArray) {
            double[] tfidfvectors = new double[allTerms.size()];
            int count = 0;
            for (String terms : allTerms) {
                tf = new TfIdf().tfCalculator(docTermsArray, terms);
                idf = new TfIdf().idfCalculator(termsDocsArray, terms);
                tfidf = tf * idf;
                tfidfvectors[count] = tfidf;
                count++;
            }
            tfidfDocsVector.add(tfidfvectors);  //storing document vectors;            
        }
         System.out.println("tfidfDocsVector"+tfidfDocsVector.size());
        
//        for(double[] tfid: tfidfDocsVector){
//            System.out.println("TFID"+tfid);
//            for(int i=0;i<tfid.length;i++)
//                System.out.println("Content "+ tfid[i]);
//            
//        }
    }

    /**
     * Method to calculate cosine similarity between all the documents.
     */
    public Double[] getCosineSimilarity() {
        Double[] cosineResults=new Double[tfidfDocsVector.size()*tfidfDocsVector.size()];
        int iterator=0;
        CosineSimilarity cs=new CosineSimilarity();
        
        for (int i = 0; i < tfidfDocsVector.size(); i++) {
            for (int j = 0; j < tfidfDocsVector.size(); j++) {
                cosineResults[iterator]=cs.cosineSimilarity(tfidfDocsVector.get(i), tfidfDocsVector.get(j));
                
                
//                System.out.println("between " + i + " and " + j + "  =  "
//                                   + new CosineSimilarity().cosineSimilarity
//                                       (
//                                         tfidfDocsVector.get(i), 
//                                         tfidfDocsVector.get(j)
//                                       )
//                                  );
                
                System.out.println("between " + i + " and " + j + "  =  "+cosineResults[iterator]);
                iterator++;
            }
            
            
        }
        for(int i=0;i<cosineResults.length;i++)
            System.out.println(cosineResults[i]);
        return cosineResults;
    }
    
    
    
    //query
    public void parseQuery(String inputQuery){
        String[] tokenizedTerms=inputQuery.replaceAll("[\\W&&[^\\s]]", "").split("\\W+");
        boolean flag=true;
                    
        for (String term : tokenizedTerms) {
            flag=sw.is(term);
                    if(flag==false){
                    if (!allTerms.contains(term)) {  //avoid duplicate entry
                        allTerms.add(term);
                    }
                }
        }
        termsDocsArray.add(tokenizedTerms);
    }
    
    public void querytfidfCalculator(){
        double tf; //term frequency
        double idf; //inverse document frequency
        double tfidf; //term requency inverse document frequency 
       
        for (String[] docTermsArray : termsDocsArray) {
            double[] tfidfvectors = new double[allTerms.size()];
            int count = 0;
            for (String terms : allTerms) {
                tf = new TfIdf().tfCalculator(docTermsArray, terms);
                idf = new TfIdf().idfCalculator(termsDocsArray, terms);
                tfidf = tf * idf;
                tfidfvectors[count] = tfidf;
                count++;
            }
            querytfidfDocsVector.add(tfidfvectors);  //storing document vectors;            
        }
         System.out.println("querytfidfDocsVector"+querytfidfDocsVector.size());
    }
    
    public HashMap<String, Double> findCosineForUserQuery(){
       
       //ArrayList<Double> cosineResults=new ArrayList<>();
       //System.out.println("1"+querytfidfDocsVector.size());
       TreeMap<String, Double> tm=new TreeMap<>();  
       Double cosineResult;
        
       CosineSimilarity cs=new CosineSimilarity();
       for (int j = 0; j < querytfidfDocsVector.size()-1; j++) {
           cosineResult =cs.cosineSimilarity(querytfidfDocsVector.get(j), querytfidfDocsVector.get(querytfidfDocsVector.size()-1));
           tm.put(listOfFiles[j].getName(), cosineResult);
           //cosineResults.add(cosineResult);
                //System.out.println(listOfFiles[5]);
       }
       HashMap<String, Double> hm=sortByComparator(tm, false);
       return hm;
    }
    
    
    public ArrayList<ArrayList<String>> saveLikes(HashMap<String, Double> hm){
        ArrayList<String> likeList=new ArrayList<>();
        Boolean flag=false;
        int i=0;
        for(Map.Entry<String,Double> entry : hm.entrySet()) {
            if(i<=2){
                String key = entry.getKey();
                Double value = entry.getValue();
                
                if(!likeList.contains(key)){
                    likeList.add(key);
                    i++;
                }
            }
            else
                break;
        }
        
        likeListCollection.add(likeList);
        return likeListCollection;
    }
    
    public HashMap<String,HashMap<String, Double>> showNext(HashMap<String, Double> hm, String userQuery){
        nextList.put(userQuery, hm);
        return nextList;
    }
    
    public boolean checkQuery(String userQuery){
        if(nextList!=null && nextList.containsKey(userQuery))
            return true;
        else
            return false;
    }

    public HashMap<String, HashMap<String, Double>> getNextList() {
        return nextList;
    }
    
    
}
