package ua.pp.lazin.webbroker;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class ChatRoomController {

    @MessageMapping("/guestroom")
    public String doBroadcast(String input) throws IOException {
        System.out.println(input);
        ObjectMapper mapper = new ObjectMapper();
        InputMessage inputMessage = mapper.readValue(input, InputMessage.class);
        BroadcastMessage broadcastMessage = new BroadcastMessage();
        if (inputMessage.getMessage().length() == 0) {
            broadcastMessage.setBroadcastMessage("" + (inputMessage.getSender() +
                    " came in. Welcome " + inputMessage.getSender()));
        } else {
            broadcastMessage.setBroadcastMessage("" + (inputMessage.getSender() + " says: " +
                    inputMessage.getMessage()));
        }
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(broadcastMessage);
    }

    @MessageMapping("/login")
    @SendTo("/topic/guestroom")
    public String doLogin(String input) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        InputMessage inputMessage = mapper.readValue(input, InputMessage.class);
        BroadcastMessage broadcastMessage = new BroadcastMessage();
            broadcastMessage.setBroadcastMessage("" + (inputMessage.getSender() +
                    " came in. Welcome " + inputMessage.getSender()));
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(broadcastMessage);
    }

    @MessageMapping("/logout")
    @SendTo("/topic/guestroom")
    public String doLogout(String input) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        InputMessage inputMessage = mapper.readValue(input, InputMessage.class);
        BroadcastMessage broadcastMessage = new BroadcastMessage();
            broadcastMessage.setBroadcastMessage("" + (inputMessage.getSender() +
                    " has left us. We'll miss you " + inputMessage.getSender()));
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(broadcastMessage);
    }
}
