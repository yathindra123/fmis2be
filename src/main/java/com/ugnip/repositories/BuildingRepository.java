package com.ugnip.repositories;

import com.ugnip.models.building.Building;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BuildingRepository extends MongoRepository<Building, String> {

    public Building findByFloorId(String floorId);
    public Building findByRoomId(String roomId);
    public List<Building> findAll();
    Building findOne(String id);
    public void deleteByRoomId(String id);

}
