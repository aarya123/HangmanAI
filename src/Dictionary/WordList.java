package Dictionary;

import java.util.*;

/**
 * User: AnubhawArya
 * Date: 10/19/13
 * Time: 1:14 PM
 */
public class WordList {
    public ArrayList<String> wordList;

    public WordList(ArrayList<String> wordList) {
        this.wordList = wordList;
    }

    public AbstractMap.SimpleEntry<Character, Double> letterToGuess(String correct, String incorrect) {
        HashMap<Character, Integer> charCount = getCharFrequency();
        int total=0;
        for(Map.Entry<Character,Integer> temp:charCount.entrySet())
        {
            total+=temp.getValue();
        }
        char maxChar = 0;
        double maxPercent=0.0;
        for (Map.Entry<Character, Integer> temp : charCount.entrySet()) {
            char tempChar = temp.getKey();
            int tempInt = temp.getValue();
            if (((tempChar >= 65 && tempChar <= 90) || (tempChar >= 97 && tempChar <= 122)) &&  ((double)tempInt)/total > maxPercent
                    && !correct.contains(tempChar + "") && !incorrect.contains(tempChar + "")) {
                maxPercent = ((double)tempInt)/total ;
                maxChar = tempChar;
            }
        }
        return new AbstractMap.SimpleEntry<Character, Double>(maxChar, maxPercent);
    }

    private HashMap<Character, Integer> getCharFrequency() {
        HashMap<Character, Integer> charCount = new HashMap<Character, Integer>();
        for (String tempString : wordList) {
            char[] charArray = tempString.toCharArray();
            for (char tempChar : charArray) {
                if (charCount.get(tempChar) == null)
                    charCount.put(tempChar, 1);
                else
                    charCount.put(tempChar, charCount.get(tempChar) + 1);
            }
        }
        return charCount;
    }

    public void refine(String word, String correct, String incorrect) {
        char[] wordArray = word.toLowerCase().toCharArray(), correctArray = correct.toLowerCase().toCharArray(),
                incorrectArray = incorrect.toLowerCase().toCharArray();
        //Remove words with incorrect letters
        for (int i = 0; i < incorrectArray.length; i++)
            for (int j = 0; j < wordList.size(); j++)
                if (wordList.get(j).contains(incorrectArray[i] + "")) {
                    wordList.remove(j);
                    j--;
                }
        for (int i = 0; i < wordArray.length; i++)
            for (int j = 0; j < wordList.size(); j++) {
                String tempWord = wordList.get(j);
                if (wordArray[i] == '_')
                    //Remove words that have letters we already guessed in incorrect places
                    for (int k = 0; k < correctArray.length; k++) {
                        if (correctArray[k] == tempWord.toCharArray()[i]) {
                            wordList.remove(tempWord);
                            j--;
                        }
                    }
                //Remove words where the word isn't equal to the guess
                else if (wordArray[i] != tempWord.toCharArray()[i]) {
                    wordList.remove(tempWord);
                    j--;
                }
            }
    }
}
