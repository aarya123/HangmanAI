package HangmanAI;

import WebConnector.WebConnector;

/**
 * User: AnubhawArya
 * Date: 10/19/13
 * Time: 11:58 AM
 */
public class AI {
    public static void main(String[] args) {
        WebConnector webConnector = new WebConnector("arya0@purdue.edu");
        GameState temp = GameState.JSONToGameStart("{\"status\": \"ALIVE\", \"token\": \"6437154\", \"remaining_guesses\": 3, \"state\": \"___ ______ ____\"}");
        System.out.println(webConnector.sendGet(temp, "I").toString());
    }
}
