package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@Controller
public class SocialMediaController {

    @Autowired
    AccountService accountService;
    @Autowired
    MessageService messageService;

 //1
    @PostMapping("/register")
    public ResponseEntity<Account> registerAccount(@RequestBody Account account){
        //check for duplicate username
        if(accountService.existsByUsername(account.getUsername())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "username already taken");
        }
        //check password is >= 4 and username not blank:
        if(account.getPassword().length() >= 4 && account.getUsername().length() > 0){
            accountService.registerUser(account);
            return ResponseEntity.ok(account);
        }
        return null;
    }
//2
    @PostMapping("/login")
    public ResponseEntity<Account> loginUser(@RequestBody Account account){
        Account persistedAccount = accountService.login(account.getUsername(), account.getPassword());
        if( persistedAccount != null){
            return ResponseEntity.ok(persistedAccount);
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
//3
    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message){
        //check user id is valid
        //check messageText is valid
        Optional<Account> optionalAccount = Optional.ofNullable(accountService.findById(message.getPostedBy()));
        if(!optionalAccount.isPresent()){
            return ResponseEntity.status(400).build();
        }
        if(message.getMessageText().length() > 0 && message.getMessageText().length() <= 255){
            Message persistedMessage = messageService.createMessage(message);
            if(persistedMessage != null){
                return ResponseEntity.ok(persistedMessage);
            }
        }
        return ResponseEntity.status(400).body(null);
    }
//4
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> retrieveAllMessages(){
        List<Message> messagesList = messageService.retrieveAllMessages();
        return ResponseEntity.ok(messagesList);
    }
//5
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> findById(@PathVariable int messageId) {
        Optional<Message> optionalMessage = Optional.ofNullable(messageService.findById(messageId));
        if(optionalMessage.isPresent()){
            return ResponseEntity.ok(optionalMessage.get());
        }
        return ResponseEntity.status(200).body(null);

    }
    
//6
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable int messageId){
        int numRowsAffected = messageService.deleteByMessageId(messageId);
        if(numRowsAffected == 1){
            return ResponseEntity.ok(numRowsAffected);
        }
        return ResponseEntity.status(200).body(null);
    }

//7
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<?> updateMessageById(@RequestBody Message message, @PathVariable Integer messageId){

        String messageText = message.getMessageText();

        if(messageId != null && messageText != null && messageText.length() > 0 && messageText.length() <= 255){
            
            int numRowsAffected = messageService.update(messageId, messageText);
            if(numRowsAffected == 1){
                return ResponseEntity.status(200).body(numRowsAffected);
            }
        }
        return ResponseEntity.status(400).build();
    }
//8
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> findAllMessagesByAccountId(@PathVariable int accountId){
        List<Message> messagesList =  messageService.findAllMessagesByAccountId(accountId);
        return ResponseEntity.ok(messagesList);
    }

}
