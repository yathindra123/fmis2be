package com.ugnip.controllers;

import com.ugnip.models.building.Building;
import com.ugnip.repositories.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/buildings")
@CrossOrigin("*")
public class BuildingController {

    @Autowired
    private BuildingRepository buildingRepository;

    //View all buildings
    @GetMapping
    public List<Building> viewAllBuildings(){
        Sort sortBuildingsByDescOrder = new Sort(Sort.Direction.DESC, "createdAt");
        return buildingRepository.findAll(sortBuildingsByDescOrder);
    }

    //Create a new room with floor and building
    @PostMapping
    public Building createBuilding(@Valid @RequestBody Building building){
        return buildingRepository.save(building);
    }

    //Find a room  with floor and building
    @GetMapping(value="/{barcode}")
    public ResponseEntity<Building> findBuilding(@PathVariable("floorId") String floorId) {
        Building building = buildingRepository.findByFloorId(floorId);

        if(building == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(building, HttpStatus.OK);
        }
    }

    //Update building details with floor with room id
    @PutMapping(value="/{roomId}")
    public ResponseEntity<Building> updateBuilding(@PathVariable("roomId") String roomId,
                                           @Valid @RequestBody Building building) {
        Building buildingData = buildingRepository.findByRoomId(roomId);

        //If floor not found return not found
        if(buildingData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        //If room found update it
        buildingData.setRoomId(building.getRoomId());
        buildingData.setFloorId(building.getFloorId());
        buildingData.setSpaceType(building.getSpaceType());
        buildingData.setSpaceCode(building.getSpaceCode());
        buildingData.setDepartment(building.getDepartment());
        buildingData.setOccupant(building.getOccupant());
        buildingData.setGfa(building.getGfa());
        buildingData.setUfa(building.getUfa());
        Building updatedBuilding = buildingRepository.save(buildingData);

        return new ResponseEntity<>(updatedBuilding, HttpStatus.OK);
    }

    // delete the floor using the floor id
    @DeleteMapping(value="/{floorId}")
    public void deleteTodo(@PathVariable("floorId") String floorId) {
        buildingRepository.deleteByRoomId(floorId);
    }

}
