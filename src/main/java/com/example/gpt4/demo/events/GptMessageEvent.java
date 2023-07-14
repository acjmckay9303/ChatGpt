package com.example.gpt4.demo.events;

import com.example.gpt4.demo.frontend.entity.Conversation;
import com.example.gpt4.demo.frontend.entity.Message;
import lombok.Data;
import org.springframework.context.ApplicationEvent;

@Data
public class GptMessageEvent extends ApplicationEvent {
    
    private Message message;
    
    private Conversation conversation;
    
    public GptMessageEvent(
        Object source,
        Conversation conversation) {
        
        super(source);
        this.message = message;
        this.conversation = conversation;
    }
}
