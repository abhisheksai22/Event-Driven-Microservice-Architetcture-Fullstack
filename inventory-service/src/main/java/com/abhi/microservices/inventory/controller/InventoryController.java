package com.abhi.microservices.inventory.controller;

import com.abhi.microservices.inventory.dto.InventoryRequest;
import com.abhi.microservices.inventory.model.Inventory;
import com.abhi.microservices.inventory.repo.InventoryRepository;
import com.abhi.microservices.inventory.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@Slf4j
@CrossOrigin("*")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private InventoryRepository inventoryRepository;

    @GetMapping
    public Boolean isInStock(@RequestParam("skuCode") String skuCode) {
        log.info("SkuCode : {}", skuCode);
        return inventoryService.getNonExistingSkewCodes(skuCode);
    }

    @PostMapping
    public ResponseEntity<Inventory> createInventory(@RequestBody InventoryRequest inventoryRequest){
        return new ResponseEntity<>(inventoryService.createInventory(inventoryRequest), HttpStatus.OK);
    }


    @GetMapping("/getAllInventory")
    public ResponseEntity<List<Inventory>> getAllInventory(){
        return new ResponseEntity<>(inventoryRepository.findAll(), HttpStatus.OK);
    }
}
