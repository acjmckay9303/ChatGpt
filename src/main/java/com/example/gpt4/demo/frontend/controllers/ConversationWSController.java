package com.example.gpt4.demo.frontend.controllers;

import com.example.gpt4.demo.events.EventPublisher;
import com.example.gpt4.demo.events.GptMessageEvent;
import com.example.gpt4.demo.frontend.entity.Message;
import com.example.gpt4.demo.frontend.repositories.repositories.ConversationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
public class ConversationWSController {
    
    private final ConversationRepository conversationRepository;
    private final SimpMessagingTemplate template;
    
    private final EventPublisher eventPublisher;
    
    @MessageMapping("/chat/user/{conversationId}")
    @SendTo("/topic/chat/user/{conversationId}")
    public Mono<Message> sendMessage(
        @DestinationVariable String conversationId,
        Message message) {
        
        return conversationRepository.findById(conversationId)
                                     .doOnNext(conversation -> eventPublisher.publishUserMessageEvent(message,
                                         conversation))
                                     .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                                         "Conversation not found")))
                                     .thenReturn(message);
    }
    
    @EventListener
    public void onGptMessageEvent(GptMessageEvent event) {
        
        this.template.convertAndSend("/topic/chat/gpt/" + event.getConversation().getId(), event.getMessage());
    }
}
