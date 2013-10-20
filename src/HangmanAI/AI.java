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
        //Sets up game and plays it
        AI player = new AI("arya0@purdue.edu");
        double plays = 0, wins = 0;
        while (true) {
            wins = player.play() ? wins + 1 : wins;
            plays++;
            System.out.println("Win Percent = " + wins / plays);
        }
    }

    private boolean play() {
        //Starts by getting game
        GameState gameState = webConnector.sendGet(null, null);
        String guess = "", correct = "", incorrect = "";
        //Get dictionaries set up
        ArrayList<WordList> wordList = new ArrayList<WordList>();
        String[] words = gameState.getState().split(" ");
        for (int i = 0; i < words.length; i++)
            wordList.add(new WordList(dictionary.get(words[i].length())));
        WordListCoordinator wordListCoordinator = new WordListCoordinator(wordList);
        //Start guessing
        while (gameState.getStatus() == GameState.HumanState.ALIVE) {
            //Track guesses
            if (gameState.getState().contains(guess))
                correct += guess;
            else
                incorrect += guess;
            //Refine and guess
            wordListCoordinator.refine(gameState.getState(), correct, incorrect);
            guess = wordListCoordinator.letterToGuess(gameState.getState(), correct, incorrect);
            if (guess.equals("-1")) {
                System.out.println("No more guesses :'(\nQuitting game...");
                return false;
            }
            System.out.println("Guessing " + guess + " for " + gameState.getState()
                    + " where correct = " + correct + " and incorrect = " + incorrect);
            gameState = webConnector.sendGet(gameState, guess);
        }
        //Final game state
        if (gameState.getStatus() == GameState.HumanState.DEAD)
            incorrect += guess;
        else
            correct += guess;
        System.out.println(gameState.toString() + ", correct = " + correct + ", incorrect = " + incorrect);
        if (gameState.getStatus() == GameState.HumanState.FREE)
            return true;
        return false;
    }
}
