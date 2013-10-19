package HangmanAI;

import com.google.gson.Gson;

/**
 * User: AnubhawArya
 * Date: 10/19/13
 * Time: 11:37 AM
 */
public class GameState {
    static Gson gson = new Gson();
    static String BASE_TO_STRING = "status = %s, token = %d, remaining_guesses = %d, state = %s";
    HumanState status;
    String state;
    int token, remaining_guesses;

    public GameState(HumanState status, int token, int remaining_guesses, String state) {
        this.status = status;
        this.token = token;
        this.remaining_guesses = remaining_guesses;
        this.state = state;
    }

    public static GameState JSONToGameStart(String JSON) {
        return gson.fromJson(JSON, GameState.class);
    }

    public HumanState getStatus() {
        return status;
    }

    public String getState() {
        return state;
    }

    public int getToken() {
        return token;
    }

    public int getRemaining_guesses() {
        return remaining_guesses;
    }

    public String toString() {
        return String.format(BASE_TO_STRING, status, token, remaining_guesses, state);
    }

    enum HumanState {ALIVE, DEAD, FREE}
}
