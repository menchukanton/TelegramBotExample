import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class Main {

    public static void main(String[] args) {

        ApiContextInitializer.init(); // TODO Initialize Api Context

        TelegramBotsApi botsApi = new TelegramBotsApi(); // TODO Instantiate Telegram Bots API

        // TODO Register our bot
        try {
            botsApi.registerBot(new BusinkaBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
