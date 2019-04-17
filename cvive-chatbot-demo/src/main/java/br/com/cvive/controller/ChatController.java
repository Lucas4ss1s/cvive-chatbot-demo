package br.com.cvive.controller;

import br.com.cvive.chatbot.ChatBot;
import br.com.cvive.model.ChatMessage;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

/**
 * Created by rajeevkumarsingh on 24/07/17.
 */
@Controller
public class ChatController {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
    	
        return chatMessage;
    }
    
    @MessageMapping("/chat.sendAnswer")
    @SendTo("/topic/public")
    public ChatMessage sendAnswer(@Payload ChatMessage chatMessage) {
    	
    	ChatBot chatBot = new ChatBot();
		String retorno = chatBot.callWatson(chatMessage.getContent());
		chatMessage.setType(chatMessage.getType().CHAT);
		chatMessage.setSender("C-vive");
		chatMessage.setContent(retorno);
		
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

}
