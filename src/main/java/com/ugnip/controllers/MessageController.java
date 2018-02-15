package com.ugnip.controllers;

import com.ugnip.models.message.MaintenanceMessage;
import com.ugnip.models.message.MoveMessage;
import com.ugnip.repositories.MaintenanceMessageRepository;
import com.ugnip.repositories.MoveMessageRepository;
import com.ugnip.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/messages")
@CrossOrigin("*")
public class MessageController {

    @Autowired
    private MoveMessageRepository moveMessageRepository;

    @Autowired
    private MaintenanceMessageRepository maintenanceMessageRepository;

    @Autowired
    private ItemService itemService;


    //View all move requests
    @GetMapping("/move")
    public List<MoveMessage> viewAllMoveRequests(){
        Sort sortReqByDescOrder = new Sort(Sort.Direction.DESC, "createdAt");
        return moveMessageRepository.findAll(sortReqByDescOrder);
    }

    //View all move requests
    @GetMapping("/move/sender/{sender}")
    public List<MoveMessage> viewMoveRequests(@PathVariable("sender") String sender){
        return moveMessageRepository.findAllBySender(sender);
    }

    //View all move requests
    @GetMapping("/maintenance/sender/{sender}")
    public List<MaintenanceMessage> viewMaintenanceRequests(@PathVariable("sender") String sender){
        return maintenanceMessageRepository.findAllBySender(sender);
    }

    //View all maintenance requests
    @GetMapping("/maintenance")
    public List<MaintenanceMessage> viewAllMaintenanceRequests(){
        Sort sortReqByDescOrder = new Sort(Sort.Direction.DESC, "createdAt");
        return maintenanceMessageRepository.findAll(sortReqByDescOrder);
    }

    //Create a new move request
    @PostMapping("/move")
    public MoveMessage createMoveRequest(@Valid @RequestBody MoveMessage message){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        message.setCreatedAt(dateFormat.format(date));
        return moveMessageRepository.save(message);
    }

    //Create a new maintenance request
    @PostMapping("/maintenance")
    public MaintenanceMessage createMaintenanceRequest(@Valid @RequestBody MaintenanceMessage message){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        message.setCreatedAt(dateFormat.format(date));
        return maintenanceMessageRepository.save(message);
    }

    //Update move message
    @PutMapping(value="/move/{messageId}")
    public ResponseEntity<MoveMessage> updateMsg(@PathVariable("messageId") String messageId,
                                           @Valid @RequestBody MoveMessage message) {
        MoveMessage moveMessage = moveMessageRepository.findByMessageId(messageId);

        //If item not found return not found
        if(moveMessage == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        //If item found update it
        moveMessage.setMessageId(message.getMessageId());
        moveMessage.setRoomId(message.getRoomId());
        moveMessage.setBarcode(message.getBarcode());
        moveMessage.setMessage(message.getMessage());
        moveMessage.setPrice(message.getPrice());
        moveMessage.setStatus(true);
        MoveMessage updatedItem = moveMessageRepository.save(moveMessage);

        itemService.updatePlaceOfItem(message.getBarcode(), message.getRoomId());

        return new ResponseEntity<>(updatedItem, HttpStatus.OK);
    }

    //Approve maintenance request
    @PutMapping(value="/maintenance/{messageId}")
    public ResponseEntity<MaintenanceMessage> updateMaintenanceMsg(@PathVariable("messageId") String messageId,
                                                 @Valid @RequestBody MaintenanceMessage message) {
        MaintenanceMessage messageData = maintenanceMessageRepository.findByMessageId(messageId);

        //If message not found return not found
        if(messageData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        //If message found update it
        messageData.setMessageId(message.getMessageId());
        messageData.setBarcode(message.getBarcode());
        messageData.setMessage(message.getMessage());
        messageData.setPrice(message.getPrice());
        messageData.setStatus(true);
        MaintenanceMessage updatedItem = maintenanceMessageRepository.save(messageData);

        return new ResponseEntity<>(updatedItem, HttpStatus.OK);
    }

    // Delete maintenance request
    @DeleteMapping(value="/maintenance/{id}")
    public void deleteMaintenanceMsg(@PathVariable("id") String id) {
        maintenanceMessageRepository.deleteByMessageId(id);
    }

    // Delete relocation request
    @DeleteMapping(value="/move/{id}")
    public void deleteMoveMsg(@PathVariable("id") String id) {
        moveMessageRepository.deleteByMessageId(id);
    }

}
