package com.ugnip.repositories;

import com.ugnip.models.message.MoveMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MoveMessageRepository extends MongoRepository<MoveMessage, String> {
    public List<MoveMessage> findAll();
    public MoveMessage findByMessageId(String messageId);
    public List<MoveMessage> findAllBySender(String sender);
    public void deleteByMessageId(String messageId);
}
