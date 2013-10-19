package Dictionary;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * User: AnubhawArya
 * Date: 10/19/13
 * Time: 11:49 AM
 */
public class Dictionary {
    HashMap<Integer, ArrayList<String>> dictionary = new HashMap<Integer, ArrayList<String>>();

    public Dictionary() {
        try {
            //Read word list
            String fileName = "DictionaryFinal.txt";
            FileReader inputFile = new FileReader(fileName);
            BufferedReader bufferReader = new BufferedReader(inputFile);
            String line;
            //Add to words list based upon length
            while ((line = bufferReader.readLine()) != null) {
                int length = line.length();
                if (dictionary.get(length) == null)
                    dictionary.put(length, new ArrayList<String>());
                dictionary.get(length).add(line.toLowerCase());
            }
            bufferReader.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<String> get(int length) {
        return (ArrayList<String>) dictionary.get(length).clone();
    }

}
