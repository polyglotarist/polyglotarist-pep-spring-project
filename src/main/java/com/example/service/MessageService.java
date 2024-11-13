package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    @Autowired
    MessageRepository messageRepository;

    // 3: Our API should be able to process the creation of new messages.
    public Message createMessage(Message message){
        return messageRepository.save(message);
    }

    // 4: Our API should be able to retrieve all messages.
    public List<Message> retrieveAllMessages(){
        return messageRepository.findAll();
    }

    // 5: Our API should be able to retrieve a message by its ID.
    public Message findById(int id){
        return messageRepository.findById(id);
    }

    // 6: Our API should be able to delete a message identified by a message ID.
    public int deleteByMessageId(int id){
         return messageRepository.deleteByMessageId(id);
    }

    // 7: Our API should be able to update a message text identified by a message ID.
    public int update(int messageId, String messageText){
        return messageRepository.update(messageId, messageText);
    }

    // 8: Our API should be able to retrieve all messages written by a particular user.
    public List<Message> findAllMessagesByAccountId(int id){
        return messageRepository.findAllMessagesByAccountId(id);
    }

}
