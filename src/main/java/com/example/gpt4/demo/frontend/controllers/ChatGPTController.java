package com.example.gpt4.demo.frontend.controllers;

import com.example.gpt4.demo.frontend.repositories.repositories.ConversationRepository;
import lombok.extern.slf4j.Slf4j;
import org.mvnsearch.chatgpt.model.ChatCompletionRequest;
import org.mvnsearch.chatgpt.model.ChatCompletionResponse;
import org.mvnsearch.chatgpt.spring.service.ChatGPTService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/secure/gpt")
@Slf4j
public class ChatGPTController {
    
    private final ChatGPTService chatGPTService;
    
    private final ConversationRepository conversationRepository;
    
    public ChatGPTController(
        ChatGPTService chatGPTService,
        ConversationRepository conversationRepository) {
        
        this.chatGPTService = chatGPTService;
        this.conversationRepository = conversationRepository;
    }

//    @PostMapping("/chat")
//    public ResponseEntity<String> chat(
//        @RequestParam("conversationId") String conversationId,
//        @RequestBody List<ChatMessage> messages) {
//
//        ChatCompletionRequest request = new ChatCompletionRequest();
//        request.setMessages(messages);
//
//        log.info("We are in the chat method");
//        Optional<Conversation> conversationOptional = conversationRepository.findById(conversationId);
//
//        if (conversationOptional.isPresent()) {
//            Conversation conversation = conversationOptional.get();
//
//            ChatMessage lastMessage = messages.get(messages.size() - 1);
//            Message userMessage = new Message(lastMessage);
//            conversation.addMessage(userMessage);
//
//            log.info("We are returning the chat method");
//            Mono<String> responseMono = chatGPTService.chat(request)
//                                                      .map(response -> {
//                                                          Message gptMessage = new Message(response);
//                                                          conversation.addMessage(gptMessage);
//                                                          conversationRepository.save(conversation);
//                                                          return response.getReplyText();
//                                                      });
//
//            String responseText = responseMono.block(); // Block and wait for the completion of the authentication
//            // process
//
//            return ResponseEntity.ok(responseText);
//        } else {
//            return ResponseEntity.ok().body("Conversation not found");
//        }
//    }
    
    @GetMapping("/stream-chat")
    public Flux<String> streamChat(@RequestParam String content) {
        
        return chatGPTService.stream(ChatCompletionRequest.of(content))
                             .map(ChatCompletionResponse::getReplyText);
    }
}
