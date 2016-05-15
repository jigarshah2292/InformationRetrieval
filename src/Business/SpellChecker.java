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

//checks for mistakes done by user during the search queryand suggestes the user
public class SpellChecker {

    
    //final static String filePath = "d:/desktop/words.txt";
    final static char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private HashMap<String, HashMap<String,ArrayList<Integer>>> hm;

    public SpellChecker(HashMap hm) {
           this.hm=hm;

    }

    public String run(String input) {

         
            
            if (input.equals("")){ 
                System.out.println("Please enter some value");
                return "1";
            }
            if (hm.containsKey(input)) {
                System.out.println("\n" + input + " is spelled correctly");
                return "2";
            } else {
                System.out.print("\""+input+"\" ");
                System.out.print("is not known to the system, ");
                System.out.println(printSuggestions(input));
                return "3";
            }
        
    }

    public String printSuggestions(String input) {
        StringBuilder sb = new StringBuilder();
        ArrayList<String> print = makeSuggestions(input);
        if (print.size() == 0) {
            return "and I have no idea what word you could mean.\n";
        }
        sb.append("perhaps you meant:\n");
        for (String s : print) {
            sb.append("\n  -" + s);
        }
        return sb.toString();
    }

    public ArrayList<String> makeSuggestions(String input) {
        ArrayList<String> toReturn = new ArrayList<>();
        toReturn.addAll(charAppended(input));
        toReturn.addAll(charMissing(input));
        toReturn.addAll(charsSwapped(input));
        return toReturn;
    }

    public ArrayList<String> charAppended(String input) { 
        ArrayList<String> toReturn = new ArrayList<>();
        for (char c : alphabet) {
            String atFront = c + input;
            String atBack = input + c;
            if (hm.containsKey(atFront)) {
                toReturn.add(atFront);
            }
            if (hm.containsKey(atBack)) {
                toReturn.add(atBack);
            }
        }
        return toReturn;
    }

    public ArrayList<String> charMissing(String input) {   
        ArrayList<String> toReturn = new ArrayList<>();

        int len = input.length() - 1;
        //try removing char from the front
        if (hm.containsKey(input.substring(1))) {
            toReturn.add(input.substring(1));
        }
        for (int i = 1; i < len; i++) {
            //try removing each char between (not including) the first and last
            String working = input.substring(0, i);
            working = working.concat(input.substring((i + 1), input.length()));
            if (hm.containsKey(working)) {
                toReturn.add(working);
            }
        }
        if (hm.containsKey(input.substring(0, len))) {
            toReturn.add(input.substring(0, len));
        }
        return toReturn;
    }

    public ArrayList<String> charsSwapped(String input) {   
        ArrayList<String> toReturn = new ArrayList<>();

        for (int i = 0; i < input.length() - 1; i++) {
            String working = input.substring(0, i);// System.out.println("    0:" + working);
            working = working + input.charAt(i + 1);  //System.out.println("    1:" + working);
            working = working + input.charAt(i); //System.out.println("    2:" + working);
            working = working.concat(input.substring((i + 2)));//System.out.println("    FIN:" + working); 
            if (hm.containsKey(working)) {
                toReturn.add(working);
            }
        }
        return toReturn;
    }

}
