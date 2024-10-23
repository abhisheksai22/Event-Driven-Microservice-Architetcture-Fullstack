package com.abhi.microservices.inventory.service;

import com.abhi.microservices.inventory.dto.InventoryRequest;
import com.abhi.microservices.inventory.model.Inventory;
import com.abhi.microservices.inventory.repo.InventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public Boolean getNonExistingSkewCodes(String skuCode) {
        boolean present = inventoryRepository.findBySkuCode(skuCode).isPresent();
        if (present) {
            log.info("skew code is Present");
        } else {
            log.info("skew code is not Present");
        }

        return present;
    }


    public Inventory createInventory(InventoryRequest inventoryRequest) {
        Inventory inventory = new Inventory();

        return inventoryRepository.save(inventory.builder()
                .quantity(inventoryRequest.getQuantity())
                .skuCode(inventoryRequest.getSkuCode())
                .build());
    }
}
