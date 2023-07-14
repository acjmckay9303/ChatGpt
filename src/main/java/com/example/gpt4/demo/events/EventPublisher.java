package com.example.gpt4.demo.events;

import com.example.gpt4.demo.frontend.entity.Conversation;
import com.example.gpt4.demo.frontend.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class EventPublisher {
    
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    
    public void publishUserMessageEvent(
        final Message message,
        final
        Conversation conversation) {
        
        System.out.println("Publishing custom event. ");
        UserMessageEvent customSpringEvent = new UserMessageEvent(this, message, conversation);
        applicationEventPublisher.publishEvent(customSpringEvent);
    }
}
