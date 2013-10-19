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
    public ArrayList<WordList> wordList;

    public WordListCoordinator(ArrayList<WordList> wordList) {
        this.wordList = wordList;
    }

    public void refine(String word, String correct, String incorrect) {
        //Refine all word lists
        String[] wordArray = word.split(" ");
        for (int i = 0; i < wordArray.length; i++)
            wordList.get(i).refine(wordArray[i], correct, incorrect);
    }

    public String letterToGuess(String word, String correct, String incorrect) {
        //Get best guess for all lists
        HashMap<Character, Double> charCount = new HashMap<Character, Double>();
        String[] wordArray = word.split(" ");
        for (int i = 0; i < wordList.size(); i++) {
            if (!wordArray[i].contains("_"))
                continue;
            WordList tempWordList = wordList.get(i);
            AbstractMap.SimpleEntry<Character, Double> temp = tempWordList.letterToGuess(correct, incorrect);
            if (charCount.get(temp.getKey()) == null)
                charCount.put(temp.getKey(), temp.getValue());
            else
                charCount.put(temp.getKey(), charCount.get(temp.getKey()) + temp.getValue());
        }
        //Find single best guess
        double maxInt = -1;
        char maxChar = 0;
        for (Map.Entry<Character, Double> temp : charCount.entrySet()) {
            if (temp.getValue() > maxInt) {
                maxInt = temp.getValue();
                maxChar = temp.getKey();
            }
        }
        if (!((maxChar >= 65 && maxChar <= 90) || (maxChar >= 97 && maxChar <= 122)))
            maxChar = (char) -1;
        return maxChar + "";
    }
}
