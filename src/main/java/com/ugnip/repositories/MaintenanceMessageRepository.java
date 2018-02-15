package com.ugnip.repositories;

import com.ugnip.models.message.MaintenanceMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MaintenanceMessageRepository extends MongoRepository<MaintenanceMessage, String> {
    public List<MaintenanceMessage> findAll();
    public MaintenanceMessage findByMessageId(String messageId);
    public void deleteByMessageId(String messageId);
    public List<MaintenanceMessage> findAllBySender(String sender);
}
