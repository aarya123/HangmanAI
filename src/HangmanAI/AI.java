package HangmanAI;

import Dictionary.Dictionary;
import Dictionary.WordList;
import Dictionary.WordListCoordinator;
import WebConnector.WebConnector;

import java.util.ArrayList;

/**
 * User: AnubhawArya
 * Date: 10/19/13
 * Time: 11:58 AM
 */
public class AI {
    WebConnector webConnector;
    Dictionary dictionary;

    public AI(String email) {
        webConnector = new WebConnector(email);
        dictionary = new Dictionary();
    }

    public static void main(String[] args) {
        AI player = new AI("arya0@purdue.edu");
        //player.test();
        //while (true) {
        player.play();
        //}
    }

    private void test() {
        //frosted
        WordList wList = new WordList(dictionary.get(7));
        System.out.println(wList.letterToGuess("", ""));
        wList.refine("_____e_", "e", "");
        System.out.println(wList.letterToGuess("e", ""));
        wList.refine("_____e_", "e", "r");
        System.out.println(wList.letterToGuess("e", "r"));
    }

    private void play() {
        GameState gameState = webConnector.sendGet(null, null);
        String guess = "", incorrect = "", correct = "";

        ArrayList<WordList> wordList = new ArrayList<WordList>();
        String[] words = gameState.getState().split(" ");
        for (int i = 0; i < words.length; i++)
            wordList.add(new WordList(dictionary.get(words[i].length())));
        WordListCoordinator wordListCoordinator = new WordListCoordinator(wordList);

        while (gameState.getStatus() == GameState.HumanState.ALIVE) {
            if (gameState.getState().contains(guess))
                correct += guess;
            else
                incorrect += guess;
            wordListCoordinator.refine(gameState.getState(), correct, incorrect);
            guess = wordListCoordinator.letterToGuess(correct, incorrect);
            System.out.println("Guessing " + guess + " for " + gameState.getState());
            gameState = webConnector.sendGet(gameState, guess);
        }
        System.out.println(gameState.toString());
    }
}
