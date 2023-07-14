package com.example.gpt4.demo.events;

import com.example.gpt4.demo.frontend.entity.Message;
import com.example.gpt4.demo.frontend.repositories.repositories.ConversationRepository;
import lombok.RequiredArgsConstructor;
import org.mvnsearch.chatgpt.model.ChatCompletionRequest;
import org.mvnsearch.chatgpt.spring.service.ChatGPTService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class UserMessageEventListener implements ApplicationListener<UserMessageEvent> {
    
    private final ChatGPTService chatGPTService;
    private final ConversationRepository conversationRepository;
    private final ApplicationEventPublisher eventPublisher;
    
    @Override
    public void onApplicationEvent(UserMessageEvent event) {
        
        ChatCompletionRequest request = new ChatCompletionRequest();
        
        event.getConversation().addMessage(event.getMessage());
        request.setMessages(event.getConversation().getChatMessages());
        chatGPTService.chat(request)
                      .map(response -> {
                          Message gptMessage = new Message(response);
                          event.getConversation().addMessage(gptMessage);
                          conversationRepository.save(event.getConversation());
                          return gptMessage;
                      })
                      .subscribe(gptMessage -> eventPublisher.publishEvent(new GptMessageEvent(gptMessage,
                          event.getConversation())));
    }
}
