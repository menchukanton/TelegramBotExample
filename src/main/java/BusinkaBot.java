/**
 * Created by A. Menchuk
 */

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class BusinkaBot extends TelegramLongPollingBot {

    private String BOT_USER_NAME = "{name_of_your_bot_registered_in_telegram}";
    private String BOT_TOKEN = "{token_of_yor_bot}";

    /**
     * Make smth when got update in bot.
     *
     * @param update
     */
    @Override
    public void onUpdateReceived(Update update) {
        OffersSelections offersSelections = new OffersSelections();
        Message message = update.getMessage();
        String command = "";

        if(message != null && message.hasText()){

            if(message.getText().contains("/lowcr") || message.getText().contains("/notwork") ){
                serviceMessageInBot(message, offersSelections.getReadyMessage(message.getText()));
            }
            else if(message.getText().equals("/help")){ // if received '/help'
                serviceMessageInBot(message, getHelp()); //send message with data from getHelp()
            }
            else if(message.getText().equals("/.")){ //118690024
                getChatID("118690024", message, "");
            }
        }
    }

    /**
     * Servise method. use for got chat ID
     * @param chatID
     * @param message
     * @param s
     */
    private void getChatID(String chatID,Message message, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatID);
        sendMessage.setReplyToMessageId(message.getMessageId()); //Reply на сообщение
        sendMessage.setText(s + "Chat ID: " + message.getChatId().toString());
        try{
            sendMessage(sendMessage);
        }catch (TelegramApiException e){ e.printStackTrace();}
    }
    /**
     * Use for sending message to the group
     * @param chatID
     * @param message
     * @param s
     */
    private void serviceMessageToGroup(String chatID,Message message, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatID);
        //sendMessage.setReplyToMessageId(message.getMessageId()); //Reply на сообщение
        sendMessage.setText
                (s + "\nSend by: " +
                message.getFrom().toString().replaceAll(".+firstName='(.+)'.+lastName='(.+)'.+userName='(.+)',.+", "$1 $2\nusername: @$3")
                );
        try{
            sendMessage(sendMessage);
        }catch (TelegramApiException e){ e.printStackTrace();}
    }

    /**
     * Use for send message inside the bot.
     * @param message
     * @param s
     */
    private void serviceMessageInBot(Message message, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(s);
        try{
            sendMessage(sendMessage);
        }catch (TelegramApiException e){ e.printStackTrace();}
    }

    /**
     * Use for gotten message on the command "/help"
     * @return
     */
    private String getHelp(){
        String finalMessage =
                "**Список команд:**\n"+
                        "Получить сообщение с листом офферов с низким CR: /lowcr {list of offers}\n" +
                        "Получить сообщение со списком предположительно не рабочих офферов: /notwork {list of offers}\n" +
                        "" +
                        "Получить справку: /help";
        return finalMessage;
    }

    @Override
    public String getBotUsername() {
        // TODO
        return BOT_USER_NAME;
    }
    @Override
    public String getBotToken() {
        // TODO
        return BOT_TOKEN;
    }
}
