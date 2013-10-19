package Dictionary;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * User: AnubhawArya
 * Date: 10/19/13
 * Time: 3:45 PM
 */
public class WordListCoordinator {
    ArrayList<WordList> wordList;

    public WordListCoordinator(ArrayList<WordList> wordList) {
        this.wordList = wordList;
    }

    public void refine(String word, String correct, String incorrect) {
        String[] wordArray=word.split(" ");
        for(int i=0;i<wordArray.length;i++)
            wordList.get(i).refine(wordArray[i], correct, incorrect);
    }

    public String letterToGuess(String correct, String incorrect) {
        HashMap<Character, Integer> charCount = new HashMap<Character, Integer>();
        for (WordList tempWordList : wordList) {
            AbstractMap.SimpleEntry<Character,Integer> temp= tempWordList.letterToGuess(correct, incorrect);
            if(charCount.get(temp.getKey())==null)
                charCount.put(temp.getKey(), temp.getValue());
            else
                charCount.put(temp.getKey(),charCount.get(temp.getKey())+temp.getValue());
        }
        int maxInt=0;
        char maxChar = 0;
        for(Map.Entry<Character,Integer> temp:charCount.entrySet())
        if(temp.getValue()>maxInt){
            maxInt = temp.getValue();
            maxChar = temp.getKey();
        }
        return maxChar+"";
    }
}
