package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Integer> {

    //custom query that deletes a message by its id and returns the number of rows affected
    @Modifying
    @Transactional
    @Query("DELETE FROM Message m WHERE m.messageId = :messageId")
    int deleteByMessageId(int messageId);    

    //custom query that updates a message text given message id
    @Modifying
    @Transactional
    @Query("UPDATE Message m SET m.messageText = :messageText WHERE m.messageId = :messageId")
    int update(int messageId, String messageText);

    // Custom query that finds all messages by a specific user
    @Query("SELECT m FROM Message m WHERE m.postedBy = :accountId")
    List<Message> findAllMessagesByAccountId(@Param("accountId") int accountId);

    //custom query to get message by id
    @Query("select m from Message m where m.messageId = :messageId")
    public Message findById(@Param("messageId") int messageId);

    

}
