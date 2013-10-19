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
        GameState temp = gson.fromJson(JSON, GameState.class);
        temp.setState(temp.getState().toLowerCase());
        return temp;
    }

    public HumanState getStatus() {
        return status;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getToken() {
        return token;
    }

    public String toString() {
        return String.format(BASE_TO_STRING, status, token, remaining_guesses, state);
    }

    enum HumanState {ALIVE, DEAD, FREE}
}
