package WebConnector;

import HangmanAI.GameState;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * User: AnubhawArya
 * Date: 10/19/13
 * Time: 11:32 AM
 */
public class WebConnector {
    String BASE_URL = "http://gallows.hulu.com/play?code=%s";
    String BASE_PARAMS = "&token=%s&guess=%s";

    public WebConnector(String email) {
        BASE_URL = String.format(BASE_URL, email);
    }

    public GameState sendGet(GameState gameState, String guess) {
        try {
            String url = getURL(gameState, guess);
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null)
                response.append(inputLine);
            in.close();
            return GameState.JSONToGameStart(response.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private String getURL(GameState gameState, String guess) {
        String url;
        if (gameState == null || guess == null)
            url = BASE_URL;
        else {
            url = BASE_URL + BASE_PARAMS;
            url = String.format(url, gameState.getToken(), guess);
        }
        return url;
    }
}
