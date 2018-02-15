package com.ugnip.repositories;

import com.ugnip.models.item.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ItemRepository extends MongoRepository<Item, String> {
    public List<Item> findAll();

    public Item findByBarcode(String barcode);

    public void deleteByBarcode(String barcode);
}
