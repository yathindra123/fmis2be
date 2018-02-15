package com.ugnip.services;

import com.ugnip.models.item.Item;
import com.ugnip.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    @Autowired
    ItemRepository itemRepository;

    //Update an place of an item
    public void updatePlaceOfItem(String barcode, String roomId) {
        Item itemData = itemRepository.findByBarcode(barcode);
        itemData.setRoom(roomId);
        Item updatedItem = itemRepository.save(itemData);
    }
}
