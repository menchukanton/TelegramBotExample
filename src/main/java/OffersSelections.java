import java.util.Random;

/**
 * Created by patron on 11/24/2017.
 */
public class OffersSelections {

   private String randomGodDay[] = {"Доброе утро!", "Доброго утречка!", "Добрый день"};
   private String randomNiceDay[] = {"Хорошего дня =)", "Хорошего дня!", "Удачного дня!", "Хорошего настроения)"};
   private String commands[] = {"/lowcr","/notwork"};
   private String receivedCommand;

    public String getReadyMessage(String dirtyMessage){
        Random random = new Random();
        return
                randomGodDay[random.nextInt(randomGodDay.length)] + "\n" +
                getReceivedCommand(dirtyMessage)+ "\n" +
                getMessageWithoutCommand(receivedCommand, dirtyMessage) + "\n" +
                randomNiceDay[random.nextInt(randomNiceDay.length)];
    }

    public String[] getCommands(String commands[]){
        return this.commands = commands;
    }

    private String getReceivedCommand(String message){
       String finalMessage = "WooPs...";
        if(message.contains("/lowcr")){
            receivedCommand = commands[0];
            finalMessage = "Выборка офферов с низким CR:";
        }
        else if(message.contains("/notwork")){
            receivedCommand = commands[1];
            finalMessage = "Выборка предположительно не рабочих офферов:";

        }
        return finalMessage;
    }

    private String getMessageWithoutCommand(String command, String message){
        message = message.replaceAll(command + " (.+)","$1");
        return message;
    }


}
