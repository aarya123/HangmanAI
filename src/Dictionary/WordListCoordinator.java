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
        String[] wordArray = word.split(" ");
        for (int i = 0; i < wordArray.length; i++)
            wordList.get(i).refine(wordArray[i], correct, incorrect);
    }

    public String letterToGuess(String word, String correct, String incorrect) {
        HashMap<Character, Integer> charCount = new HashMap<Character, Integer>();
        String[] wordArray = word.split(" ");
        for (int i = 0; i < wordList.size(); i++) {
            if (!wordArray[i].contains("_"))
                continue;
            WordList tempWordList = wordList.get(i);
            AbstractMap.SimpleEntry<Character, Integer> temp = tempWordList.letterToGuess(correct, incorrect);
            if (charCount.get(temp.getKey()) == null)
                charCount.put(temp.getKey(), temp.getValue());
            else
                charCount.put(temp.getKey(), charCount.get(temp.getKey()) + temp.getValue());
        }
        int maxInt = 0;
        char maxChar = 0;
        for (Map.Entry<Character, Integer> temp : charCount.entrySet()) {
            if (temp.getValue() > maxInt) {
                maxInt = temp.getValue();
                maxChar = temp.getKey();
            }
        }
        if (!((maxChar >= 65 && maxChar <= 90) || (maxChar >= 97 && maxChar <= 122)))
            maxChar = (char) -1;
        return maxChar + "";
    }

    public String toString(String correct, String incorrect) {
        String toReturn = "size=" + wordList.size() + "\n";
        for (int i = 0; i < wordList.size(); i++) {
            toReturn += " guessing for word #" + i + " is " + wordList.get(i).letterToGuess(correct, incorrect) + "\n";
        }
        return toReturn;
    }
}
