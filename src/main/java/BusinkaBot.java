/**
 * Created by A. Menchuk
 */

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class BusinkaBot extends TelegramLongPollingBot {

    private String BOT_USER_NAME = "@businka_support_bot";
    private String BOT_TOKEN = "491975755:AAHiSjlnOCXXXs6y8VcP3E9fyCK03lU5gd8";
    public int msgType = 0; // 1- low cr; 2 - doesn't work offers

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
            if(message.getText().contains("Low CR!")){
                //serviceMessageInBot(message, offersSelections.getReadyMessage(message.getText()));
                msgType = 1;
                sendMsg(message, "Please, enter offers numbers:");
             }
            if(message.getText().contains("Doesn't work offers!") ){
                msgType = 2;
                sendMsg(message, "Please, enter offers numbers:");
            }
            else if(message.getText().equals("Help!")){ // if received '/help'
                serviceMessageInBot(message, getHelp()); //send message with data from getHelp()
            }
            else if(message.getText().equals("/.")){ //118690024
                getChatID("118690024", message, "");
            }
            else if(message.getText().equals("/clear")){ //118690024
                sendMsg(message,"Bot was updated!\nChoose an option from menu!");
            }

            if(message.getText().contains("Low CR!") == false && msgType == 1){
                sendMsg(message, offersSelections.getReadyMessage(message.getText()));
            }

        }
    }


    public void sendMsg (Message message, String text) {
        OffersSelections offersSelections = new OffersSelections();

        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(false);

        // Создаем клавиуатуру
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(false);
        replyKeyboardMarkup.setResizeKeyboard(false);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow(); // Первая строчка клавиатуры
        keyboardFirstRow.add("Low CR!"); // Добавляем кнопки в первую строчку клавиатуры

        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add("Doesn't work offers!");

        KeyboardRow keyboardThirdRow = new KeyboardRow();
        keyboardThirdRow.add("Help!");

        // Добавляем все строчки клавиатуры в список
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        keyboard.add(keyboardThirdRow);

        // и устанваливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);

        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
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
